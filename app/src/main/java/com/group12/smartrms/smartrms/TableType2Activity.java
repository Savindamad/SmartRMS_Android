package com.group12.smartrms.smartrms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TableType2Activity extends AppCompatActivity {
    String userID = "";
    MenuItems[][] menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_type2);

        Intent prIntent = getIntent();
        userID = prIntent.getStringExtra("userID");
        Object[] menu = (Object[]) prIntent.getExtras().getSerializable("MenuArray");

        if(menu!=null){
            menuItems = new MenuItems[menu.length][];
            for(int i=0;i<menu.length;i++){
                menuItems[i]=(MenuItems[]) menu[i];
            }
        }
        System.out.println("pass constructor");
    }


    public void SelectTable1(View view) {
        AccessTable("9");
    }
    public void SelectTable2(View view) {
        AccessTable("10");
    }
    public void SelectTable3(View view) {
        AccessTable("11");
    }
    public void SelectTable4(View view) {
        AccessTable("12");
    }
    public void SelectTable5(View view) {
        AccessTable("13");
    }
    public void SelectTable6(View view) {
        AccessTable("14");
    }
    public void SelectTable7(View view) {
        AccessTable("15");
    }
    public void SelectTable8(View view) {
        AccessTable("16");
    }

    public void AccessTable(String tableNum){
        CheckTable checkTable = new CheckTable(this);
        Intent intent = new Intent(TableType2Activity.this,OrderActivity.class);
        checkTable.setIntent(intent);
        checkTable.execute(userID, tableNum);
    }
}
