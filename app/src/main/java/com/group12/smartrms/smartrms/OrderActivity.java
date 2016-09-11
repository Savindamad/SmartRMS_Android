package com.group12.smartrms.smartrms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    String userID = "";
    String tableNum = "";
    ArrayList<MenuItems> menu = new ArrayList<>();
    ArrayList<Order> AllOrders = new ArrayList<>();

    RequestQueue requestQueue1;
    StringRequest request1;

    private static final String URL = "http://smartrmswebb.azurewebsites.net/updateTable1.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        final TextView tabNum = (TextView)findViewById(R.id.tvTableNum);

        //get userId, tableNum, menu arrayList from previous Activity
        Intent prIntent = getIntent();
        userID = prIntent.getStringExtra("userID");
        tableNum = prIntent.getStringExtra("tableNum");
        menu = (ArrayList<MenuItems>)prIntent.getSerializableExtra("menu");

        //set TableNum label
        tabNum.setText("Table "+tableNum);

        requestQueue1 = Volley.newRequestQueue(this);

    }

    //start NewOrderActivity
    public void NewOrder(View view) {
        Intent intent1 = new Intent(OrderActivity.this,AddNewOrderActivity.class);
        intent1.putExtra("userID",userID);
        intent1.putExtra("menu",menu);
        intent1.putExtra("tableNum", tableNum);
        startActivity(intent1);
    }

    //Start viewAndCancelOrderActivity
    public void ViewAndCancelOrder(View view) {
        Intent intent1 = new Intent(OrderActivity.this,ViewAndEditOrderActivity.class);
        intent1.putExtra("userID",userID);
        intent1.putExtra("menu", menu);
        startActivity(intent1);
    }

    //start ViewMenuActivity
    public void ViewMenu(View view){
        Intent intent1 = new Intent(OrderActivity.this,ViewMenuActivity.class);
        intent1.putExtra("menu",menu);
        startActivity(intent1);
    }

    //Notify to cleaner (send msg)
    public void NotifyCleaner(View view) {
        //todo
    }

    //Set table as free
    public void OrderFinished(View view) {
        updateTable();
    }

    //update table_type table in DB
    public void updateTable(){
        request1 = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println(jsonObject.names().get(0));
                    if(jsonObject.names().get(0).equals("success")){
                        updateOrder();
                    }
                    else{
                        //todo cannot find table
                        //db error
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("error "+e);
                    //todo network error
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {System.out.println("volley error "+error);}})
        {   //map tableNum
            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("tableNum",tableNum);
                return hashMap;
            }
        };
        System.out.println("table num "+tableNum+" user id "+userID);
        requestQueue1.add(request1);
    }

    //Add new Order
    public void updateOrder(){
        //todo
        loadActivity();
    }

    //start TableTypeActivity
    public void loadActivity(){
        Intent intent1 = new Intent(OrderActivity.this,TableTypeActivity.class);
        intent1.putExtra("userID",userID);
        intent1.putExtra("menu", menu);
        startActivity(intent1);
    }
}
