package com.qf.liwushuo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.ui.fragment.BaseFragment;
import com.qf.liwushuo.ui.fragment.ItemFragment;
import com.qf.liwushuo.utils.FragmentMang;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GonglueItemActivity extends AppCompatActivity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.pinlei)
    TextView pinlei;
    @BindView(R.id.fl)
    FrameLayout fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gonglue_item);
        ButterKnife.bind(this);
        FragmentMang fragmentMang=new FragmentMang(getSupportFragmentManager());
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        String name = intent.getStringExtra("name");
        pinlei.setText(name);
        //Log.e("zq", "id:" + id + "name:" + name);
        BaseFragment baseFragment=new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        baseFragment.setArguments(bundle);
        fragmentMang.fragmentManager(R.id.fl,baseFragment,name);
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
