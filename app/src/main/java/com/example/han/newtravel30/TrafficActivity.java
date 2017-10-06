package com.example.han.newtravel30;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class TrafficActivity extends AppCompatActivity {
    private ImageButton imageBtnBike;
    private ImageButton imageBtnBus;
    private ImageButton imageBtnTrain;
//    public GridView gTraffic;
//    public final String[] list = new String[]{"單車", "客運", "台鐵", "道路災情", "即時路況資訊"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);

        init_view();
        registerBtnHandle();
        
//        gTraffic = (GridView) findViewById(R.id.gridview);
//        gTraffic.setOnItemClickListener(this);

//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list); // simple_list_item_1內建樣式
//        gTraffic.setNumColumns(3); // 列數
//        gTraffic.setAdapter(adapter);
    }

    private void init_view() {
        imageBtnBike = (ImageButton) findViewById(R.id.imageButtonBike);
        imageBtnBus = (ImageButton) findViewById(R.id.imageButtonBus);
        imageBtnTrain = (ImageButton) findViewById(R.id.imageButtonTrain);
    }

    private void registerBtnHandle() {
        imageBtnBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bike = "http://pbike.pthg.gov.tw/Station/Map.aspx"; //PBIKE的租賃地點
                Intent i = new Intent(TrafficActivity.this, TransWeb.class);
                i.putExtra("uri", bike);
                startActivity(i);
                Toast.makeText(TrafficActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
            }
        });

        imageBtnBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bus = "http://taiwanbus.tw/Route.aspx?bus=%E5%B1%8F%E6%9D%B1%E5%AE%A2%E9%81%8B&Lang="; //屏東公車的班次、站別
                Intent i = new Intent(TrafficActivity.this, TransWeb.class);
                i.putExtra("uri", bus);
                startActivity(i);
                Toast.makeText(TrafficActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
            }
        });

        imageBtnTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String train = "http://twtraffic.tra.gov.tw/twrail/"; //台鐵查詢時刻表
                Intent i = new Intent(TrafficActivity.this, TransWeb.class);
                i.putExtra("uri", train);
                startActivity(i);
                Toast.makeText(TrafficActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(TrafficActivity.this, "你選擇的是: " + list[position], Toast.LENGTH_SHORT).show();
//
//        if (position == 0) {
//            // System.out.println(list[p]);
//            String bike = "http://pbike.pthg.gov.tw/Station/Map.aspx"; //PBIKE的租賃地點
//            Intent i = new Intent(TrafficActivity.this, TransWeb.class);
//            i.putExtra("uri", bike);
//            startActivity(i);
//        } else if (position == 1) {
//            // System.out.println(list[p]);
//            String bus = "http://taiwanbus.tw/Route.aspx?bus=%E5%B1%8F%E6%9D%B1%E5%AE%A2%E9%81%8B&Lang="; //屏東公車的班次、站別
//            Intent i = new Intent(TrafficActivity.this, TransWeb.class);
//            i.putExtra("uri", bus);
//            startActivity(i);
//        } else if (position == 2) {
//            // System.out.println(list[p]);
//            String train = "http://twtraffic.tra.gov.tw/twrail/"; //台鐵查詢時刻表
//            Intent i = new Intent(TrafficActivity.this, TransWeb.class);
//            i.putExtra("uri", train);
//            startActivity(i);
//        } else if (position == 3) {
//            // System.out.println(list[p]);
//            String bobe = "https://bobe168.tw/"; //道路災情
//            Intent i = new Intent(TrafficActivity.this, TransWeb.class);
//            i.putExtra("uri", bobe);
//            startActivity(i);
//        } else if (position == 4) {
//            // System.out.println(list[p]);
//            String thb = "http://168.thb.gov.tw/thb/navigate.do#"; //省道路況資訊
//            Intent i = new Intent(TrafficActivity.this, TransWeb.class);
//            i.putExtra("uri", thb);
//            startActivity(i);
//        }
//        /*
//        Uri uri = Uri.parse("http://pbike.pthg.gov.tw/Station/Map.aspx");
//        // 建立一個 Intent 用來表示要從現在這頁跳到文章閱讀頁 TextActivity
//        Intent intent = new Intent(Transportation.this, Web.class);
//        // 使用準備好的 Intent 來開啟新的頁面
//        startActivity(intent);
//        */
//    }
}
