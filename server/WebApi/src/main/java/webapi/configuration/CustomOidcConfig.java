package webapi.configuration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.oidc.authentication.OidcIdTokenDecoderFactory;
import org.springframework.context.annotation.Bean;

@Configuration
public class CustomOidcConfig {

    private static final String CUSTOM_JWK_SET_URI = "https://noisy-pine-722e.souri-m82.workers.dev/";

    @Bean
    @Primary
    public JwtDecoderFactory<ClientRegistration> customJwtDecoderFactory() {
        return registration -> {
            if ("google".equals(registration.getRegistrationId())) {
                NimbusJwtDecoder decoder = NimbusJwtDecoder.withJwkSetUri(CUSTOM_JWK_SET_URI).build();
                return decoder;
            } else {
                return new OidcIdTokenDecoderFactory().createDecoder(registration);
            }
        };
    }
}
