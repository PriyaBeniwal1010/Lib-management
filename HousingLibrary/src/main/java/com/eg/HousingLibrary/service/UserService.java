package com.eg.HousingLibrary.service;


import com.eg.HousingLibrary.ConverterUtility.EntityDTOMapper;
import com.eg.HousingLibrary.dto.UserDTO;
import com.eg.HousingLibrary.exception.UserNotFoundException;
import com.eg.HousingLibrary.globalException.ResourceNotFoundException;
import com.eg.HousingLibrary.model.User;
import com.eg.HousingLibrary.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";


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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

        userRepository.delete(user);
    }


    public String uploadProfilePicture(Integer userId, MultipartFile file) {
        try {
            // Validate user exists
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

            // Validate file type
            if (!file.getContentType().startsWith("image/")) {
                throw new RuntimeException("Invalid file type. Only images are allowed.");
            }

            // Create uploads directory if not exists
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();

            // Save file
            String filePath = UPLOAD_DIR + userId + "_" + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            // Update user profile picture path
            user.setProfilePicture(filePath);
            userRepository.save(user);

            return filePath;
        } catch (Exception e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }




}
