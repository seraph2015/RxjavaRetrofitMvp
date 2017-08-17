package org.seraph.mvprxjavaretrofit.data.local.db.help;

import org.seraph.mvprxjavaretrofit.data.local.db.gen.DaoSession;
import org.seraph.mvprxjavaretrofit.data.local.db.gen.SearchHistoryTableDao;
import org.seraph.mvprxjavaretrofit.data.local.db.table.SearchHistoryTable;

import java.util.List;

import javax.inject.Inject;

/**
 * 通用的用户记录表
 * date：2017/7/26 17:01
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class SearchHistoryHelp {

    private SearchHistoryTableDao mSearchHistoryTableDao;

    @Inject
    public SearchHistoryHelp(DaoSession daoSession) {
        mSearchHistoryTableDao = daoSession.getSearchHistoryTableDao();
    }

    /**
     * 保存到数据库
     *
     * @param userId  区分不同用户的id
     * @param type    区分不同地方使用的type
     * @param saveStr 保存的str
     */
    public void saveSearchToDB(int userId, String type, String saveStr) {
        //清理之前在同一用户种同一类型的重复的key
        deleteAllSearchDB(userId, type, saveStr);

        SearchHistoryTable searchHistoryTable = new SearchHistoryTable();
        searchHistoryTable.setSearchKey(saveStr);
        searchHistoryTable.setSearchTime(System.currentTimeMillis());
        searchHistoryTable.setType(type);
        searchHistoryTable.setUserId(userId);
        mSearchHistoryTableDao.save(searchHistoryTable);
    }

    /**
     * 清理对应用户对应类型的所有历史数据库
     */
    public void deleteAllSearchDB(int userId, String type) {
        List<SearchHistoryTable> historyTableList = mSearchHistoryTableDao.queryBuilder()
                .where(SearchHistoryTableDao.Properties.UserId.eq(userId), SearchHistoryTableDao.Properties.Type.eq(type))
                .list();
        for (SearchHistoryTable searchHistoryTable : historyTableList) {
            mSearchHistoryTableDao.delete(searchHistoryTable);
        }
    }

    /**
     * 清理对应用户对应类型的单个历史数据库
     * 在保存的时候清理了对应重复保存的key，所有使用key进行唯一性移除数据
     *
     * @param userId    用户id
     * @param type      记录的类型（区分不同的使用地方）
     * @param deleteStr 需要移除的对应key
     */
    public void deleteAllSearchDB(int userId, String type, String deleteStr) {
        List<SearchHistoryTable> historyTableList = mSearchHistoryTableDao.queryBuilder()
                .where(SearchHistoryTableDao.Properties.UserId.eq(userId), SearchHistoryTableDao.Properties.Type.eq(type), SearchHistoryTableDao.Properties.SearchKey.eq(deleteStr))
                .list();
        for (SearchHistoryTable searchHistoryTable : historyTableList) {
            mSearchHistoryTableDao.delete(searchHistoryTable);
        }
    }


    /**
     * 查询对应用户的和对应区域的历史记录信息（时间倒叙）
     *
     * @param userId 用户id
     * @param type   记录的类型（区分不同的使用地方）
     */
    public List<SearchHistoryTable> querySearchDB(int userId, String type) {
        return mSearchHistoryTableDao.queryBuilder()
                .where(SearchHistoryTableDao.Properties.UserId.eq(userId), SearchHistoryTableDao.Properties.Type.eq(type))
                .orderDesc(SearchHistoryTableDao.Properties.SearchTime).list();
    }

}
