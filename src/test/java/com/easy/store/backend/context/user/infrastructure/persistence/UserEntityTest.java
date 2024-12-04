package com.easy.store.backend.context.user.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserEntityTest {

    @InjectMocks
    private UserEntity userEntity;

    @Test
    void onCreateTest() {
        userEntity.onCreate();

        assertEquals(userEntity.getState(), "active");
        assertNotNull(userEntity.getRole());
        assertEquals(userEntity.getRole().getId(), 1L);
        assertEquals(userEntity.getRole().getName(), "client");
    }

}