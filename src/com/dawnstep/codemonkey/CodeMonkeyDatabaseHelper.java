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

public class CodeMonkeyDatabaseHelper extends OrmLiteSqliteOpenHelper {
	
	private static final String TAG = "NewsDatabaseHelper";
    // 数据库名称
    private static final String DATABASE_NAME = "news.db";
    // 数据库version
    private static final int DATABASE_VERSION = 1;
    private static CodeMonkeyDatabaseHelper databaseHelper;
    private static Context appContext;
    private Map<String, Dao> daoMaps = null;
    
    static public void setContext(Context context) {
		appContext = context;
	}
    
    public static synchronized CodeMonkeyDatabaseHelper getInstance(){
		if (databaseHelper == null) {
			databaseHelper = new CodeMonkeyDatabaseHelper(appContext);
		}
		return databaseHelper;           
	}

    private void initDaoMaps() {
		daoMaps = new HashMap<String, Dao>();
		daoMaps.put("news", null);
		daoMaps.put("newsImage", null);
		daoMaps.put("newsSkillGetKind", null);
		daoMaps.put("newSkillGet", null);
		
	}
    
	public CodeMonkeyDatabaseHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
		// TODO Auto-generated constructor stub
	}
	
	public CodeMonkeyDatabaseHelper(Context context)    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        initDaoMaps();
	}
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		// TODO Auto-generated method stub
		try {
			TableUtils.createTable(connectionSource, News.class);
			TableUtils.createTable(connectionSource, NewsImage.class);
			TableUtils.createTable(connectionSource, NewSkillGet.class);
			TableUtils.createTable(connectionSource, NewSkillGetKind.class);
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
			TableUtils.dropTable(connectionSource, NewsImage.class, true);
			TableUtils.dropTable(connectionSource, NewSkillGet.class, true);
			TableUtils.dropTable(connectionSource, NewSkillGetKind.class, true);
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
				daoMaps.put("news", newsDao);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return newsDao;
	}
	
	public Dao<NewsImage, Integer> getNewsImageDao() {
		Dao<NewsImage, Integer> newsImageDao = daoMaps.get("newsImage");
		if (newsImageDao == null) {
			try {
				newsImageDao = getDao(NewsImage.class);
				daoMaps.put("newsImage", newsImageDao);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return newsImageDao;
	}

	public Dao<NewSkillGet, Integer> getNewSkillGetDao() {
		Dao<NewSkillGet, Integer> newSkillGetDao = daoMaps.get("newSkillGet");
		if (newSkillGetDao == null) {
			try {
				newSkillGetDao = getDao(NewSkillGet.class);
				daoMaps.put("newsImage", newSkillGetDao);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return newSkillGetDao;
	}
	
	public Dao<NewSkillGetKind, Integer> getNewSkillGetKindDao() {
		Dao<NewSkillGetKind, Integer> newSkillGetSkillDao = daoMaps.get("newSkillGetKind");
		if (newSkillGetSkillDao == null) {
			try {
				newSkillGetSkillDao = getDao(NewSkillGetKind.class);
				daoMaps.put("newSkillGetKind", newSkillGetSkillDao);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return newSkillGetSkillDao;
	}
}
