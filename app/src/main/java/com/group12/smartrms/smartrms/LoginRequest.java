package com.group12.smartrms.smartrms;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Savinda Keshan on 8/10/2016.
 */
public class LoginRequest extends AsyncTask<String,Void,String>{

    Context ctx;
    String user_ID = "";
    boolean flag = false;
    String username,password, loginURL;
    Intent intent;

    LoginRequest(Context ctx){
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        loginURL = "http://smartrmswebb.azurewebsites.net/login.php";
        username = params[0];
        password = params[1];

        try {
            URL url = new URL(loginURL);
            HttpURLConnection httpURLConnection1;
            httpURLConnection1=(HttpURLConnection) url.openConnection();

            httpURLConnection1.setRequestMethod("POST");
            httpURLConnection1.setDoOutput(true);
            httpURLConnection1.setDoInput(true);
            OutputStream outputStream = httpURLConnection1.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection1.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String response = "";
            String line = "";

            while((line=bufferedReader.readLine())!=null){
                response+=line;
            }
            System.out.println(response);
            bufferedReader.close();
            inputStream.close();
            flag = true;
            return response;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  "null";
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        user_ID = result;
        if(user_ID.equals("Login fail...") || user_ID.equals("null")){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(ctx);
            builder1.setTitle("Login fail..");
            if(user_ID.equals("Login fail...")){
                builder1.setMessage("Incorrect username or password");
            }
            else{
                builder1.setMessage("network problem has been occurred");
            }
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Try again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();

        } else {
            intent.putExtra("userID", user_ID);
            ctx.startActivity(intent);
        }
    }
    public void setIntent(Intent intent){
        this.intent=intent;
    }

}
