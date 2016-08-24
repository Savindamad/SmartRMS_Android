package com.group12.smartrms.smartrms;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TableType1Activity extends AppCompatActivity {
    String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_type1);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
    }


    public void SelectTable1(View view) {
        AccessTable("1");
    }
    public void SelectTable2(View view) {
        AccessTable("2");
    }
    public void SelectTable3(View view) {
        AccessTable("3");
    }
    public void SelectTable4(View view) {
        AccessTable("4");
    }
    public void SelectTable5(View view) {
        AccessTable("5");
    }
    public void SelectTable6(View view) {
        AccessTable("6");
    }
    public void SelectTable7(View view) {
        AccessTable("7");
    }
    public void SelectTable8(View view) {
        AccessTable("8");
    }

    public void AccessTable(String tableNum){
        CheckTable checkTable = new CheckTable(this);
        Intent intent = new Intent(TableType1Activity.this,OrderActivity.class);
        checkTable.setIntent(intent);
        checkTable.execute(userID,tableNum);
    }
}
