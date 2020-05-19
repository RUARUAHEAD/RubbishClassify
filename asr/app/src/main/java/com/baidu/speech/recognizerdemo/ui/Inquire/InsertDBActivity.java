package com.baidu.speech.recognizerdemo.ui.Inquire;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.speech.recognizerdemo.DataBase.MyDatabaseHelper;
import com.baidu.speech.recognizerdemo.R;

public class InsertDBActivity extends AppCompatActivity {
    private Button popup;
    private Button insert;
    private String category;
    private String name;
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_db);
        popup=(Button)findViewById(R.id.choose_class);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu(v);
            }

        });
        Intent intent=getIntent();
        name=intent.getStringExtra("RubbishName");
        insert=(Button)findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    insertInfo();
                }
            });
    }
    public void popupMenu(View view){
        PopupMenu menu=new PopupMenu(this,view);
        MenuInflater inflater=menu.getMenuInflater();
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.popup_menu_dry:
                        popup.setText("干垃圾");

                        return true;
                    case R.id.popup_menu_wet:
                        popup.setText("湿垃圾");
                        return true;
                    case R.id.popup_menu_recyclable:
                        popup.setText("可回收垃圾");
                        return true;
                    case R.id.popup_menu_harmful:
                        popup.setText("有害垃圾");
                        return true;
                    default:
                        return false;
                }
            }
        });
        inflater.inflate(R.menu.popupmenu,menu.getMenu());
        menu.show();
    }
    private void insertInfo(){
        category=popup.getText().toString();
        if(category.equals("类别")) {
            Toast.makeText(getApplicationContext(),"请选择垃圾类别！",Toast.LENGTH_SHORT).show();
            return;
        }
        dbHelper = new MyDatabaseHelper(this,"GarbageStore.db",null,1);
        dbHelper.getWritableDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("category",category);
        values.put("frequency",1);
        db.insert("Garbage",null,values);
        values.clear();
        Toast.makeText(getApplicationContext(),category+": "+name+"插入成功",Toast.LENGTH_SHORT).show();
    }

}
