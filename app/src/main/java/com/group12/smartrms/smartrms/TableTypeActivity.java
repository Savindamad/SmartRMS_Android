package com.group12.smartrms.smartrms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TableTypeActivity extends AppCompatActivity {
    String userID = "";
    MenuItems[][] menuItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_type);
        Intent prIntent = getIntent();
        userID = prIntent.getStringExtra("userID");
        Object[] menu = (Object[]) prIntent.getExtras().getSerializable("MenuArray");

        if(menu!=null){
            menuItems = new MenuItems[menu.length][];
            for(int i=0;i<menu.length;i++){
                menuItems[i]=(MenuItems[]) menu[i];
                System.out.println(menuItems[i]);
            }
        }
        System.out.println("pass constructor");

    }
    public void TableType1(View view){
        System.out.println("pass table1 method");
        Intent intent1 = new Intent(TableTypeActivity.this,TableType1Activity.class);
        intent1.putExtra("userID", userID);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MenuArray",menuItems);
        intent1.putExtras(bundle);
        startActivity(intent1);
    }
    public void TableType2(View view){
        System.out.println("pass table2 method");
        Intent intent2 = new Intent(TableTypeActivity.this,TableType2Activity.class);
        intent2.putExtra("userID", userID);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MenuArray",menuItems);
        intent2.putExtras(bundle);
        startActivity(intent2);
    }
    public void TableType3(View view){
        System.out.println("pass table3 method");
        Intent intent3 = new Intent(TableTypeActivity.this,TableType3Activity.class);
        intent3.putExtra("userID", userID);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MenuArray",menuItems);
        intent3.putExtras(bundle);
        startActivity(intent3);
    }

}
