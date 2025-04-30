package com.avanes.backend.repository;

import com.avanes.backend.models.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice,Integer> {
    List<Notice> findAllByModule(String module);
}
