package webapi.configuration;

import domain.entities.user.Role;
import domain.entities.user.User;
import infra.jwt.JwtUtil;
import infra.repositories.application.UserRepository;
import jakarta.servlet.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import webapi.services.AuthenticationService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    public CustomOAuth2SuccessHandler(AuthenticationService authenticationService, UserRepository userRepository) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));

        String token = authenticationService.generateTokenForUser(user);

        String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);
        String encodedUsername = URLEncoder.encode(user.getUsername(), StandardCharsets.UTF_8);

        response.sendRedirect("http://localhost:3000/oauth2/redirect?token=" + encodedToken +"&user=" + encodedUsername );
    }
}
