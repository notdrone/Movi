package me.droan.movi.favorite;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.droan.movi.MovieListModel.Result;
import me.droan.movi.R;
import me.droan.movi.favorite.db.FavoriteContract;
import me.droan.movi.utils.CursorRecyclerViewAdapter;
import me.droan.movi.view.CoolLayoutItem;

/**
 * Created by drone on 15/04/16.
 */
public class FavoriteAdapter extends CursorRecyclerViewAdapter<FavoriteAdapter.Holder> {
  private static final int VIEW_FOOTER = 314;
  private static final int VIEW_FIRST = 442;
  private static final int VIEW_OTHER = 433;
  private Context context;
  private OnItemClickListener listener;

  public FavoriteAdapter(Context context, Cursor cursor, OnItemClickListener listener) {
    super(context, cursor);
    this.context = context;
    this.listener = listener;
  }

  @Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    if (viewType == VIEW_FIRST) {
      return new Holder(
          (CoolLayoutItem) inflater.inflate(R.layout.recycler_first_item, parent, false));
    } else {
      throw new IllegalStateException("NO VIEW TYPE FOUND");
    }
  }

  @Override public void onBindViewHolder(Holder viewHolder, Cursor cursor) {
    viewHolder.bindTo(cursor, listener);
  }

  @Override public int getItemViewType(int position) {
    return VIEW_FIRST;
  }

  public interface OnItemClickListener {
    public void onItemClick(Result model);
  }

  class Holder extends RecyclerView.ViewHolder {
    CoolLayoutItem itemView;

    public Holder(CoolLayoutItem itemView) {
      super(itemView);
      this.itemView = itemView;
    }

    public void bindTo(Cursor cursor, final OnItemClickListener listener) {
      final Result model = generateModel(cursor);
      itemView.onBind(model);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          listener.onItemClick(model);
        }
      });
    }

    @NonNull private Result generateModel(Cursor cursor) {
      Result model = new Result();
      model.id = cursor.getInt(cursor.getColumnIndex(FavoriteContract.FavoriteTable.ID));
      model.title =
          cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteTable.COL_TITLE));
      model.vote_average =
          cursor.getInt(cursor.getColumnIndex(FavoriteContract.FavoriteTable.COL_RATING));
      model.overview =
          cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteTable.COL_OVERVIEW));
      model.poster_path =
          cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteTable.COL_POSTER));
      model.release_date =
          cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteTable.COL_RELEASE));
      model.backdrop_path =
          cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteTable.COL_BACKDROP));
      return model;
    }
  }
}
