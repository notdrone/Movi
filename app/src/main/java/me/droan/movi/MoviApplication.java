package me.droan.movi;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;

/**
 * Created by drone on 15/04/16.
 */
public class MoviApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);
    Stetho.initializeWithDefaults(this);
  }
}
