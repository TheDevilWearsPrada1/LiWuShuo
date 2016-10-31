package com.qf.liwushuo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Administrator on 2016/10/20 0020.
 */

public class UserDataBaseOpenHelper extends OrmLiteSqliteOpenHelper {

    //获取一个单例的对象
    private static UserDataBaseOpenHelper openHelper;
    public static UserDataBaseOpenHelper getOpenHelper(Context context) {
        if (openHelper == null) {
            openHelper = new UserDataBaseOpenHelper(context);
        }
        return openHelper;
    }

    public UserDataBaseOpenHelper(Context context) {
        super(context, "myuser.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            //创建对应的数据库表
            TableUtils.createTableIfNotExists(connectionSource,OldSearchEntity.class );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        if (i>i1) return;

        try {
            TableUtils.dropTable(connectionSource,OldSearchEntity.class,true);//丢弃老的数据库表
            onCreate(sqLiteDatabase, connectionSource);//表更新
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
