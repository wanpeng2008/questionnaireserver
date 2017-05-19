package com.zjp.repository;

import com.zjp.entity.Questionnaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by pengwan on 2017/5/19.
 */
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, UUID> {
    Questionnaire findById(UUID id);

    Page<Questionnaire> findAll(Specification<Questionnaire> spec, Pageable pageable);
}
