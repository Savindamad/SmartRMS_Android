package com.group12.smartrms.smartrms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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

public class TableType2Activity extends AppCompatActivity {
    String userID = "";
    ArrayList<MenuItems> menu = new ArrayList<MenuItems>();
    RequestQueue requestQueue;

    StringRequest request;

    private static final String URL = "http://smartrmswebb.azurewebsites.net/checkTable.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_type2);

        Intent prIntent = getIntent();
        userID = prIntent.getStringExtra("userID");
        menu = (ArrayList<MenuItems>)prIntent.getSerializableExtra("menu");
        requestQueue = Volley.newRequestQueue(this);
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

    public void AccessTable(final String tableNum){

        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (!(jsonObject.names().get(0).equals("error"))) {
                        String temp = jsonObject.getString("waiter_id");
                        if(userID.equals(temp) || temp.equals("0")){
                            LoadActivity(tableNum);
                        }
                        else{
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(TableType2Activity.this);
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
                    else {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(TableType2Activity.this);
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
                } catch (JSONException e) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(TableType2Activity.this);
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
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("tableNum", tableNum);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }
    public void LoadActivity(String tableNum){
        Intent intent1 = new Intent(TableType2Activity.this,OrderActivity.class);
        intent1.putExtra("userID",userID);
        intent1.putExtra("tableNum",tableNum);
        intent1.putExtra("menu",menu);
        startActivity(intent1);
    }
}
