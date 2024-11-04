package kea.wishlist.service;

import kea.wishlist.model.User;
import kea.wishlist.repo.UserRepository;
import kea.wishlist.util.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ActiveProfiles("test")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User("first name", "last name", "email", 25, "password");
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
    }

    @DisplayName("Test adding new user in service layer")
    @Test
    void testSaveUser() throws SQLException {

        when(userRepository.addUser(any())).thenReturn(user);
        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("first name", savedUser.getFirstName(), "First names are not equal.");
        assertEquals("encodedPassword", savedUser.getPassword(), "Password is not encoded.");
        verify(userRepository, times(1)).addUser(user);
        logger.info("testSaveUser - " + savedUser);
    }

}