package me.droan.movi.detail.cast;

import android.content.Context;
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
import me.droan.movi.detail.cast.model.Cast;
import me.droan.movi.utils.Constants;

/**
 * Created by drone on 19-04-2016.
 */
public class CastAdapter extends RecyclerView.Adapter<CastAdapter.Holder> {

  private Context context;
  private List<Cast> cast;

  public CastAdapter(Context context, List<Cast> cast) {
    this.context = context;
    this.cast = cast;
  }

  @Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(R.layout.cast_item, parent, false);
    return new Holder(view);
  }

  @Override public void onBindViewHolder(Holder holder, int position) {
    String url = Constants.POSTER_BASE + cast.get(position).profile_path;
    holder.castProfile.setImageURI(Uri.parse(url));
  }

  @Override public int getItemCount() {
    return cast.size();
  }

  class Holder extends RecyclerView.ViewHolder {
    @Bind(R.id.castProfile) SimpleDraweeView castProfile;
    public Holder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
