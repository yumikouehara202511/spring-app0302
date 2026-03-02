package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todo")
public class TodoController {

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
        return "todo/new";
    }

    @GetMapping("/confirm")
    public String confirm() {
        return "todo/confirm";
    }

    @GetMapping("/complete")
    public String complete() {
        return "todo/complete";
    }
}
