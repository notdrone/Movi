package me.droan.movi.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import java.util.ArrayList;
import java.util.List;
import me.droan.movi.MoviServices;
import me.droan.movi.MovieListModel.Result;
import me.droan.movi.R;
import me.droan.movi.detail.cast.CastAdapter;
import me.droan.movi.detail.cast.model.Cast;
import me.droan.movi.detail.cast.model.CastModel;
import me.droan.movi.detail.review.ReviewAdapter;
import me.droan.movi.detail.review.model.ReviewModel;
import me.droan.movi.detail.video.TrailerAdapter;
import me.droan.movi.detail.video.model.VideoModel;
import me.droan.movi.utils.Constants;
import me.droan.movi.utils.DbUtils;
import me.droan.movi.utils.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by drone on 16/04/16.
 */
public class DetailFragment extends Fragment {
  private static final String EXTRA_REVIEW = "review";
  private static final String EXTRA_TRAILER = "trailer";
  private static final String EXTRA_CAST = "cast";
  @Bind(R.id.title) TextView title;
  @Bind(R.id.poster) SimpleDraweeView poster;
  @Bind(R.id.backdrop) SimpleDraweeView backdrop;
  @Bind(R.id.rating) RatingBar rating;
  @Bind(R.id.description) TextView description;
  @Bind(R.id.release) TextView release;
  @Bind(R.id.castRecycler) RecyclerView castRecycler;
  @Bind(R.id.reviewRecycler) RecyclerView reviewRecycler;
  @Bind(R.id.trailerRecycler) RecyclerView trailerRecycler;
  @Bind(R.id.cardReview) CardView cardReview;
  @Bind(R.id.cardCast) CardView cardCast;
  @Bind(R.id.cardOverview) CardView cardOverview;
  @Bind(R.id.cardTrailer) CardView cardTrailer;
  @Bind(R.id.favorite) Button favorite;
  @Bind(R.id.parent_layout) LinearLayout root;
  private Result model;
  private List<me.droan.movi.detail.review.model.Result> reviewModel = new ArrayList<>();
  private List<me.droan.movi.detail.video.model.Result> trailerModel = new ArrayList<>();
  private List<Cast> castModel = new ArrayList<>();
  private ReviewAdapter reviewAdapter;
  private CastAdapter castAdapter;
  private TrailerAdapter trailerAdapter;

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
    if (savedInstanceState == null) {
      handleReviewService();
      handleTrailerService();
      handleCastService();
    } else {
      reviewModel = savedInstanceState.getParcelableArrayList(EXTRA_REVIEW);
      castModel = savedInstanceState.getParcelableArrayList(EXTRA_CAST);
      trailerModel = savedInstanceState.getParcelableArrayList(EXTRA_TRAILER);
    }
  }

  private void setAdapters() {
    reviewAdapter = new ReviewAdapter(getActivity(), reviewModel);
    castAdapter = new CastAdapter(getActivity(), castModel);
    trailerAdapter = new TrailerAdapter(getActivity(), trailerModel);
    reviewRecycler.setAdapter(reviewAdapter);
    castRecycler.setAdapter(castAdapter);
    trailerRecycler.setAdapter(trailerAdapter);
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelableArrayList(EXTRA_TRAILER, (ArrayList<? extends Parcelable>) trailerModel);
    outState.putParcelableArrayList(EXTRA_CAST, (ArrayList<? extends Parcelable>) castModel);
    outState.putParcelableArrayList(EXTRA_REVIEW, (ArrayList<? extends Parcelable>) reviewModel);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_detail, container, false);
    ButterKnife.bind(this, view);
    setAdapters();
    trailerRecycler.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    castRecycler.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

    reviewRecycler.setLayoutManager(new UnscrollableLinearLayoutManager(getActivity()));
    changeFav();
    checkRotation();
    title.setText(model.title);
    Uri posterUri = Uri.parse(Constants.POSTER_BASE + model.poster_path);
    poster.setImageURI(posterUri);
    backdrop.setImageURI(Uri.parse(Constants.POSTER_BASE + model.backdrop_path));
    rating.setProgress((int) model.vote_average);
    release.setText(model.release_date);
    if (model.overview.length() > 0) {
      cardOverview.setVisibility(View.VISIBLE);
      description.setText(Html.fromHtml(model.overview));
    }

    ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(posterUri).build();
    DraweeController controller = Fresco.newDraweeControllerBuilder()
        .setImageRequest(imageRequest)
        .setOldController(poster.getController())
        .build();
    processImageWithPaletteApi(imageRequest, controller);

    return view;
  }

  private void checkRotation() {
    if (castModel != null && castModel.size() > 0) {
      cardCast.setVisibility(View.VISIBLE);
      castAdapter.refresh(castModel);
    }
    if (reviewModel != null && reviewModel.size() > 0) {
      cardReview.setVisibility(View.VISIBLE);
      reviewAdapter.refresh(reviewModel);
    }
    if (trailerModel != null && trailerModel.size() > 0) {
      cardTrailer.setVisibility(View.VISIBLE);
      trailerAdapter.refresh(trailerModel);
    }
  }

  private void handleCastService() {
    Retrofit retrofit = RetrofitHelper.getRetrofitObj();
    MoviServices service = retrofit.create(MoviServices.class);
    Call<CastModel> call = service.getCast(model.id);
    call.enqueue(new Callback<CastModel>() {
      @Override public void onResponse(Call<CastModel> call, Response<CastModel> response) {
        if (response.isSuccessful()) {
          castModel = response.body().cast;
          if (castModel.size() > 0) {
            cardCast.setVisibility(View.VISIBLE);
            castAdapter.refresh(castModel);
          }
        }
      }

      @Override public void onFailure(Call<CastModel> call, Throwable t) {

      }
    });
  }

  private void processImageWithPaletteApi(ImageRequest request, DraweeController controller) {
    DataSource<CloseableReference<CloseableImage>> dataSource =
        Fresco.getImagePipeline().fetchDecodedImage(request, poster.getContext());
    dataSource.subscribe(new BaseBitmapDataSubscriber() {
      @Override
      protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
        getActivity().getWindow()
            .getDecorView()
            .setBackgroundColor(getResources().getColor(R.color.md_white_1000));
      }

      @Override protected void onNewResultImpl(@Nullable Bitmap bitmap) {
        Palette.from(bitmap).maximumColorCount(64).generate(new Palette.PaletteAsyncListener() {
          @Override public void onGenerated(Palette palette) {
            int mutedColor = palette.getMutedColor(0xFFFFFF);
            if (mutedColor > 0) {
              mutedColor = -1 * mutedColor / 2;
            }
            if (getActivity() != null) {
              getActivity().getWindow().getDecorView().setBackgroundColor(mutedColor);
            }
            final int cardColor = palette.getMutedColor(0) + 1000;
            cardCast.setCardBackgroundColor(cardColor);
            cardOverview.setCardBackgroundColor(cardColor);
            cardTrailer.setCardBackgroundColor(cardColor);
            cardReview.setCardBackgroundColor(cardColor);
          }
        });
      }
    }, CallerThreadExecutor.getInstance());

    poster.setController(controller);
  }

  public void addRemoveFav() {
    if (isInDB()) {
      DbUtils.removeFavorite(getActivity(), model.id);
    } else {

      DbUtils.insertFavorite(getActivity(), model);
    }

    changeFav();
  }

  private void changeFav() {
    favorite.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        addRemoveFav();
      }
    });
    final int whiteColor = getContext().getResources().getColor(R.color.md_white_1000);
    final int blackColor = getContext().getResources().getColor(R.color.md_black_1000);
    final int accentColor = getContext().getResources().getColor(R.color.accent);
    if (isInDB()) {
      favorite.setBackgroundColor(accentColor);
      favorite.setText("Unfavorite");
      favorite.setTextColor(whiteColor);
    } else {
      favorite.setBackgroundColor(whiteColor);
      favorite.setText("Favorite");
      favorite.setTextColor(blackColor);
    }
  }

  public boolean isInDB() {
    return DbUtils.isInDB(getActivity(), model.id);
  }

  private void handleReviewService() {
    Retrofit retrofit = RetrofitHelper.getRetrofitObj();
    MoviServices service = retrofit.create(MoviServices.class);
    Call<ReviewModel> call = service.getReviews(model.id);
    call.enqueue(new Callback<ReviewModel>() {
      @Override public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
        if (response.isSuccessful()) {
          reviewModel = response.body().results;
          if (reviewModel.size() > 0) {
            cardReview.setVisibility(View.VISIBLE);
            reviewAdapter.refresh(reviewModel);
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
          trailerModel = response.body().results;
          if (trailerModel.size() > 0) {
            cardTrailer.setVisibility(View.VISIBLE);
            trailerAdapter.refresh(trailerModel);
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
