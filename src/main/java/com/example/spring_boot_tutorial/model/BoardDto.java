package com.example.spring_boot_tutorial.model;

import lombok.*;
import org.apache.http.util.TextUtils;

@Getter
@Setter
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private Picture picture;
    private String filePath;
    private String originalFileName;
    private String storedFileName;



    @Builder
    public BoardDto(String title, String content, String filePath) {
        this.title = title;
        this.content = content;
        this.filePath = filePath;
    }
}