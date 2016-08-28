package com.group12.smartrms.smartrms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddNewOrderActivity extends FragmentActivity {
    private String userID = "";
    private String tableNum = "";
    MenuItems[] menuItems;

    private List<MenuItems> menu = new ArrayList<MenuItems>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_order);

        TabHost tab = (TabHost)findViewById(R.id.tabHost);
        tab.setup();

        TabHost.TabSpec tab1 = tab.newTabSpec("TAB 1");
        tab1.setIndicator("TAB 1");
        tab1.setContent(R.id.add);
        tab.addTab(tab1);

        TabHost.TabSpec tab2 = tab.newTabSpec("TAB 2");
        tab2.setIndicator("TAB 2");
        tab2.setContent(R.id.accept);
        tab.addTab(tab2);

        System.out.println("pass a");
        Intent prIntent = getIntent();
        userID = prIntent.getStringExtra("userID");
        tableNum = prIntent.getStringExtra("tableNum");

        Object[] menu = (Object[]) prIntent.getExtras().getSerializable("MenuArray");

        if(menu!=null){
            menuItems = new MenuItems[menu.length];
            for(int i=0;i<menu.length;i++){
                menuItems[i]=(MenuItems) menu[i];
            }
        }
        System.out.println("pass b");
        addToArrayList();
        System.out.println("pass c");
        addToListView();
        System.out.println("pass d");
    }

    private void addToListView() {
        ArrayAdapter<MenuItems> adapter = new MyListAdapter();
        ListView list = (ListView)findViewById(R.id.lvMenu);
        list.setAdapter(adapter);
    }

    private void addToArrayList() {
        for(int i =0; i<menuItems.length; i++){
            menu.add(menuItems[i]);
        }
    }
    private class MyListAdapter extends ArrayAdapter<MenuItems>{

        public MyListAdapter() {
            super(AddNewOrderActivity.this,R.layout.list_view_layout,menu);
        }
        @Override
        public View getView(int position, View convertView,ViewGroup parent){
            View itemView = convertView;
            if(itemView==null){
                itemView = getLayoutInflater().inflate(R.layout.list_view_layout,parent,false);
            }
            MenuItems tempItem = menu.get(position);

            TextView code = (TextView)findViewById(R.id.tvMealCode);
            TextView name = (TextView)findViewById(R.id.tvMealName);
            TextView description = (TextView)findViewById(R.id.tvMealDes);

            code.setText(tempItem.getItemCode());
            name.setText(tempItem.getItemName());
            description.setText(tempItem.getItemDescription());

            return itemView;
        }
    }
}
