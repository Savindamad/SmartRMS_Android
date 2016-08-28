package com.group12.smartrms.smartrms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {
    String userID = "";
    String tableNum = "";
    MenuItems[][] menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        final TextView tabNum = (TextView)findViewById(R.id.tvTableNum);

        Intent prIntent = getIntent();
        userID = prIntent.getStringExtra("userID");
        tableNum = prIntent.getStringExtra("tableNum");
        tabNum.setText("Table "+tableNum);

        Object[] menu = (Object[]) prIntent.getExtras().getSerializable("MenuArray");

        if(menu!=null){
            menuItems = new MenuItems[menu.length][];
            for(int i=0;i<menu.length;i++){
                menuItems[i]=(MenuItems[]) menu[i];
            }
        }
    }

    public void NewOrder(View view) {
        Intent intent1 = new Intent(OrderActivity.this,AddNewOrderActivity.class);
        intent1.putExtra("userID",userID);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MenuArray",menuItems);
        intent1.putExtras(bundle);
        startActivity(intent1);
    }

    public void ViewAndEditOrder(View view) {
        Intent intent1 = new Intent(OrderActivity.this,ViewAndEditOrderActivity.class);
        intent1.putExtra("userID",userID);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MenuArray",menuItems);
        intent1.putExtras(bundle);
        startActivity(intent1);
    }

    public void NotifyCleaner(View view) {
    }
}
