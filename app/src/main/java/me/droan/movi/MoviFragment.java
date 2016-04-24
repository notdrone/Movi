package me.droan.movi;

import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import butterknife.Bind;

/**
 * Created by drone on 15/04/16.
 */
abstract public class MoviFragment extends Fragment {
  public static final int WITH_HEADER_FANCY_TYPE = 843;
  public static final int SIMPLE_FANCY_TYPE = 857;
  public static final int FAVORITE_FANCY_TYPE = 581;
  @Bind(R.id.recyclerView) public RecyclerView recyclerView;
  protected int spanCount = 2;
  RecyclerView.Adapter adapter;

  abstract public void initViews();

  //@Nullable @Override
  //public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
  //    @Nullable Bundle savedInstanceState) {
  //  View view = inflater.inflate(R.layout.simple_recycler_view, container, false);
  //  ButterKnife.bind(this, view);
  //  initViews();
  //  initRecyclerView();
  //  return view;
  //}
  //
  //private void initRecyclerView() {
  //  GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), getSpanCount());
  //  fancyGridManager(layoutManager, getFancyGridType());
  //  recyclerView.setLayoutManager(layoutManager);
  //  recyclerView.setAdapter(adapter);
  //  int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
  //  recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
  //}

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

  public int getSpanCount() {
    Display display = getActivity().getWindowManager().getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    int width = size.x;
    int height = size.y;
    return spanCount;
  }

  abstract public int getFancyGridType();


}

