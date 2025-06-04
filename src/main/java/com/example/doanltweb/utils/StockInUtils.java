package com.example.doanltweb.utils;

import com.example.doanltweb.dao.StockInDao;
import com.example.doanltweb.dao.model.Product;
import com.example.doanltweb.dao.model.StockIn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockInUtils {
    StockInDao stockInDao = new StockInDao();

    public Map<Integer,Integer> stockInRecord(){
        List<StockIn> list = stockInDao.getAllRecord();
        Map<Integer,Integer> stockInRecord = new HashMap<Integer,Integer>();
        for (StockIn stockIn : list) {
            Product product = stockIn.getProduct();
            int currentQty = stockInRecord.getOrDefault(product.getId(), 0);
            stockInRecord.put(product.getId(), currentQty + stockIn.getQuantity());
        }
        return stockInRecord;
    }
}
