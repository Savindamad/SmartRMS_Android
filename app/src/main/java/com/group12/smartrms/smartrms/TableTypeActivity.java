package com.group12.smartrms.smartrms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class TableTypeActivity extends AppCompatActivity {
    String userID = "";
    ArrayList<MenuItems> menu = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_type);

        //get userId and name from previous Activity
        Intent prIntent = getIntent();
        userID = prIntent.getStringExtra("userID");
        menu = (ArrayList<MenuItems>)prIntent.getSerializableExtra("menu");

    }
    //start TableType1Activity
    public void TableType1(View view){
        Intent intent1 = new Intent(TableTypeActivity.this,TableType1Activity.class);
        intent1.putExtra("userID", userID);
        intent1.putExtra("menu", menu);
        startActivity(intent1);
    }
    //start TableType2Activity
    public void TableType2(View view){
        Intent intent2 = new Intent(TableTypeActivity.this,TableType2Activity.class);
        intent2.putExtra("userID", userID);
        intent2.putExtra("menu",menu);
        startActivity(intent2);
    }
    //start TableType3Activity
    public void TableType3(View view){
        Intent intent3 = new Intent(TableTypeActivity.this,TableType3Activity.class);
        intent3.putExtra("userID", userID);
        intent3.putExtra("menu",menu);
        startActivity(intent3);
    }

}
