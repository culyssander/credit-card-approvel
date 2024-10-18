package br.com.santander.userservice.services;

import br.com.santander.userservice.dto.UserDto;
import br.com.santander.userservice.dto.UserInputDto;
import br.com.santander.userservice.entities.Role;
import br.com.santander.userservice.entities.User;
import br.com.santander.userservice.exception.UserAlreadyExistsException;
import br.com.santander.userservice.exception.UserNotFoundException;
import br.com.santander.userservice.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServicesTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private UserServices userServices;

    @BeforeEach
    void setUp() {
        userServices = new UserServices(userRepository, modelMapper);
    }

    @Test
    void itShouldCreateNewUserWithClientRole() {
        // given
        UserInputDto userInputDto = getUserInputDto();

        // when
        userServices.createUserRoleClient(userInputDto);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        Mockito.verify(userRepository).save(captor.capture());

        User userCapture = captor.getValue();
        
        // then
        assertEquals(userInputDto.getName(), userCapture.getName());
        assertEquals(Role.CLIENT, userCapture.getRole());
        assertTrue(userCapture.isStatus());
    }

    @Test
    void itShouldCreateUpdateUserWithClientRole() {
        // given
        UserInputDto userInputDto = getUserInputDto();
        userInputDto.setId(1L);

        // when
        userServices.createUserRoleClient(userInputDto);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        Mockito.verify(userRepository).save(captor.capture());

        User userCapture = captor.getValue();

        // then
        assertEquals(userInputDto.getName(), userCapture.getName());
        assertEquals(Role.CLIENT, userCapture.getRole());
        assertTrue(userCapture.isStatus());
        assertNotNull(userCapture.getLastUpdated());
    }

    @Test
    void itShouldReturnThrowWhenUserAlreadyExists() {
        // given
        UserInputDto userInputDto = getUserInputDto();
        BDDMockito.given(userRepository.existsByEmail(Mockito.anyString())).willReturn(true);

        // when
        // then
        assertThrows(UserAlreadyExistsException.class, () -> userServices.createUserRoleClient(userInputDto));
    }

    @Test
    void itShouldReturnUserWhenFindByEmail() {
        // given
        String email = "admin@culysoft.com";

        User user = createNewUser();
        BDDMockito.given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        Mockito.when(modelMapper.map(user, UserDto.class)).thenReturn(userDto());

        // when
        UserDto userDto = userServices.findByEmail(email);

        // then
        assertEquals("Admin", userDto.getName());
        assertEquals(Role.ADMIN, userDto.getRole());
    }

    @Test
    void itShouldReturnThrowWhenUserNotFound() {
        // given
        String email = "admin@culysoft.com";

        // when
        // then
        assertThrows(UserNotFoundException.class, () -> userServices.findByEmail(email));
    }

    private User createNewUser() {
        return User.builder()
                .email("admin@culysoft.com")
                .name("Admin")
                .password("1234")
                .role(Role.ADMIN)
                .status(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private UserDto userDto() {
        UserDto userDto = new UserDto();

        userDto.setName("Admin");
        userDto.setRole(Role.ADMIN);

        return userDto;
    }

    private UserInputDto getUserInputDto() {
        UserInputDto userInputDto = new UserInputDto();

        userInputDto.setName("Client");
        userInputDto.setEmail("client@culysoft.com");
        userInputDto.setPassword("1234");

        return userInputDto;
    }
}