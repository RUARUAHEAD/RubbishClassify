package com.baidu.speech.recognizerdemo.ui.AboutMe;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.baidu.speech.recognizerdemo.DataBase.MyDatabaseHelper;
import com.baidu.speech.recognizerdemo.R;

import java.util.ArrayList;
import java.util.List;


public class AboutMeFragment extends Fragment {
    private ListView mListView;
    private ArrayAdapter mAdapter;
    private int list_num;
    private MyDatabaseHelper dbHelper;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_about_me,container,false);
        mListView=(ListView) view.findViewById(R.id.often_use_rubbish);
        refresh();
        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            refresh();
        }
    }
    private void refresh(){
        list_num=15;
        List<String> data=FindFrequencyItem();
        mAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, data);
        mListView.setAdapter(mAdapter);
        mListView.setVisibility(View.VISIBLE);
    }
    private List<String > FindFrequencyItem(){
        int count=0;
        List<String> data=new ArrayList<String>();
        dbHelper = new MyDatabaseHelper(getContext(),"GarbageStore.db",null,1);
        dbHelper.getWritableDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Garbage", null, null,
                null, null, null, "frequency DESC");
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String category = cursor.getString(cursor.getColumnIndex("category"));
                int frequency = cursor.getInt(cursor.getColumnIndex("frequency"));
                data.add(name+", "+category+", 查询次数："+frequency);
                count++;
            }while(cursor.moveToNext() && count<=list_num);
        }else{
            Toast.makeText(getContext(),"cursor fail",Toast.LENGTH_SHORT).show();
        }
        return data;
    }
}
