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
    public String editToConfirm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Todo todo = todoService.findById(id);
        if (todo == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "対象データが見つかりません");
            return "redirect:/todo";
        }
        bindTodoAttributes(model, todo);
        model.addAttribute("editMode", true);
        return "todo/confirm";
    }

    @PostMapping("/confirm")
    public String confirm(@RequestParam(value = "id", required = false) Long id,
                          @RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "phone", required = false) String phone,
                          @RequestParam(value = "birthday", required = false) String birthday,
                          @RequestParam(value = "orderHistory", required = false) String orderHistory,
                          @RequestParam(value = "monthlyVisits", required = false) String monthlyVisits,
                          @RequestParam(value = "amount", required = false) Integer amount,
                          @RequestParam(value = "companionCount", required = false) Integer companionCount,
                          @RequestParam(value = "companionMemo", required = false) String companionMemo,
                          @RequestParam(value = "visitDate", required = false) String visitDate,
                          @RequestParam(value = "notes", required = false) String notes,
                          @RequestParam(value = "salesTrendData", required = false) String salesTrendData,
                          @RequestParam(value = "visitCountTrendData", required = false) String visitCountTrendData,
                          @RequestParam(value = "visitDateTrendData", required = false) String visitDateTrendData,
                          @RequestParam(value = "bottleTrendData", required = false) String bottleTrendData,
                          Model model) {
        Todo todo = buildCustomerTodo(id, name, phone, birthday, orderHistory, monthlyVisits, amount,
                companionCount, companionMemo, visitDate, notes, salesTrendData, visitCountTrendData,
                visitDateTrendData, bottleTrendData);
        bindTodoAttributes(model, todo);
        return "todo/confirm";
    }

    @PostMapping("/complete")
    public String complete(@RequestParam(value = "id", required = false) Long id,
                           @RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "phone", required = false) String phone,
                           @RequestParam(value = "birthday", required = false) String birthday,
                           @RequestParam(value = "orderHistory", required = false) String orderHistory,
                           @RequestParam(value = "monthlyVisits", required = false) String monthlyVisits,
                           @RequestParam(value = "amount", required = false) Integer amount,
                           @RequestParam(value = "companionCount", required = false) Integer companionCount,
                           @RequestParam(value = "companionMemo", required = false) String companionMemo,
                           @RequestParam(value = "visitDate", required = false) String visitDate,
                           @RequestParam(value = "notes", required = false) String notes,
                           @RequestParam(value = "salesTrendData", required = false) String salesTrendData,
                           @RequestParam(value = "visitCountTrendData", required = false) String visitCountTrendData,
                           @RequestParam(value = "visitDateTrendData", required = false) String visitDateTrendData,
                           @RequestParam(value = "bottleTrendData", required = false) String bottleTrendData,
                           RedirectAttributes redirectAttributes) {
        Todo todo = buildCustomerTodo(id, name, phone, birthday, orderHistory, monthlyVisits, amount,
                companionCount, companionMemo, visitDate, notes, salesTrendData, visitCountTrendData,
                visitDateTrendData, bottleTrendData);

        if (todo.getId() != null && todoService.findById(todo.getId()) != null) {
            todoService.update(todo);
            redirectAttributes.addFlashAttribute("successMessage", "顧客情報を更新しました");
        } else {
            todoService.create(todo);
            redirectAttributes.addFlashAttribute("successMessage", "顧客情報を登録しました");
        }
        return "redirect:/todo";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean deleted = todoService.deleteById(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "顧客情報を削除しました");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "削除に失敗しました");
        }
        return "redirect:/todo";
    }

    private void bindTodoAttributes(Model model, Todo todo) {
        model.addAttribute("id", todo.getId());
        model.addAttribute("name", todo.getName());
        model.addAttribute("phone", todo.getPhone());
        model.addAttribute("birthday", todo.getBirthday());
        model.addAttribute("orderHistory", todo.getOrderHistory());
        model.addAttribute("monthlyVisits", todo.getMonthlyVisits());
        model.addAttribute("amount", todo.getAmount());
        model.addAttribute("companionCount", todo.getCompanionCount());
        model.addAttribute("companionMemo", todo.getCompanionMemo());
        model.addAttribute("visitDate", todo.getVisitDate());
        model.addAttribute("notes", todo.getNotes());
        model.addAttribute("salesTrendData", todo.getSalesTrendData());
        model.addAttribute("visitCountTrendData", todo.getVisitCountTrendData());
        model.addAttribute("visitDateTrendData", todo.getVisitDateTrendData());
        model.addAttribute("bottleTrendData", todo.getBottleTrendData());
    }

    private Todo buildCustomerTodo(Long id,
                                   String name,
                                   String phone,
                                   String birthday,
                                   String orderHistory,
                                   String monthlyVisits,
                                   Integer amount,
                                   Integer companionCount,
                                   String companionMemo,
                                   String visitDate,
                                   String notes,
                                   String salesTrendData,
                                   String visitCountTrendData,
                                   String visitDateTrendData,
                                   String bottleTrendData) {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setTitle(name);
        todo.setName(name);
        todo.setPhone(phone);
        todo.setBirthday(birthday);
        todo.setOrderHistory(orderHistory);
        todo.setMonthlyVisits(monthlyVisits);
        todo.setAmount(amount);
        todo.setCompanionCount(companionCount);
        todo.setCompanionMemo(companionMemo);
        todo.setVisitDate(visitDate);
        todo.setNotes(notes);
        todo.setSalesTrendData(salesTrendData);
        todo.setVisitCountTrendData(visitCountTrendData);
        todo.setVisitDateTrendData(visitDateTrendData);
        todo.setBottleTrendData(bottleTrendData);
        todo.setCompleted(false);
        return todo;
    }
}
