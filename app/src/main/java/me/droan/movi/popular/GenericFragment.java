package me.droan.movi.popular;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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
public class GenericFragment extends MoviFragment {
  public static final int FROM_POPULAR = 1;
  public static final int FROM_UPCOMING = 2;
  public static final int FROM_TOP = 3;
  private static final String TAG = "GenericFragment";
  private static final String KEY_FROM = "popularfragment.from";
  ArrayList<Result> list = new ArrayList<>();
  private MoviServices services;
  private int from;
  private int page = 1;
  private boolean serviceCalled = false;
  private GenericAdapter adapter;

  public static GenericFragment newInstance(int from) {
    Bundle args = new Bundle();
    args.putInt(KEY_FROM, from);
    GenericFragment fragment = new GenericFragment();
    fragment.setArguments(args);
    return fragment;
  }

  private void initRetrofit() {

    services = RetrofitHelper.getRetrofitObj().create((MoviServices.class));
    serviceHandler();
  }

  private void serviceHandler() {
    Call<MovieList> call;

    call = getMoviesReuest();

    call.enqueue(new Callback<MovieList>() {
      @Override public void onResponse(Call<MovieList> call, Response<MovieList> response) {
        serviceCalled = false;
        MovieList movieList = response.body();
        list.addAll((ArrayList<Result>) movieList.results);
        adapter.refresh(list);
        incrementPager();
      }

      @Override public void onFailure(Call<MovieList> call, Throwable t) {
        Log.d(TAG, "onFailure() called with: " + "call = [" + call + "], t = [" + t + "]");
      }
    });
  }

  private void incrementPager() {
    page++;
  }

  private Call<MovieList> getMoviesReuest() {
    serviceCalled = true;
    if (from == FROM_POPULAR) {
      return services.getPopularMovies(page);
    } else if (from == FROM_TOP) {
      return services.getTopRatedMovies(page);
    } else if (from == FROM_UPCOMING) {
      return services.getUpcomingRatedMovies(page);
    } else {
      throw new IllegalStateException("FROM mismatch");
    }
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable("Key", list);
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    from = getArguments().getInt(KEY_FROM);
    initRetrofit();
  }

  @Override public void initViews() {
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        final int latVisiblePos =
            ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        if (latVisiblePos > list.size() - 8 && !serviceCalled) {
          serviceHandler();
        }
      }
    });
  }

  @Override public RecyclerView.Adapter getAdapter() {
    adapter = new GenericAdapter(getActivity(), getArguments().getInt(KEY_FROM), list,
        new GenericAdapter.OnItemClickListener() {
          @Override public void onItemClick(Result model) {
            ((OpenDetailListener) getActivity()).openDetail(model);
          }
        });
    return adapter;
  }

  @Override public int getFancyGridType() {
    if (from == FROM_POPULAR) {
      return WITH_HEADER_FANCY_TYPE;
    } else if (from == FROM_TOP) {
      return SIMPLE_FANCY_TYPE;
    } else if (from == FROM_UPCOMING) {
      return SIMPLE_FANCY_TYPE;
    } else {
      throw new IllegalStateException("FROM mismatch");
    }
  }
}

