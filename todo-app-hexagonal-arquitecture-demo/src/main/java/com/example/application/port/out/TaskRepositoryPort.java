package com.example.application.port.out;

import java.util.List;
import java.util.Optional;

import com.example.domain.model.Task;

public interface TaskRepositoryPort {
	Task save(Task task);
	Optional<Task> findById(long id);
	List<Task> findAll();
	Void deleteById(Long id);
}
