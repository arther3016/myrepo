package com.acn.mysb.demo.entity;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class OrderPage {
    @Min(value = 1, message = "should be more than 1.")
    private int userId;

    private int page;

    private int pageSize;
    
}
