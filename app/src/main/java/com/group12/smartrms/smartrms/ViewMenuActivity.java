package com.group12.smartrms.smartrms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ViewMenuActivity extends Activity {
    private String userID = "";

    private ArrayList<MenuItems> menu = new ArrayList<MenuItems>();
    private List<MenuItems> menuTemp = new ArrayList<MenuItems>();

    ArrayAdapter<MenuItems> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);

        Intent prIntent = getIntent();
        userID = prIntent.getStringExtra("userID");
        menu = (ArrayList<MenuItems>)prIntent.getSerializableExtra("menu");
        menuTemp.addAll(menu);

        final ListView list = (ListView)findViewById(R.id.lvMenu1);
        adapter = new MyListAdapter();
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

        final EditText search = (EditText)findViewById(R.id.etSearch1);
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
                if (text.length() == 0) {
                    menuTemp.addAll(menu);
                } else {
                    for (int i = 0; i < menu.size(); i++) {
                        if (menu.get(i).getItemName().toLowerCase(Locale.getDefault()).contains(text)) {
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
            super(ViewMenuActivity.this, R.layout.list_view_layout2, menuTemp);
        }
        @Override
        public View getView(final int position, View convertView,ViewGroup parent){
            View itemView = convertView;
            if(itemView==null){
                itemView = getLayoutInflater().inflate(R.layout.list_view_layout2,parent,false);
            }
            MenuItems tempItem = menuTemp.get(position);
            TextView code = (TextView)itemView.findViewById(R.id.tvMealCode1);
            TextView name = (TextView)itemView.findViewById(R.id.tvMealName1);
            TextView type = (TextView)itemView.findViewById(R.id.tvMealType1);
            TextView des = (TextView)itemView.findViewById(R.id.tvMealDes1);
            TextView price = (TextView)itemView.findViewById(R.id.tvMealPrice1);

            code.setText(""+(position+1));
            name.setText(tempItem.getItemName());
            type.setText(tempItem.getItemType());
            des.setText(tempItem.getItemDescription());
            price.setText("RS "+tempItem.getItemPrice());
            return itemView;
        }
    }
}
