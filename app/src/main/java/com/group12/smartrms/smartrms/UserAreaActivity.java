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

import java.util.ArrayList;

public class UserAreaActivity extends Activity {

    private String userID;
    private String name;
    ArrayList<MenuItems> menu = new ArrayList<>();

    StringRequest request;
    private RequestQueue requestQueue;
    private static final String URL = "http://smartrmswebb.azurewebsites.net/getMenu.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        Intent intentpr = getIntent();
        userID = intentpr.getStringExtra("userID");
        name = intentpr.getStringExtra("Name");


        final Button bTableTypes = (Button)findViewById(R.id.bTableTypes);
        final TextView etName = (TextView)findViewById(R.id.lName);
        etName.setText(name);

    }
    void getMenu(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray menuItems = response.getJSONArray("menu_items");
                    int size = menuItems.length();

                    for (int i = 0; i < size; i++) {

                        JSONObject menuItem = menuItems.getJSONObject(i);
                        String itemCode = menuItem.getString("menu_item_code");
                        String itemName = menuItem.getString("name");
                        String itemType = menuItem.getString("type");
                        String itemDescription = menuItem.getString("description");
                        String itemPrice = menuItem.getString("price");
                        menu.add(new MenuItems(itemCode,itemName,itemType,itemDescription,itemPrice));

                    }

                    loadPage();

                } catch (JSONException e) {
                    System.out.println("error "+e);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error user area "+error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    public void Logout(View view) {
        Intent intent = new Intent(UserAreaActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    void loadPage(){
        Intent intent = new Intent(UserAreaActivity.this,TableTypeActivity.class);
        intent.putExtra("userID", userID);
        intent.putExtra("menu", menu);
        startActivity(intent);
    }

    public void tableTypes(View view) {
        getMenu();
    }
}
