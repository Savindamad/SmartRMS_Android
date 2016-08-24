package com.group12.smartrms.smartrms;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    private static final String URL = "http://smartrmswebb.azurewebsites.net/loginNew.php";
    private static final String URL1 = "http://smartrmswebb.azurewebsites.net/getMenu.php";
    String username, password;
    StringRequest request;
    StringRequest request1;
    private RequestQueue requestQueue;
    private RequestQueue requestQueue1;

    final String[][] menuArray = new String[1][5];
    String[] menuArrayCode = new String[1];
    String[] menuArrayName = new String[1];
    String[] menuArrayType = new String[1];
    String[] menuArrayDescription = new String[1];
    String[] menuArrayPrice = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        System.out.println("a");
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        System.out.println("b");
        final Button bLogin = (Button) findViewById(R.id.bLogin);

        requestQueue = Volley.newRequestQueue(this);

        bLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("d");
                            JSONObject jsonObject = new JSONObject(response);
                            if(!(jsonObject.names().get(0).equals("fail"))){
                                String userID = jsonObject.getString("user_id");
                                String name = jsonObject.getString("f_name")+jsonObject.getString("l_name");

                                getMenu();

                                Intent intent = new Intent(LoginActivity.this,UserAreaActivity.class);
                                intent.putExtra("userID",userID);
                                intent.putExtra("Name",name);
                                intent.putExtra("menuArrayCode",menuArrayCode);
                                intent.putExtra("menuArrayName",menuArrayName);
                                intent.putExtra("menuArrayType",menuArrayType);
                                intent.putExtra("menuArrayDescription",menuArrayDescription);
                                intent.putExtra("menuArrayPrice",menuArrayPrice);

                                startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                                builder1.setTitle("Login fail");
                                builder1.setMessage("Incorrect username or password..");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Retry",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                etUsername.setText("");
                                                etPassword.setText("");
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();

                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                            builder1.setTitle("Login fail");
                            builder1.setMessage("Network error..");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Retry",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            etUsername.setText("");
                                            etPassword.setText("");
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
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        System.out.println("f");
                        HashMap<String,String> hashMap = new HashMap<String, String>();
                        hashMap.put("username",etUsername.getText().toString()); //todo encript username and password
                        hashMap.put("password",etPassword.getText().toString());
                        return hashMap;
                    }
                };
                requestQueue.add(request);
            }
        });
    }
    private void getMenu(){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,URL1,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray menuItems = response.getJSONArray("menu_items");

                    menuArrayCode = new String[menuItems.length()];
                    menuArrayName = new String[menuItems.length()];
                    menuArrayType = new String[menuItems.length()];
                    menuArrayDescription = new String[menuItems.length()];
                    menuArrayPrice = new String[menuItems.length()];

                    for(int i=0; i<menuItems.length(); i++){
                        JSONObject menuItem = menuItems.getJSONObject(i);
                        menuArrayCode[i]=menuItem.getString("menu_item_code");
                        menuArrayName[i]=menuItem.getString("name");
                        menuArrayType[i]=menuItem.getString("type");
                        menuArrayDescription[i]=menuItem.getString("description");
                        menuArrayPrice[i]=menuItem.getString("price");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue1.add(jsonObjectRequest);
    }

}
