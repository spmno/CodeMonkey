package com.dawnstep.codemonkey;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class NewsDatabaseHelper extends OrmLiteSqliteOpenHelper {
	
	private static final String TAG = "NewsDatabaseHelper";
    // 数据库名称
    private static final String DATABASE_NAME = "news.db";
    // 数据库version
    private static final int DATABASE_VERSION = 1;
    private static NewsDatabaseHelper databaseHelper;
    private static Context appContext;
    private Map<String, Dao<News, Integer>> daoMaps = null;
    
    static public void setContext(Context context) {
		appContext = context;
	}
    
    public static synchronized NewsDatabaseHelper getInstance(){
		if (databaseHelper == null) {
			databaseHelper = new NewsDatabaseHelper(appContext);
		}
		return databaseHelper;           
	}

    private void initDaoMaps() {
		daoMaps = new HashMap<String, Dao<News, Integer>>();
		daoMaps.put("news", null);
	}
    
	public NewsDatabaseHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
		// TODO Auto-generated constructor stub
	}
	
	public NewsDatabaseHelper(Context context)    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        initDaoMaps();
	}
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		// TODO Auto-generated method stub
		try {
			TableUtils.createTable(connectionSource, News.class);
		} catch (Exception e) {
			Log.e(TAG, "create db fail");
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		try {
			TableUtils.dropTable(connectionSource, News.class, true);
			onCreate(db, connectionSource);
			Log.i(TAG, "update database success");
		} catch (java.sql.SQLException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "update database fail");
			e.printStackTrace();
		}
	}
	
	@Override
	public void close() {
		super.close();
		daoMaps.clear();
	}

	public Dao<News, Integer> getNewsDao() {
		Dao<News, Integer> newsDao = daoMaps.get("news");
		if (newsDao == null) {
			try {
				newsDao = getDao(News.class);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return newsDao;
	}

}
