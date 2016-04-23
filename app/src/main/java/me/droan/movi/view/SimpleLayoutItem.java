package me.droan.movi.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.drawee.view.SimpleDraweeView;
import me.droan.movi.MovieListModel.Result;
import me.droan.movi.R;
import me.droan.movi.utils.Constants;
import me.droan.movi.utils.GenreHelper;

/**
 * Created by drone on 15/04/16.
 */
public class SimpleLayoutItem extends FrameLayout {
  @Bind(R.id.genre) TextView genre;
  @Bind(R.id.poster) SimpleDraweeView poster;
  @Bind(R.id.rating) RatingBar rating;

  public SimpleLayoutItem(Context context) {
    super(context);
  }

  public SimpleLayoutItem(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public SimpleLayoutItem(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }

  public void onBind(Result model) {
    genre.setText(GenreHelper.getAllGenre(model.genre_ids));
    rating.setProgress((int) model.vote_average);
    poster.setImageURI(Uri.parse(Constants.POSTER_BASE + model.poster_path));
  }
}
