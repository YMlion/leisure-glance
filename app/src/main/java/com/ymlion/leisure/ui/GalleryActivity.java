package com.ymlion.leisure.ui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.ymlion.leisure.R;
import com.ymlion.leisure.base.BaseActivity;
import com.ymlion.leisure.data.model.Meizi;

import uk.co.senab.photoview.PhotoView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.ymlion.leisure.ui.MainActivity.EXTRA_CURRENT_GALLERY_POSITION;
import static com.ymlion.leisure.ui.MainActivity.EXTRA_STARTING_GALLERY_POSITION;

/**
 * @author ymlion
 * @date 2016/7/15
 */

public class GalleryActivity extends BaseActivity {

    @BindView(R.id.gallery_vp)
    ViewPager viewPager;

    private List<Meizi> meizis;
    private int startPosition = 0;

    private boolean mIsReturning;
    private GalleryAdapter adapter;

    private final SharedElementCallback mCallback = new SharedElementCallback() {
        @Override
        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
            if (mIsReturning) {
                ImageView sharedElement = adapter.getView(viewPager.getCurrentItem());
                if (sharedElement == null) {
                    // If shared element is null, then it has been scrolled off screen and
                    // no longer visible. In this case we cancel the shared element transition by
                    // removing the shared element from the shared elements map.
                    names.clear();
                    sharedElements.clear();
                } else if (startPosition != viewPager.getCurrentItem()) {
                    // If the user has swiped to a different ViewPager page, then we need to
                    // remove the old shared element and replace it with the new shared element
                    // that should be transitioned instead.
                    names.clear();
                    names.add(sharedElement.getTransitionName());
                    sharedElements.clear();
                    sharedElements.put(sharedElement.getTransitionName(), sharedElement);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        postponeEnterTransition();
        setEnterSharedElementCallback(mCallback);
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
        meizis = intent.getParcelableArrayListExtra("mz");
        if (meizis == null) {
            finish();
            return;
        }
        adapter = new GalleryAdapter();
        viewPager.setAdapter(adapter);
        startPosition = intent.getIntExtra(EXTRA_STARTING_GALLERY_POSITION, 0);
        viewPager.setCurrentItem(startPosition);
    }

    @Override
    public void finishAfterTransition() {
        mIsReturning = true;
        Intent data = new Intent();
        data.putExtra(EXTRA_STARTING_GALLERY_POSITION, startPosition);
        data.putExtra(EXTRA_CURRENT_GALLERY_POSITION, viewPager.getCurrentItem());
        setResult(RESULT_OK, data);
        super.finishAfterTransition();
    }

    @Override
    public boolean isSwipeBackEnable() {
        return viewPager.getCurrentItem() == 0;
    }

    public static void start(Activity context, ArrayList<Meizi> meizis, int position, View sharedView) {
        Intent starter = new Intent(context, GalleryActivity.class);
        starter.putExtra(EXTRA_STARTING_GALLERY_POSITION, position);
        starter.putParcelableArrayListExtra("mz", meizis);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(context, sharedView,
                sharedView.getTransitionName());
        context.startActivity(starter, options.toBundle());
    }

    private class GalleryAdapter extends PagerAdapter {

        private PhotoView[] mViews;

        @Override
        public int getCount() {
            return meizis == null ? 0 : meizis.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (meizis == null) {
                return null;
            }
            if (mViews == null) {
                mViews = new PhotoView[meizis.size()];
                for (int i = 0; i < 4; i++) {
                    mViews[i] = new PhotoView(container.getContext());
                    mViews[i].setTransitionName(meizis.get(position).get_id());
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
                    .load(meizis.get(position).getUrl())
                    .apply(options)
                    .listener(new CallbackListener(mViews[position], position))
                    .into(mViews[position]);

            return mViews[position];
        }

        public PhotoView getView(int position) {
            if (mViews == null) {
                return null;
            } else {
                return mViews[position];
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            Glide.with(GalleryActivity.this).clear((View) object);
            Log.e("TAG", "destroyItem: " + position);
        }

        private class CallbackListener implements RequestListener<Drawable> {

            WeakReference<View> mViewReference;
            int mPosition;

            CallbackListener(View view, int position) {
                this.mViewReference = new WeakReference<View>(view);
                this.mPosition = position;
            }

            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                final View view = mViewReference.get();
                if (mPosition == startPosition && view != null) {
                    view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            view.getViewTreeObserver().removeOnPreDrawListener(this);
                            startPostponedEnterTransition();
                            return true;
                        }
                    });
                }
                return false;
            }
        }

    }
}
