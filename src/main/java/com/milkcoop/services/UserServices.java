package com.milkcoop.services;

import com.milkcoop.data.model.vo.CooperativeVO;
import com.milkcoop.data.model.vo.UserVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;

public interface UserServices {

    UserVO create(UserVO userVO) throws ParseException;

    UserVO update(UserVO userVO);

    void delete(Long id);

    Page<UserVO> find(Pageable pageable);

    UserVO findById(Long id);

    UserVO findUser(String username);
}
