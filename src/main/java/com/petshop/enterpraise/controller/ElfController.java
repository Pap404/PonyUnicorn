package com.petshop.enterpraise.controller;
import com.petshop.enterpraise.model.Elf;
import com.petshop.enterpraise.repository.ElfRepository;
import com.petshop.enterpraise.service.ElfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.*;

@CrossOrigin
@RestController
@RequestMapping("/api/elf")

public class ElfController {

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> uploadPicture(@RequestPart("image") FilePart filePart) throws IOException {
        System.out.println(filePart.filename());
        Path tempFile = Files.createTempFile("test", filePart.filename());
        Path path = Files.createFile(Paths.get("uploads", filePart.filename()));
        AsynchronousFileChannel channel =
                AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
//        ByteBuffer buffer1 = ByteBuffer.allocate(1024);
//    long position1 = 0;
//
//buffer1.put(data);
//buffer1.flip();
//
//    Future<Integer> operation1 = fileChannel1.write(buffer1, position1);
//buffer1.clear();
        DataBufferUtils.write(filePart.content(), channel, 0)
                .doOnComplete(() -> {
                    System.out.println("finish");
                })
                .subscribe();
        System.out.println(path.toString());
        return Mono.just(filePart.filename());
    }

    @GetMapping("/download/{fileName:.+}")
    public Mono<Void> downloadByWriteWith(@PathVariable String fileName, ServerHttpResponse response) throws IOException {
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=uploads/" + fileName);
        File file = Paths.get("uploads/"+fileName).toFile();
        response.getHeaders().setContentType(MediaType.parseMediaType("application/octet-stream"));
        return zeroCopyResponse.writeWith(file, 0, file.length());
//        File file = resource.getFile();
//        return zeroCopyResponse.writeWith(resource,  0, file.length());
    }
//    @PostMapping("/uploadFile")
//    public UploadElfResponse uploadFile(@RequestPart("image") Mono<FilePart> elfFile) {
////        elfFile.flatMap(f -> elfService.storeFile(f));
////        String elfFileName = elfService.storeFile(elfFile);
//        String elfFileDownloadUri = FileSystems.getDefault().getPath("").toUri().toString();
////                .getPath("/downloadFile/")
////                .getPath(elfFileName)
////                .toUriString();
//        return new UploadElfResponse("elfFileName", elfFileDownloadUri, "elfFile.getContentType()", 1L);
//    }

    @Autowired
    private ElfService elfService;

    @Autowired
    private ElfRepository elfRepository;

    @GetMapping
    public Flux<Elf> getAll() {
        return elfRepository.findAll();
    }

    @GetMapping("/{elfId}")
    public Mono<Elf> getElfById(@PathVariable String elfId){
        return elfRepository.findById(elfId);
    }

    @PostMapping
    public Mono<Elf> createElf(@RequestBody Elf elf){
        return elfRepository.save(elf);
    }

    @PutMapping("/{elfId}")
    public Mono<Elf> updateElf(@RequestBody Elf newElf, @PathVariable String elfId) {
        return elfRepository.findById(elfId).flatMap(oldElf -> {
            oldElf.setName(newElf.getName());
            oldElf.setRace(newElf.getRace());
            oldElf.setElfClass(newElf.getElfClass());
            return elfRepository.save(oldElf);
        });
    }

    @DeleteMapping("/{elfId}")
    public Mono<Void> deleteElf(@PathVariable String elfId) {
        return elfRepository.deleteById(elfId);
    }
}