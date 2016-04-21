package me.droan.movi.detail;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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
  @Bind(R.id.trailerRecycler) RecyclerView trailerRecycler;
  @Bind(R.id.cardReview) CardView cardReview;
  @Bind(R.id.cardCast) CardView cardCast;
  @Bind(R.id.cardOverview) CardView cardOverview;
  @Bind(R.id.cardTrailer) CardView cardTrailer;
  @Bind(R.id.favorite) Button favorite;
  @Bind(R.id.parent_layout) LinearLayout root;
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
    trailerRecycler.setAdapter(new ReviewAdapter(getActivity(),
        new ArrayList<me.droan.movi.detail.review.model.Result>()));
    trailerRecycler.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    castRecycler.setAdapter(new CastAdapter(getActivity(), new ArrayList<Cast>()));
    castRecycler.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    reviewRecycler.setAdapter(new ReviewAdapter(getActivity(),
        new ArrayList<me.droan.movi.detail.review.model.Result>()));
    reviewRecycler.setLayoutManager(new UnscrollableLinearLayoutManager(getActivity()));
    //tralerRecycler.setAdapter(new TrailerAdapter(getActivity(), model.results));

    handleReviewService();
    handleTrailerService();
    handleCastService();
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

    favorite.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        insertData();
      }
    });

    return view;
  }

  private void handleCastService() {
    Retrofit retrofit = RetrofitHelper.getRetrofitObj();
    MoviServices service = retrofit.create(MoviServices.class);
    Call<CastModel> call = service.getCast(model.id);
    call.enqueue(new Callback<CastModel>() {
      @Override public void onResponse(Call<CastModel> call, Response<CastModel> response) {
        if (response.isSuccessful()) {
          CastModel model = response.body();
          if (model.cast.size() > 0) {
            cardCast.setVisibility(View.VISIBLE);
            castRecycler.setAdapter(new CastAdapter(getActivity(), model.cast));
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
        Palette.from(bitmap).maximumColorCount(50).generate(new Palette.PaletteAsyncListener() {
          @Override public void onGenerated(Palette palette) {
            getActivity().getWindow()
                .getDecorView()
                .setBackgroundColor(palette.getMutedColor(0x000000));
            cardCast.setCardBackgroundColor(palette.getMutedColor(0) + 1000);
            cardOverview.setCardBackgroundColor(palette.getMutedColor(0) + 1000);
            cardTrailer.setCardBackgroundColor(palette.getMutedColor(0) + 1000);
            cardReview.setCardBackgroundColor(palette.getMutedColor(0) + 1000);
          }
        });
      }
    }, CallerThreadExecutor.getInstance());

    poster.setController(controller);
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
            cardReview.setVisibility(View.VISIBLE);
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
            cardTrailer.setVisibility(View.VISIBLE);
            trailerRecycler.setAdapter(new TrailerAdapter(getActivity(), model.results));
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
