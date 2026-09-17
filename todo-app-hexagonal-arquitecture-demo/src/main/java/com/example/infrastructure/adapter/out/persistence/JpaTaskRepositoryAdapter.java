package com.example.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.application.port.out.TaskRepositoryPort;
import com.example.domain.model.Task;

import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class JpaTaskRepositoryAdapter implements TaskRepositoryPort {
	
	private final SpringDataTaskRepository springDataTaskRepository;
	private final TaskPersintenceMapper mapper;

	@Override
	public Task save(Task task) {
		
		task.initDefaults();
		TaskJpaEntity entity = mapper.toJpaEntity(task);
		TaskJpaEntity saved = springDataTaskRepository.save(entity);
		
		return mapper.toDomain(saved);
	}

	@Override
	public Optional<Task> findById(long id) {
		return springDataTaskRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public List<Task> findAll() {
		return springDataTaskRepository.findAll()
					.stream()
					.map(mapper::toDomain)
					.collect(Collectors.toList());
	}

	@Override
	public void deleteById(Long id) {
		springDataTaskRepository.deleteById(id);;
	}

}
