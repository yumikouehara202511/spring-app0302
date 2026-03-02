package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        List<Todo> todos = todoService.findAll();
        model.addAttribute("todos", todos);
        return "todo/list";
    }

    @GetMapping("/new")
    public String newTodo() {
        return "todo/form";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Todo todo = todoService.findById(id);
        model.addAttribute("todo", todo);
        return "todo/edit";
    }

    @PostMapping("/confirm")
    public String confirm(@RequestParam(value = "id", required = false) Long id,
                          @RequestParam("name") String name,
                          @RequestParam(value = "phone", required = false) String phone,
                          @RequestParam(value = "birthday", required = false) String birthday,
                          @RequestParam(value = "orderHistory", required = false) String orderHistory,
                          @RequestParam(value = "amount", required = false) Integer amount,
                          @RequestParam(value = "companionCount", required = false) Integer companionCount,
                          @RequestParam(value = "companionMemo", required = false) String companionMemo,
                          @RequestParam(value = "visitDate", required = false) String visitDate,
                          @RequestParam(value = "notes", required = false) String notes,
                          Model model) {
        bindCustomerAttributes(model, id, name, phone, birthday, orderHistory, amount, companionCount, companionMemo, visitDate, notes);
        return "todo/confirm";
    }

    @PostMapping("/complete")
    public String complete(@RequestParam(value = "id", required = false) Long id,
                           @RequestParam("name") String name,
                           @RequestParam(value = "phone", required = false) String phone,
                           @RequestParam(value = "birthday", required = false) String birthday,
                           @RequestParam(value = "orderHistory", required = false) String orderHistory,
                           @RequestParam(value = "amount", required = false) Integer amount,
                           @RequestParam(value = "companionCount", required = false) Integer companionCount,
                           @RequestParam(value = "companionMemo", required = false) String companionMemo,
                           @RequestParam(value = "visitDate", required = false) String visitDate,
                           @RequestParam(value = "notes", required = false) String notes,
                           RedirectAttributes redirectAttributes) {
        Todo todo = buildCustomerTodo(id, name, phone, birthday, orderHistory, amount, companionCount, companionMemo, visitDate, notes);
        todoService.create(todo);
        redirectAttributes.addFlashAttribute("successMessage", "顧客情報を登録しました");
        return "redirect:/todo";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id,
                         @RequestParam("title") String title,
                         RedirectAttributes redirectAttributes) {
        Todo todo = todoService.findById(id);
        if (todo == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "更新に失敗しました");
            return "redirect:/todo";
        }

        todo.setTitle(title);
        boolean updated = todoService.update(todo);
        if (updated) {
            redirectAttributes.addFlashAttribute("successMessage", "更新が完了しました");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "更新に失敗しました");
        }
        return "redirect:/todo";
    }

    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable("id") Long id) {
        todoService.toggleCompleted(id);
        return "redirect:/todo";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean deleted = todoService.deleteById(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "ToDoを削除しました");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "削除に失敗しました");
        }
        return "redirect:/todo";
    }

    private void bindCustomerAttributes(Model model,
                                        Long id,
                                        String name,
                                        String phone,
                                        String birthday,
                                        String orderHistory,
                                        Integer amount,
                                        Integer companionCount,
                                        String companionMemo,
                                        String visitDate,
                                        String notes) {
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        model.addAttribute("phone", phone);
        model.addAttribute("birthday", birthday);
        model.addAttribute("orderHistory", orderHistory);
        model.addAttribute("amount", amount);
        model.addAttribute("companionCount", companionCount);
        model.addAttribute("companionMemo", companionMemo);
        model.addAttribute("visitDate", visitDate);
        model.addAttribute("notes", notes);
    }

    private Todo buildCustomerTodo(Long id,
                                   String name,
                                   String phone,
                                   String birthday,
                                   String orderHistory,
                                   Integer amount,
                                   Integer companionCount,
                                   String companionMemo,
                                   String visitDate,
                                   String notes) {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setTitle(name);
        todo.setName(name);
        todo.setPhone(phone);
        todo.setBirthday(birthday);
        todo.setOrderHistory(orderHistory);
        todo.setAmount(amount);
        todo.setCompanionCount(companionCount);
        todo.setCompanionMemo(companionMemo);
        todo.setVisitDate(visitDate);
        todo.setNotes(notes);
        todo.setCompleted(false);
        return todo;
    }
}
