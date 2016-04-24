package me.droan.movi.detail.video.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VideoModel implements Serializable {

  public int id;
  public List<Result> results = new ArrayList<Result>();
}
