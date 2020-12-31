package com.example.spring_boot_tutorial.repository;

import com.example.spring_boot_tutorial.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {


}