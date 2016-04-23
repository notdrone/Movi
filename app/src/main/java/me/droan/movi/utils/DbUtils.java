package me.droan.movi.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import me.droan.movi.MovieListModel.Result;
import me.droan.movi.favorite.db.FavoriteContract;

/**
 * Created by drone on 23/04/16.
 */
public class DbUtils {
  public static void insertFavorite(Context context, Result model) {
    ContentValues value = new ContentValues();
    // Loop through static array of Flavors, add each to an instance of ContentValues
    // in the array of ContentValues

    value.put(FavoriteContract.FavoriteTable.COL_TITLE, model.title);
    value.put(FavoriteContract.FavoriteTable._ID, model.id);
    value.put(FavoriteContract.FavoriteTable.COL_BACKDROP, model.backdrop_path);
    value.put(FavoriteContract.FavoriteTable.COL_POSTER, model.poster_path);
    value.put(FavoriteContract.FavoriteTable.COL_OVERVIEW, model.overview);
    value.put(FavoriteContract.FavoriteTable.COL_RATING, model.vote_average);
    value.put(FavoriteContract.FavoriteTable.COL_RELEASE, model.release_date);

    // bulkInsert our ContentValues array
    context.getContentResolver().insert(FavoriteContract.FavoriteTable.CONTENT_URI, value);
  }

  public static boolean isInDB(Context context, int id) {
    Uri contentUri = FavoriteContract.FavoriteTable.CONTENT_URI;
    String whereClause = FavoriteContract.FavoriteTable.ID + "=?";
    String[] whereArgs = new String[] {
        "" + id
    };
    Cursor c = context.getContentResolver()
        .query(contentUri, new String[] { FavoriteContract.FavoriteTable.ID }, whereClause,
            whereArgs, null, null);
    if (c.getCount() > 0) {
      return true;
    } else {
      return false;
    }
  }

  public static void removeFavorite(Context context, int id) {
    Uri contentUri = FavoriteContract.FavoriteTable.CONTENT_URI;
    String whereClause = FavoriteContract.FavoriteTable.ID + "=?";
    String[] whereArgs = new String[] {
        "" + id
    };
    int c = context.getContentResolver().delete(contentUri, whereClause, whereArgs);
  }
}
