package com.Erudio.demo.services;

import com.Erudio.demo.config.FileStorageConfig;
import com.Erudio.demo.exception.FileNotFoundException;
import com.Erudio.demo.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        Path path = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().toAbsolutePath().normalize();
        this.fileStorageLocation = path;
        try {
            Files.createDirectories(this.fileStorageLocation);
        }catch (Exception e ){
            throw new FileStorageException("Não pode criar o diretionrio onde os arquivos serão salvos", e);
        }
    }

    public String storeFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            if(fileName.contains("..")){
                throw new FileStorageException("Nome invalido" +fileName);
            }
            Path targetLocaltion = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(),targetLocaltion, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }catch (Exception e){
            throw new FileStorageException("Não foi possivel salvar o arquivo" + fileName + ". Tente novamente", e);
        }
    }

    public Resource loadFileAsResource(String fileName){
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()){
                return resource;
            }else{
                throw new FileNotFoundException("File not found" + fileName);
            }
        }catch(Exception e){
            throw new FileNotFoundException("File not found " +  fileName, e);
        }
    }
}
