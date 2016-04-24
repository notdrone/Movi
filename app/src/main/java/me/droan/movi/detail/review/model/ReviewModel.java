package me.droan.movi.detail.review.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReviewModel implements Serializable {

  public int id;
  public int page;
  public List<Result> results = new ArrayList<Result>();
  public int total_pages;
  public int total_results;
}
