package org.seraph.mvprxjavaretrofit.data.local.db.table;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.seraph.mvprxjavaretrofit.data.local.db.gen.DaoSession;
import org.seraph.mvprxjavaretrofit.data.local.db.gen.SearchHistoryTableDao;
import org.seraph.mvprxjavaretrofit.data.local.db.gen.UserTableDao;

import java.util.List;

/**
 * 用户表
 * date：2017/2/14 17:06
 * author：xiongj
 * mail：417753393@qq.com
 *
 * 一般用户表保存一条当前的登录用户的信息，此为测试数据库示例。
 * 在实际开发中，直接用服务端返回的用户id会更加方便使用。
 * 在注销的时候，清除数据库表。
 * 更新的实体判断以主键为基础。
 **/
@Entity
public class UserTable {

    /**
     * id 自增长
     */
    @Id(autoincrement = true)
    private Long _id;

    /**
     * 用户id
     */
    @NotNull
    private int id;

    /**
     * 用户token
     */
    private String token;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String gender;
    /**
     * 头像
     */
    private String headPortrait;

    //@ToMany(referencedJoinProperty = "userId") -> SearchHistoryTable类的userId作为外键，与当前类的主键相连
    //@ToMany(joinProperties = {@JoinProperty(name = "userId", referencedName = "userId")}) -> SearchHistoryTable类的userId作为外键，与当前类的非主键不为空的键相连
    //@JoinEntity 如果在做多对多的关系，有其他的表或实体参与，可以给目标属性添加这个额外的注解
    @ToMany(joinProperties = {@JoinProperty(name = "id", referencedName = "userId")})
    private List<SearchHistoryTable> listSearchHistory;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1224316336)
    private transient UserTableDao myDao;

    @Generated(hash = 2133800168)
    public UserTable(Long _id, int id, String token, String name, String gender, String headPortrait) {
        this._id = _id;
        this.id = id;
        this.token = token;
        this.name = name;
        this.gender = gender;
        this.headPortrait = headPortrait;
    }

    @Generated(hash = 726134616)
    public UserTable() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeadPortrait() {
        return this.headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 465397896)
    public List<SearchHistoryTable> getListSearchHistory() {
        if (listSearchHistory == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SearchHistoryTableDao targetDao = daoSession.getSearchHistoryTableDao();
            List<SearchHistoryTable> listSearchHistoryNew = targetDao._queryUserTable_ListSearchHistory(id);
            synchronized (this) {
                if (listSearchHistory == null) {
                    listSearchHistory = listSearchHistoryNew;
                }
            }
        }
        return listSearchHistory;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 2001824891)
    public synchronized void resetListSearchHistory() {
        listSearchHistory = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1889643915)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserTableDao() : null;
    }


}
