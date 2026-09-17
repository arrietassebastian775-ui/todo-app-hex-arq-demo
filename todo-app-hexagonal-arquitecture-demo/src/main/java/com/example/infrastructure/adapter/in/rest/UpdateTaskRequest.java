package com.example.infrastructure.adapter.in.rest;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class UpdateTaskRequest {
    
    private String title;
    private String description;

}
