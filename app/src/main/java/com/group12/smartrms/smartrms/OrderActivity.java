package com.group12.smartrms.smartrms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OrderActivity extends AppCompatActivity {
    String userID = "";
    String tableNum = "";
    MenuItems[][] menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent prIntent = getIntent();
        userID = prIntent.getStringExtra("userID");
        tableNum = prIntent.getStringExtra("tableNum");
        Object[] menu = (Object[]) prIntent.getExtras().getSerializable("MenuArray");

        if(menu!=null){
            menuItems = new MenuItems[menu.length][];
            for(int i=0;i<menu.length;i++){
                menuItems[i]=(MenuItems[]) menu[i];
            }
        }
    }

    public void NewOrder(View view) {
    }

    public void ViewAndEditOrder(View view) {
    }

    public void NotifyCleaner(View view) {
    }
}
