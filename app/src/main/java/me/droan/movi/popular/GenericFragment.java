package me.droan.movi.popular;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.ArrayList;
import me.droan.movi.MoviServices;
import me.droan.movi.MovieListModel.MovieList;
import me.droan.movi.MovieListModel.Result;
import me.droan.movi.R;
import me.droan.movi.utils.RetrofitHelper;
import me.droan.movi.utils.SpacesItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by drone on 15/04/16.
 */
public class GenericFragment extends Fragment {

  public static final int WITH_HEADER_FANCY_TYPE = 843;
  public static final int SIMPLE_FANCY_TYPE = 857;
  public static final int FAVORITE_FANCY_TYPE = 581;
  public static final int FROM_POPULAR = 1;
  public static final int FROM_UPCOMING = 2;
  public static final int FROM_TOP = 3;
  private static final String TAG = "GenericFragment";
  private static final String KEY_FROM = "popularfragment.from";
  private static final String EXTRA_LIST = "list";
  private static final String EXTRA_PAGE = "page";
  @Bind(R.id.recyclerView) public RecyclerView recyclerView;
  protected int spanCount = 2;
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

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    from = getArguments().getInt(KEY_FROM);
    services = RetrofitHelper.getRetrofitObj().create((MoviServices.class));
    if (savedInstanceState == null) {
      serviceHandler();
    } else {
      list = (ArrayList<Result>) savedInstanceState.getSerializable(EXTRA_LIST);
      page = savedInstanceState.getInt(EXTRA_PAGE);
    }
    adapter = new GenericAdapter(getActivity(), getArguments().getInt(KEY_FROM), list,
        new GenericAdapter.OnItemClickListener() {
          @Override public void onItemClick(Result model) {
            ((OpenDetailListener) getActivity()).openDetail(model);
          }
        });
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.simple_recycler_view, container, false);
    ButterKnife.bind(this, view);
    initRecyclerView();
    initViews();
    return view;
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

  private void initRecyclerView() {
    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
    fancyGridManager(layoutManager, getFancyGridType());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
    int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
    recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
  }

  private void fancyGridManager(GridLayoutManager manager, int fancyType) {
    if (fancyType == WITH_HEADER_FANCY_TYPE) {
      manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
        @Override public int getSpanSize(int position) {
          if (position == 0) {
            return spanCount;
          } else {
            return 1;
          }
        }
      });
    } else if (fancyType == SIMPLE_FANCY_TYPE) {

    } else if (fancyType == FAVORITE_FANCY_TYPE) {
      manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
        @Override public int getSpanSize(int position) {
          return spanCount;
        }
      });
    }
  }

  private void initViews() {
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

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable(EXTRA_LIST, list);
    outState.putInt(EXTRA_PAGE, page);
  }

  private int getFancyGridType() {
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

  public interface OpenDetailListener {
    void openDetail(Result model);
  }
}

