package me.droan.movi.upcomingTop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import me.droan.movi.MovieListModel.Result;
import me.droan.movi.R;
import me.droan.movi.view.SimpleLayoutItem;

/**
 * Created by drone on 15/04/16.
 */
public class CommonUpcomingTopAdapter extends RecyclerView.Adapter {
  private static final int VIEW_FOOTER = 314;
  private static final int VIEW_FIRST = 442;
  private static final int VIEW_OTHER = 433;
  private Context context;

  public CommonUpcomingTopAdapter(Context context) {
    this.context = context;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);

    if (viewType == VIEW_OTHER) {
      return new Holder(
          (SimpleLayoutItem) inflater.inflate(R.layout.recycler_other_item, parent, false));
    } else if (viewType == VIEW_FOOTER) {
      return null;
    } else {
      throw new IllegalStateException("NO VIEW TYPE FOUND");
    }
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

  }

  @Override public int getItemCount() {
    return 10;
  }

  @Override public int getItemViewType(int position) {
    return VIEW_OTHER;
  }

  class Holder extends RecyclerView.ViewHolder {
    SimpleLayoutItem itemView;

    public Holder(SimpleLayoutItem itemView) {
      super(itemView);
      this.itemView = itemView;
    }

    public void bindTo(Result model) {

    }
  }
}

