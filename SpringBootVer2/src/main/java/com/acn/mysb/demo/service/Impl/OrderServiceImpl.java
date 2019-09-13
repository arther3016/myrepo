package com.acn.mysb.demo.service.Impl;

import com.acn.mysb.demo.entity.OrderDetail;
import com.acn.mysb.demo.entity.OrderPage;
import com.acn.mysb.demo.entity.OrderSummary;
import com.acn.mysb.demo.entity.Stock;
import com.acn.mysb.demo.mapper.OrderMapper;
import com.acn.mysb.demo.mapper.StockMapper;
import com.acn.mysb.demo.service.OrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StockMapper stockMapper;

    public void insertOrder(OrderSummary orderSummary){
        orderMapper.insertOrder(orderSummary);
        logger.info("insert into order_summary id=["+orderSummary.getId()+"]");
        List<OrderDetail> orderDetails = orderSummary.getOrderDetails();
        for(OrderDetail orderDetail : orderDetails){
            insertDetailStock(orderDetail);
            logger.info("insert into order_detail order_id=["+orderDetail.getOrderId()+"] " +
                    "detail_no=["+orderDetail.getDetailNo()+"]");
        }
    }

    private void insertDetailStock(OrderDetail orderDetail){
        Stock stock = stockMapper.getStock(orderDetail.getProductId(), orderDetail.getWarehouseId());
        if (orderDetail.getDemandCount() <= stock.getAvaliableCount()){
            stock.setAvaliableCount(stock.getAvaliableCount() - orderDetail.getDemandCount());
            stock.setAllocatedCount(stock.getAllocatedCount() + orderDetail.getDemandCount());
            stockMapper.updateStock(stock);
            orderDetail.setAllocatedCount(orderDetail.getDemandCount());
            orderMapper.insertOrderDetail(orderDetail);
        }else{
            stock.setAllocatedCount(stock.getAllocatedCount() + stock.getAvaliableCount());
            stock.setUnallocatedCount(stock.getUnallocatedCount() + orderDetail.getDemandCount() - stock.getAvaliableCount());
            orderDetail.setAllocatedCount(stock.getAvaliableCount());
            orderDetail.setUnallocatedCount(orderDetail.getDemandCount() - stock.getAvaliableCount());
            stock.setAvaliableCount(0);
            stockMapper.updateStock(stock);
            orderMapper.insertOrderDetail(orderDetail);
        }
    }

    public String deleteOrder(OrderSummary orderSummary){
        List<OrderDetail> orderDetails = orderMapper.getDetailsById(orderSummary.getId());
        for(OrderDetail orderDetail : orderDetails){
            if(orderDetail.getShipmentOrderCount() > 0){
                return "already shipped";
            }
        }
        orderMapper.deleteOrderDetail(orderSummary);
        orderMapper.deleteOrderSummary(orderSummary);
        logger.info("delete order_summary id=["+orderSummary.getId()+"]");
        for(OrderDetail orderDetail : orderDetails){
            deleteDetailStock(orderDetail);
        }
        return "delete successful";
    }

    private void deleteDetailStock(OrderDetail orderDetail) {
        Stock stock = stockMapper.getStock(orderDetail.getProductId(), orderDetail.getWarehouseId());
        stock.setAvaliableCount(stock.getAvaliableCount() + orderDetail.getAllocatedCount());
        stock.setAllocatedCount(stock.getAllocatedCount() - orderDetail.getAllocatedCount());
        stock.setUnallocatedCount(stock.getUnallocatedCount() - orderDetail.getUnallocatedCount());
        stockMapper.updateStock(stock);
    }

    public String updateOrderDetail(List<OrderDetail> orderDetails){
        for(OrderDetail orderDetail : orderDetails){
            if(orderDetail.getShipmentOrderCount() > 0){
                return "already shipped";
            }
        }
        for(OrderDetail orderDetail : orderDetails){
            updateDetailStock(orderDetail);
            logger.info("update order_detail " +
                    "order_id=["+orderDetail.getOrderId()+"] detail_no=["+orderDetail.getDetailNo()+"]");
        }
        return "update successful";
    }

    private void updateDetailStock(OrderDetail orderDetail) {
        Stock stock = stockMapper.getStock(orderDetail.getProductId(), orderDetail.getWarehouseId());
        OrderDetail oldDetail = orderMapper.getDetailByKey(orderDetail.getOrderId(), orderDetail.getDetailNo());
        // delete old detail count
        stock.setAvaliableCount(stock.getAvaliableCount() + oldDetail.getAllocatedCount());
        stock.setAllocatedCount(stock.getAllocatedCount() - oldDetail.getAllocatedCount());
        stock.setUnallocatedCount(stock.getUnallocatedCount() - oldDetail.getUnallocatedCount());
        // recalculate
        if (orderDetail.getDemandCount() <= stock.getAvaliableCount()){
            stock.setAvaliableCount(stock.getAvaliableCount() - orderDetail.getDemandCount());
            stock.setAllocatedCount(stock.getAllocatedCount() + orderDetail.getDemandCount());
            stockMapper.updateStock(stock);
            orderDetail.setAllocatedCount(orderDetail.getDemandCount());
            orderDetail.setUnallocatedCount(0);
            orderMapper.updateOrderDetail(orderDetail);
        }else{
            stock.setAllocatedCount(stock.getAllocatedCount() + stock.getAvaliableCount());
            stock.setUnallocatedCount(stock.getUnallocatedCount() + orderDetail.getDemandCount() - stock.getAvaliableCount());
            orderDetail.setAllocatedCount(stock.getAvaliableCount());
            orderDetail.setUnallocatedCount(orderDetail.getDemandCount() - stock.getAvaliableCount());
            stock.setAvaliableCount(0);
            stockMapper.updateStock(stock);
            orderMapper.updateOrderDetail(orderDetail);
        }
    }

    public IPage<OrderSummary> getOrderListByUser(OrderSummary orderSummary, OrderPage orderPage){
        Page<OrderSummary> p = new Page<>(orderPage.getPage(), orderPage.getPageSize());
        p.setRecords(orderMapper.getOrderListByUser(p, orderSummary, orderPage.getUserId()));
        return p;
    }

    public List<Stock> getStockByProduct(int productId){
        return stockMapper.getStockByProduct(productId);
    }

}
