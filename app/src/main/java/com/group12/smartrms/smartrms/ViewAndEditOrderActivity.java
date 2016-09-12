package com.group12.smartrms.smartrms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewAndEditOrderActivity extends Activity {

    String userID = "";
    ArrayList<MenuItems> menu = new ArrayList<>();
    ArrayList<Order> AllOrders = new ArrayList<>();
    ArrayAdapter<Order> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_edit_order);

        Intent prIntent = getIntent();
        userID = prIntent.getStringExtra("userID");
        menu = (ArrayList<MenuItems>)prIntent.getSerializableExtra("menu");
        AllOrders = (ArrayList<Order>)prIntent.getSerializableExtra("AllOrders");
        System.out.println(AllOrders.size());

        final ListView list = (ListView)findViewById(R.id.lvOrderName);
        adapter = new MyListAdapter();
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Order>{

        public MyListAdapter() {
            super(ViewAndEditOrderActivity.this, R.layout.list_view_layout3,AllOrders);
        }
        @Override
        public View getView(final int position, View convertView,ViewGroup parent){
            View itemView = convertView;
            if(itemView==null){
                itemView = getLayoutInflater().inflate(R.layout.list_view_layout3,parent,false);
            }
            Order order = AllOrders.get(position);
            ArrayList<OrderItem> tempOrderItem = order.getOrderItems();
            TextView name = (TextView)itemView.findViewById(R.id.etOrderNo);
            Button bView = (Button)itemView.findViewById(R.id.bView);
            Button bCancel = (Button)itemView.findViewById(R.id.bCancel);

            bView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentnw = new Intent(ViewAndEditOrderActivity.this,ViewOrderItemsActivity.class);
                    intentnw.putExtra("OrderItems",AllOrders.get(position).getOrderItems());
                    startActivity(intentnw);
                }
            });

            bCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            name.setText("Order " + (position + 1));

            return itemView;

        }
    }

}
