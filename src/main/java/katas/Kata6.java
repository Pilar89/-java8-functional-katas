package katas;

import com.google.common.collect.ImmutableMap;
import model.BoxArt;
import model.Movie;
import util.DataUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
    Goal: Retrieve the url of the largest boxart using map() and reduce()
    DataSource: DataUtil.getMovieLists()
    Output: String
*/
public class Kata6 {
  public static String execute() {
    List<Movie> movies = DataUtil.getMovies();
    var r = movies.stream()
      .flatMap(m -> m.getBoxarts().stream())
      .reduce(new BoxArt(0, 0, "url"), (accum, next) -> {
        var accumArea = accum.getHeight() * accum.getWidth();
        var nextArea = next.getHeight() * next.getWidth();
        return nextArea > accumArea ? next : accum;
      });
    return r.getUrl();
  }
}
