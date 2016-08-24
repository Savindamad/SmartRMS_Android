package com.group12.smartrms.smartrms;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

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
public class CheckTable extends AsyncTask<String,Void,String> {

    Context ctx;
    CheckTable(Context ctx){
        this.ctx=ctx;
    }
    Intent intent;

    String userID;
    String tableNum;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String checkTableUrl = "http://smartrmswebb.azurewebsites.net/checkTable.php";
        userID = params[0];
        tableNum = params[1];

        try {
            URL url = new URL(checkTableUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

            String data = URLEncoder.encode("tableNum", "UTF-8")+"="+URLEncoder.encode(tableNum,"UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String response = "";
            String line = "";

            while((line=bufferedReader.readLine())!=null){
                response+=line;
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return response;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "none";
    }

    @Override
    protected void onPostExecute(String result) {
        if(userID.equals(result) || result.equals("0")){
            if(result.equals("0")){
                UpdateTable updateTable = new UpdateTable();
                updateTable.execute(userID, tableNum);
            }
            intent.putExtra("userID", userID);
            intent.putExtra("tableNum",tableNum);
            ctx.startActivity(intent);

        }
        else{
            //to do(send message to particular waiter)
        }
    }

    public void setIntent(Intent intent){
        this.intent=intent;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
