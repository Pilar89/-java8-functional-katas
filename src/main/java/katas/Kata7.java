package katas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import model.Bookmark;
import model.BoxArt;
import model.Movie;
import model.MovieList;
import util.DataUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    Goal: Retrieve the id, title, and smallest box art url for every video
    DataSource: DataUtil.getMovieLists()
    Output: List of ImmutableMap.of("id", "5", "title", "Bad Boys", "boxart": "url)
*/

public class Kata7 {
  public static List<Map> execute() {
    List<MovieList> movieLists = DataUtil.getMovieLists();
    var bigNumber = (int) Math.floor(Math.sqrt(Integer.MAX_VALUE));

    List<Map> result = movieLists.stream()
      .flatMap(movieList -> {
        return movieList.getVideos().stream()
          .map(movie -> {
            // buscar box art
            var boxart = movie.getBoxarts()
              .stream()
              .reduce(new BoxArt(bigNumber, bigNumber, "url"), (accum, next) -> {
                var accumArea = accum.getHeight() * accum.getWidth();
                var nextArea = next.getHeight() * next.getWidth();
                return nextArea < accumArea ? next : accum;
              });

            return ImmutableMap.of("id", movie.getId(), "title", movie.getTitle(), "boxart", boxart.getUrl());
          });
      })
      .collect(Collectors.toList());


    return result;

  }
}
