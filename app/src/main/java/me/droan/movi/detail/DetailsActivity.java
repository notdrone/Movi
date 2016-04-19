package me.droan.movi.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.mikepenz.materialize.MaterializeBuilder;
import me.droan.movi.MovieListModel.Result;
import me.droan.movi.R;

public class DetailsActivity extends AppCompatActivity {

  public static Intent putIntent(Context context, Result model) {

    Intent intent = new Intent(context, DetailsActivity.class);
    intent.putExtra("KEY", model);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_details);
    FragmentManager fm = getSupportFragmentManager();
    getWindow().getDecorView()
        .setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    if (fm.findFragmentById(R.id.fragmentContainer) == null) {
      fm.beginTransaction()
          .add(R.id.fragmentContainer,
              DetailFragment.newInstance((Result) getIntent().getSerializableExtra("KEY")))
          .commit();
    }
  }
}
