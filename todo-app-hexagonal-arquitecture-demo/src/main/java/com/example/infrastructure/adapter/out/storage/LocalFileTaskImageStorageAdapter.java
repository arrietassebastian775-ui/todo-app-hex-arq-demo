package com.example.infrastructure.adapter.out.storage;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.application.port.out.TaskImageStoragePort;

@Component 
public class LocalFileTaskImageStorageAdapter implements TaskImageStoragePort{

    @SuppressWarnings("unused")
    private static final String BASE_DIR = "uploads/tasks";

    @Override
    public String store(long taskId, byte[] data, String originalFilename) {
        try {
			Path dir = Path.of(BASE_DIR);
			Files.createDirectories(dir);

			String extension = "";
			int dot = originalFilename == null ? -1 : originalFilename.lastIndexOf('.');
			if (dot >= 0)
				extension = originalFilename.substring(dot);

			String filename = taskId + "_" + UUID.randomUUID() + extension;
			Path target = dir.resolve(filename);
			Files.write(target, data);

			return target.toString();
		} catch (IOException e) {
			throw new UncheckedIOException("No se pudo guardar la imagen de la tarea " + taskId, e);
		}
    }
    
}
