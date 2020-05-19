package com.baidu.speech.recognizerdemo.ui.Inquire;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.speech.recognizerdemo.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InquireActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquire);
        final List<String> data=readCSV("garbage.csv",false);

        final SearchView mSearchView = (SearchView) findViewById(R.id.searchView);
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new ArrayAdapter(InquireActivity.this, android.R.layout.simple_list_item_1, data);
        mListView.setAdapter(mAdapter);
        mListView.setVisibility(mListView.GONE);
        mListView.setTextFilterEnabled(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text=(String)mListView.getItemAtPosition(position);
                int id_text = mSearchView.getContext().getResources().getIdentifier
                        ("android:id/search_src_text", null, null);
                TextView textView = (TextView) mSearchView.findViewById(id_text);
                textView.setText(text);
            }
        });
        //搜索框内容变化监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {//点击提交按钮时
                Intent intent = new Intent(InquireActivity.this, InquireResult.class);
                intent.putExtra("RubbishName", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {//搜索框内容变化时
                if (!TextUtils.isEmpty(newText)) {
                    mAdapter.getFilter().filter(newText);
                    mListView.setVisibility(mListView.VISIBLE);
                } else {
                    mListView.clearTextFilter();
                }
                return true;
            }
        });
    }
    public List<String> readCSV(String filePath, boolean hasTitle) {
        List<String> data = new ArrayList<>();
        String line = null;
        InputStream inputStream = null;
        try {
            //BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath));
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(filePath));
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            if (hasTitle){
                //第一行信息，为标题信息
                line = bufferedReader.readLine();
                String[] items=line.split(",");
                System.out.println("标题行："+line);
            }
            while((line=bufferedReader.readLine())!=null){
                //数据行
                String[] items=line.split(",");
                data.add(items[0]);
                //System.out.println("第"+i+"行："+line);
            }

        } catch (FileNotFoundException e) {
            System.out.println("No file exists");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
