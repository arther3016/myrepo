package com.acn.mysb.demo.entity;

import lombok.Data;
import org.apache.logging.log4j.message.Message;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class Stock {
    @Min(value = 1, message = "should be more than 1.")
    private int productId;

    private int warehouseId;

    private int stockCount;

    private int avaliableCount;

    private int allocatedCount;

    private int unallocatedCount;

    private String creator;

    private String updater;

}
