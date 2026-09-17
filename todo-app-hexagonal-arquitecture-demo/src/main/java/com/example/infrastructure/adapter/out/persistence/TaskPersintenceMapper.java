package com.example.infrastructure.adapter.out.persistence;

import org.mapstruct.Mapper;

import com.example.domain.model.Task;

@Mapper(componentModel = "spring")
public interface TaskPersintenceMapper {

	
	Task toDomain(TaskJpaEntity entity);

	TaskJpaEntity toJpaEntity(Task task);
}
