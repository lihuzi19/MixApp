package com.example.lihuzi.infiniteviewpager.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.infiniteviewpager.R;

import java.util.ArrayList;

public abstract class WholePictureDisplayAdapter extends PagerAdapter {
    public abstract int setCount();

    public abstract View initCurrentView(View v, int position);

    public abstract int setResourceId(int resId);

    public abstract ViewPager setViewPager();

    private boolean infinite;

    public boolean isInfinite() {
        return infinite;
    }

    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
        setViewPager().post(() -> setViewPager().setCurrentItem(1, false));
    }

    private ArrayList<View> viewCacheList;
    private int childCount = 0;//PagerAdapter刷新机制有问题，需要重写notifyDataSetChanged和getItemPosition方法

    public WholePictureDisplayAdapter() {
        viewCacheList = new ArrayList<>();
    }

    private View getViewHolder(ViewGroup container) {
        View v;
        if (viewCacheList.size() == 0) {
            v = LayoutInflater.from(setViewPager().getContext()).inflate(R.layout.item_main, container, false);
        } else {
            v = viewCacheList.remove(viewCacheList.size() - 1);
        }
        container.addView(v);
        return v;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (infinite) {
            if (position == 0) {
                position = getCount() - 3;
            } else if (position == getCount() - 1) {
                position = 0;
            } else {
                position--;
            }
        }
        View v = getViewHolder(container);
        return initCurrentView(v, position);
    }


    @Override
    public int getCount() {
        int realCount = setCount();
        if (realCount > 0) {
            return infinite ? setCount() + 2 : setCount();
        } else {
            return 0;
        }
    }

    @Override
    public void notifyDataSetChanged() {
        childCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (childCount > 0) {
            childCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((View) object));
        viewCacheList.add(((View) object));
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        if (infinite) {
            int realPosition = setViewPager().getCurrentItem();
            if (realPosition == 0) {
                setViewPager().setCurrentItem(getCount() - 2, false);
            } else if (realPosition == getCount() - 1) {
                setViewPager().setCurrentItem(1, false);
            }
        }
        super.finishUpdate(container);
    }
}