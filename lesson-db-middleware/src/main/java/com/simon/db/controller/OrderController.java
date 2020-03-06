package com.simon.db.controller;

import com.simon.db.service.OrderService;
import com.simon.db.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sang on 2019/12/3.
 */
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/add")
    public Order add(  Order o ){
         orderService.addOrder(o);
        return o;
    }

    @RequestMapping("get")
    public Order getOrder(Long orderId) {
        return this.orderService.get(orderId);
    }
}
