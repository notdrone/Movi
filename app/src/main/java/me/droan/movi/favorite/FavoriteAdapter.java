package me.droan.movi.favorite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.droan.movi.R;

/**
 * Created by drone on 15/04/16.
 */
public class FavoriteAdapter extends RecyclerView.Adapter {
  private static final int VIEW_FOOTER = 314;
  private static final int VIEW_FIRST = 442;
  private static final int VIEW_OTHER = 433;
  private Context context;

  public FavoriteAdapter(Context context) {
    this.context = context;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    if (viewType == VIEW_FIRST) {
      return new Holder(inflater.inflate(R.layout.recycler_first_item, parent, false));
    } else if (viewType == VIEW_FOOTER) {
      return new Holder(inflater.inflate(R.layout.recycler_first_item, parent, false));
    } else {
      throw new IllegalStateException("NO VIEW TYPE FOUND");
    }
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

  }

  @Override public int getItemCount() {
    return 100;
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
