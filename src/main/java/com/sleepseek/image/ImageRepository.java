package com.sleepseek.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByUrl(String url);
}
