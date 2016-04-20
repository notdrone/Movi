package me.droan.movi.favorite.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by drone on 20/04/16.
 */
public class FavoriteProvider extends ContentProvider {
  private static final int FAVORITE = 604;
  private static final int FAAVORITE_WITH_ID = 713;
  private static final UriMatcher sUriMatcher = buildUriMatcher();
  private FavoriteDbHelper openDbHelper;

  private static UriMatcher buildUriMatcher() {
    final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    final String authority = FavoriteContract.CONTENT_AUTHORITY;
    matcher.addURI(authority, FavoriteContract.FavoriteTable.TABLE_NAME, FAVORITE);
    matcher.addURI(authority, FavoriteContract.FavoriteTable.TABLE_NAME + "/#", FAAVORITE_WITH_ID);
    return matcher;
  }

  @Override public boolean onCreate() {
    openDbHelper = new FavoriteDbHelper(getContext());
    return true;
  }

  @Nullable @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
      String sortOrder) {
    Cursor retCursor;
    switch (sUriMatcher.match(uri)) {
      // All Flavors selected
      case FAVORITE: {
        retCursor = openDbHelper.getReadableDatabase()
            .query(FavoriteContract.FavoriteTable.TABLE_NAME, projection, selection, selectionArgs,
                null, null, sortOrder);
        return retCursor;
      }
      // Individual flavor based on Id selected
      case FAAVORITE_WITH_ID: {
        retCursor = openDbHelper.getReadableDatabase()
            .query(FavoriteContract.FavoriteTable.TABLE_NAME, projection,
                FavoriteContract.FavoriteTable._ID + " = ?",
                new String[] { String.valueOf(ContentUris.parseId(uri)) }, null, null, sortOrder);
        return retCursor;
      }
      default: {
        // By default, we assume a bad URI
        throw new UnsupportedOperationException("Unknown uri: " + uri);
      }
    }
  }

  @Nullable @Override public String getType(Uri uri) {
    final int match = sUriMatcher.match(uri);
    switch (match) {
      case FAVORITE:
        return FavoriteContract.FavoriteTable.CONTENT_DIR_TYPE;
      case FAAVORITE_WITH_ID:
        return FavoriteContract.FavoriteTable.CONTENT_ITEM_TYPE;
      default:
        throw new UnsupportedOperationException("Unknown Uri: " + uri);
    }
  }

  @Nullable @Override public Uri insert(Uri uri, ContentValues values) {
    final SQLiteDatabase db = openDbHelper.getWritableDatabase();
    Uri returnUri;
    switch (sUriMatcher.match(uri)) {
      case FAVORITE: {
        long _id = db.insert(FavoriteContract.FavoriteTable.TABLE_NAME, null, values);
        // insert unless it is already contained in the database
        if (_id > 0) {
          returnUri = FavoriteContract.FavoriteTable.buildFavoriteUri(_id);
        } else {
          throw new android.database.SQLException("Failed to insert row into: " + uri);
        }
        break;
      }
      case FAAVORITE_WITH_ID: {
        long _id = db.insert(FavoriteContract.FavoriteTable.TABLE_NAME, null, values);
        // insert unless it is already contained in the database
        if (_id > 0) {
          returnUri = FavoriteContract.FavoriteTable.buildFavoriteUri(_id);
        } else {
          throw new android.database.SQLException("Failed to insert row into: " + uri);
        }
        break;
      }

      default: {
        throw new UnsupportedOperationException("Unknown uri: " + uri);
      }
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return returnUri;
  }

  @Override public int delete(Uri uri, String selection, String[] selectionArgs) {
    final SQLiteDatabase db = openDbHelper.getWritableDatabase();
    final int match = sUriMatcher.match(uri);
    int numDeleted;
    switch (match) {
      case FAVORITE:
        numDeleted = db.delete(FavoriteContract.FavoriteTable.TABLE_NAME, selection, selectionArgs);
        // reset _ID
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
            FavoriteContract.FavoriteTable.TABLE_NAME + "'");
        break;
      case FAAVORITE_WITH_ID:
        numDeleted = db.delete(FavoriteContract.FavoriteTable.TABLE_NAME,
            FavoriteContract.FavoriteTable._ID + " = ?",
            new String[] { String.valueOf(ContentUris.parseId(uri)) });
        // reset _ID
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
            FavoriteContract.FavoriteTable.TABLE_NAME + "'");

        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    return numDeleted;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    return 0;
  }
}
