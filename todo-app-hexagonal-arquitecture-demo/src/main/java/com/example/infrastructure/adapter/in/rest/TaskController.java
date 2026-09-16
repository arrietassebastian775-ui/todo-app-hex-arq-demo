package com.example.infrastructure.adapter.in.rest;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.application.port.in.CreateTaskUseCase;
import com.example.application.port.in.DeleteTaskUseCase;
import com.example.application.port.in.GetTaskUseCase;
import com.example.application.port.in.ListTaskUseCase;
import com.example.application.port.in.UpdateTaskUseCase;
import com.example.application.port.in.UploadTaskImageUseCase;
import com.example.domain.model.Task;
import com.example.infrastructure.adapter.in.rest.dto.CreateTaskRequest;
import com.example.infrastructure.adapter.in.rest.dto.TaskResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

	private final CreateTaskUseCase createTaskUseCase;
	private final GetTaskUseCase getTaskUseCase;
	private final ListTaskUseCase listTaskUseCase;
	private final DeleteTaskUseCase deleteTaskUseCase;
	private final UpdateTaskUseCase updateTaskUseCase;
	private final UploadTaskImageUseCase uploadTaskImageUseCase;
	
	@PostMapping
	public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest request) {
		
		Task task = Task.builder()
				.title(request.getTitle())
				.description(request.getDescription())
				.build();
		
		Task saved = createTaskUseCase.create(task);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(TaskResponse.from(saved));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TaskResponse> getById(@PathVariable long id) {
		
		Task task = getTaskUseCase.getById(id);
		
		return ResponseEntity.ok(TaskResponse.from(task));
	}
	
	@GetMapping	
	public ResponseEntity<List<TaskResponse>> listAll() {
		
		List<TaskResponse> response = listTaskUseCase.listAll()
				.stream()
				.map(TaskResponse::from)
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(response);
	}
	@PutMapping("/{id}")
	public ResponseEntity<TaskResponse> update(@PathVariable long id, @RequestBody UpdateTaskRequest request) {

		Task changes = Task.builder()
				.title(request.getTitle())
				.description(request.getDescription())
				.build();

		Task updated = updateTaskUseCase.update(id, changes);
		return ResponseEntity.ok(TaskResponse.from(updated));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id) {
		deleteTaskUseCase.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<TaskResponse> uploadImage(@PathVariable long id,
			@RequestParam("file") MultipartFile file) throws IOException {

		Task updated = uploadTaskImageUseCase.uploadImage(id, file.getBytes(), file.getOriginalFilename());
		return ResponseEntity.ok(TaskResponse.from(updated));
	}
}












