package com.example.appthibanglaixe.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appthibanglaixe.Adapter.BienBaoAdapter;
import com.example.appthibanglaixe.Adapter.Fragment_BienBao_Adapter;
import com.example.appthibanglaixe.R;
import com.example.appthibanglaixe.model.bienbao;
import com.example.appthibanglaixe.uliti.Server;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class BienBaoActivity extends AppCompatActivity {
    Toolbar toobarbienbao;
    private TabLayout mtablayout;
    private ViewPager2 mviewpage;
    private Fragment_BienBao_Adapter adapter;
    int page = 1;
    ListView lstbienbao;
    ArrayList<bienbao> arrbienbao;
    BienBaoAdapter bienBaoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bien_bao);
        Anhxa();
        XulitoobarBienbao();

        //Xử lí chuyển các page của màn hình biển báo
        adapter = new Fragment_BienBao_Adapter(this);
        mviewpage.setAdapter(adapter);
        lstbienbao.setAdapter(bienBaoAdapter);
        new TabLayoutMediator(mtablayout, mviewpage, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Biển Báo Cấm");
                        break;
                    case 1:
                        tab.setText("Biển Hiệu Lệnh");
                        break;
                    case 2:
                        tab.setText("Biển Chỉ Dẫn");
                        break;
                }
            }
        }).attach();
    }

    //Hàm này xử lí nút điều hướng
    private void XulitoobarBienbao() {
        setSupportActionBar(toobarbienbao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toobarbienbao.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toobarbienbao = findViewById(R.id.abb_toobargiaothong);
        mtablayout = findViewById(R.id.abb_tablayout);
        mviewpage = findViewById(R.id.abb_viewpage);
        lstbienbao = findViewById(R.id.abb_lstbienbao);
        arrbienbao = new ArrayList<>();
        bienBaoAdapter = new BienBaoAdapter(getApplicationContext(), arrbienbao);
        lstbienbao.setAdapter(bienBaoAdapter);
    }
}