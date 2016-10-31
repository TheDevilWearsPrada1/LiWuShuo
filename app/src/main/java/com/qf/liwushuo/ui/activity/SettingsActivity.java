package com.qf.liwushuo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.application.MyApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.lv_settings)
    ListView lvSettings;
    ArrayAdapter<String> adapter;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        list=new ArrayList<>();
        String s = MyApplication.getaCache().CacheSize();
        list.add("清除缓存\t\t"+s);
        list.add("网络检测");
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,list);
        lvSettings.setAdapter(adapter);
        lvSettings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        MyApplication.getaCache().clear();
                        break;
                    case 1:
                        Intent intent=new Intent(SettingsActivity.this,NetLookActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
