package com.example.appthibanglaixe.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appthibanglaixe.Adapter.OnLyThuyetAdapter;
import com.example.appthibanglaixe.R;
import com.example.appthibanglaixe.data.sqDuLieu;
import com.example.appthibanglaixe.model.cauhoi_traloi;
import com.example.appthibanglaixe.model.lythuyet;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class HocLyThuyetActivity extends AppCompatActivity {
    Toolbar toobarhoclythuet;
    ListView lstlythuet;
    sqDuLieu dulieu;
    OnLyThuyetAdapter adapter;
    int index ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoc_ly_thuyet);
        Anhxa();
        XuliToobar();
        dulieu = new sqDuLieu(this);

        ArrayList<lythuyet> values = dulieu.getdulieulythuyet();
        adapter = new OnLyThuyetAdapter(this,values);
        lstlythuet.setAdapter(adapter);
        lstlythuet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index =  position + 1;
                // Do trong bộ đề cái loại bằng 1 mà trong list bắt đầu bằng o nên phải cộng 1
                sendata(index);
            }

            private void sendata(int i) {
                Intent intent = new Intent(HocLyThuyetActivity.this, OnTapLyThuyetActivity.class);
                intent.putExtra("data1",i);
                startActivity(intent);
            }
        });
    }

    //Nút điều hướng
    private void XuliToobar() {
        setSupportActionBar(toobarhoclythuet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toobarhoclythuet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toobarhoclythuet = findViewById(R.id.ahl_toobarlythuyet);
        lstlythuet = findViewById(R.id.ahlt_lstlythuyet);
    }
}