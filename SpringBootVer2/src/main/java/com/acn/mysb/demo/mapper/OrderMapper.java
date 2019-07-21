package com.acn.mysb.demo.mapper;

import com.acn.mysb.demo.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {
    Order getOrderById(String orderId);

    void deleteOrder(String orderId);

    List<Order> findList(Page<Order> page, @Param("order") Order order);

    void insertOrder(Order order);

    void updateOrder(Order order);
}
