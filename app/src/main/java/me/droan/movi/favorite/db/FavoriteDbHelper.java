package me.droan.movi.favorite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by drone on 20/04/16.
 */
public class FavoriteDbHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "favorite.db";
  private static final int DATABASE_VERSION = 1;
  private String SQL_CREATE_FLAVOR_TABLE =
      "CREATE TABLE " + FavoriteContract.FavoriteTable.TABLE_NAME + " ( " +
          " " + FavoriteContract.FavoriteTable._ID + " INTEGER  PRIMARY KEY AUTOINCREMENT," +
          " " + FavoriteContract.FavoriteTable.COL_TITLE + " TEXT NOT NULL," +
          " " + FavoriteContract.FavoriteTable.COL_RATING + " TEXT NOT NULL," +
          " " + FavoriteContract.FavoriteTable.COL_RELEASE + " TEXT NOT NULL," +
          " " + FavoriteContract.FavoriteTable.COL_OVERVIEW + " TEXT NOT NULL," +
          " " + FavoriteContract.FavoriteTable.COL_POSTER + " TEXT NOT NULL," +
          " " + FavoriteContract.FavoriteTable.COL_BACKDROP + " TEXT NOT NULL);";

  private String SQL_DROP_FLAVOR_TABLE =
      "DROP TABLE IF EXISTS " + FavoriteContract.FavoriteTable.TABLE_NAME + ";";

  public FavoriteDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_FLAVOR_TABLE);

  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(SQL_DROP_FLAVOR_TABLE);
    onCreate(db);
  }
}
