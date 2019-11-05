package com.petshop.enterpraise.controller;

import com.petshop.enterpraise.model.Unicorn;
import com.petshop.enterpraise.payload.UploadUnicornResponse;
import com.petshop.enterpraise.repository.UnicornRepository;
import com.petshop.enterpraise.service.UnicornService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
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
@RestController //обрабатывает класс, содержит методы, которые будут обрабатывать HTTP-запросы
@RequestMapping("/api/unicorn")

public class UnicornController {

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
        ZeroCopyHttpOutputMessage zeroMessage = (ZeroCopyHttpOutputMessage) response;
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=uploads/" + fileName);
        File file = Paths.get("uploads/"+fileName).toFile();
        response.getHeaders().setContentType(MediaType.parseMediaType("application/octet-stream"));
        return zeroMessage.writeWith(file, 0 , file.length());
    }

    @Autowired
    private UnicornService unicornService;

    @PostMapping("/uploadFile")
    public UploadUnicornResponse uploadFile(@RequestParam("image") MultipartFile unicornFile) {
        String unicornFileName = unicornService.storeFile(unicornFile);
        String unicornFileDownloadUri = FileSystems.getDefault().getPath("").toUri().toString();
//                .path("/downloadFile/")
//                .path(unicornFileName)
//                .toUriString();
        return new UploadUnicornResponse(unicornFileName, unicornFileDownloadUri, unicornFile.getContentType(), unicornFile.getSize());
    }

    @Autowired //Spring реализует интерфейс и даст нам на него ссылку
    private UnicornRepository repository;
    @GetMapping
        public Flux<Unicorn> getAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
        public Mono<Unicorn> getById(@PathVariable String id) {
        return repository.findById(id);
    }

    @PostMapping
//    {
//        "id": "1",
//            "name": "Rainbow",
//            "magicSkills": "Flash"
//    }
//    Данные приходят в формате JSON, а их с помощью аннотации @RequestBody
    public Mono<Unicorn> create(@RequestBody Unicorn unicorn) { //@RequestBody - принимаем тело запроса и интерпретируем его как экземпляр класса Unicorn
        return repository.save(unicorn);
    }

    @PutMapping("/{id}")
    public Mono<Unicorn> updateUnicorn(@RequestBody Unicorn newUnicorn, @PathVariable String id) {
        return repository.findById(id).flatMap(oldUnicorn -> {
            oldUnicorn.setName(newUnicorn.getName());
            oldUnicorn.setMagicSkills(newUnicorn.getMagicSkills());
            return repository.save(oldUnicorn);
        });
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUnicorn(@PathVariable String id) {
        return repository.deleteById(id);
    }
}
