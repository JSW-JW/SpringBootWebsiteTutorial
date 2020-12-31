package com.example.spring_boot_tutorial.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
public class Picture {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String filePath;
    private String originalFileName;
    private String storedFileName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    public Picture(String filePath) {
        this.filePath = filePath;
    }

    public Picture() {
    }
}