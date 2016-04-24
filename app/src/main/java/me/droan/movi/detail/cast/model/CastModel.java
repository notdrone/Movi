package me.droan.movi.detail.cast.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CastModel implements Serializable {

  public int id;
  public List<Cast> cast = new ArrayList<Cast>();
  public List<Crew> crew = new ArrayList<Crew>();
}
