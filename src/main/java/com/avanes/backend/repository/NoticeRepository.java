package com.avanes.backend.repository;

import com.avanes.backend.models.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice,Integer> {
}
