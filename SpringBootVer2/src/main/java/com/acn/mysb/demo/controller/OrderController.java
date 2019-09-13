package com.acn.mysb.demo.controller;

import com.acn.mysb.demo.entity.OrderDetail;
import com.acn.mysb.demo.entity.OrderPage;
import com.acn.mysb.demo.entity.OrderSummary;
import com.acn.mysb.demo.entity.Stock;
import com.acn.mysb.demo.service.Impl.OrderServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService ;

    @ResponseBody
    @GetMapping("/stock")
    public List<Stock> getStockByProduct(@RequestBody @Valid Stock stock){
        return  orderService.getStockByProduct(stock.getProductId());
    }

    @PostMapping
    public String insertOrder(@RequestBody @Valid OrderSummary orderSummary){
        orderService.insertOrder(orderSummary);
        return "insert success";
    }

    @DeleteMapping
    public String deleteOrder(@RequestBody @Valid OrderSummary orderSummary){
        return orderService.deleteOrder(orderSummary);
    }

    @PutMapping
    public String updateOrderDetail(@RequestBody List<OrderDetail> orderDetails){
        return orderService.updateOrderDetail(orderDetails);
    }



    @GetMapping
    @ResponseBody
    public List<OrderSummary> getOrderListByUser(@RequestBody @Valid OrderPage orderPage){
        OrderSummary orderSummary = new OrderSummary();
        IPage<OrderSummary> ip = orderService.getOrderListByUser(orderSummary, orderPage);
        return ip.getRecords();
    }

}
