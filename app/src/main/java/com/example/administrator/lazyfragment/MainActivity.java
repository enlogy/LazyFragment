package com.example.administrator.lazyfragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout tableLayout;
    private ViewPager viewPager;
    private String[] titles = new String[]{"风景","探索","美女","帅哥","跑车","足球"};
    private List<Fragment> fragments = new ArrayList<>(2);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        fragments.add(new TestFragment1());
        fragments.add(new TestFragment1());
        fragments.add(new TestFragment1());
        fragments.add(new TestFragment1());
        fragments.add(new TestFragment1());
        fragments.add(new TestFragment1());
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tableLayout.setupWithViewPager(viewPager);
    }
    class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
