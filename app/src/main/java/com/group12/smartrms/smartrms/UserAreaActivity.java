package com.group12.smartrms.smartrms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserAreaActivity extends Activity {
    private String userID;
    private String name;
    private String[] menuCode;
    private String[] menuName;
    private String[] menuType;
    private String[] menuDescription;
    private String[] menuPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        name = intent.getStringExtra("Name");
        menuCode =intent.getStringArrayExtra("menuArrayCode");
        menuName =intent.getStringArrayExtra("menuArrayName");
        menuType =intent.getStringArrayExtra("menuArrayType");
        menuDescription =intent.getStringArrayExtra("menuArrayDescription");
        menuPrice =intent.getStringArrayExtra("menuArrayPrice");

        final Button bLogout = (Button)findViewById(R.id.bLogout);
        final Button bTableTypes = (Button)findViewById(R.id.bTableTypes);
        final TextView etName = (TextView)findViewById(R.id.lName);

        etName.setText(name);
    }

    public void Logout(View view) {
        Intent intent = new Intent(UserAreaActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void TableTypes(View view) {
        Intent intent = new Intent(UserAreaActivity.this,TableType1Activity.class);
        intent.putExtra("userID",userID);
        startActivity(intent);
    }
}
