package katas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import model.*;
import util.DataUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    Goal: Retrieve each video's id, title, middle interesting moment time, and smallest box art url
    DataSource: DataUtil.getMovies()
    Output: List of ImmutableMap.of("id", 5, "title", "some title", "time", new Date(), "url", "someUrl")
*/
public class Kata9 {
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
            var time = movie.getInterestingMoments().stream()
              .reduce(new InterestingMoment("End", new Date()), (accum, next) -> {
                if (next.getType() == "Middle") {
                  accum = next;
                }
                return accum;
              });


            return ImmutableMap.of("id", movie.getId(), "title", movie.getTitle(), "time", time.getTime(), "boxart", boxart.getUrl());
          });
      })
      .collect(Collectors.toList());

    return result;

  }
}
