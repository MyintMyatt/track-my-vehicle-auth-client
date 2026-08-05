package dev.orion.track_my_vehicle_auth_client;

import dev.orion.grpc.auth.public_client.RenewTokenRequest;
import dev.orion.grpc.auth.public_client.ServiceLoginRequest;
import dev.orion.track_my_vehicle_auth_client.metadata.AuthClientConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/// This ServiceTokenManager store access and refresh tokens.
/// When communicate with others services, get token from this manager
///

@Slf4j
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
        log.info("Requesting service token for clientId: {}", properties.getClientId());
        try {
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
                var response = authClient.renewS2STokenPair(
                        RenewTokenRequest.newBuilder().setRefreshToken(refreshToken).build()
                );

                accessToken = response.getAccessToken();
                refreshToken = response.getRefreshToken();
            }
        } catch (Exception e){
            log.error("ServiceTokenManager : {} Client Id - {}", "Failed to authenticate service with Auth Server. ", properties.getClientId());
            log.error("ServiceTokenManager : " , e);
        }
    }
}
