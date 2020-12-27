package com.example.spring_boot_tutorial.controller;


import com.example.spring_boot_tutorial.model.Board;
import com.example.spring_boot_tutorial.repository.BoardRepository;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
class BoardApiController {

    private final BoardRepository repository;

    BoardApiController(BoardRepository repository) {
        this.repository = repository;
    }

//    @GetMapping("/boards")
//    List<Board> all(@RequestParam(required = false) String title,
//                    @RequestParam(required = false) String content) {
//        if (StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
//            return repository.findAll();
//        }
//        else {
//            return repository.findByTitleOrContent(title, content);
//        }
//    }

    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    // Single item

    @GetMapping("/boards/{id}")
    Board findBoardById(@PathVariable Long id) {

        return repository.findById(id).orElse(null);
    }

    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(Board -> {
                    Board.setTitle(newBoard.getTitle());
                    Board.setContent(newBoard.getContent());
                    return repository.save(Board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }
}