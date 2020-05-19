package com.baidu.speech.recognizerdemo.ui.Inquire;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.TextView;

import com.baidu.aip.asrwakeup3.core.mini.ActivityMiniVoiceInquire;

public class VoiceInquire extends ActivityMiniVoiceInquire {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView.setAdapter(mAdapter);
        mListView.setVisibility(mListView.GONE);
        mListView.setTextFilterEnabled(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text=(String)mListView.getItemAtPosition(position);
                int id_text = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
                TextView textView = (TextView) mSearchView.findViewById(id_text);
                textView.setText(text);
            }
        });
        //搜索框内容变化监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {//点击提交按钮时
                Intent intent = new Intent(VoiceInquire.this, InquireResult.class);
                intent.putExtra("RubbishName", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {//搜索框内容变化时
                if (!TextUtils.isEmpty(newText)) {
//              mListView.setFilterText(newText);
                    mAdapter.getFilter().filter(newText);
                    mListView.setVisibility(mListView.VISIBLE);
                } else {
                    mListView.clearTextFilter();
                }
                return true;
            }
        });
    }
}
