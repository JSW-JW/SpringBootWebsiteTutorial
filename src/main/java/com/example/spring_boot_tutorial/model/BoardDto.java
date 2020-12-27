package com.example.spring_boot_tutorial.model;

import lombok.*;

@Getter
@Setter
@ToString // Be cautious!
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private Picture picture;
    private String filePath;

    public Board toEntity(){

        Board board = Board.builder()
                .title(title)
                .content(content)
                .build();

        Picture picture = new Picture(filePath);
        picture.setBoard(board); // set FK on Picture Table

        board.setPicture(picture);

        return board;
    }

    @Builder
    public BoardDto(String title, String content, String filePath) {
        this.title = title;
        this.content = content;
        this.filePath = filePath;
    }
}