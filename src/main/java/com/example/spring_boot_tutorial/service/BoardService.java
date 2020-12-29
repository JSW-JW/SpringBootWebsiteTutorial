package com.example.spring_boot_tutorial.service;


import com.example.spring_boot_tutorial.model.Board;
import com.example.spring_boot_tutorial.model.BoardDto;
import com.example.spring_boot_tutorial.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BoardService {
    private BoardRepository boardRepository;

    public void saveBoard(BoardDto boardDto) {
        boardRepository.save(boardDto.toEntity());
    } // TODO: edit entity fields.

    public BoardDto changeEntity(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setTitle(board.getTitle());
        boardDto.setContent(board.getContent());
        boardDto.setPicture(board.getPicture());
        boardDto.setFilePath(board.getPicture().getFilePath());

        return boardDto;
    }
}