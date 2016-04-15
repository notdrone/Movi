package me.droan.movi.Popular;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.droan.movi.R;

/**
 * Created by drone on 15/04/16.
 */
public class PopularAdapter extends RecyclerView.Adapter {
  private static final int VIEW_FOOTER = 314;
  private static final int VIEW_FIRST = 442;
  private static final int VIEW_OTHER = 433;
  private Context context;

  public PopularAdapter(Context context) {
    this.context = context;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    if (viewType == VIEW_FIRST) {
      return new Holder(inflater.inflate(R.layout.recycler_first_item, parent, false));
    } else if (viewType == VIEW_OTHER) {
      return new Holder(inflater.inflate(R.layout.recycler_other_item, parent, false));
    } else if (viewType == VIEW_FOOTER) {
      return new Holder(inflater.inflate(R.layout.recycler_first_item, parent, false));
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
    if (position == 0) {
      return VIEW_FIRST;
    } else {
      return VIEW_OTHER;
    }
  }

  class Holder extends RecyclerView.ViewHolder {

    public Holder(View itemView) {
      super(itemView);
    }
  }
}
