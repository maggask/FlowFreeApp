package is.ru.flowfreeapp.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
  * @(#)FlowFreeApp 19.9.2014 Anna  
  *  
  * Copyright (c) Anna Laufey Stefánsdóttir  
  */

public class GameAdapter {

    SQLiteDatabase db;
    DbHelper DbHelper;
    Context context;

    public GameAdapter(Context c) {
        context = c;
    }

    public GameAdapter openToRead() {
        DbHelper = new DbHelper(context);
        db = DbHelper.getReadableDatabase();
        return this;
    }

    public GameAdapter openToWrite() {
        DbHelper = new DbHelper(context);
        db = DbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public long insertGame(int gid, boolean isComplete, int bestMove) {
        String[] cols = DbHelper.TableGamesCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put(cols[1], ((Integer)gid).toString());
        contentValues.put(cols[2], isComplete ? "1" : "0");
        contentValues.put(cols[3], ((Integer)bestMove).toString());

        openToWrite();
        long value = db.insert(DbHelper.TableGames, null, contentValues);
        close();
        return value;
    }

    public long updateWonGame(int gid, boolean finished, int bestMove) {
        String[] cols = DbHelper.TableGamesCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put(cols[1], ((Integer)gid).toString());
        contentValues.put(cols[2], finished ? "1" : "0");
        contentValues.put(cols[3], ((Integer)bestMove).toString());

        openToWrite();
        long value = db.update(DbHelper.TableGames, contentValues, cols[1] + "=" + gid, null ); //package id, puzzle id, game id
        close();
        return value;
    }

    public Cursor queryGames() {
        openToRead();
        Cursor cursor = db.query( DbHelper.TableGames,
                DbHelper.TableGamesCols, null, null, null, null, null);
        return cursor;
    }

    public Cursor queryGames(int sid) {
        openToRead();
        String[] cols = DbHelper.TableGamesCols;
        Cursor cursor = db.query( DbHelper.TableGames,
                cols, cols[1] + "=" + sid, null, null, null, null);
        return cursor;
    }

}
