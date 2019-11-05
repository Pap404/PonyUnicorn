package com.petshop.enterpraise.controller;

import com.petshop.enterpraise.model.Pony;
import com.petshop.enterpraise.payload.UploadPonyResponse;
import com.petshop.enterpraise.repository.PonyRepository;
import com.petshop.enterpraise.service.PonyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.*;

@CrossOrigin
@RestController
@RequestMapping("/api/pony")

public class PonyController {

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> uploadPicture(@RequestPart("image") FilePart filePart) throws IOException {
        System.out.println(filePart.filename());
        Path tempFile = Files.createTempFile("text", filePart.filename());
        Path path = Files.createFile(Paths.get("uploads", filePart.filename()));
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        DataBufferUtils.write(filePart.content(), channel, 0)
                .doOnComplete(() -> {
                    System.out.println("FINISH");
                })
                .subscribe();
        System.out.println(path.toString());
        return Mono.just(filePart.filename());
    }

    @GetMapping("/download/{fileName:.+}")
    public Mono<Void> downloadPicture(@PathVariable String fileName,  ServerHttpResponse response) throws IOException{
        ZeroCopyHttpOutputMessage zeroCopyMessage = (ZeroCopyHttpOutputMessage) response;
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=uploads/" + fileName);
        File file = Paths.get("uploads/"+fileName).toFile();
        response.getHeaders().setContentType(MediaType.parseMediaType("application/octet-stream"));
        return zeroCopyMessage.writeWith(file, 0 , file.length());
    }

    @Autowired
    private PonyService ponyService;

    @PostMapping("/uploadFile")
    public UploadPonyResponse uploadPonyFile(@RequestParam("image") MultipartFile ponyFile) {
        String ponyFileName = ponyService.storeFile(ponyFile);

        String ponyFileDownloadUri = FileSystems.getDefault().getPath("").toUri().toString();
//                .path("/downloadPonyFile/")
//                .path(ponyFileName)
//                .toUriString();

        return new UploadPonyResponse(ponyFileName, ponyFileDownloadUri,
                ponyFile.getContentType(), ponyFile.getSize());
    }

    @Autowired
    private PonyRepository ponyRepository;

    @GetMapping
    public Flux<Pony> getAll() {
        return ponyRepository.findAll();
    }

    @GetMapping("/{ponyId}")
    public Mono<Pony> getPonyById(@PathVariable String ponyId){
        return ponyRepository.findById(ponyId);
    }

    @PostMapping
    public Mono<Pony> createPony(@RequestBody Pony pony){
        return ponyRepository.save(pony);
    }

    @PutMapping("/{ponyId}")
    public Mono<Pony> updatePony(@RequestBody Pony newPony, @PathVariable String ponyId) {
        return ponyRepository.findById(ponyId).flatMap(oldPony -> {
         oldPony.setName(newPony.getName());
         oldPony.setLear(newPony.getLear());
         oldPony.setOld(newPony.getOld());
        return ponyRepository.save(oldPony);
        });
    }

    @DeleteMapping("/{ponyId}")
    public Mono<Void> deletePony(@PathVariable String ponyId) {
        return ponyRepository.deleteById(ponyId);
    }
}
