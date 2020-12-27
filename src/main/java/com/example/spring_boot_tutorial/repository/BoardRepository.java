package com.example.spring_boot_tutorial.repository;

import com.example.spring_boot_tutorial.model.Board;
import com.example.spring_boot_tutorial.model.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

//    Page<Board> findAll(Pageable pageable);

    List<BoardDto> findByTitle(String title);
    List<BoardDto> findByTitleOrContent(String title, String content);

    Page<BoardDto> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

}
