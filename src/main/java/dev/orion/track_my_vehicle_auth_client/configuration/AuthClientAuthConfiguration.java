package dev.orion.track_my_vehicle_auth_client.configuration;

import dev.orion.grpc.auth.client.PermissionCheckServiceGrpc;
import dev.orion.track_my_vehicle_auth_client.AuthClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.client.GrpcChannelFactory;

@AutoConfiguration
@ConditionalOnClass(GrpcChannelFactory.class)
@ConditionalOnBean(GrpcChannelFactory.class)
public class AuthClientAuthConfiguration {

    @Bean
    @ConditionalOnMissingBean
    PermissionCheckServiceGrpc.PermissionCheckServiceStub permissionCheckServiceStub(GrpcChannelFactory factory){
        return PermissionCheckServiceGrpc.newStub(factory.createChannel("auth"));
    }
    @Bean
    @ConditionalOnMissingBean
    AuthClient authClient(PermissionCheckServiceGrpc.PermissionCheckServiceStub stub){
        return new dev.orion.track_my_vehicle_auth_client.AuthClientDefault(stub);
    }
}
