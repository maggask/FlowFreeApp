package is.ru.flowfreeapp.app;
/**
  * @(#)FlowFreeApp 19.9.2014 Anna  
  *  
  * Copyright (c) Anna Laufey Stefánsdóttir  
  */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "GAME_DB";
    public static final int DB_VERSION = 1;

    public static final String TableGames = "games";
    public static final String[] TableGamesCols = { "_id", "gid", "isComplete", "bestMove" };

    private static final String sqlCreateTableGames =
            "CREATE TABLE games(" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " gid INTEGER NOT NULL," +
                    " isComplete BOOLEAN," +
                    " bestMove INTEGER" +
                    ");";

    private static final String sqlDropTableGames =
            "DROP TABLE IF EXISTS games;";

    public DbHelper(Context context) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( sqlCreateTableGames );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
<<<<<<< HEAD
        db.execSQL( sqlDropTableGames );
        onCreate( db );
=======
        db.execSQL(sqlDropTableGames);
        onCreate(db);
>>>>>>> 363e55803dc2c047201d38442b1c32074f06026d
    }
}
