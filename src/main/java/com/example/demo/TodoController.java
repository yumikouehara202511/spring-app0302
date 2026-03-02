package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Todo;
import com.example.demo.service.TodoService;

@Controller
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping({"", "/"})
    public String list(Model model) {
        List<Map<String, Object>> todoList = List.of(
            Map.of("id", 1, "title", "Learn Spring Boot", "status", "TODO"),
            Map.of("id", 2, "title", "Create Controller", "status", "DOING"),
            Map.of("id", 3, "title", "Build Thymeleaf View", "status", "DONE")
        );

        model.addAttribute("todoList", todoList);
        return "todo/list";
    }

    @GetMapping("/new")
    public String newTodo() {
        return "todo/form";
    }

    @PostMapping("/confirm")
    public String confirm(@RequestParam("title") String title, Model model) {
        model.addAttribute("title", title);
        return "todo/confirm";
    }

    @PostMapping("/complete")
    public String complete(@RequestParam("title") String title) {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setCompleted(false);
        todoService.create(todo);
        return "redirect:/todo";
    }
}
