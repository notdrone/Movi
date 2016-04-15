package me.droan.movi.Popular;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import me.droan.movi.AbstractFragment;
import me.droan.movi.utils.SpacesItemDecoration;

/**
 * Created by drone on 15/04/16.
 */
public class PopularFragment extends AbstractFragment {

  public static PopularFragment newInstance() {
    Bundle args = new Bundle();
    PopularFragment fragment = new PopularFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void initViews() {
    initRecyclerView();
  }

  private void initRecyclerView() {
    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(new PopularAdapter(getActivity()));
    recyclerView.addItemDecoration(new SpacesItemDecoration(20));
  }
}
