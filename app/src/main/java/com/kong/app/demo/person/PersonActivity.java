package com.kong.app.demo.person;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.kong.R;
import com.kong.app.demo.about.TextItemViewBinder;
import com.kong.app.demo.about.TextViewItem;
import com.kong.app.demo.me.SettingImgTvItem;
import com.kong.app.demo.me.SettingImgTvItemViewBinder;
import com.kong.app.news.base.ThemeActivity;
import com.library.utils.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by CaoPengfei on 17/6/18.
 */

public class PersonActivity extends ThemeActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;
    private List<Object> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        initBar();
        init();
    }

    private void initBar() {
        mToolbar = (Toolbar) findViewById(R.id.person_toolbar);
        initToolBar(mToolbar, R.string.descover);
    }

    private void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.person_recycle_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(SettingImgTvItem.class, new SettingImgTvItemViewBinder());
        mAdapter.register(TextTextViewItem.class,new TvTvItemViewBinder());
        mAdapter.register(TextViewItem.class, new TextItemViewBinder());

        list.add(new SettingImgTvItem());

        for (int i = 0; i < 3; i++) {
            TextTextViewItem item = new TextTextViewItem();
            item.title = "item:" + i;
            list.add(item);
        }

        list.add(new SettingImgTvItem());

        for (int i = 0; i < 3; i++) {
            TextTextViewItem item = new TextTextViewItem();
            item.title = "item:" + i;
            list.add(item);
        }

        TextViewItem item1 = new TextViewItem();
        item1.text = ResourceUtil.getString(R.string.about_copyright);
        list.add(item1);

        mAdapter.setItems(list);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);

    }
}
