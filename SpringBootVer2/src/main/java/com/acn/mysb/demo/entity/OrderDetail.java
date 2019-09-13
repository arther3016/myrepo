package com.acn.mysb.demo.entity;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class OrderDetail {
    @Min(value = 1, message = "should be more than 1.")
    private int orderId;
    @Min(value = 1, message = "should be more than 1.")
    private int detailNo;

    private int productId;

    private int warehouseId;

    private int specifyWarehouseId;

    private int demandCount;

    private int allocatedCount;

    private int unallocatedCount;

    private int shipmentOrderCount;

    private int creator;

    private int updater;
    
}
