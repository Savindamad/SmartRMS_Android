package com.group12.smartrms.smartrms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TableType3Activity extends AppCompatActivity {
    String userID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_type3);

        Intent prIntent = getIntent();
        userID = prIntent.getStringExtra("userID");

    }


    public void SelectTable1(View view) {
        AccessTable("17");
    }
    public void SelectTable2(View view) {
        AccessTable("18");
    }


    public void AccessTable(String tableNum){

    }
}
