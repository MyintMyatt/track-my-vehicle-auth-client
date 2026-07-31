package dev.orion.track_my_vehicle_auth_client.metadata;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth-service.client")
@Data
public class AuthClientConfigurationProperties {

    ///
    /// Auth Service gRPC channel name
    ///
    private String channel = "auth-service";
    private String clientId;
    private String clientSecret;
}
