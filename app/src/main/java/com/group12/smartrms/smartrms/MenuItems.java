package com.group12.smartrms.smartrms;

import java.io.Serializable;

/**
 * Created by Savinda Keshan on 8/26/2016.
 */
public class MenuItems implements Serializable{
    public String itemCode;
    public String itemName;
    public String itemType;
    public String itemDescription;
    public String itemPrice;

    public MenuItems(String itemCode,String itemName, String itemType, String itemDescription,String itemPrice){
        this.itemCode=itemCode;
        this.itemName=itemName;
        this.itemType=itemType;
        this.itemDescription=itemDescription;
        this.itemPrice=itemPrice;

    }
    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }
}
