package com.example.han.newtravel30.HotList;

/**
 * Created by z3632 on 2017/5/20.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.han.newtravel30.AR.Touris;
import com.example.han.newtravel30.AR.useAPI;
import com.example.han.newtravel30.R;

import java.io.InputStream;
import java.util.ArrayList;

public class HotListActivity extends AppCompatActivity {
    private ListView mList;
    ArrayList<Touris> dbTourism = new ArrayList<>();
    ArrayList<Touris> displayTourism = new ArrayList<>();
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_list);

        init_view();

        // Receive request from Hot.java
        Bundle bundle = this.getIntent().getExtras();

        // Load Raw file and covert to String
        data = useAPI.covRawToString(loadRawFile());

        // Parser json data
        dbTourism = useAPI.parserJsonFromTouris(data);

        // Specify the classify of tourism
        for (int i=0; i<dbTourism.size();i++) {
            if (dbTourism.get(i).getName().indexOf("宮") > 0 || dbTourism.get(i).getName().indexOf("廟") > 0 ) {
                dbTourism.get(i).setClassification(Hot.classifyItemEnum.CLASSIFY_TEMPLE.ordinal()); //  CLASSIFY_TEMPLE : 宮廟
                continue;
            }

            if (dbTourism.get(i).getName().indexOf("公") > 0 && dbTourism.get(i).getName().indexOf("園") > 0) {
                dbTourism.get(i).setClassification(Hot.classifyItemEnum.CLASSIFY_PARK.ordinal()); // CLASSIFY_PARK : 公園
                continue;
            }

            if (dbTourism.get(i).getName().indexOf("農") > 0) {
                dbTourism.get(i).setClassification(Hot.classifyItemEnum.CLASSIFY_NATURE.ordinal()); // CLASSIFY_NATURE : 農場
                continue;
            }

            if (dbTourism.get(i).getName().indexOf("景") > 0 || dbTourism.get(i).getName().indexOf("館") > 0) {
                dbTourism.get(i).setClassification(Hot.classifyItemEnum.CLASSIFY_SCENE.ordinal()); // CLASSIFY_SCENE : 風景
            }
        }

        // Classify tourism
        int newIndex = 0;
        for (int i=0; i<dbTourism.size();i++) {
            if (dbTourism.get(i).getClassification() == bundle.getInt("classify")) {
                Touris.createEmptyEntry(displayTourism);
                displayTourism.get(newIndex).setName(dbTourism.get(i).getName());
                displayTourism.get(newIndex).setIntroduction(dbTourism.get(i).getIntroduction());
                displayTourism.get(newIndex).setAddress(dbTourism.get(i).getAddress());
                displayTourism.get(newIndex).setImageURL(dbTourism.get(i).getImageURL());
                displayTourism.get(newIndex).setLongitude(dbTourism.get(i).getLongitude());
                displayTourism.get(newIndex).setLatitude(dbTourism.get(i).getLatitude());
                newIndex++;
            }
        }

        // insert to ListView
        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        ListAdapter adapter = new ListAdapter(this, displayTourism);
        mList.setAdapter(adapter);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(HotListActivity.this, ListContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", displayTourism.get(position).getName());
                bundle.putString("introduction", displayTourism.get(position).getIntroduction());
                bundle.putString("imageURL", displayTourism.get(position).getImageURL());
                bundle.putString("Elong", displayTourism.get(position).getLongitude());
                bundle.putString("Nlat", displayTourism.get(position).getLatitude());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void init_view() {
        mList = (ListView) findViewById(R.id.listClassify);
    }

    public InputStream loadRawFile() {
        return getResources().openRawResource(R.raw.data);
    }
}
