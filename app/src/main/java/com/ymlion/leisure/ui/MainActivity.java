package com.ymlion.leisure.ui;

import android.app.SharedElementCallback;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.bumptech.glide.Glide;
import com.ymlion.leisure.R;
import com.ymlion.leisure.base.BaseActivity;
import com.ymlion.leisure.net.Http;
import com.ymlion.leisure.ui.adapter.FuliAdapter;
import com.ymlion.leisure.ui.model.Meizi;
import com.ymlion.leisure.util.SubscriberAdapter;
import com.ymlion.leisure.view.DividerItemDecoration;
import com.ymlion.lib.base.RvBaseAdapter;
import com.ymlion.lib.utils.DiffCallback;
import com.ymlion.lib.utils.OnRvBottomListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final int PAGE_SIZE = 10;
    static final String EXTRA_STARTING_GALLERY_POSITION = "extra_starting_gallery_position";
    static final String EXTRA_CURRENT_GALLERY_POSITION = "extra_current_gallery_position";

    @BindView(R.id.fuli_rv)
    RecyclerView fuliRv;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.fuli_srl)
    SwipeRefreshLayout refreshLayout;

    private List<Meizi> meizis;
    private RvBaseAdapter<Meizi> adapter;
    private int pageIndex = 1;

    private Bundle mTmpReenterState;

    private final SharedElementCallback mCallback = new SharedElementCallback() {
        @Override
        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
            if (mTmpReenterState != null) {
                int startingPosition = mTmpReenterState.getInt(EXTRA_STARTING_GALLERY_POSITION);
                int currentPosition = mTmpReenterState.getInt(EXTRA_CURRENT_GALLERY_POSITION);
                if (startingPosition != currentPosition) {
                    // If startingPosition != currentPosition the user must have swiped to a
                    // different page in the DetailsActivity. We must update the shared element
                    // so that the correct one falls into place.
                    String newTransitionName = meizis.get(currentPosition).get_id();
                    View newSharedElement = fuliRv.findViewHolderForAdapterPosition(currentPosition).itemView.findViewById(R.id.fuli_iv);
                    if (newSharedElement != null) {
                        names.clear();
                        names.add(newTransitionName);
                        sharedElements.clear();
                        sharedElements.put(newTransitionName, newSharedElement);
                    }
                }

                mTmpReenterState = null;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setExitSharedElementCallback(mCallback);
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        refreshLayout.setOnRefreshListener(this);
        initRv();
        fab.setOnClickListener(view -> {
            Schedulers.io().createWorker().schedule(() -> Glide.get(MainActivity.this).clearDiskCache());
            Snackbar.make(view, "cache is cleared", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
    }

    @Override
    protected void initData() {
        meizis = new ArrayList<>();
        getMeizis(true);
        adapter = new FuliAdapter(this, meizis, R.layout.item_fuli);
        adapter.setOnItemClickListener((view, position) -> GalleryActivity.start(MainActivity.this,
                (ArrayList<Meizi>) meizis, position, view.findViewById(R.id.fuli_iv)));
        fuliRv.setAdapter(adapter);
    }

    private void initRv() {
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        //layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        fuliRv.setLayoutManager(layoutManager);
        //fuliRv.setAnimation(null);
        fuliRv.addOnScrollListener(new OnRvBottomListener() {
            @Override
            public void onBottom() {
                pageIndex++;
                getMeizis(false);
            }
        });
        fuliRv.addItemDecoration(new DividerItemDecoration(this, R.drawable.shape_rect_divider, DividerItemDecoration.BOTH_LIST));
    }

    /**
     * 获取数据
     */
    private void getMeizis(boolean loadCache) {
        Http.build().getMeizhis(PAGE_SIZE, pageIndex, loadCache)
                .subscribe(new SubscriberAdapter<List<Meizi>>(true) {
                    @Override
                    public void onNext(List<Meizi> meiziList) {
                        super.onNext(meizis);
                        updateList(meiziList);
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(false);
                        }
                    }
                });
    }

    private void updateList(List<Meizi> meiziList) {
        if (pageIndex <= 1) {
            if (meizis.isEmpty()) {
                meizis.addAll(meiziList);
                adapter.notifyDataSetChanged();
            } else {
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback<Meizi>(meiziList, meizis), true);
                meizis.clear();
                meizis.addAll(meiziList);
                diffResult.dispatchUpdatesTo(adapter);
            }
        } else {
            final int num = adapter.getItemCount();
            meizis.addAll(meiziList);
            Observable.range(0, meiziList.size())
                    .subscribe(new SubscriberAdapter<Integer>() {
                        @Override
                        public void onNext(Integer integer) {
                            adapter.notifyItemInserted(num + integer);
                        }

                        @Override
                        public void onCompleted() {
                            adapter.notifyItemRangeInserted(num, adapter.getItemCount());
                        }
                    });
        }
    }

    @Override
    public void onActivityReenter(int requestCode, Intent data) {
        super.onActivityReenter(requestCode, data);
        mTmpReenterState = new Bundle(data.getExtras());
        int startingPosition = mTmpReenterState.getInt(EXTRA_STARTING_GALLERY_POSITION);
        int currentPosition = mTmpReenterState.getInt(EXTRA_CURRENT_GALLERY_POSITION);
        if (startingPosition != currentPosition) {
            fuliRv.scrollToPosition(currentPosition);
        }
        postponeEnterTransition();
        fuliRv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                fuliRv.getViewTreeObserver().removeOnPreDrawListener(this);
                // TODO: figure out why it is necessary to request layout here in order to get a smooth transition.
                fuliRv.requestLayout();
                startPostponedEnterTransition();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        Observable.timer(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(aLong -> getMeizis(false));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
