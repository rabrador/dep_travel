package com.example.han.newtravel30;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class ChaoZhou extends AppCompatActivity {

    private String data;
    //private ListView List;
    private TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chao_zhou);

        initView();
        covRawToString(loadRawFile());
        parserJson(data);
    }

    //讀取Json檔
    public InputStream loadRawFile() {
        return getResources().openRawResource(R.raw.attractions);
    }

    public String covRawToString(InputStream inputStream) {

        try {
            int size = inputStream.available();

            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            data = new String(buffer, "UTF-8");
            txt.setText(data);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    //解析Json檔
    public void parserJson(String sJson) {
        try {
            JSONObject obj = new JSONObject(sJson);
            ;
            JSONArray array = obj.getJSONArray("data");


            for (int i = 0; i < array.length(); i++) {

                JSONObject jsonObject = array.getJSONObject(i);

                String name = jsonObject.getString("Name");
                String fulladdress = jsonObject.getString("FullAddress");
                String introduction = jsonObject.getString("Introduction");
                String nlat = jsonObject.getString("Nlat");
                String elong = jsonObject.getString("Elong");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        txt = (TextView) findViewById(R.id.textView);
        //List = (ListView) findViewById(R.id.chaozhoulist);
    }
}
