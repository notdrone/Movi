package me.droan.movi.top;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import me.droan.movi.MoviFragment;
import me.droan.movi.upcomingTop.CommonUpcomingTopAdapter;

/**
 * Created by drone on 15/04/16.
 */
public class TopFragment extends MoviFragment {
  public static TopFragment newInstance() {
    Bundle args = new Bundle();
    TopFragment fragment = new TopFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void initViews() {

  }

  @Override public RecyclerView.Adapter getAdapter() {
    return new CommonUpcomingTopAdapter(getActivity());
  }

  @Override public int getFancyGridType() {
    return SIMPLE_FANCY_TYPE;
  }
}
//500
//480
