package com.example.demo.model;

import lombok.Data;

@Data
public class Todo {
    private Long id;
    private String title;
    private String name;
    private String phone;
    private String birthday;
    private String orderHistory;
    private Integer amount;
    private Integer companionCount;
    private String companionMemo;
    private String visitDate;
    private String notes;
    private Boolean completed;
}
