package me.droan.movi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by drone on 16/04/16.
 */
public class DetailFragment extends Fragment {
  @Bind(R.id.textView) TextView textView;

  public static DetailFragment newInstance(String str) {
    Bundle args = new Bundle();
    args.putString("KEY", str);
    DetailFragment fragment = new DetailFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_detail, container, false);
    ButterKnife.bind(this, view);
    Toast.makeText(getActivity(), "TAB TEXT:" + getArguments().getString("KEY"), Toast.LENGTH_SHORT)
        .show();
    textView.setText(getArguments().getString("KEY"));

    return view;
  }
}
