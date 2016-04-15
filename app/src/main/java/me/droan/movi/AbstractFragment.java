package me.droan.movi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by drone on 15/04/16.
 */
abstract public class AbstractFragment extends Fragment {
  @Bind(R.id.recyclerView) public RecyclerView recyclerView;

  abstract public void initViews();

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.simple_recycler_view, container, false);
    ButterKnife.bind(this, view);
    initViews();
    return view;
  }
}
