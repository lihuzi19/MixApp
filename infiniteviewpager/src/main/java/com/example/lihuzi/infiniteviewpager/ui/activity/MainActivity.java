package com.example.lihuzi.infiniteviewpager.ui.activity;


import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.infiniteviewpager.R;
import com.example.lihuzi.infiniteviewpager.ui.adapter.WholePictureDisplayAdapter;
import com.example.lihuzi.infiniteviewpager.ui.utils.SPUtil;
import com.example.lihuzi.infiniteviewpager.ui.view.DLFloatViewGroup;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final ArrayList<String> pictureList = new ArrayList<>();
    static {
        pictureList.add("https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF");
        pictureList.add("https://b-ssl.duitang.com/uploads/item/201808/14/20180814175618_hypnp.jpeg");
        pictureList.add("https://t7.baidu.com/it/u=4069854689,43753836&fm=193&f=GIF");
        pictureList.add("https://t7.baidu.com/it/u=2621658848,3952322712&fm=193&f=GIF");
        pictureList.add("https://t7.baidu.com/it/u=2084624597,235761712&fm=193&f=GIF");
    }
    ViewPager _viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_infi);
        _viewpager = findViewById(R.id.act_main_viewpager);
        initView();
        SPUtil.getInstance().init(this, getSPFile());
    }

    public File getSPFile() {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "aaa_lining_share_pref.xml");
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < 1000; i++) {
            SPUtil.getInstance().addString(i + "", i + "");
        }
        Log.e("SPUtil", "onResume addString 1000");
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (int i = 0; i < 1000; i++) {
            Log.e("SPUtil", String.format("onPause key %s,value %s", "" + i, SPUtil.getInstance().getString(i + "")));
        }
    }

    private void initView() {
        WholePictureDisplayAdapter adapter = new WholePictureDisplayAdapter() {
            @Override
            public int setCount() {
                return pictureList.size();
            }

            @Override
            public View initCurrentView(View v, int position) {
                ImageView iv = v.findViewById(R.id.item_main_iv);
                Glide.with(_viewpager.getContext()).load(pictureList.get(position)).into(iv);
                DLFloatViewGroup floatView = v.findViewById(R.id.item_main_floatview);
                floatView.initDLFloatView(null, null);
                int visible = position == pictureList.size() - 1 ? View.VISIBLE : View.GONE;
                System.out.println("visible:" + visible);
                floatView.setVisibility(View.GONE);
                return v;
            }

            @Override
            public int setResourceId(int resId) {
                return R.layout.item_main;
            }

            @Override
            public ViewPager setViewPager() {
                return _viewpager;
            }
        };
        adapter.setInfinite(true);
        _viewpager.setAdapter(adapter);
    }

}
