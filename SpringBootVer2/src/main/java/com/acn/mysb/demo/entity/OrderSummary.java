package com.acn.mysb.demo.entity;

import com.acn.mysb.demo.entity.OrderDetail;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class OrderSummary {
    @Min(value = 1, message = "should be more than 1.")
    private int id;

    private int userId;

    private String zip;

    private int creator;

    private int updater;

    @Size(min=1)
    private List<OrderDetail> orderDetails;
}
