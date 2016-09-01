package com.group12.smartrms.smartrms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    String userID = "";
    String tableNum = "";
    ArrayList<MenuItems> menu = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        final TextView tabNum = (TextView)findViewById(R.id.tvTableNum);

        Intent prIntent = getIntent();
        userID = prIntent.getStringExtra("userID");
        tableNum = prIntent.getStringExtra("tableNum");
        menu = (ArrayList<MenuItems>)prIntent.getSerializableExtra("menu");
        tabNum.setText("Table "+tableNum);

    }

    public void NewOrder(View view) {
        Intent intent1 = new Intent(OrderActivity.this,AddNewOrderActivity.class);
        intent1.putExtra("userID",userID);
        intent1.putExtra("menu",menu);
        intent1.putExtra("tableNum",tableNum);
        startActivity(intent1);
    }

    public void ViewAndEditOrder(View view) {
        Intent intent1 = new Intent(OrderActivity.this,ViewAndEditOrderActivity.class);
        intent1.putExtra("userID",userID);
        intent1.putExtra("menu",menu);
        startActivity(intent1);
    }

    public void NotifyCleaner(View view) {
    }
}
