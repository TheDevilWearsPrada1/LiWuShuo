package com.qf.liwushuo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.qf.liwushuo.R;
import com.qf.liwushuo.db.OldSearchEntity;
import com.qf.liwushuo.db.UserDataBaseOpenHelper;
import com.qf.liwushuo.ui.fragment.HotFragment;
import com.qf.liwushuo.ui.fragment.SearchRFregment;
import com.qf.liwushuo.ui.fragment.SearchWordFragment;
import com.qf.liwushuo.utils.FragmentMang;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchActivity extends AppCompatActivity implements TextWatcher {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_br)
    TextView tvBr;
    FragmentMang fragmentMang;
    Dao<OldSearchEntity, Integer> userDao;//数据库操作类
    UserDataBaseOpenHelper mOpenHelper;



    HotFragment hotFragment=new HotFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initDao();
        fragmentMang = new FragmentMang(getSupportFragmentManager());
        fragmentMang.fragmentManager(R.id.hot_fl, hotFragment, "hot");
        etSearch.addTextChangedListener(this);
    }

    @Subscribe
    public void getData(String s) {
        Log.e("hehe", s);
        etSearch.setText(s);
        //保存数据到数据库
        OldSearchEntity oldSearchEntity=new OldSearchEntity();
        oldSearchEntity.setWord(s);
        try {
            userDao.create(oldSearchEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //启动搜索结果的fragment
        SearchRFregment fregment = new SearchRFregment();
        Bundle bundle = new Bundle();
        bundle.putString("keyWord", s);
        fregment.setArguments(bundle);
        fragmentMang.fragmentManager(R.id.hot_fl, fregment, s);
    }
    private void initDao() {
        mOpenHelper = UserDataBaseOpenHelper.getOpenHelper(this);
        try {
            userDao = mOpenHelper.getDao(OldSearchEntity.class);//获取数据库操作类
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0) {
            fragmentMang.fragmentManager(R.id.hot_fl, hotFragment, "hot");
            hotFragment.startData();
        } else {
            SearchWordFragment searchWordFragment = new SearchWordFragment();
            Bundle bundle = new Bundle();
            bundle.putString("keyWord", s.toString());
            searchWordFragment.setArguments(bundle);
            fragmentMang.fragmentManager(R.id.hot_fl, searchWordFragment, s.toString());
        }
    }

    @OnClick(R.id.tv_br)
    public void onClick() {
        finish();
    }
}
