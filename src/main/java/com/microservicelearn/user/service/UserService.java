package com.microservicelearn.user.service;

import com.microservicelearn.user.entity.User;
import com.microservicelearn.user.repository.UserRepository;
import com.microservicelearn.user.valueObject.Department;
import com.microservicelearn.user.valueObject.ResponseTemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) {
        log.info("Inside the saveUser method service!!");
        return userRepository.save(user);
    }

    public ResponseTemplateVO getUserWithDepartment(Long userId) {
        ResponseTemplateVO vo = new ResponseTemplateVO();
        User user = userRepository.findById(userId).get();
        Department department = restTemplate.getForObject("http://localhost:9001/departments/"
                + user.getDepartmentId(), Department.class);
        vo.setDepartment(department);
        vo.setUser(user);
        return vo;
    }
}
