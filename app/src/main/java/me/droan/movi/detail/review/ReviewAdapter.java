package me.droan.movi.detail.review;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.List;
import me.droan.movi.R;
import me.droan.movi.detail.review.model.Result;

/**
 * Created by drone on 19-04-2016.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.Holder> {

  private Context context;
  private List<Result> list;

  public ReviewAdapter(Context context, List<Result> list) {
    this.context = context;
    this.list = list;
  }

  @Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(R.layout.review_item, parent, false);
    return new Holder(view);
  }

  @Override public void onBindViewHolder(Holder holder, int position) {
    holder.bindTo(list.get(position));
  }

  @Override public int getItemCount() {
    return list.size();
  }

  public void refresh(List<Result> reviewModel) {
    this.list = reviewModel;
    notifyDataSetChanged();
  }

  class Holder extends RecyclerView.ViewHolder {
    @Bind(R.id.author) TextView author;
    @Bind(R.id.review) TextView review;

    public Holder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bindTo(Result result) {
      author.setText(result.author);
      review.setText(Html.fromHtml(result.content));
    }
  }
}
