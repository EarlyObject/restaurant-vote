package com.sar.ws.service.impl.unitTests;

import com.sar.ws.TestConfig;
import com.sar.ws.io.repositories.*;
import com.sar.ws.service.impl.MealServiceImpl;
import com.sar.ws.service.impl.RestaurantServiceImpl;
import com.sar.ws.service.impl.UserServiceImpl;
import com.sar.ws.service.impl.VoteServiceImpl;
import com.sar.ws.shared.dto.Utils;
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
