package com.example.spring_boot_tutorial.model;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Data
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=3, max=30, message = "제목은 2자 이상 30자 이하입니다")
    private String title;

    private String content;

    @Column(columnDefinition = "picture")
    private Picture picture;

    @Builder
    public Board( String title, String content, Picture picture) {
        this.title = title;
        this.content = content;
        this.picture = picture;
    }
}
