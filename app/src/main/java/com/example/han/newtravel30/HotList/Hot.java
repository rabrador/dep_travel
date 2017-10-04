package com.example.han.newtravel30.HotList;

/**
 * Created by z3632 on 2017/5/20.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.han.newtravel30.R;


public class Hot extends AppCompatActivity {

    private ImageButton imageButtonCulture;
    private ImageButton imageButtonBike;
    private ImageButton imageButtonNature;
    private ImageButton imageButtonPoint;

    public static int REQUEST_CLASSIFY = 1;
    public enum classifyItemEnum {
        CLASSIFY_NO_SUCH, // = ALL, 0
        CLASSIFY_TEMPLE,   // 寺廟
        CLASSIFY_PARK,     // 公園
        CLASSIFY_NATURE,  // 自然
        CLASSIFY_SCENE,    // 風景
        CLASSIFY_OTHER,
        CLASSIFY_NUMBER,
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot);

        init_view();

        registerBtnHandle();
    }

    private void registerBtnHandle() {
        imageButtonCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Hot.this, HotListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("classify", classifyItemEnum.CLASSIFY_TEMPLE.ordinal());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        imageButtonBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Hot.this, HotListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("classify", classifyItemEnum.CLASSIFY_PARK.ordinal());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        imageButtonNature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Hot.this, HotListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("classify", classifyItemEnum.CLASSIFY_NATURE.ordinal());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        imageButtonPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Hot.this, HotListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("classify", classifyItemEnum.CLASSIFY_SCENE.ordinal());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void init_view() {
        imageButtonCulture = (ImageButton) findViewById(R.id.imageButtonCulture);
        imageButtonBike = (ImageButton) findViewById(R.id.imageButtonBike);
        imageButtonNature = (ImageButton) findViewById(R.id.imageButtonNature);
        imageButtonPoint = (ImageButton) findViewById(R.id.imageButtonPoint);
    }

}
