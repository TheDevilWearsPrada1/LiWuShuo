package com.qf.liwushuo.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import com.qf.liwushuo.R;
import com.qf.liwushuo.ui.fragment.ClassificationFragment;
import com.qf.liwushuo.ui.fragment.HomeFragment;
import com.qf.liwushuo.ui.fragment.ListFragment;
import com.qf.liwushuo.ui.fragment.MeFragment;
import com.qf.liwushuo.utils.FragmentMang;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.rg)
    RadioGroup rg;
    FragmentMang fragmentMang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentMang = new FragmentMang(getSupportFragmentManager());
        rg.setOnCheckedChangeListener(this);
        rg.getChildAt(0).performClick();//模拟点击事件
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                //首页
                fragmentMang.fragmentManager(R.id.fgl, new HomeFragment(), "home");
                break;
            case R.id.rb_list:
                //榜单
                fragmentMang.fragmentManager(R.id.fgl, new ListFragment(), "list");

                break;
            case R.id.rb_classification:
                //分类
                fragmentMang.fragmentManager(R.id.fgl, new ClassificationFragment(), "class");

                break;
            case R.id.rb_me:
                //我的
                fragmentMang.fragmentManager(R.id.fgl, new MeFragment(), "me");
                break;
        }
    }

    //退出时的提示框
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 监听返回键，根据keyCode来判断 ，是否是返回键被按下了
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 弹出一个提示框
            // 1.创建一个AlertDialog.Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // 2.设置标题和文本内容
            builder.setIcon(R.mipmap.icon_more_identify).setTitle("温馨提示")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 用户退出
                            finish();
                        }
                    }).setNegativeButton("取消", null);

            // 3.显示
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
