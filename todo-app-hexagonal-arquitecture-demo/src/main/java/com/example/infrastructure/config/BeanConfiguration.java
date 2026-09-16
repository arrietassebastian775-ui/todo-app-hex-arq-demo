package com.example.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.application.port.out.TaskImageStoragePort;
import com.example.application.port.out.TaskRepositoryPort;
import com.example.application.service.TaskService;

@Configuration 
public class BeanConfiguration {

    @Bean 
    public TaskService taskService(TaskRepositoryPort taskRepositoryPort,
			TaskImageStoragePort taskImageStoragePort) {
		return new TaskService(taskRepositoryPort, taskImageStoragePort);
	}


}
