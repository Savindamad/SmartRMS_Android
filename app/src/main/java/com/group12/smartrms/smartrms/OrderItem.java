package com.group12.smartrms.smartrms;

/**
 * Created by Savinda Keshan on 9/11/2016.
 */
public class OrderItem {
    String ItemId;
    String ItemQty;

    public OrderItem(String itemId, String itemQty) {
        ItemId = itemId;
        ItemQty = itemQty;
    }

    public String getItemId() {
        return ItemId;
    }

    public String getItemQty() {
        return ItemQty;
    }

}
