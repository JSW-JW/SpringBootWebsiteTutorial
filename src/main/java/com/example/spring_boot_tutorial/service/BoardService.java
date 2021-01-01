package com.example.spring_boot_tutorial.service;


import com.example.spring_boot_tutorial.model.Board;
import com.example.spring_boot_tutorial.model.BoardDto;
import com.example.spring_boot_tutorial.model.Picture;
import com.example.spring_boot_tutorial.repository.BoardRepository;
import com.example.spring_boot_tutorial.repository.PictureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BoardService {

    private PictureRepository pictureRepository;
    private BoardRepository boardRepository;

    public void saveBoard(Board board) {
        boardRepository.save(board);
    } // TODO: edit entity fields.

    public Board toEntity(BoardDto boardDto, Long deletedFileId, Boolean isPicture) {

        Board board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .build();

        ImageHandler(board, boardDto, deletedFileId, isPicture); // Handle Image if it's update, delete or no Image

        return board;
    }

    public void ImageHandler(Board board, BoardDto boardDto, Long deletedFileId, Boolean isPicture) {
        if (boardDto.getId() != null) { // check if it's updating page
            Board findBoard = boardRepository.findById(boardDto.getId()).orElse(null);
            board.setId(findBoard.getId());
            if (isPicture) {
                if (deletedFileId != 0) {
                    pictureRepository.deleteById(deletedFileId);
                }

                Picture picture = new Picture();
                picture.setFilePath(boardDto.getFilePath());
                picture.setOriginalFileName(boardDto.getOriginalFileName());
                picture.setStoredFileName(boardDto.getStoredFileName());
                picture.setBoard(board); // set FK on Picture Table
                board.setPicture(picture);
            } else {
                if (deletedFileId != 0) {
                    pictureRepository.deleteById(deletedFileId);
                }
            }
        } else { // TODO remove multipart file when user clicked 'x' button
            if (isPicture) {
                Picture picture = new Picture();
                picture.setFilePath(boardDto.getFilePath());
                picture.setOriginalFileName(boardDto.getOriginalFileName());
                picture.setStoredFileName(boardDto.getStoredFileName());
                picture.setBoard(board); // set FK on Picture Table
                board.setPicture(picture);
            }
        }
    }

    public BoardDto toDto(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setTitle(board.getTitle());
        boardDto.setContent(board.getContent());
        boardDto.setPicture(board.getPicture());

        return boardDto;
    }
}