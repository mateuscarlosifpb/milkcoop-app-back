package com.milkcoop.services.impl;

import com.milkcoop.data.model.User;
import com.milkcoop.data.model.vo.UserVO;
import com.milkcoop.exceptions.ResourceNotFoundException;
import com.milkcoop.repository.UserRepository;
import com.milkcoop.services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserVO create(UserVO userVO) {
        var entity = toConvert(userVO);
        return toConvert(userRepository.save(entity));
    }

    @Override
    public UserVO update(UserVO userVO) {
        return null;
    }

    @Override
    public void delete(Long id) {
        var entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to delete in this scenario"));
        userRepository.delete(entity);
    }

    @Override
    public Page<UserVO> find(Pageable pageable) {
        var page = userRepository.findAll(pageable);
        return page.map(this::convertToUserVO);
    }

    @Override
    public UserVO findById(Long id) {
        return toConvert(userRepository.findById(id).orElseThrow());
    }

    @Override
    public UserVO findUser(String username) {
        return toConvert(userRepository.findByUserName(username));
    }

    private UserVO convertToUserVO(User user) {
        return toConvert(user);

    }

    private UserVO toConvert(User user) {
        return modelMapper.map(user, UserVO.class);

    }

    private User toConvert(UserVO userVO) {
        return modelMapper.map(userVO, User.class);

    }

}
