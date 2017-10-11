package com.example.han.newtravel30.HotList;

/**
 * Created by z3632 on 2017/5/20.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.han.newtravel30.AR.Touris;
import com.example.han.newtravel30.AR.useAPI;
import com.example.han.newtravel30.R;

import java.io.InputStream;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class HotListActivity extends AppCompatActivity {
    private RecyclerView mList;
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
        for (int i=0; i<dbTourism.size();i++) {
            if (dbTourism.get(i).getClassification() == bundle.getInt("classify")) {
                Touris tourism = new Touris(dbTourism.get(i).getName(), "", dbTourism.get(i).getIntroduction(), dbTourism.get(i).getAddress(),
                        dbTourism.get(i).getLongitude(), dbTourism.get(i).getLatitude(), dbTourism.get(i).getImageURL(), dbTourism.get(i).getClassification());

                displayTourism.add(tourism);
            }
        }

        /* Sort by Name */
        Collections.sort(displayTourism, new Comparator<Touris>(){
            @Override
            public int compare(Touris o1, Touris o2) {
                Collator instance = Collator.getInstance(Locale.TAIWAN);
                return instance.compare(o1.getName(), o2.getName());
            }
        });

        // insert to ListView
        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        myListAdapter myAdapter = new myListAdapter(displayTourism);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(myAdapter);
    }

    public class myListAdapter extends RecyclerView.Adapter<myListAdapter.ViewHolder> {
        List<Touris> rowItem;

        /* Step 1 */
        public myListAdapter(List<Touris> data) {
            rowItem = data;
        }

        /* Step 2 */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.hotlist, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        /* Step 3 */
        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView mImageURL;
            public TextView mTextViewName;
            public TextView mTextViewAddress;
            public ViewHolder(View v) {
                super(v);
                mImageURL = (ImageView) v.findViewById(R.id.imageViewThum);
                mTextViewName = (TextView) v.findViewById(R.id.textViewName);
                mTextViewAddress = (TextView) v.findViewById(R.id.textViewAddress);
            }
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    final Bitmap bitmap = useAPI.getImageFromURL(rowItem.get(position).getImageURL(),
                            HotListActivity.this.getResources(), R.drawable.image_list_default, HotListActivity.this);

                    // Display image
                    HotListActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.mImageURL.setImageBitmap(bitmap);
                        }
                    });
                }
            }).start();

            holder.mTextViewName.setText(rowItem.get(position).getName());
            holder.mTextViewAddress.setText(rowItem.get(position).getAddress());

            /* Click Event */
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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

        @Override
        public int getItemCount() {
            return rowItem.size();
        }
    }

    private void init_view() {
        mList = (RecyclerView ) findViewById(R.id.listClassify);
    }

    public InputStream loadRawFile() {
        return getResources().openRawResource(R.raw.data);
    }
}
