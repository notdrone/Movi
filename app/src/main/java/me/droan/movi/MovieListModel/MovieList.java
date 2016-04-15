package me.droan.movi.MovieListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Drone on 3/22/2016.
 */
public class MovieList {

  public int page;
  public List<Result> results = new ArrayList<Result>();
  public int total_results;
  public int total_pages;
}
