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
    private String monthlyVisits;
    private Integer amount;
    private Integer companionCount;
    private String companionMemo;
    private String visitDate;
    private String notes;
    private String salesTrendData;
    private String visitCountTrendData;
    private String visitDateTrendData;
    private String bottleTrendData;
    private Boolean completed;
}
