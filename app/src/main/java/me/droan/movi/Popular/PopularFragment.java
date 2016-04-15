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
    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
    fancyGridManager(layoutManager);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(new PopularAdapter(getActivity()));
    recyclerView.addItemDecoration(new SpacesItemDecoration(20));
  }

  private void fancyGridManager(GridLayoutManager manager) {
    manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override public int getSpanSize(int position) {
        if (position == 0) {
          return 2;
        } else {
          return 1;
        }
      }
    });
  }
}
