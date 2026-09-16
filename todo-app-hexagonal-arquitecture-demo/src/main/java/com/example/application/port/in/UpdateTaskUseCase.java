package com.example.application.port.in;

import com.example.domain.model.Task;

public interface UpdateTaskUseCase {
    Task update(long id, Task changes);

}
