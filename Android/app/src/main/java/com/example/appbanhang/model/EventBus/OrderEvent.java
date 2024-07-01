package com.example.appbanhang.model.EventBus;

import com.example.appbanhang.model.Order;

public class OrderEvent {
    Order order;

    public OrderEvent(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
