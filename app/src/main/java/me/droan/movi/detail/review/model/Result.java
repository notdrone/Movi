package me.droan.movi.detail.review.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable {

  @SuppressWarnings("unused") public static final Parcelable.Creator<Result> CREATOR =
      new Parcelable.Creator<Result>() {
        @Override public Result createFromParcel(Parcel in) {
          return new Result(in);
        }

        @Override public Result[] newArray(int size) {
          return new Result[size];
        }
      };
  public String id;
  public String author;
  public String content;
  public String url;

  protected Result(Parcel in) {
    id = in.readString();
    author = in.readString();
    content = in.readString();
    url = in.readString();
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(id);
    dest.writeString(author);
    dest.writeString(content);
    dest.writeString(url);
  }
}
