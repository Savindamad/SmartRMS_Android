package com.group12.smartrms.smartrms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class UserAreaActivity extends Activity {

    MenuItems[] menuItemArray = new MenuItems[1];
    String[] menuArrayCode = new String[1];
    String[] menuArrayName = new String[1];
    String[] menuArrayType = new String[1];
    String[] menuArrayDescription = new String[1];
    String[] menuArrayPrice = new String[1];

    private String userID;
    private String name;

    StringRequest request;
    private RequestQueue requestQueue;
    private static final String URL = "http://smartrmswebb.azurewebsites.net/getMenu.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        name = intent.getStringExtra("Name");

        final Button bTableTypes = (Button)findViewById(R.id.bTableTypes);
        final TextView etName = (TextView)findViewById(R.id.lName);
        etName.setText(name);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        bTableTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray menuItems = response.getJSONArray("menu_items");
                            int size = menuItems.length();
                            menuArrayCode = new String[size];
                            menuArrayName = new String[size];
                            menuArrayType = new String[size];
                            menuArrayDescription = new String[size];
                            menuArrayPrice = new String[size];
                            menuItemArray = new MenuItems[size];

                            for (int i = 0; i < size; i++) {
                                JSONObject menuItem = menuItems.getJSONObject(i);
                                menuItemArray[i] = new MenuItems(menuItem.getString("menu_item_code"),menuItem.getString("name"),menuItem.getString("type"),menuItem.getString("description"),menuItem.getString("price"));
                                menuArrayCode[i] = menuItem.getString("menu_item_code");
                                menuArrayName[i] = menuItem.getString("name");
                                menuArrayType[i] = menuItem.getString("type");
                                menuArrayDescription[i] = menuItem.getString("description");
                                menuArrayPrice[i] = menuItem.getString("price");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonObjectRequest);
                Intent intent1 = new Intent(UserAreaActivity.this,TableTypeActivity.class);
                intent1.putExtra("userID",userID);
                Bundle bundle = new Bundle();
                bundle.putSerializable("MenuArray",menuItemArray);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });
    }

    public void Logout(View view) {
        Intent intent = new Intent(UserAreaActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
