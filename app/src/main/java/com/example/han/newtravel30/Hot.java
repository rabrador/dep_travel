package com.example.han.newtravel30;

/**
 * Created by z3632 on 2017/5/20.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class Hot extends AppCompatActivity {

    private ImageButton btnA;
    private ImageButton btnB;
    private ImageButton btnC;
    private ImageButton btnD;

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
        btnA.setOnClickListener(new View.OnClickListener() {
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

        btnB.setOnClickListener(new View.OnClickListener() {
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

        btnC.setOnClickListener(new View.OnClickListener() {
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

        btnD.setOnClickListener(new View.OnClickListener() {
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
        btnA = (ImageButton) findViewById(R.id.imgBtnA);
        btnB = (ImageButton) findViewById(R.id.imgBtnB);
        btnC = (ImageButton) findViewById(R.id.imgBtnC);
        btnD = (ImageButton) findViewById(R.id.imgBtnD);
    }

}
