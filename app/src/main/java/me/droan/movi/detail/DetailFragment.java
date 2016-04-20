package me.droan.movi.detail;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;
import me.droan.movi.MoviServices;
import me.droan.movi.MovieListModel.Result;
import me.droan.movi.R;
import me.droan.movi.detail.review.ReviewAdapter;
import me.droan.movi.detail.review.model.ReviewModel;
import me.droan.movi.detail.video.TrailerAdapter;
import me.droan.movi.detail.video.model.VideoModel;
import me.droan.movi.favorite.db.FavoriteContract;
import me.droan.movi.utils.Constants;
import me.droan.movi.utils.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by drone on 16/04/16.
 */
public class DetailFragment extends Fragment {
  @Bind(R.id.title) TextView title;
  @Bind(R.id.poster) SimpleDraweeView poster;
  @Bind(R.id.backdrop) SimpleDraweeView backdrop;
  @Bind(R.id.rating) RatingBar rating;
  @Bind(R.id.description) TextView description;
  @Bind(R.id.release) TextView release;
  @Bind(R.id.castRecycler) RecyclerView castRecycler;
  @Bind(R.id.reviewRecycler) RecyclerView reviewRecycler;
  @Bind(R.id.trailerRecycler) RecyclerView tralerRecycler;
  @Bind(R.id.reviewRoot) CardView reviewRoot;
  @Bind(R.id.favorite) Button favorite;
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
    tralerRecycler.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    castRecycler.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    reviewRecycler.setAdapter(new ReviewAdapter(getActivity(),
        new ArrayList<me.droan.movi.detail.review.model.Result>()));
    reviewRecycler.setLayoutManager(new UnscrollableLinearLayoutManager(getActivity()));
    //tralerRecycler.setAdapter(new TrailerAdapter(getActivity(), model.results));
    castRecycler.setAdapter(new CastAdapter(getActivity()));
    handleReviewService();
    //handleTrailerService();
    title.setText(model.title);
    poster.setImageURI(Uri.parse(Constants.POSTER_BASE + model.poster_path));
    backdrop.setImageURI(Uri.parse(Constants.POSTER_BASE + model.backdrop_path));
    rating.setProgress((int) model.vote_average);
    release.setText(model.release_date);
    description.setText(model.overview);
    favorite.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        insertData();
      }
    });

    return view;
  }

  public void insertData() {
    ContentValues value = new ContentValues();
    // Loop through static array of Flavors, add each to an instance of ContentValues
    // in the array of ContentValues

    value.put(FavoriteContract.FavoriteTable.COL_TITLE, model.title);
    value.put(FavoriteContract.FavoriteTable._ID, model.id);
    value.put(FavoriteContract.FavoriteTable.COL_BACKDROP, model.backdrop_path);
    value.put(FavoriteContract.FavoriteTable.COL_POSTER, model.poster_path);
    value.put(FavoriteContract.FavoriteTable.COL_OVERVIEW, model.overview);
    value.put(FavoriteContract.FavoriteTable.COL_RATING, model.vote_average);
    value.put(FavoriteContract.FavoriteTable.COL_RELEASE, model.release_date);

    // bulkInsert our ContentValues array
    getActivity().getContentResolver().insert(FavoriteContract.FavoriteTable.CONTENT_URI, value);
  }

  private void handleReviewService() {
    Retrofit retrofit = RetrofitHelper.getRetrofitObj();
    MoviServices service = retrofit.create(MoviServices.class);
    Call<ReviewModel> call = service.getReviews(model.id);
    call.enqueue(new Callback<ReviewModel>() {
      @Override public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
        if (response.isSuccessful()) {
          ReviewModel model = response.body();
          if (model.results.size() > 0) {
            reviewRoot.setVisibility(View.VISIBLE);
            reviewRecycler.setAdapter(new ReviewAdapter(getActivity(), model.results));
          }
        }
      }

      @Override public void onFailure(Call<ReviewModel> call, Throwable t) {

      }
    });

  }

  private void handleTrailerService() {
    Retrofit retrofit = RetrofitHelper.getRetrofitObj();
    MoviServices service = retrofit.create(MoviServices.class);
    Call<VideoModel> call = service.getVideos(model.id);
    call.enqueue(new Callback<VideoModel>() {
      @Override public void onResponse(Call<VideoModel> call, Response<VideoModel> response) {
        if (response.isSuccessful()) {
          VideoModel model = response.body();
          if (model.results.size() > 0) {
            reviewRoot.setVisibility(View.VISIBLE);
            reviewRecycler.setAdapter(new TrailerAdapter(getActivity(), model.results));
          }
        }
      }

      @Override public void onFailure(Call<VideoModel> call, Throwable t) {

      }
    });
  }

  private static class UnscrollableLinearLayoutManager extends LinearLayoutManager {
    public UnscrollableLinearLayoutManager(Context context) {
      super(context, LinearLayoutManager.VERTICAL, false);
    }

    @Override public boolean canScrollHorizontally() {
      return false;
    }

    @Override public boolean canScrollVertically() {
      return false;
    }
  }
}
