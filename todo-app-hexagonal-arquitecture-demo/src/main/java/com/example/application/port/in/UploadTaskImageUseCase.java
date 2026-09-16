package com.example.application.port.in;

import com.example.domain.model.Task;

public interface UploadTaskImageUseCase {
    Task uploadImage(long taskId, byte[] imageData, String originalFilename);

}
