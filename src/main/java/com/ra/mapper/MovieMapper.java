package com.ra.mapper;

import com.ra.dto.request.MovieRequest;
import com.ra.dto.response.MovieResponse;
import com.ra.entity.Categories;
import com.ra.entity.Movie;
import com.ra.entity.MovieStatus;
import com.ra.exception.CustomException;
import com.ra.repository.ICategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

    @Autowired
    private ICategoriesRepository categoriesRepository ;

    public MovieResponse toMovieResponse(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .name(movie.getName())
                .director(movie.getDirector())
                .performer(movie.getPerformer())
                .startTime(movie.getStartTime())
                .endTime(movie.getEndTime())
                .title(movie.getTitle())
                .image(movie.getImage())
                .price(movie.getPrice())
                .movieStatus(movie.getMovieStatus().name())
                .categoryName(movie.getCategories().stream().map(item-> item.getName()).collect(Collectors.toList()))
                .build();
    }

    public Movie toEntity(MovieRequest movieRequest) throws CustomException {
        String movieStatusString = movieRequest.getMovieStatus();
        MovieStatus movieStatusEnum;

        switch (movieStatusString) {
            case "coming":
                movieStatusEnum = MovieStatus.COMING_SOON;
                break;
            case "showing":
                movieStatusEnum = MovieStatus.IS_SHOWING;
                break;
            case "expired":
                movieStatusEnum = MovieStatus.EXPIRED;
                break;
            default:
                // Xử lý nếu trạng thái không hợp lệ
                throw new CustomException( movieStatusString + " Not Found");
        }


        List<Categories> list = categoriesRepository.findAllById(movieRequest.getCategoryId());
        return Movie.builder()
                .name(movieRequest.getName())
                .director(movieRequest.getDirector())
                .performer(movieRequest.getPerformer())
                .startTime(movieRequest.getStartTime())
                .endTime(movieRequest.getEndTime())
                .title(movieRequest.getTitle())
                .image(movieRequest.getImage().getOriginalFilename())
                .price(movieRequest.getPrice())
                .movieStatus(movieStatusEnum)
                .categories(list)
                .build();
    }


}
