package com.credible.api.resources;

import com.credible.api.exceptions.StorageFileNotFoundException;
import com.credible.api.model.requests.FileUploadRequest;
import com.credible.api.services.StorageService;
import com.credible.api.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/{userId}/image")
public class FileUploadController {

    private final StorageService storageService;
    private final UploadService uploadService;

    @Autowired
    public FileUploadController(StorageService storageService, UploadService uploadService) {
        this.storageService = storageService;
        this.uploadService = uploadService;
    }

    @PostMapping
    @PreAuthorize("@securityService.isOwner(#userId)")
    public ResponseEntity<String> handleFileUpload(@ModelAttribute FileUploadRequest request,
                                                   @PathVariable Long userId) {
        String imagePath = storageService.store(request.getFile(), userId);
        uploadService.addImageLocation(imagePath, userId, request.getFile().getOriginalFilename(),
                request.getTitle(), request.getDescription());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
