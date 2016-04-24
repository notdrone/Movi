package me.droan.movi.detail.cast.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cast implements Parcelable {

  public static final Creator<Cast> CREATOR = new Creator<Cast>() {
    @Override public Cast createFromParcel(Parcel in) {
      return new Cast(in);
    }

    @Override public Cast[] newArray(int size) {
      return new Cast[size];
    }
  };
  public int cast_id;
  public String character;
  public String credit_id;
  public int id;
  public String name;
  public int order;
  public String profile_path;

  protected Cast(Parcel in) {
    cast_id = in.readInt();
    character = in.readString();
    credit_id = in.readString();
    id = in.readInt();
    name = in.readString();
    order = in.readInt();
    profile_path = in.readString();
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(cast_id);
    dest.writeString(character);
    dest.writeString(credit_id);
    dest.writeInt(id);
    dest.writeString(name);
    dest.writeInt(order);
    dest.writeString(profile_path);
  }
}
