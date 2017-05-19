package com.zjp.controller;

import com.zjp.entity.Questionnaire;
import com.zjp.entity.User;
import com.zjp.service.QuestionnaireService;
import com.zjp.vo.QuestionnaireVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by pengwan on 2017/5/19.
 */
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    protected Logger logger = Logger.getLogger(UserController.class.getName());
    @Autowired
    private QuestionnaireService questionnaireService;
    @GetMapping(value = "/{id}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Questionnaire getById(@PathVariable(value="id" ,required =true ) UUID id){
        return questionnaireService.getById(id);
    }
    @GetMapping(produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Page<Questionnaire> pageList(QuestionnaireVO questionnaireVO, @RequestParam(name="page",required = false, defaultValue = "0") Integer  page, @RequestParam (name="size",required = false, defaultValue = "20") Integer  size){
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(page, size, sort);
        return questionnaireService.findByQuestionnaireVO(questionnaireVO,pageable);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Questionnaire> saveUpdate(@Valid @RequestBody QuestionnaireVO questionnaireVO, BindingResult bindingResult, HttpSession httpSession) {
        if(bindingResult.hasErrors()){
            logger.warning("验证失败!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Questionnaire questionnaire = (questionnaireVO.getId()==null)?(new Questionnaire()):questionnaireService.getById(questionnaireVO.getId());
            User loginUser=(User)httpSession.getAttribute("user");
            BeanUtils.copyProperties(questionnaireVO,questionnaire);
            Questionnaire res = questionnaireService.save(questionnaire);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
