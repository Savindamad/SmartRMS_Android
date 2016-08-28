package com.group12.smartrms.smartrms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TableType3Activity extends AppCompatActivity {
    String userID = "";
    MenuItems[][] menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_type3);

        Intent prIntent = getIntent();
        userID = prIntent.getStringExtra("userID");
        Object[] menu = (Object[]) prIntent.getExtras().getSerializable("MenuArray");

        if(menu!=null){
            menuItems = new MenuItems[menu.length][];
            for(int i=0;i<menu.length;i++){
                menuItems[i]=(MenuItems[]) menu[i];
            }
        }
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
