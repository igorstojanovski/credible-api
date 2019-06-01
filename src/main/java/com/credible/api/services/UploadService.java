package com.credible.api.services;

import com.credible.api.model.Upload;
import com.credible.api.model.User;
import com.credible.api.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadService {

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserService userService;

    public Upload addImageLocation(String imagePath, Long userId, String name, String title, String description) {
        User user = userService.get(userId).orElseThrow();

        Upload upload = new Upload();
        upload.setLocation("/api/user/" + userId + "/uploads/" + name);
        upload.setUser(user);
        upload.setTitle(title);
        upload.setDescription(description);
        upload.setAbsolutePath(imagePath);
        imageRepository.save(upload);

        return upload;
    }
}
