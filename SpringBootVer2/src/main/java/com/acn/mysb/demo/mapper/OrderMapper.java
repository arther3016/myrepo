package com.acn.mysb.demo.mapper;

import com.acn.mysb.demo.entity.OrderDetail;
import com.acn.mysb.demo.entity.OrderSummary;
import com.acn.mysb.demo.entity.Stock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<OrderSummary> {
    List<OrderDetail> getDetailsById(int orderId);

    OrderDetail getDetailByKey(int orderId, int detailNo);

    void deleteOrderSummary(OrderSummary orderSummary);

    void deleteOrderDetail(OrderSummary orderSummary);

    List<OrderSummary> getOrderListByUser(Page<OrderSummary> page, @Param("orderSummary") OrderSummary orderSummary, int userId);

    int insertOrder(OrderSummary orderSummary);

    int insertOrderDetail(OrderDetail orderDetail);

    int updateOrderDetail(OrderDetail orderDetail);

}
