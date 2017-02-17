package org.seraph.mvprxjavaretrofit.db.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 用户表
 * date：2017/2/14 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Entity
public class UserTable {

    /**
     * id
     */
    @Id(autoincrement = true)
    private Long _id;

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
    @Generated(hash = 1883399409)
    public UserTable(Long _id, String token, String name, String gender,
            String headPortrait) {
        this._id = _id;
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

}
