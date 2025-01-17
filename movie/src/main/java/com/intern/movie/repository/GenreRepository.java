package com.intern.movie.repository;

import com.intern.movie.entity.GenreEntity;
import com.intern.movie.entity.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

    Page<GenreEntity> findAll(Pageable pageable);
}
