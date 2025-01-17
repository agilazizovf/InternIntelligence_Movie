package com.intern.movie.service.impl;

import com.intern.movie.entity.GenreEntity;
import com.intern.movie.entity.UserEntity;
import com.intern.movie.mapper.GenreMapper;
import com.intern.movie.model.dto.request.GenreRequest;
import com.intern.movie.model.dto.response.GenreResponse;
import com.intern.movie.model.dto.response.PageResponse;
import com.intern.movie.model.exception.AuthenticationException;
import com.intern.movie.model.exception.ResourceNotFoundException;
import com.intern.movie.repository.GenreRepository;
import com.intern.movie.repository.UserRepository;
import com.intern.movie.service.GenreService;
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
public class GenreServiceImpl implements GenreService {

    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<GenreResponse> create(GenreRequest request) {
        UserEntity user = getCurrentUser();

        GenreEntity genre = new GenreEntity();
        modelMapper.map(request, genre);
        genre.setUser(user);
        genreRepository.save(genre);

        log.info("Genre: {} created successfully!", genre.getName());

        GenreResponse response = new GenreResponse();
        modelMapper.map(genre, response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public PageResponse<GenreResponse> findAllGenres(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GenreEntity> genreEntities = genreRepository.findAll(pageable);

        List<GenreResponse> responses = genreEntities
                .stream()
                .map(GenreMapper::toGenreDTO)
                .collect(Collectors.toList());

        PageResponse<GenreResponse> pageResponse = new PageResponse<>();
        pageResponse.setContent(responses);
        pageResponse.setPage(genreEntities.getPageable().getPageNumber());
        pageResponse.setSize(genreEntities.getPageable().getPageSize());
        pageResponse.setTotalElements(genreEntities.getTotalElements());
        pageResponse.setTotalPages(genreEntities.getTotalPages());
        pageResponse.setLast(genreEntities.isLast());
        pageResponse.setFirst(genreEntities.isFirst());

        return pageResponse;
    }

    @Override
    public ResponseEntity<GenreResponse> findGenreById(Long id) {
        GenreEntity genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found"));

        GenreResponse response = new GenreResponse();
        modelMapper.map(genre, response);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @Override
    public ResponseEntity<GenreResponse> update(Long id, GenreRequest request) {
        UserEntity user = getCurrentUser();

        GenreEntity genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found"));

        if (!genre.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to update this genre");
        }
        modelMapper.map(request, genre);
        genreRepository.save(genre);

        log.info("Genre: {} updated successfully!", genre.getName());

        GenreResponse response = new GenreResponse();
        modelMapper.map(genre, response);

        return ResponseEntity.ok(response);
    }

    @Override
    public void delete(Long id) {
        UserEntity user = getCurrentUser();

        GenreEntity genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found"));

        if (!genre.getUser().equals(user)) {
            throw new AuthenticationException("You do not have permission to delete this genre");
        }
        genreRepository.deleteById(id);

        log.info("Genre: {} deleted successfully!", genre.getName());
    }

    @Override
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }
}
