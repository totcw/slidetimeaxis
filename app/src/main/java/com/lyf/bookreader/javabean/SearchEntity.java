package com.lyf.bookreader.javabean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by gan lin on 2017/4/22.
 */

@Entity
public class SearchEntity implements Serializable {
    public String key;

    @Generated(hash = 456590710)
    public SearchEntity(String key) {
        this.key = key;
    }
    @Generated(hash = 1021466028)
    public SearchEntity() {
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "SearchEntity{" +
                "key='" + key + '\'' +
                '}';
    }
}
