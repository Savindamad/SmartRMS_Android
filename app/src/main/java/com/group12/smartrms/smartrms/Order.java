package com.group12.smartrms.smartrms;

import java.util.ArrayList;

/**
 * Created by Savinda Keshan on 9/2/2016.
 */
public class Order {
    String orderId;
    ArrayList<OrderItem> orderItems;


    public Order() {
        orderItems = new ArrayList<OrderItem>();
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
    }

    public String getOrderId() {
        return orderId;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }
}
