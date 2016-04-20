package me.droan.movi.detail.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import me.droan.movi.R;
import me.droan.movi.detail.video.model.Result;

/**
 * Created by drone on 19-04-2016.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.Holder> {

  private Context context;
  private List<Result> list;

  public TrailerAdapter(Context context, List<Result> list) {
    this.context = context;
    this.list = list;
  }

  @Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(R.layout.trailer_item, parent, false);
    return new Holder(view);
  }

  @Override public void onBindViewHolder(Holder holder, int position) {
  }

  @Override public int getItemCount() {
    return list.size();
  }

  class Holder extends RecyclerView.ViewHolder {

    public Holder(View itemView) {
      super(itemView);
    }
  }
}