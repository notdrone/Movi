package me.droan.movi;

import me.droan.movi.MovieListModel.MovieList;
import me.droan.movi.detail.cast.model.CastModel;
import me.droan.movi.detail.review.model.ReviewModel;
import me.droan.movi.detail.video.model.VideoModel;
import me.droan.movi.utils.Constants;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by drone on 15/04/16.
 */
public interface MoviServices {
  @GET("popular?&api_key=" + Constants.API_KEY) Call<MovieList> getPopularMovies(
      @Query("page") int page);

  @GET("top_rated?api_key=" + Constants.API_KEY) Call<MovieList> getTopRatedMovies(
      @Query("page") int page);

  @GET("upcoming?api_key=" + Constants.API_KEY) Call<MovieList> getUpcomingRatedMovies(
      @Query("page") int page);

  @GET("{id}/reviews?api_key=" + Constants.API_KEY) Call<ReviewModel> getReviews(
      @Path("id") int id);

  @GET("{id}/videos?api_key=" + Constants.API_KEY) Call<VideoModel> getVideos(
      @Path("id") int id);

  @GET("{id}/credits?api_key=" + Constants.API_KEY) Call<CastModel> getCast(@Path("id") int id);
}
