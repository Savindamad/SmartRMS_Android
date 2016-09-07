package com.group12.smartrms.smartrms;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddNewOrderActivity extends FragmentActivity {

    private String userID = "";
    private String tableNum = "";
    private ArrayList<MenuItems> menu = new ArrayList<MenuItems>();
    private List<MenuItems> menuTemp = new ArrayList<MenuItems>();
    private ArrayList<MenuItems> order =new ArrayList<MenuItems>();

    ArrayAdapter<MenuItems> adapter;
    ArrayAdapter<MenuItems> adapter1;

    StringRequest request;
    private RequestQueue requestQueue;

    String URL = "http://smartrmswebb.azurewebsites.net/addNewOrder.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_order);

        requestQueue = Volley.newRequestQueue(this);

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

        for(int i=0;i<tab.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tab.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#ffffff"));
        }

        Intent prIntent = getIntent();
        userID = prIntent.getStringExtra("userID");
        tableNum = prIntent.getStringExtra("tableNum");
        menu = (ArrayList<MenuItems>)prIntent.getSerializableExtra("menu");
        menuTemp.addAll(menu);

        final ListView list = (ListView)findViewById(R.id.lvMenu);
        adapter = new MyListAdapter();
        list.setAdapter(adapter);

        final ListView list1 = (ListView)findViewById(R.id.lvOrder);
        adapter1 = new MyListAdapter1();
        list1.setAdapter(adapter1);

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

        Button AcceptOrder = (Button)findViewById(R.id.bAcceptOrder);
        AcceptOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(order.isEmpty()){
                    //todo
                }
                else{
                    System.out.println("order is not empty");
                    AddNewOrder();
                }
            }
        });

    }

    private class MyListAdapter extends ArrayAdapter<MenuItems>{

        public MyListAdapter() {
            super(AddNewOrderActivity.this, R.layout.list_view_layout, menuTemp);
        }
        @Override
        public View getView(final int position, View convertView,ViewGroup parent){
            View itemView = convertView;
            if(itemView==null){
                itemView = getLayoutInflater().inflate(R.layout.list_view_layout,parent,false);
            }
            MenuItems tempItem = menuTemp.get(position);
            TextView code = (TextView)itemView.findViewById(R.id.tvMealCode);
            TextView name = (TextView)itemView.findViewById(R.id.tvMealName1);
            TextView type = (TextView)itemView.findViewById(R.id.tvMealDes);
            Button Add = (Button)itemView.findViewById(R.id.bAdd);
            code.setText(""+(position+1));
            name.setText(tempItem.getItemName());
            type.setText(tempItem.getItemType());
            Add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    order.add(menuTemp.get(position));
                    for(int i = 0; i<menu.size(); i++){
                        if(menuTemp.get(position).getItemCode().equals(menu.get(i).getItemCode())){
                            menu.remove(i);
                            menuTemp.remove(position);
                            adapter.notifyDataSetChanged();
                            adapter1.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            });
            return itemView;
        }
    }
    private class MyListAdapter1 extends ArrayAdapter<MenuItems>{

        public MyListAdapter1() {
            super(AddNewOrderActivity.this, R.layout.list_view_layout1, order);
        }
        @Override
        public View getView(final int position, View convertView,ViewGroup parent){
            View itemView = convertView;
            if(itemView==null){
                itemView = getLayoutInflater().inflate(R.layout.list_view_layout1,parent,false);
            }
            MenuItems tempItem = order.get(position);
            TextView code = (TextView)itemView.findViewById(R.id.tvMealCode1);
            TextView name = (TextView)itemView.findViewById(R.id.tvMealName1);
            TextView type = (TextView)itemView.findViewById(R.id.tvMealDes1);

            code.setText("0"+(position+1));
            name.setText(tempItem.getItemName());
            type.setText(tempItem.getItemType());

            Button remove = (Button)itemView.findViewById(R.id.bRemove);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menu.add(order.get(position));
                    menuTemp.add(order.get(position));
                    order.remove(position);
                    adapter.notifyDataSetChanged();
                    adapter1.notifyDataSetChanged();
                }
            });

            final EditText itemQty = (EditText)itemView.findViewById(R.id.etItemQty);

            Button bPlus =(Button)itemView.findViewById(R.id.bPlus);
            Button bMinus = (Button)itemView.findViewById(R.id.bMinus);

            bPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Integer.parseInt(itemQty.getText().toString())<10){
                        itemQty.setText("" + (Integer.parseInt(itemQty.getText().toString()) + 1));
                        int tempInt = order.get(position).getItemQty();
                        order.get(position).setItemQty(tempInt + 1);
                    }
                }
            });

            bMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Integer.parseInt(itemQty.getText().toString())>1){
                        itemQty.setText(""+(Integer.parseInt(itemQty.getText().toString())-1));
                        int tempInt = order.get(position).getItemQty();
                        order.get(position).setItemQty(tempInt-1);
                    }
                }
            });

            return itemView;

        }
    }
    public void AddNewOrder() {
        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.names().get(0).equals("result")){
                        System.out.println("success");
                        //String orderNo = jsonObject.getString("");
                    }
                    else{
                        System.out.println("not success");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("tableNum", tableNum);
                hashMap.put("userId", userID);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }
    public void AddOrderItems(){

    }
}
