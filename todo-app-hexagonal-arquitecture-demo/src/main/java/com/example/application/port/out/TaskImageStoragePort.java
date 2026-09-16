package com.example.application.port.out;

public interface TaskImageStoragePort {
    String store(long taskId, byte[] data, String originalFilename);

}
