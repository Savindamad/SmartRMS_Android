package com.group12.smartrms.smartrms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddNewOrderActivity extends FragmentActivity {

    private String userID = "";
    private String tableNum = "";
    private ArrayList<MenuItems> menu = new ArrayList<MenuItems>();
    private List<MenuItems> menuTemp = new ArrayList<MenuItems>();

    ArrayAdapter<MenuItems> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_order);

        TabHost tab = (TabHost)findViewById(R.id.tabHost);
        tab.setup();

        TabHost.TabSpec tab1 = tab.newTabSpec("TAB 1");
        tab1.setIndicator("Add Items");
        tab1.setContent(R.id.add);
        tab.addTab(tab1);

        TabHost.TabSpec tab2 = tab.newTabSpec("TAB 2");
        tab2.setIndicator("Accept Order");
        tab2.setContent(R.id.accept);
        tab.addTab(tab2);

        Intent prIntent = getIntent();
        userID = prIntent.getStringExtra("userID");
        tableNum = prIntent.getStringExtra("tableNum");
        menu = (ArrayList<MenuItems>)prIntent.getSerializableExtra("menu");
        menuTemp.addAll(menu);

        final ListView list = (ListView)findViewById(R.id.lvMenu);
        adapter = new MyListAdapter();
        list.setAdapter(adapter);

        final EditText search = (EditText)findViewById(R.id.etSearch);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = search.getText().toString().toLowerCase(Locale.getDefault());

                menuTemp.clear();
                if(text.length()==0){
                    menuTemp.addAll(menu);
                }
                else{
                    for(int i=0; i<menu.size(); i++){
                        if(menu.get(i).getItemName().toLowerCase(Locale.getDefault()).contains(text)){
                            menuTemp.add(menu.get(i));
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                list.setAdapter(adapter);


            }
        });

    }


    private class MyListAdapter extends ArrayAdapter<MenuItems>{

        public MyListAdapter() {
            super(AddNewOrderActivity.this, R.layout.list_view_layout, menuTemp);
        }
        @Override
        public View getView(int position, View convertView,ViewGroup parent){
            View itemView = convertView;
            if(itemView==null){
                itemView = getLayoutInflater().inflate(R.layout.list_view_layout,parent,false);
            }
            MenuItems tempItem = menuTemp.get(position);
            TextView code = (TextView)itemView.findViewById(R.id.tvMealCode);
            TextView name = (TextView)itemView.findViewById(R.id.tvMealName);
            TextView description = (TextView)itemView.findViewById(R.id.tvMealDes);
            code.setText("00"+(position+1));
            name.setText(tempItem.getItemName());
            description.setText(tempItem.getItemDescription());
            return itemView;

        }
    }
}
