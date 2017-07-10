package com.ymlion.leisure.data;

import com.ymlion.leisure.AppContext;
import com.ymlion.leisure.data.model.Coser;
import com.ymlion.leisure.data.model.DaoSession;
import com.ymlion.leisure.data.model.Meizi;

import org.greenrobot.greendao.rx.RxDao;

import java.util.List;

import rx.Observable;

/**
 * 数据库工具类
 * <p>
 * @author YML
 * @date 2016/9/23
 */

public class DbHelper {

    private static DbHelper DB;
    private RxDao<Meizi, String> meiziDao;
    private RxDao<Coser, Long> coserDao;

    private DbHelper() {
        DaoSession daoSession = AppContext.getInstance().getDaoSession();
        meiziDao = daoSession.getMeiziDao().rx();
        coserDao = daoSession.getCoserDao().rx();
    }

    public static DbHelper get() {
        if (DB == null) {
            DB = new DbHelper();
        }

        return DB;
    }

    /**
     * 获取所有的妹子数据
     *
     * @return list
     */
    public Observable<List<Meizi>> getMeizis() {
        return meiziDao.loadAll()
                .flatMap(Observable::from)
                .toSortedList((meizi, meizi2) -> meizi2.getPublishedAt().compareTo(meizi.getPublishedAt()))
                .filter(list -> list != null && list.size() > 0);
    }

    /**
     * 保存妹子列表
     *
     * @param list 列表
     */
    public void saveMeizis(List<Meizi> list) {
        meiziDao.getDao().insertOrReplaceInTx(list);
    }

    /**
     * 获取所有的coser数据
     *
     * @return list
     */
    public Observable<List<Coser>> getCosers() {
        return coserDao.loadAll()
                .flatMap(Observable::from)
                .toSortedList((preCoser, nextCoser) -> Long.compare(preCoser.getId(), nextCoser.getId()))
                .filter(list -> list != null && list.size() > 0);
    }

    /**
     * 保存coser列表
     *
     * @param list 列表
     */
    public void saveCosers(List<Coser> list) {
        coserDao.getDao().insertOrReplaceInTx(list);
    }
}
