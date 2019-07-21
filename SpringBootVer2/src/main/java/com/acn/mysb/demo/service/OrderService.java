package com.acn.mysb.demo.service;

import com.acn.mysb.demo.entity.Order;
import com.acn.mysb.demo.mapper.OrderMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderService {
    @Autowired
    @Resource
    private OrderMapper om;

    public Order getOrderById(String orderId){
        return om.getOrderById(orderId);
    }

    public void insertOrder(Order order){
        om.insertOrder(order);
    }

    public void updateOrder(Order order){
        om.updateOrder(order);
    }

    public void deleteOrder(String orderId){
        om.deleteOrder(orderId);
    }

    public IPage<Order> findList(Order order, int page, int pageSize){
        Page<Order> p = new Page<>(page, pageSize);
        p.setRecords(om.findList(p, order));
        return p;
    }

}
