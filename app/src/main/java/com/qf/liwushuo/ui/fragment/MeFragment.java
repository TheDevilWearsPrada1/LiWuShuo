package com.qf.liwushuo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.QQLoginBean;
import com.qf.liwushuo.ui.activity.SettingsActivity;
import com.qf.liwushuo.utils.CircleTransform;
import com.squareup.picasso.Picasso;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zijunlin.Zxing.Demo.CaptureActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2016/10/24 0024.
 * <p>
 * 我的
 */

public class MeFragment extends Fragment {


    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.iv_settings)
    ImageView ivSettings;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.iv_acatar)
    ImageView ivAcatar;
    @BindView(R.id.tab_me)
    TabLayout tabMe;
    @BindView(R.id.vp_me)
    ViewPager vpMe;


    //定义一个腾讯实例
    Tencent mTencent;

    int action = QQ_LOGIN;

    private static final int QQ_LOGIN = 245;
    private static final int QQ_USERINFO = 244;
    @BindView(R.id.tb_name)
    TextView tbName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTencent = Tencent.createInstance("1105713093", getContext().getApplicationContext());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabMe.addTab(tabMe.newTab().setText("单品"));
        tabMe.addTab(tabMe.newTab().setText("攻略"));

    }

    @OnClick({R.id.iv_settings, R.id.iv_scan, R.id.iv_acatar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_settings:
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_scan:
                Intent intent1 = new Intent(getContext(), CaptureActivity.class);
                startActivityForResult(intent1, 100);
                break;
            case R.id.iv_acatar:
                //点击登录
               /* if (mTencent.isSessionValid()){
                    //判断是否登录过
                    return;
                }*/
                mIUiListener = new IUiListener() {
                    @Override
                    public void onComplete(Object o) {
                        Log.e("自定义控件", "登录完成。。。。。。。");
                        //msgId.setText("登录成功。。。。");
                        JSONObject jsonObject = (JSONObject) o;
                        Log.e("自定义控件", "json==" + jsonObject);
                        QQLoginBean qqLoginBean = null;

                        switch (action) {
                            case QQ_LOGIN:
                                try {
                                    Gson gson = new Gson();
                                    qqLoginBean = gson.fromJson(jsonObject.toString(), QQLoginBean.class);
                                    //将拿到的Token添加给实例对象
                                    mTencent.setOpenId(qqLoginBean.getOpenid());
                                    mTencent.setAccessToken(qqLoginBean.getAccess_token(), qqLoginBean.getExpires_in() + "");
                                    UserInfo userInfo = new UserInfo(getContext().getApplicationContext(), mTencent.getQQToken());
                                    action = QQ_USERINFO;
                                    userInfo.getUserInfo(mIUiListener);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case QQ_USERINFO:
                                /*try {
                                    //msgId.setText(jsonObject.getString("nickname"));
                                    //x.image().bind(imageId, jsonObject.getString("figureurl_qq_2"), new ImageOptions.Builder().setCircular(true).build());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }*/
                                try {
                                    action = QQ_LOGIN;
                                    Log.e("自定义控件", "json==" + jsonObject);
                                    Picasso.with(getContext()).load(jsonObject.getString("figureurl_qq_2")).transform(new CircleTransform()).into(ivAcatar);
                                    tbName.setText(jsonObject.getString("nickname"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e("自定义控件", "登录出错。。。。。。。" + uiError.errorCode + "----------" + uiError.errorMessage);
                    }

                    @Override
                    public void onCancel() {
                        Log.e("自定义控件", "登录取消。。。。。。。");
                    }
                };
                mTencent.login(this, "all", mIUiListener);//调用登录方法
                break;
        }
    }

    IUiListener mIUiListener;//定义qq登录的监听器

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //拿到扫描结果
            String result = data.getStringExtra("result");
            Log.e("自定义控件", result);
            //处理扫苗结果
        } else if (requestCode == Constants.REQUEST_LOGIN) {
            mTencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);

        }


        super.onActivityResult(requestCode, resultCode, data);

    }
}
