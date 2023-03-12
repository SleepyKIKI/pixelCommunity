package com.pixeldreamer.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixeldreamer.community.dto.QuestionDTO;
import com.pixeldreamer.community.mapper.QuestionMapper;
import com.pixeldreamer.community.mapper.UserMapper;
import com.pixeldreamer.community.model.Question;
import com.pixeldreamer.community.model.Users;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> List() {
        List<Question> questions= questionMapper.List();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            Users users = userMapper.fingById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUsers(users);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
} 
