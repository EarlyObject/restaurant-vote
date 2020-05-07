package com.earlyobject.ws.service.impl.unitTests;

import com.earlyobject.ws.TestConfig;
import com.earlyobject.ws.io.repositories.*;
import com.earlyobject.ws.service.impl.MealServiceImpl;
import com.earlyobject.ws.service.impl.RestaurantServiceImpl;
import com.earlyobject.ws.service.impl.UserServiceImpl;
import com.earlyobject.ws.service.impl.VoteServiceImpl;
import com.earlyobject.ws.shared.dto.Utils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
public class AbstractServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @InjectMocks
    MealServiceImpl mealService;

    @InjectMocks
    RestaurantServiceImpl restaurantService;

    @InjectMocks
    VoteServiceImpl voteService;

    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    UserRepository userRepository;

    @Mock
    RestaurantRepository restaurantRepository;

    @Mock
    MealRepository mealRepository;

    @Mock
    VoteRepository voteRepository;

    @Mock
    RoleRepository roleRepository;
}
