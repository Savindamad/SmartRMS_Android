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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TableType1Activity extends AppCompatActivity {
    String userID = "";
    MenuItems[][] menuItems;
    RequestQueue requestQueue;

    StringRequest request;

    private static final String URL = "http://smartrmswebb.azurewebsites.net/checkTable.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_type1);

        Intent prIntent = getIntent();
        userID = prIntent.getStringExtra("userID");
        Object[] menu = (Object[]) prIntent.getExtras().getSerializable("MenuArray");

        if(menu!=null){
            menuItems = new MenuItems[menu.length][];
            for(int i=0;i<menu.length;i++){
                menuItems[i]=(MenuItems[]) menu[i];
            }
        }
        requestQueue = Volley.newRequestQueue(this);
    }


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

        /*CheckTable checkTable = new CheckTable(this);
        Intent intent = new Intent(TableType1Activity.this,OrderActivity.class);
        checkTable.setIntent(intent);
        checkTable.execute(userID,tableNum);*/

        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (!(jsonObject.names().get(0).equals("error"))) {
                        String temp = jsonObject.getString("waiter_id");
                        if(userID.equals(temp) || temp.equals("0")){
                            LoadActivity();
                        }
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
                } catch (JSONException e) {
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
    public void LoadActivity(){
        Intent intent1 = new Intent(TableType1Activity.this,OrderActivity.class);
        intent1.putExtra("userID",userID);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MenuArray",menuItems);
        intent1.putExtras(bundle);
        startActivity(intent1);
    }
}
