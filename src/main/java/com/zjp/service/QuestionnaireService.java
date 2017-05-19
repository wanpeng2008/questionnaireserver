package com.zjp.service;

import com.zjp.entity.Questionnaire;
import com.zjp.repository.QuestionnaireRepository;
import com.zjp.vo.QuestionnaireVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by pengwan on 2017/5/19.
 */
@Service
public class QuestionnaireService {
    @Autowired
    private QuestionnaireRepository questionnaireRepository;
    public Questionnaire getById(UUID id) {
        return questionnaireRepository.findById(id);
    }
    public Page<Questionnaire> findAll(Specification<Questionnaire> spec, Pageable pageable){
        return questionnaireRepository.findAll(spec,pageable);
    }
    public Page<Questionnaire> findByQuestionnaireVO(QuestionnaireVO questionnaireVO, Pageable pageable) {
        Specification<Questionnaire> spec = (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if(questionnaireVO.getId()!=null){
                list.add(cb.equal(root.get("uuid").as(UUID.class), questionnaireVO.getId()));
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };
        return this.findAll(spec, pageable);
    }

    public Questionnaire save(Questionnaire questionnaire) {
        return questionnaireRepository.save(questionnaire);
    }
}
