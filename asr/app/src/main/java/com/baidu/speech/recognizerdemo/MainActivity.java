package com.baidu.speech.recognizerdemo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.baidu.speech.recognizerdemo.DataBase.MyDatabaseHelper;
import com.baidu.speech.recognizerdemo.ui.AboutMe.AboutMeFragment;
import com.baidu.speech.recognizerdemo.ui.Classify.ClassifyFragment;
import com.baidu.speech.recognizerdemo.ui.Inquire.InquireFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private MyDatabaseHelper dbHelper;
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment[] fragments;
    private int lastfragment;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatabase();
        initFragment();

    }

    private void initDatabase(){
        dbHelper = new MyDatabaseHelper(this,"GarbageStore.db",null,1);
        dbHelper.getWritableDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String line = null;
        InputStream inputStream = null;
        try {
            //BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath));
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open("garbage.csv"));
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            line=bufferedReader.readLine();
            while((line=bufferedReader.readLine())!=null){
                //数据行
                String[] items=line.split(",");
                String category=null;
                if(items[1].equals("1"))
                    category="可回收垃圾";
                else if(items[1].equals("2"))
                    category="有害垃圾";
                else if(items[1].equals("4"))
                    category="湿垃圾";
                else if(items[1].equals("8"))
                    category="干垃圾";
                else
                    continue;
                values.put("name",items[0]);
                values.put("category",category);
                values.put("frequency",0);
                db.insert("Garbage",null,values);
                values.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initFragment() {

        fragment1 = new InquireFragment();
        fragment2 = new ClassifyFragment();
        fragment3 = new AboutMeFragment();
        fragments = new Fragment[]{fragment1, fragment2,fragment3};
        lastfragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.mainview,fragment1).show(fragment1).commit();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(changeFragment);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId())
            {
                case R.id.nav_search:
                {
                    if(lastfragment!=0)
                    {
                        switchFragment(lastfragment,0);
                        lastfragment=0;
                    }

                    return true;
                }
                case R.id.nav_classify:
                {
                    if(lastfragment!=1)
                    {
                        switchFragment(lastfragment,1);
                        lastfragment=1;

                    }
                    return true;
                }
                case R.id.nav_aboutme:
                {
                    if(lastfragment!=2)
                    {
                        switchFragment(lastfragment,2);
                        lastfragment=2;

                    }
                    return true;
                }
            }
            return false;
        }
    };
    private void switchFragment(int lastfragment,int index)
    {

        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);//隐藏上个Fragment
        if(fragments[index].isAdded()==false)
        {
            transaction.add(R.id.mainview,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }
}