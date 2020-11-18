package com.io.bookstoreapi;

import com.io.bookstoreapi.data.repositories.UserRepository;
import com.io.bookstoreapi.data.service.UserService;
import com.io.bookstoreapi.domain.User;
import com.io.bookstoreapi.exceptions.UserAlreadyExistsException;
import com.io.bookstoreapi.exceptions.UserIsNullException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {
    @Mock
    private UserRepository    userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserService SUT;

    @Before
    public void setup() throws Exception {
        SUT = new UserService(userRepository,passwordEncoder);
    }
    @Test
    public void test_pass_null_as_argument_save_User(){
     assertThrows(UserIsNullException.class,()->SUT.saveUser(null));
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void test_addUser_when_that_user_already_exists()
    {   User user=new User("kamil","kamil","kamil@kamil.pl");
        when(userRepository.existsByEmailAddress(user.getEmailAddress())).thenReturn(true);
        SUT.saveUser(user);
    }

    @Test
    public void test_saveUser(){
        User user=new User("kamil","kamil","kamil@kamil.pl");
        when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        assertEquals(user,SUT.saveUser(user));
    }





}
