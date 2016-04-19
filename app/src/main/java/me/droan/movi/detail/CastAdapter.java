package me.droan.movi.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.droan.movi.R;

/**
 * Created by drone on 19-04-2016.
 */
public class CastAdapter extends RecyclerView.Adapter {

  private Context context;

  public CastAdapter(Context context) {
    this.context = context;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(R.layout.cast_item, parent, false);
    return new Holder(view);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

  }

  @Override public int getItemCount() {
    return 20;
  }

  class Holder extends RecyclerView.ViewHolder {

    public Holder(View itemView) {
      super(itemView);
    }
  }
}
