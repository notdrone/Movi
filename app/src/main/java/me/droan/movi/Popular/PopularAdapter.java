package me.droan.movi.popular;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import me.droan.movi.MovieListModel.Result;
import me.droan.movi.R;
import me.droan.movi.view.CoolLayoutItem;
import me.droan.movi.view.SimpleLayoutItem;

/**
 * Created by drone on 15/04/16.
 */
public class PopularAdapter extends RecyclerView.Adapter {
  private static final int VIEW_FOOTER = 314;
  private static final int VIEW_FIRST = 442;
  private static final int VIEW_OTHER = 433;
  private Context context;
  private ArrayList<Result> list;

  public PopularAdapter(Context context, ArrayList<Result> list) {
    this.context = context;
    this.list = list;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    if (viewType == VIEW_FIRST) {
      return new Holder1(
          (CoolLayoutItem) inflater.inflate(R.layout.recycler_first_item, parent, false));
    } else if (viewType == VIEW_OTHER) {
      return new Holder2(
          (SimpleLayoutItem) inflater.inflate(R.layout.recycler_other_item, parent, false));
    } else if (viewType == VIEW_FOOTER) {
      return new Holder2(
          (SimpleLayoutItem) inflater.inflate(R.layout.recycler_first_item, parent, false));
    } else {
      throw new IllegalStateException("NO VIEW TYPE FOUND");
    }
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder _holder, int position) {
    if (position == 0) {
      Holder1 holder = (Holder1) _holder;
      holder.bindTo(list.get(position));
    } else {
      Holder2 holder = (Holder2) _holder;
      holder.bindTo(list.get(position));
    }

  }

  @Override public int getItemCount() {
    return list.size();
  }

  @Override public int getItemViewType(int position) {
    if (position == 0) {
      return VIEW_FIRST;
    } else {
      return VIEW_OTHER;
    }
  }

  class Holder1 extends RecyclerView.ViewHolder {
    CoolLayoutItem itemView;

    public Holder1(CoolLayoutItem itemView) {
      super(itemView);
      this.itemView = itemView;
    }

    public void bindTo(Result model) {
      itemView.onBind(model);
    }
  }

  class Holder2 extends RecyclerView.ViewHolder {
    SimpleLayoutItem itemView;

    public Holder2(SimpleLayoutItem itemView) {
      super(itemView);
      this.itemView = itemView;
    }

    public void bindTo(Result model) {
      itemView.onBind(model);

    }
  }
}
