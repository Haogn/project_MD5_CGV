package com.ra.service.impl;

import com.ra.dto.request.MovieRequest;
import com.ra.dto.response.MovieResponse;
import com.ra.entity.Categories;
import com.ra.entity.Movie;
import com.ra.entity.MovieStatus;
import com.ra.exception.CustomException;
import com.ra.mapper.MovieMapper;
import com.ra.repository.ICategoriesRepository;
import com.ra.repository.IMovieRepository;
import com.ra.service.interfaces.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService implements IMovieService {
    @Autowired
    private IMovieRepository movieRepository ;

    @Autowired
    private MovieMapper movieMapper ;

    @Autowired
    private ICategoriesRepository categoriesRepository ;
    @Override
    public Page<MovieResponse> findAllMovie(String name, Pageable pageable) {
        Page<Movie> moviePage ;
        if (name.isEmpty() || name == null) {
            moviePage = movieRepository.findAll(pageable) ;
        } else {
            moviePage = movieRepository.findAllByName(name, pageable) ;
        }
        return moviePage.map(item -> movieMapper.toMovieResponse(item));
    }

    @Override
    public MovieResponse findById(Long id) throws CustomException {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new CustomException("Movie Not Found"));
        return movieMapper.toMovieResponse(movie);
    }

    @Override
    public MovieResponse save(MovieRequest movieRequest) throws CustomException {
        if (movieRepository.existsByName(movieRequest.getName())) {
            throw new CustomException("Exits Movie Name");
        }

        Movie movie = movieRepository.save(movieMapper.toEntity(movieRequest)) ;
        return movieMapper.toMovieResponse(movie);
    }

    @Override
    public MovieResponse update(Long id, MovieRequest movieRequest) throws CustomException {
        Movie movie = movieRepository.findById(id).orElseThrow(()-> new CustomException("Movie Not Found"));
        if (movieRepository.existsByName(movieRequest.getName()) || movieRequest.getName().equalsIgnoreCase(movie.getName())){
            throw new CustomException("Exits Movie Name");
        }
        String movieStatusString = movieRequest.getMovieStatus();
        MovieStatus movieStatusEnum = MovieStatus.valueOf(movieStatusString);

        List<Categories> list = categoriesRepository.findAllById(movieRequest.getCategoryId());
        String imageName = movieRequest.getImage().getOriginalFilename();
        movie.setId(id);
        movie.setName(movieRequest.getName());
        movie.setDirector(movieRequest.getDirector());
        movie.setPerformer(movieRequest.getPerformer());
        movie.setStartTime(movieRequest.getStartTime());
        movie.setEndTime(movieRequest.getEndTime());
        movie.setImage(imageName);
        movie.setTitle(movieRequest.getTitle());
        movie.setPrice(movieRequest.getPrice());
        movie.setMovieStatus(movieStatusEnum);
        movie.setCategories(list);

        return movieMapper.toMovieResponse(movieRepository.save(movie));
    }

    @Override
    public MovieResponse changeMovieStatus(Long id , String newStatus) throws CustomException {
        Movie movie = movieRepository.findById(id).orElseThrow(()-> new CustomException("Movie Not Found"));

        switch (newStatus) {
            case "coming":
                movie.setMovieStatus(MovieStatus.COMING_SOON);
                break;
            case "showing" :
                movie.setMovieStatus(MovieStatus.IS_SHOWING);
                break;
            case "expired":
                movie.setMovieStatus(MovieStatus.EXPIRED);
                break;
            default:
                // Xử lý nếu trạng thái không hợp lệ
                throw new CustomException( newStatus + " Not Found");
        }

        return movieMapper.toMovieResponse(movieRepository.save(movie));
    }

    @Override
    public void delete(Long id) throws CustomException {
        Movie movie = movieRepository.findById(id).orElseThrow(()-> new CustomException("Movie Not Found"));
        if (movie != null) {
            movieRepository.deleteById(id);
        }
    }
}
