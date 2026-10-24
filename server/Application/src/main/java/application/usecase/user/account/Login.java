package application.usecase.user.account;

import application.cache.ICacheService;
import application.exceptions.businessexceptions.userexceptions.UserNotFound;
import application.exceptions.businessexceptions.userexceptions.WrongPassword;
import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.user.User;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class Login implements IUseCase {

	private final IUserRepository userRepository;
	private final PasswordEncoder passwordEncoder; // inject this

	@Override
	public UseCaseType getType() {
		return UseCaseType.LOGIN;
	}

	public Result<User> perform(LoginData data) {
		assert (data.username != null && !data.username.isBlank()) ||
				(data.email != null && !data.email.isBlank()) : "we rely on valid input data, validation should be done in presentation layer";

		if (data.username != null && !data.username.isBlank())
			return loginByUsername(data.username, data.password);
		else
			return loginByEmail(data.email, data.password);
	}

	private Result<User> loginByUsername(String username, String password) {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isEmpty())
			return Result.failure(UserNotFound.usernameNotFound(username));

		User user = optionalUser.get();

		if (!passwordEncoder.matches(password, user.getPassword()))
			return Result.failure(WrongPassword.wrongPasswordForUsername(username));

		return Result.success(user);
	}

	private Result<User> loginByEmail(String email, String password) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isEmpty())
			return Result.failure(UserNotFound.emailNotFound(email));

		User user = optionalUser.get();

		if (!passwordEncoder.matches(password, user.getPassword()))
			return Result.failure(WrongPassword.wrongPasswordForEmail(email));

		return Result.success(user);
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LoginData {
		String username;
		@Email String email;
		@NotBlank String password;

		@AssertTrue(message = "Both email and username can't be blank")
		private boolean isBothEmailAndUsernameBlank() {
			try {
				return (username != null && !username.isBlank()) || (email != null && !email.isBlank());
			} catch (Exception e) {
				return false;
			}
		}
	}
}
