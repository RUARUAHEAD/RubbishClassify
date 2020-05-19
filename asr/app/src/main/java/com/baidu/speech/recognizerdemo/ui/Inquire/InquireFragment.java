package com.baidu.speech.recognizerdemo.ui.Inquire;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.baidu.speech.recognizerdemo.R;

public class InquireFragment extends Fragment {
    private Button text_inquire;
    private Button voice_inquire;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_inquire,container,false);
        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        text_inquire=(Button)getActivity().findViewById(R.id.text_inquire_button);
        voice_inquire=(Button)getActivity().findViewById(R.id.voice_inquire_button);
        text_inquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),InquireActivity.class);
                startActivity(intent);
            }
        });
        voice_inquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),VoiceInquire.class);
                startActivity(intent);
            }
        });
    }
}
