package com.baidu.speech.recognizerdemo.ui.Classify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.baidu.speech.recognizerdemo.R;


public class Fragment_recycled extends Fragment {
    TextView text_view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_recycled,container,false);
        ImageView imageView=view.findViewById(R.id.image_recycled);
        imageView.setImageResource(R.drawable.info_recyclable);
        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
