package com.eg.HousingLibrary.service;


import com.eg.HousingLibrary.ConverterUtility.EntityDTOMapper;
import com.eg.HousingLibrary.dto.UserDTO;
import com.eg.HousingLibrary.model.User;
import com.eg.HousingLibrary.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(EntityDTOMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    //add a user
    @Transactional
    public UserDTO addUser(UserDTO userdto){
        User user= EntityDTOMapper.toUserEntity(userdto);
        return EntityDTOMapper.toUserDTO(userRepository.save(user));
    }

    //find user by its mid
    public Optional<User> findUserById(Integer id){
        return userRepository.findById(id);
    }

    //update user Details
    @Transactional
    public UserDTO updateUser(Integer  id, UserDTO userdto){
        if(userRepository.existsById(id)){
            User user= EntityDTOMapper.toUserEntity(userdto);
            user.setid(id);
            return EntityDTOMapper.toUserDTO(userRepository.save(user));
        }
        return null;
    }

    // Delete a user
    @Transactional
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }



}
