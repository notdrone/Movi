package me.droan.movi.favorite;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.droan.movi.MoviFragment;
import me.droan.movi.MovieListModel.Result;
import me.droan.movi.R;
import me.droan.movi.favorite.db.FavoriteContract;

/**
 * Created by drone on 15/04/16.
 */
public class FavouriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
  private static final int CURSOR_LOADER_ID = 0;
  FavoriteAdapter cAdapter;
  @Bind(R.id.recyclerView) RecyclerView recyclerView;
  private Cursor cursor;

  public static FavouriteFragment newInstance() {
    Bundle args = new Bundle();
    FavouriteFragment fragment = new FavouriteFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.simple_recycler_view, container, false);
    ButterKnife.bind(this, view);

    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    return view;
  }

  @Override public void onResume() {
    super.onResume();
    getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
    cursor = getActivity().getContentResolver()
        .query(FavoriteContract.FavoriteTable.CONTENT_URI, null, null, null, null);
    cAdapter = new FavoriteAdapter(getContext(), cursor, new FavoriteAdapter.OnItemClickListener() {
      @Override public void onItemClick(Result model) {
        ((MoviFragment.OpenDetailListener) getActivity()).openDetail(model);
      }
    });
    recyclerView.setAdapter(cAdapter);
    cAdapter.notifyDataSetChanged();
  }

  @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(getActivity(), FavoriteContract.FavoriteTable.CONTENT_URI, null, null,
        null, null);
  }

  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    cAdapter.swapCursor(data);
  }

  @Override public void onLoaderReset(Loader<Cursor> loader) {
    cAdapter.swapCursor(null);
  }
}
