package me.droan.movi.detail.video;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.drawee.view.SimpleDraweeView;
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

    holder.bindTo(context, list.get(position));
  }

  @Override public int getItemCount() {
    return list.size();
  }

  public void refresh(List<Result> trailerModel) {
    this.list = trailerModel;
    notifyDataSetChanged();
  }

  class Holder extends RecyclerView.ViewHolder {
    @Bind(R.id.videoThumb) SimpleDraweeView viewThumb;
    public Holder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

    }

    public void bindTo(final Context context, final Result result) {
      String url = "https://i.ytimg.com/vi/"
          + result.key
          + "/hqdefault.jpg?custom=true&w=196&h=110&stc=true&jpg444=true&jpgq=90&sp=68&sigh=jdKUu6Lrouuoq8u5KEsmGosnoRE";
      viewThumb.setImageURI(Uri.parse(url));
      viewThumb.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          context.startActivity(new Intent(Intent.ACTION_VIEW,
              Uri.parse("http://www.youtube.com/watch?v=" + result.key)));
        }
      });
    }
  }
}