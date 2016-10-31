package com.qf.liwushuo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qf.liwushuo.R;
import com.qf.liwushuo.bean.SearchWordBean;
import com.qf.liwushuo.presenter.SearchWordPresenter;
import com.qf.liwushuo.ui.view.SearchWordView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by Administrator on 2016/10/26 0026.
 */

public class SearchWordFragment extends MySuperFragment<SearchWordPresenter> implements SearchWordView, AdapterView.OnItemClickListener {
    String keyWord;
    @BindView(R.id.lv)
    ListView lv;
    ArrayAdapter<String> adapter;
    List<String> list;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keyWord = getArguments().getString("keyWord");
        presenter.getData(keyWord);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_word, container, false);
        ButterKnife.bind(this, view);
        list=new ArrayList<>();
        adapter=new ArrayAdapter<String>(container.getContext(),
                android.R.layout.simple_expandable_list_item_1,list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        return view;
    }

    @Override
    public SearchWordPresenter creatersenter() {
        return new SearchWordPresenter();
    }

    @Override
    public void getData(SearchWordBean searchWordBean) {
        list.addAll(searchWordBean.getData().getWords());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EventBus.getDefault().post(list.get(position));
    }
}
