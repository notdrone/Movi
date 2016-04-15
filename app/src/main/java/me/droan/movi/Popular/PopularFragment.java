package me.droan.movi.popular;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import java.util.ArrayList;
import me.droan.movi.MoviFragment;
import me.droan.movi.MoviServices;
import me.droan.movi.MovieListModel.MovieList;
import me.droan.movi.MovieListModel.Result;
import me.droan.movi.utils.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by drone on 15/04/16.
 */
public class PopularFragment extends MoviFragment {
  private static final String TAG = "PopularFragment";
  ArrayList<Result> list = new ArrayList<>();
  private MoviServices services;

  public static PopularFragment newInstance() {
    Bundle args = new Bundle();
    PopularFragment fragment = new PopularFragment();
    fragment.setArguments(args);
    return fragment;
  }

  private void initRetrofit() {

    services = RetrofitHelper.getRetrofitObj().create((MoviServices.class));
    serviceHandler();
  }

  private void serviceHandler() {
    Call<MovieList> call;
    call = services.getPopularMovies();

    call.enqueue(new Callback<MovieList>() {
      @Override public void onResponse(Call<MovieList> call, Response<MovieList> response) {
        Log.d(TAG,
            "onResponse() called with: " + "call = [" + call + "], response = [" + response + "]");
        MovieList movieList = response.body();
        list = (ArrayList<Result>) movieList.results;
        recyclerView.setAdapter(new PopularAdapter(getActivity(), list));
      }

      @Override public void onFailure(Call<MovieList> call, Throwable t) {
        Log.d(TAG, "onFailure() called with: " + "call = [" + call + "], t = [" + t + "]");
      }
    });
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable("Key", list);
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState != null) {

    } else {
      initRetrofit();
    }
  }

  @Override public void initViews() {
  }

  @Override public RecyclerView.Adapter getAdapter() {
    return new PopularAdapter(getActivity(), list);
  }

  @Override public int getFancyGridType() {
    return WITH_HEADER_FANCY_TYPE;
  }
}

