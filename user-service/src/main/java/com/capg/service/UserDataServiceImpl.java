package com.capg.service;

import com.capg.dto.UserDataDTO;
import com.capg.entity.UserData;
import com.capg.exception.UserAlreadyExistsException;
import com.capg.exception.UserNotFoundException;
import com.capg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDataServiceImpl implements UserDataService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    //Get all users
    @Override
    public List<UserDataDTO> getUserData() {
        List<UserData> userData = userRepository.findAll();
        return userData.stream().map(userData1 -> new UserDataDTO(userData1)).collect(Collectors.toList());
    }

    //Get user with given ID
    @Override
    public UserDataDTO getUser(Integer id) {
        UserData userData = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User does not exist with ID:" + id));
        return new UserDataDTO(userData);
    }

    //Create new user
    @Override
    public UserDataDTO newUser(UserDataDTO userDataDTO) {
        // Check if the user with the given email already exists
        Optional<UserData> existingUser = userRepository.getUserDataByEmail(userDataDTO.getEmail());

        if (existingUser.isPresent()) {
            // User with the same email already exists, handle the situation accordingly
            throw new UserAlreadyExistsException("User already exists with email: " + userDataDTO.getEmail());
        }

        // User with the given email does not exist, proceed with creating a new user
        UserData userData = new UserData(userDataDTO);
        userData.setUserId(sequenceGeneratorService.getSequenceNumber(UserData.SEQUENCE_NAME));
        return new UserDataDTO(userRepository.save(userData));
    }
    //Update user data for given ID
    @Override
    public UserDataDTO updateUser(Integer id, UserDataDTO userDataDTO) {
        UserData userData = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User does not exist with ID: " + id));

        Optional<UserData> optionalUser = userRepository.findById(id);
        userRepository.delete(userData);

        if (optionalUser.isPresent()) {
            UserData userSave = getUserData(userDataDTO, optionalUser, userData);

            userRepository.save(userSave);
            return new UserDataDTO(userSave);
        }
        return new UserDataDTO(userData);
    }

    private static UserData getUserData(UserDataDTO userDataDTO, Optional<UserData> optionalUser, UserData userData) {
        UserData userSave = optionalUser.get();

        userSave.setUserId(userData.getUserId());
        userSave.setUsername(userDataDTO.getUsername() != null ? userDataDTO.getUsername() : userSave.getUsername());
        userSave.setPhoneNo(userDataDTO.getPhoneNo() != null ? userDataDTO.getPhoneNo() : userSave.getPhoneNo());
        userSave.setEmail(userDataDTO.getEmail() != null ? userDataDTO.getEmail() : userSave.getEmail());
        userSave.setUserPassword(userDataDTO.getUserPassword() != null ? userDataDTO.getUserPassword() : userSave.getUserPassword());
        return userSave;
    }

    //Delete user data for given ID
    @Override
    public void deleteUser(Integer id) {
        UserData userData = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User does not exist with ID:" + id));
        userRepository.delete(userData);
    }

    //Delete all user data
    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
