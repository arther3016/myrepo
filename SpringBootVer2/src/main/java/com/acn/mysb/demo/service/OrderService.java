package com.acn.mysb.demo.service;

import com.acn.mysb.demo.entity.OrderDetail;
import com.acn.mysb.demo.entity.OrderSummary;

import java.util.List;

public interface OrderService {

    public void insertOrder(OrderSummary orderSummary);

    public String updateOrderDetail(List<OrderDetail> orderDetails);

    public String deleteOrder(OrderSummary orderSummary);

}