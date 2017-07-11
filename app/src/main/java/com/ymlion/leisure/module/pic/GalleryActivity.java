package com.ymlion.leisure.module.pic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.ymlion.leisure.R;
import com.ymlion.leisure.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author ymlion
 * @date 2016/7/15
 */

public class GalleryActivity extends BaseActivity {

    @BindView(R.id.gallery_vp)
    ViewPager viewPager;

    private List<String> photos;
    private int currentPos = 0;
    private GalleryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        postponeEnterTransition();
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        photos = intent.getStringArrayListExtra("photos");
        if (photos == null) {
            finish();
            return;
        }
        currentPos = intent.getIntExtra("pos", 0);
        adapter = new GalleryAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPos);
    }

    @Override
    public boolean isSwipeBackEnable() {
        return viewPager.getCurrentItem() == 0;
    }

    public static void start(Context context, ArrayList<String> photos, int position) {
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.putExtra("pos", position);
        intent.putStringArrayListExtra("photos", photos);
        context.startActivity(intent);
    }

    private class GalleryAdapter extends PagerAdapter {

        private PhotoView[] mViews;

        @Override
        public int getCount() {
            return photos == null ? 0 : photos.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (photos == null) {
                return null;
            }
            if (mViews == null) {
                mViews = new PhotoView[photos.size()];
                for (int i = 0; i < 4; i++) {
                    mViews[i] = new PhotoView(container.getContext());
                    mViews[i].setBackgroundColor(Color.BLACK);
                }
            }
            if (mViews[position] == null) {
                mViews[position] = mViews[position % 4];
            }
            container.addView(mViews[position], ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            Log.e("TAG", "addView: " + position);
            RequestOptions options = new RequestOptions()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .diskCacheStrategy(DiskCacheStrategy.DATA);
            Glide.with(GalleryActivity.this)
                    .load(photos.get(position))
                    .apply(options)
                    .into(mViews[position]);

            return mViews[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            Glide.with(GalleryActivity.this).clear((View) object);
            Log.e("TAG", "destroyItem: " + position);
        }
    }
}
