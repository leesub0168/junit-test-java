package com.example.junittestjava.Study;

import com.example.junittestjava.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
