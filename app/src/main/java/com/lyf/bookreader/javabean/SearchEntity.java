package com.lyf.bookreader.javabean;

import java.io.Serializable;

/**
 * Created by ganlin on 2017/4/22.
 */

public class SearchEntity implements Serializable {
    public String key;

    public SearchEntity(String key) {
        this.key = key;
    }

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
