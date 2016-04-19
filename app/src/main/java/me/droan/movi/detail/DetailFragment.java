package me.droan.movi.detail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.drawee.view.SimpleDraweeView;
import me.droan.movi.MovieListModel.Result;
import me.droan.movi.R;
import me.droan.movi.utils.Constants;

/**
 * Created by drone on 16/04/16.
 */
public class DetailFragment extends Fragment {
  @Bind(R.id.title) TextView title;
  @Bind(R.id.poster) SimpleDraweeView poster;
  @Bind(R.id.backdrop) SimpleDraweeView backdrop;
  @Bind(R.id.rating) RatingBar rating;
  private Result model;

  public static DetailFragment newInstance(Result model) {
    Bundle args = new Bundle();
    args.putSerializable("KEY", model);
    DetailFragment fragment = new DetailFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    model = (Result) getArguments().getSerializable("KEY");
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_detail, container, false);
    ButterKnife.bind(this, view);
    title.setText(model.title);
    poster.setImageURI(Uri.parse(Constants.POSTER_BASE + model.poster_path));
    Log.e("*******************", Uri.parse(Constants.POSTER_BASE + model.poster_path).toString());
    backdrop.setImageURI(Uri.parse(Constants.POSTER_BASE + model.backdrop_path));
    rating.setProgress((int) model.vote_average);
    return view;
  }
}
