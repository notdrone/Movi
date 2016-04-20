package me.droan.movi.favorite;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import me.droan.movi.R;
import me.droan.movi.utils.CursorRecyclerViewAdapter;

/**
 * Created by drone on 15/04/16.
 */
public class FavoriteAdapter extends CursorRecyclerViewAdapter<FavoriteAdapter.Holder> {
  private static final int VIEW_FOOTER = 314;
  private static final int VIEW_FIRST = 442;
  private static final int VIEW_OTHER = 433;
  private Context context;

  public FavoriteAdapter(Context context, Cursor cursor) {
    super(context, cursor);
    this.context = context;
  }

  @Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    if (viewType == VIEW_FIRST) {
      return new Holder(inflater.inflate(R.layout.recycler_first_item, parent, false));
    } else if (viewType == VIEW_FOOTER) {
      return new Holder(inflater.inflate(R.layout.recycler_first_item, parent, false));
    } else {
      throw new IllegalStateException("NO VIEW TYPE FOUND");
    }
  }

  @Override public void onBindViewHolder(Holder viewHolder, Cursor cursor) {
    Toast.makeText(context, "" + cursor.getCount(), Toast.LENGTH_SHORT).show();
  }

  @Override public int getItemViewType(int position) {
    return VIEW_FIRST;
  }

  class Holder extends RecyclerView.ViewHolder {

    public Holder(View itemView) {
      super(itemView);
    }
  }
}
