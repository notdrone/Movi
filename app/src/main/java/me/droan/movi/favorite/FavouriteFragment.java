package me.droan.movi.favorite;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import me.droan.movi.MoviFragment;

/**
 * Created by drone on 15/04/16.
 */
public class FavouriteFragment extends MoviFragment {
  public static FavouriteFragment newInstance() {
    Bundle args = new Bundle();
    FavouriteFragment fragment = new FavouriteFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void initViews() {

  }

  @Override public RecyclerView.Adapter getAdapter() {
    return new FavoriteAdapter(getActivity());
  }

  @Override public int getFancyGridType() {
    return FAVORITE_FANCY_TYPE;
  }
}
