package com.qf.liwushuo.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2016/10/27 0027.
 */
@DatabaseTable(tableName = "tb_oldsearch")
public class OldSearchEntity {
    @DatabaseField(columnName = "_id",generatedId = true)
    long _id;
    @DatabaseField(columnName = "word",canBeNull = true,unique = true)
    String word;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
