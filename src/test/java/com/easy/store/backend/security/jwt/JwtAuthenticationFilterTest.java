package com.easy.store.backend.security.jwt;

import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.security.data.SecurityData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterChain filterChain;
    private SecurityData securityData;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = new MockFilterChain();
        securityData = new SecurityData();
    }

    @Test
    void doFilterInternalSuccess() throws Exception {
        String token = securityData.getTokenResponse().getToken();
        String username = "test@test.com";
        User mockUser = mock(User.class);

        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        when(jwtService.getUsernameFromToken(any(String.class))).thenReturn(username);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(mockUser));
        when(jwtService.isTokenValid(eq(token), any(User.class))).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertTrue(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken);

        verify(jwtService).getUsernameFromToken(any(String.class));
        verify(userRepository).findByUsername(any(String.class));
        verify(jwtService).isTokenValid(any(String.class), any());
    }

    @Test
    void doFilterInternalFailedNoToken() throws Exception {
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService, never()).getUsernameFromToken(any(String.class));
        verify(userRepository, never()).findByUsername(any(String.class));
        verify(jwtService, never()).isTokenValid(any(String.class), any());
    }

}