package com.acn.mysb.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Order {
    @TableId("orderId")
    private String orderId;
    @TableId("clientId")
    private String clientId;
    @TableId("orderDate")
    private String orderDate;
    @TableId("quantity")
    private String quantity;
    @TableId("comment")
    private String comment;
}
