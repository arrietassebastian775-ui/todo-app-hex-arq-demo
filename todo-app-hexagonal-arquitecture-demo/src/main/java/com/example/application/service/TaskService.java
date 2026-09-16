/* Implementa el caso de uso */
package com.example.application.service;

import java.util.List;

import com.example.application.port.in.CreateTaskUseCase;
import com.example.application.port.in.DeleteTaskUseCase;
import com.example.application.port.in.GetTaskUseCase;
import com.example.application.port.in.ListTaskUseCase;
import com.example.application.port.in.UpdateTaskUseCase;
import com.example.application.port.in.UploadTaskImageUseCase;
import com.example.application.port.out.TaskImageStoragePort;
import com.example.application.port.out.TaskRepositoryPort;
import com.example.domain.exception.TaskNotFoundException;
import com.example.domain.model.Task;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
/* ¿Es correcta una anotacion de Spring aqui? 
 * 
 * Los mas puristas dirian que NO, pero tiene un coste implementar esto
 * correctamente.
 * 
 * Con esta anotacion estamos introduciendo una dependencia del framework
 * en la capa de aplicacion, y el problema es que si mañana migramos a quarkus
 * o cualquier otro framework o si queremos testear el caso de uso en aislamiento total, esta clase
 * ya no seria agnostica del framework, es decir, estaria acoplada el Spring Framework
 * 
 * TODO: ¿Que deberia hacerse para que este acoplamiento no existiera?
 * 
 * Rta. Crear una clase de configuracion, anotada con @Configuration o @Component
 * en la capa de Infraestructura donde tengamos todos los Bean que 
 * hay que crear cuando se levanta el contexto de Spring
 * 
 * */
public class TaskService implements CreateTaskUseCase, GetTaskUseCase, ListTaskUseCase,
		DeleteTaskUseCase, UpdateTaskUseCase, UploadTaskImageUseCase {

	private final TaskRepositoryPort taskRepositoryPort;
	private final TaskImageStoragePort taskImageStoragePort;

	@Override
	public Task create(Task task) {
		// TODO Auto-generated method stub
		return taskRepositoryPort.save(task);
	}

	@Override
	public Task getById(long id) {
		// TODO Auto-generated method stub
		return taskRepositoryPort.findById(id)
					.orElseThrow(() -> new TaskNotFoundException(id));
	}

	@Override
	public List<Task> listAll() {
		// TODO Auto-generated method stub
		return taskRepositoryPort.findAll();
	}

	@Override
	public void deleteById(long id) {
		getById(id);
		taskRepositoryPort.deleteById(id);
	}

	@Override
	public Task update(long id, Task changes) {
		Task task = getById(id);

		if (changes.getTitle() != null)
			task.setTitle(changes.getTitle());
		if (changes.getDescription() != null)
			task.setDescription(changes.getDescription());

		return taskRepositoryPort.save(task);
	}

	@Override
	public Task uploadImage(long taskId, byte[] imageData, String originalFilename) {
		Task task = getById(taskId);
		String path = taskImageStoragePort.store(taskId, imageData, originalFilename);
		task.attachImage(path);
		return taskRepositoryPort.save(task);
	}


}
