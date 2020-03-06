package com.simon.db.service;

import com.simon.db.dao.OrderDao;
import com.simon.db.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sang on 2019/12/3.
 */
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    public void addOrder(Order o){
        orderDao.addOrder(o);
    }

    public Order get(Long id ){
        return orderDao.get(id);
    }
}
