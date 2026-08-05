package dev.orion.track_my_vehicle_auth_client.configuration;

import dev.orion.grpc.auth.private_client.AuthPrivateServiceGrpc;
import dev.orion.grpc.auth.public_client.AuthPublicServiceGrpc;
import dev.orion.track_my_vehicle_auth_client.implementation.AuthClientDefault;
import dev.orion.track_my_vehicle_auth_client.metadata.AuthClientConfigurationProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.client.GrpcChannelFactory;

@AutoConfiguration
@EnableConfigurationProperties(AuthClientConfigurationProperties.class)
@ConditionalOnClass(GrpcChannelFactory.class)
public class AuthClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    AuthPublicServiceGrpc.AuthPublicServiceStub publicAuthServiceStub(GrpcChannelFactory factory, AuthClientConfigurationProperties config){
        try {
            return AuthPublicServiceGrpc.newStub(factory.createChannel(config.getChannel()));
        }
        catch (Exception ex) {
            throw new IllegalStateException(
                    "No Spring gRPC channel named '" + config.getChannel()
                            + "' has been configured. Configure it under "
                            + "'spring.grpc.client.channels." + config.getChannel() + "'.",
                    ex);
        }
    }

    @Bean
    @ConditionalOnMissingBean
    AuthPrivateServiceGrpc.AuthPrivateServiceStub privateAuthServiceStub(GrpcChannelFactory factory, AuthClientConfigurationProperties config){
        try {
            return AuthPrivateServiceGrpc.newStub(factory.createChannel(config.getChannel()));
        }
        catch (Exception ex) {
            throw new IllegalStateException(
                    "No Spring gRPC channel named '" + config.getChannel()
                            + "' has been configured. Configure it under "
                            + "'spring.grpc.client.channels." + config.getChannel() + "'.",
                    ex);
        }
    }

    @Bean
    @ConditionalOnMissingBean
    dev.orion.track_my_vehicle_auth_client.AuthClient authClient(AuthPublicServiceGrpc.AuthPublicServiceStub publicStub, AuthPrivateServiceGrpc.AuthPrivateServiceStub privateStub){
        return new AuthClientDefault(publicStub, privateStub);
    }
}
