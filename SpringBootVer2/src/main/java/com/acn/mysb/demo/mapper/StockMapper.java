package com.acn.mysb.demo.mapper;

import com.acn.mysb.demo.entity.OrderDetail;
import com.acn.mysb.demo.entity.OrderSummary;
import com.acn.mysb.demo.entity.Stock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockMapper extends BaseMapper<Stock> {

    Stock getStock(int productId, int warehouseId);

    int updateStock(Stock stock);

    List<Stock> getStockByProduct(int productId);
}
