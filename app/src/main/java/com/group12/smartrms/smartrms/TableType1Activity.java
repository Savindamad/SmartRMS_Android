package com.group12.smartrms.smartrms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TableType1Activity extends AppCompatActivity {
    String userID = "";
    String tempTableNum = "";

    ArrayList<MenuItems> menu = new ArrayList<MenuItems>();
    ArrayList<Order> AllOrders = new ArrayList<Order>();
    RequestQueue requestQueue;
    RequestQueue requestQueue1;
    RequestQueue requestQueue2;

    StringRequest request;
    StringRequest request1;

    private static final String URL = "http://smartrmswebb.azurewebsites.net/checkTable.php";
    private static final String URL1 = "http://smartrmswebb.azurewebsites.net/updateTable.php";
    private static final String URL2 = "http://smartrmswebb.azurewebsites.net/getAllOrders.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_type1);

        //get userId and menu from previous Activity
        Intent prIntent = getIntent();
        userID = prIntent.getStringExtra("userID");
        menu = (ArrayList<MenuItems>)prIntent.getSerializableExtra("menu");

        requestQueue = Volley.newRequestQueue(this);
        requestQueue1 = Volley.newRequestQueue(this);
        requestQueue2 =Volley.newRequestQueue(this);

    }

    //click table
    public void SelectTable1(View view) {
        AccessTable("1");
    }
    public void SelectTable2(View view) {
        AccessTable("2");
    }
    public void SelectTable3(View view) {
        AccessTable("3");
    }
    public void SelectTable4(View view) {
        AccessTable("4");
    }
    public void SelectTable5(View view) {
        AccessTable("5");
    }
    public void SelectTable6(View view) {
        AccessTable("6");
    }
    public void SelectTable7(View view) {
        AccessTable("7");
    }
    public void SelectTable8(View view) {
        AccessTable("8");
    }

    public void AccessTable(final String tableNum){

        tempTableNum = tableNum;
        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    //json response
                    if (!(jsonObject.names().get(0).equals("error"))) {
                        String temp = jsonObject.getString("waiter_id");
                        //waiter can access table
                        if(userID.equals(temp) || temp.equals("0")){
                            //update table_type table
                            if(temp.equals("0")){
                                updateTable(tableNum);
                            }
                            //start OrderActivity
                            else{
                                GetAllOrders(tableNum);
                            }

                        }
                        //waiter cannot access table
                        else{
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(TableType1Activity.this);
                            builder1.setTitle("Can not access");
                            builder1.setMessage("you can not access this table");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Okay",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    }
                    // database error --> table_type table
                    else {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(TableType1Activity.this);
                        builder1.setTitle("Error");
                        builder1.setMessage("Database error..");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Retry",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                }
                //Network error
                catch (JSONException e) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(TableType1Activity.this);
                    builder1.setTitle("Error");
                    builder1.setMessage("Network error..");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Retry",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}})
        {
            @Override
            //map table_number
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("tableNum", tableNum);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    //update table_type table in database
    public void updateTable(final String tableNum){

        request1 = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println(jsonObject.names().get(0));

                    //update successful
                    if(jsonObject.names().get(0).equals("success")){
                        GetAllOrders(tempTableNum);
                    }

                    //update not successful
                    else{
                        //todo cannot find table
                        //db error
                    }
                }
                //Network error
                catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("error "+e);
                    //todo network error
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {System.out.println("volley error "+error);}})
        {
            //map userId and tableNum
            protected Map<String,String> getParams() throws AuthFailureError{
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("userId", userID);
                hashMap.put("tableNum",tableNum);
                return hashMap;
            }
        };
        requestQueue1.add(request1);
    }

    //start OrderActivity

    public void GetAllOrders(final String tempTableNum){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("table_no", tempTableNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL2,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            //response --> json array
            public void onResponse(JSONObject response) {
                try {
                    JSONArray allOrders = response.getJSONArray("orders");
                    int size = allOrders.length();
                    if(!(allOrders.get(0).equals("none"))) {
                        for (int i = 0; i < size; i++) {

                            Order orderObj = new Order();
                            String OrderId;
                            String OrderStatus;

                            JSONArray orders = allOrders.getJSONArray(i);

                            int size1 = orders.length();
                            for (int j = 0; j < size1; j++) {
                                JSONObject order = orders.getJSONObject(j);
                                if (j == 0) {
                                    OrderId = order.getString("order_no");
                                    //OrderStatus = order.getString("status");

                                    orderObj.setOrderId(OrderId);

                                }
                                String item_Code = order.getString("item_id");
                                String item_Qty = order.getString("quantity");

                                OrderItem orderItem = new OrderItem(item_Code, item_Qty);
                                orderObj.addOrderItem(orderItem);
                            }
                            AllOrders.add(orderObj);
                        }
                    }
                    System.out.println("no 14 size" + AllOrders.size());
                    LoadActivity(tempTableNum);
                }
                catch (JSONException e) {
                        e.printStackTrace();
                }
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });


        requestQueue.add(jsonObjectRequest);
    }
    public void LoadActivity(String tableNum){
        Intent intent1 = new Intent(TableType1Activity.this,OrderActivity.class);
        intent1.putExtra("userID",userID);
        intent1.putExtra("tableNum", tableNum);
        intent1.putExtra("menu", menu);
        intent1.putExtra("AllOrders", AllOrders);
        startActivity(intent1);
        System.out.println("no 19");
    }
}
