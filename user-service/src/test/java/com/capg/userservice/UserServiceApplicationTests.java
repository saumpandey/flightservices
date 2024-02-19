package com.capg.userservice;

import com.capg.dto.UserDataDTO;
import com.capg.entity.UserData;
import com.capg.exception.UserAlreadyExistsException;
import com.capg.exception.UserNotFoundException;
import com.capg.repository.UserRepository;
import com.capg.service.SequenceGeneratorService;
import com.capg.service.UserDataServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceApplicationTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @InjectMocks
    private UserDataServiceImpl userDataService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserData() {
        List<UserData> userDataList = createDummyUserDataList();
        when(userRepository.findAll()).thenReturn(userDataList);

        List<UserDataDTO> result = userDataService.getUserData();

        assertEquals(userDataList.size(), result.size());
        for (int i = 0; i < userDataList.size(); i++) {
            UserData userData = userDataList.get(i);
            UserDataDTO userDataDTO = result.get(i);
            assertEquals(userData.getUserId(), userDataDTO.getUserId());
            assertEquals(userData.getUsername(), userDataDTO.getUsername());
            assertEquals(userData.getPhoneNo(), userDataDTO.getPhoneNo());
            assertEquals(userData.getEmail(), userDataDTO.getEmail());
            assertEquals(userData.getUserPassword(), userDataDTO.getUserPassword());
        }
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById() {
        int id = 1;
        UserData userData = createDummyUserData(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(userData));

        UserDataDTO result = userDataService.getUser(id);

        assertNotNull(result);
        assertEquals(userData.getUserId(), result.getUserId());
        assertEquals(userData.getUsername(), result.getUsername());
        assertEquals(userData.getPhoneNo(), result.getPhoneNo());
        assertEquals(userData.getEmail(), result.getEmail());
        assertEquals(userData.getUserPassword(), result.getUserPassword());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    public void testGetUserById_UserNotFound() {
        int id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userDataService.getUser(id));
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    public void testNewUser() {
        UserDataDTO userDataDTO = createDummyUserDataDTO();
        UserData userData = createDummyUserData(1);
        when(userRepository.getUserDataByEmail(userDataDTO.getEmail())).thenReturn(Optional.empty());
        when(sequenceGeneratorService.getSequenceNumber(UserData.SEQUENCE_NAME)).thenReturn((int) 1L);
        when(userRepository.save(any(UserData.class))).thenReturn(userData);

        UserDataDTO result = userDataService.newUser(userDataDTO);

        assertNotNull(result);
        assertEquals(userData.getUserId(), result.getUserId());
        // assertEquals(userDataDTO.getUsername(), result.getUsername());
        assertEquals(userDataDTO.getPhoneNo(), result.getPhoneNo());
        assertEquals(userDataDTO.getEmail(), result.getEmail());
        assertEquals(userDataDTO.getUserPassword(), result.getUserPassword());
        verify(userRepository, times(1)).getUserDataByEmail(userDataDTO.getEmail());
        verify(sequenceGeneratorService, times(1)).getSequenceNumber(UserData.SEQUENCE_NAME);
        verify(userRepository, times(1)).save(any(UserData.class));
    }

    @Test
    public void testNewUser_UserAlreadyExists() {
        UserDataDTO userDataDTO = createDummyUserDataDTO();
        UserData userData = createDummyUserData(1);
        when(userRepository.getUserDataByEmail(userDataDTO.getEmail())).thenReturn(Optional.of(userData));

        assertThrows(UserAlreadyExistsException.class, () -> userDataService.newUser(userDataDTO));
        verify(userRepository, times(1)).getUserDataByEmail(userDataDTO.getEmail());
        verify(userRepository, never()).save(any(UserData.class));
    }

    // Add tests for updateUser and deleteUser methods as per requirements

    // Helper method to create a dummy UserDataDTO for testing
    private UserDataDTO createDummyUserDataDTO() {
        UserDataDTO userDataDTO = new UserDataDTO();
        userDataDTO.setUsername("John Doe");
        userDataDTO.setPhoneNo("1234567890");
        userDataDTO.setEmail("john.doe@example.com");
        userDataDTO.setUserPassword("password123");
        return userDataDTO;
    }

    // Helper method to create a list of dummy UserData for testing
    private List<UserData> createDummyUserDataList() {
        List<UserData> userDataList = new ArrayList<>();
        userDataList.add(createDummyUserData(1));
        userDataList.add(createDummyUserData(2));
        userDataList.add(createDummyUserData(3));
        return userDataList;
    }

    // Helper method to create a dummy UserData for testing with given ID
    private UserData createDummyUserData(int id) {
        UserData userData = new UserData();
        userData.setUserId(id);
        userData.setUsername("John Doe");
        userData.setPhoneNo("1234567890");
        userData.setEmail("john.doe@example.com");
        userData.setUserPassword("password123");
        return userData;
    }
}
