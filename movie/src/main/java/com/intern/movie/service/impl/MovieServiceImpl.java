package com.intern.movie.service.impl;

import com.intern.movie.entity.MovieEntity;
import com.intern.movie.entity.UserEntity;
import com.intern.movie.mapper.MovieMapper;
import com.intern.movie.model.dto.request.MovieRequest;
import com.intern.movie.model.dto.response.MovieResponse;
import com.intern.movie.model.dto.response.PageResponse;
import com.intern.movie.model.exception.AuthenticationException;
import com.intern.movie.model.exception.ResourceNotFoundException;
import com.intern.movie.repository.MovieRepository;
import com.intern.movie.repository.UserRepository;
import com.intern.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;
    @Override
    public ResponseEntity<MovieResponse> create(MovieRequest request) {
        UserEntity user = getCurrentUser();

        MovieEntity movie = new MovieEntity();
        modelMapper.map(request, movie);
        movie.setUser(user);
        movieRepository.save(movie);

        log.info("movie: {} created successfully!", movie.getTitle());

        MovieResponse response = new MovieResponse();
        modelMapper.map(movie, response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public PageResponse<MovieResponse> findAllMovies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MovieEntity> movieEntities = movieRepository.findAll(pageable);

        List<MovieResponse> responses = movieEntities
                .stream()
                .map(MovieMapper::toMovieDTO)
                .collect(Collectors.toList());

        PageResponse<MovieResponse> pageResponse = new PageResponse<>();
        pageResponse.setContent(responses);
        pageResponse.setPage(movieEntities.getPageable().getPageNumber());
        pageResponse.setSize(movieEntities.getPageable().getPageSize());
        pageResponse.setTotalElements(movieEntities.getTotalElements());
        pageResponse.setTotalPages(movieEntities.getTotalPages());
        pageResponse.setLast(movieEntities.isLast());
        pageResponse.setFirst(movieEntities.isFirst());

        return pageResponse;
    }

    @Override
    public ResponseEntity<MovieResponse> findMovieById(Long id) {
        MovieEntity movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        MovieResponse response = new MovieResponse();
        modelMapper.map(movie, response);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @Override
    public ResponseEntity<MovieResponse> update(Long id, MovieRequest request) {
        UserEntity user = getCurrentUser();

        MovieEntity movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        if (!movie.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to update this movie");
        }
        modelMapper.map(request, movie);
        movieRepository.save(movie);

        log.info("movie: {} updated successfully!", movie.getTitle());

        MovieResponse response = new MovieResponse();
        modelMapper.map(movie, response);

        return ResponseEntity.ok(response);
    }

    @Override
    public void delete(Long id) {
        UserEntity user = getCurrentUser();

        MovieEntity movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        if (!movie.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to delete this movie");
        }
        movieRepository.deleteById(id);

        log.info("movie: {} deleted successfully!", movie.getTitle());
    }

    @Override
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }
}
