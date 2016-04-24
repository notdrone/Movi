package me.droan.movi;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import io.fabric.sdk.android.Fabric;

/**
 * Created by drone on 15/04/16.
 */
public class MoviApplication extends Application {
  private boolean turnOnFabric = false;
  @Override public void onCreate() {
    super.onCreate();
    if (turnOnFabric) Fabric.with(this, new Crashlytics());
    Fresco.initialize(this);
    Stetho.initializeWithDefaults(this);
  }
}
