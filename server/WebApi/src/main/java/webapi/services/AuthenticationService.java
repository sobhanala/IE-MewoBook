package webapi.services;

import application.cache.ICacheService;
import application.exceptions.BaseException;
import application.repositories.IUserRepository;
import domain.entities.user.User;
import infra.jwt.JwtUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webapi.exceptions.AuthenticationException;
import webapi.views.user.SessionControl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Getter
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUserRepository userRepository;
	private static final ThreadLocal<User> currentUser = new ThreadLocal<>();
	private User user = null;

	public User getUser() {
		User user = currentUser.get();
		if (user == null) {
			throw AuthenticationException.noOneLoggedIn();
		}
		return user;
	}


	public void validateSomeOneLoggedIn() {
		if (user == null)
			throw AuthenticationException.noOneLoggedIn();
	}

	public void validateNoOneLoggedIn() {
		if (user != null)
			throw AuthenticationException.someOneLoggedIn();
	}


	public void setLoggedInUser(User user) {
		currentUser.set(user);
	}

	public void unSetLoggedInUser() {
		currentUser.remove();
	}

	public String generateTokenForUser(User user) {
		return JwtUtil.generateToken(user);
	}

	public User loadUserById(String id) {
		return userRepository.findByUsername(id)
				.orElseThrow(() -> new BaseException("User not found"));
	}

}
