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

public class ViewOrderItemsActivity extends Activity {

    String orderNum;

    ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
    ArrayList<MenuItems> menu = new ArrayList<MenuItems>();
    ArrayAdapter<OrderItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_items);

        Intent prIntent = getIntent();
        orderNum = prIntent.getStringExtra("OrderNum");
        orderItems = (ArrayList<OrderItem>)prIntent.getSerializableExtra("OrderItems");
        menu = (ArrayList<MenuItems>)prIntent.getSerializableExtra("menu");

        TextView order_n =(TextView)findViewById(R.id.tvOrderNumber);
        order_n.setText("Order "+orderNum);

        final ListView list = (ListView)findViewById(R.id.lvOrderItems);
        adapter = new MyListAdapter();
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

    }


    private class MyListAdapter extends ArrayAdapter<OrderItem>{

        public MyListAdapter() {
            super(ViewOrderItemsActivity.this, R.layout.list_view_layout4,orderItems);
        }
        @Override
        public View getView(final int position, View convertView,ViewGroup parent){
            View itemView = convertView;
            if(itemView==null){
                itemView = getLayoutInflater().inflate(R.layout.list_view_layout4,parent,false);
            }

            TextView code = (TextView)itemView.findViewById(R.id.tvItemCode);
            TextView name = (TextView)itemView.findViewById(R.id.tvItemName1);
            TextView qty = (TextView)itemView.findViewById(R.id.tvQty);

            code.setText(orderItems.get(position).getItemId());
            qty.setText(orderItems.get(position).getItemQty());

            for(int i = 0; i<menu.size(); i++){
                if(menu.get(i).getItemCode().equals(orderItems.get(position).getItemId())){
                    name.setText(menu.get(i).getItemName());
                    break;
                }
            }

            return itemView;
        }
    }

}
