package com.image.Image.service;

import com.image.Image.entity.ImageData;
import com.image.Image.repository.StorageRepository;
import com.image.Image.util.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class StrorageService {

    private final StorageRepository storageRepository;

    public StrorageService(StorageRepository storageRepository){
        this.storageRepository = storageRepository;
    }

    public String uploadImage(MultipartFile file) throws IOException {

        ImageData imageData = storageRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if(imageData != null){
            return "File Uploaded, Well done: " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName){

        Optional<ImageData> dbImageData = storageRepository.findByName(fileName);
        byte[] images = ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }
}
