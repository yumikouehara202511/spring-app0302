package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.mapper.TodoMapper;
import com.example.demo.model.Todo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoMapper todoMapper;

    public void create(Todo todo) {
        todoMapper.insert(todo);
    }
}
