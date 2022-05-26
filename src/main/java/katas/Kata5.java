package katas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import model.BoxArt;
import model.Movie;
import model.MovieList;
import util.DataUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    Goal: Retrieve the largest rating using reduce()
    DataSource: DataUtil.getMovies()
    Output: Double
*/
public class Kata5 {
  public static Double execute() {
    List<Movie> movies = DataUtil.getMovies();
    var max = movies.stream()
      .map(movie -> movie.getRating())
      .reduce(0.0, (accum, next) -> {
        if (next > accum) accum = next;
        return accum;
      });
    return max;
  }
}
