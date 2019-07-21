package com.acn.mysb.demo.controller;

import com.acn.mysb.demo.entity.Order;
import com.acn.mysb.demo.service.OrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService os;

    @RequestMapping("/getorder/{orderId}")
    public String getOrderById(@PathVariable String orderId){
        return  os.getOrderById(orderId).toString();
    }

    @RequestMapping("/insert/{orderId}/{clientId}")
    public void insertOrder(@PathVariable("orderId") String orderId,
                            @PathVariable("clientId") String clientId){
        Order oo = new Order();
        oo.setOrderId(orderId);
        oo.setClientId(clientId);
        os.insertOrder(oo);
    }

    @RequestMapping("/update/{orderId}/{comment}")
    public void updateOrder(@PathVariable("orderId") String orderId,
                            @PathVariable("comment") String comment){
        Order oo = new Order();
        oo.setOrderId(orderId);
        oo.setComment(comment);
        os.updateOrder(oo);
    }

    @RequestMapping("/delete/{orderId}")
    public void deleteOrder(@PathVariable String orderId){
        os.deleteOrder(orderId);
    }

    @RequestMapping("/list/{page}/{pageSize}")
    public String findList(@PathVariable int page,
                           @PathVariable int pageSize){
        Order order = new Order();
        IPage<Order> ip = os.findList(order, page, pageSize);
        return ip.getRecords().toString();
    }

}
