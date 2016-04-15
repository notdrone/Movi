package me.droan.movi;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.ArrayList;
import me.droan.movi.favorite.FavouriteFragment;
import me.droan.movi.popular.PopularFragment;
import me.droan.movi.top.TopFragment;
import me.droan.movi.upcomingTop.UpcomingFragment;

public class MoviActivity extends AppCompatActivity {

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.tabLayout) TabLayout tabLayout;
  @Bind(R.id.viewPager) ViewPager viewPager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movi);
    initViews();
  }

  private void initViews() {
    ButterKnife.bind(this);
    initTabs();
    initViewPager();
    tabLayout.setupWithViewPager(viewPager);
    initTabIcons();
  }

  private void initTabIcons() {
    for (int i = 0; i < 4; i++) {
      tabLayout.getTabAt(i).setIcon(R.drawable.popular_icon);
    }
  }

  private void initViewPager() {
    Adapter adapter = new Adapter(getSupportFragmentManager());
    adapter.addFragment(PopularFragment.newInstance());
    adapter.addFragment(UpcomingFragment.newInstance());
    adapter.addFragment(TopFragment.newInstance());
    adapter.addFragment(FavouriteFragment.newInstance());
    viewPager.setAdapter(adapter);
  }

  private void initTabs() {
    for (int i = 0; i < 4; i++) {
      tabLayout.addTab(tabLayout.newTab());
    }
  }

  static class Adapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> fragments = new ArrayList<>();

    public Adapter(FragmentManager fm) {
      super(fm);
    }

    public void addFragment(Fragment fragment) {
      fragments.add(fragment);
    }

    @Override public Fragment getItem(int position) {
      return fragments.get(position);
    }

    @Override public int getCount() {
      return fragments.size();
    }
  }
}
