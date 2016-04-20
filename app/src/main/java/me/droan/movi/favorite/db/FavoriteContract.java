package me.droan.movi.favorite.db;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by drone on 20/04/16.
 */
public class FavoriteContract {

  public static final String CONTENT_AUTHORITY = "me.droan.movi";
  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
  private static final String TAG = "FavoriteContract";

  public static final class FavoriteTable implements BaseColumns {

    public static final String TABLE_NAME = "favorite";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String RATING = "rating";
    public static final String POSTER = "poster";
    public static final String BACKDROP = "backdrop";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE = "release";

    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

    public static final String CONTENT_DIR_TYPE =
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE =
        ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

    public static Uri buildFavoriteUri(long id) {
      return ContentUris.withAppendedId(CONTENT_URI, id);
    }
  }
}
