package com.baidu.speech.recognizerdemo.ui.Classify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.baidu.speech.recognizerdemo.Adapter.ClassifyInfoAdapter;
import com.baidu.speech.recognizerdemo.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class ClassifyFragment extends Fragment {
    private TabLayout tableLayout;
    private ViewPager viewPager;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_classify,container,false);
        tableLayout = view.findViewById(R.id.order_tab);
        viewPager = view.findViewById(R.id.order_viewpager);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new Fragment_dry());
        fragments.add(new Fragment_wet());
        fragments.add(new Fragment_harmful());
        fragments.add(new Fragment_recycled());
        FragmentPagerAdapter adapter = new ClassifyInfoAdapter
                (getFragmentManager(),fragments, new String[]{"干垃圾", "湿垃圾","有害垃圾", "可回收垃圾"});
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
