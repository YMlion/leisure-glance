package com.ymlion.leisure;

import com.ymlion.leisure.data.model.DaoMaster;
import com.ymlion.leisure.data.model.DaoSession;
import com.ymlion.lib.BaseApplication;

import org.greenrobot.greendao.database.Database;

/**
 * application object
 *
 * Created by ymlion on 16/6/18.
 */
public class AppContext extends BaseApplication {

    private static AppContext INSTANCE;
    private DaoSession daoSession;

    public static AppContext getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppContext();
        }
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        initDao();
    }

    private void initDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "gank_io");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
