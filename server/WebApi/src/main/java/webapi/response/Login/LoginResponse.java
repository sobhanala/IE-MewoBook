package webapi.response.Login;

public record LoginResponse(
        String role,
        String sessionId
) {}
