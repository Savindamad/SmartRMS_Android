package com.group12.smartrms.smartrms;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Savinda Keshan on 9/2/2016.
 */
public class Order implements Serializable {
    String orderId;
    ArrayList<OrderItem> orderItems;
    String status;


    public Order() {
        orderItems = new ArrayList<OrderItem>();
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setStatus(String status) {
        this.status = status;
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
