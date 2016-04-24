package me.droan.movi.detail.video.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable {

  public static final Creator<Result> CREATOR = new Creator<Result>() {
    @Override public Result createFromParcel(Parcel in) {
      return new Result(in);
    }

    @Override public Result[] newArray(int size) {
      return new Result[size];
    }
  };
  public String id;
  public String key;
  public String name;
  public int size;

  protected Result(Parcel in) {
    id = in.readString();
    key = in.readString();
    name = in.readString();
    size = in.readInt();
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(id);
    dest.writeString(key);
    dest.writeString(name);
    dest.writeInt(size);
  }
}
