package com.example.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {

	@EqualsAndHashCode.Include
	private long id;
	
	private String title;
	private String description;
	private TaskStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime completedAt;
	private String imagePath;
	
	/* Los metodos siguientes aportan comportamiento, es decir, las reglas de negocio para la gestion 
	 * de las tareas*/
	
	public void complete() {
		
		if (this.status == TaskStatus.COMPLETED) {
			throw new IllegalStateException("La tarea ya está completada");
		}
		
		this.status = TaskStatus.COMPLETED;
		this.completedAt = LocalDateTime.now();
	}
	
	public void reopen() {
		
		if (this.status == TaskStatus.PENDING) {
			throw new IllegalStateException("La tarea ya está pendiente");
		}
		
		this.status = TaskStatus.PENDING;
		this.completedAt = null;
	}
	
	public void initDefaults() {
		if (this.status == null)
			this.status = TaskStatus.PENDING;
		if (this.createdAt == null)
			this.createdAt = LocalDateTime.now();
	}

	public void attachImage(String imagePath) {
		this.imagePath = imagePath;
	}
}
