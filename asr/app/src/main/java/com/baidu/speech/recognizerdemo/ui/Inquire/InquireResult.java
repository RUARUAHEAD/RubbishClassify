package com.baidu.speech.recognizerdemo.ui.Inquire;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.speech.recognizerdemo.DataBase.MyDatabaseHelper;
import com.baidu.speech.recognizerdemo.R;

public class InquireResult extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquire_result);
        Intent intent=getIntent();
        final String rubbish=intent.getStringExtra("RubbishName");
        dbHelper = new MyDatabaseHelper(this,"GarbageStore.db",null,1);
        String category=null;
        int frequency=0;
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Garbage",null,"name =?",
                new String[]{rubbish},null,null,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            String name=cursor.getString(cursor.getColumnIndex("name"));
            category=cursor.getString(cursor.getColumnIndex("category"));
            frequency=cursor.getInt(cursor.getColumnIndex("frequency"));
            showResult(category,rubbish);
            ContentValues values = new ContentValues();
            values.put("name",name);
            values.put("category",category);
            values.put("frequency",frequency+1);
            db.update("Garbage",values,"name=?",new String[]{name});
        } else {
            TextView text_result = (TextView) findViewById(R.id.inquire_result_text);
            text_result.setText("数据库无法匹配，点击文本进行添加！");
            text_result.setTextSize(20);
            text_result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(InquireResult.this, InsertDBActivity.class);
                    intent.putExtra("RubbishName",rubbish);
                    startActivity(intent);
                }
            });
        }
        cursor.close();


    }
    protected void showResult(String category,String rubbish)
    {
        TextView text_title=(TextView)findViewById(R.id.inquire_result_title);
        text_title.setText("投放要求");
        TextView text_result = (TextView) findViewById(R.id.inquire_result_text);
        text_result.setText(rubbish+"是"+category);
        ImageView image_result=(ImageView) findViewById(R.id.inquire_result_image);
        TextView  text_require= (TextView) findViewById(R.id.delivery_require);
        if(category.equals("干垃圾")){
            image_result.setImageResource(R.drawable.garbage_dry);
            text_require.setText("尽量沥干水分\n"+"难以辨识类别的生活垃圾投入干垃圾容器内");
        } else if(category.equals("湿垃圾")){
            image_result.setImageResource(R.drawable.garbage_wet);
            text_require.setText("纯流质的食物垃圾，如牛奶等，应直接倒进下水口\n"+
                    "有包装物的湿垃圾应将包装物去除后分类投放，包装物请投放到对应的可回收物或干垃圾容器");
        } else if(category.equals("有害垃圾")){
            image_result.setImageResource(R.drawable.garbage_harmful);
            text_require.setText("投放时请注意轻放\n"+"" +
                    "易破损的请连带包装或包裹后轻放\n"+"如易挥发，请密封后投放");
        } else if(category.equals("可回收垃圾")){
            image_result.setImageResource(R.drawable.garbage_recyclable);
            text_require.setText("轻投轻放\n"+"清洁干燥、避免污染，废纸尽量平整\n"+
                    "立体包装请清空内容物，清洁后压扁投放\n"+"有尖锐边角的，应包裹后投放");
        }
    }

}
