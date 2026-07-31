package dev.orion.track_my_vehicle_auth_client;

import dev.orion.grpc.auth.public_client.ServiceLoginRequest;
import dev.orion.track_my_vehicle_auth_client.metadata.AuthClientConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceTokenManager {

    private final AuthClient authClient;
    private final AuthClientConfigurationProperties properties;

    private String accessToken;
    private String refreshToken;

    public synchronized String getToken() {
        if (accessToken == null) {
            refreshAccessToken();
        }
        return accessToken;
    }

    private void refreshAccessToken() {
        if (refreshToken == null) {
            var response = authClient.s2sLogin(
                    ServiceLoginRequest.newBuilder()
                            .setClientId(properties.getClientId())
                            .setClientSecret(properties.getClientSecret())
                            .build()
            );
            accessToken = response.getAccessToken();
            refreshToken = response.getRefreshToken();
        } else {
            // TODO: generate with refresh token
        }
    }
}
