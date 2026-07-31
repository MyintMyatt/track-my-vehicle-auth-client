package dev.orion.track_my_vehicle_auth_client.implementation;

import dev.orion.grpc.auth.private_client.PermissionCheckRequest;
import dev.orion.grpc.auth.private_client.PermissionCheckResponse;
import dev.orion.grpc.auth.public_client.AuthServiceGrpc;
import dev.orion.grpc.auth.public_client.ServiceLoginRequest;
import dev.orion.grpc.auth.public_client.ServiceLoginResponse;
import dev.orion.track_my_vehicle_auth_client.AuthClient;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class AuthClientDefault implements  AuthClient{

    private final AuthServiceGrpc.AuthServiceStub stub;

    @Override
    public PermissionCheckResponse checkPermission(PermissionCheckRequest request) {
        var result = new CompletableFuture<PermissionCheckResponse>();
        stub.checkPermission(request, new StreamObserver<PermissionCheckResponse>() {
            @Override
            public void onNext(PermissionCheckResponse value) {
                result.complete(value);
            }

            @Override
            public void onError(Throwable t) {
                result.completeExceptionally(t);
            }

            @Override
            public void onCompleted() {
            }
        });
        return result.join();
    }

    @Override
    public ServiceLoginResponse s2sLogin(ServiceLoginRequest request) {
        var result = new CompletableFuture<ServiceLoginResponse>();
        stub.login(request, new StreamObserver<ServiceLoginResponse>() {
            @Override
            public void onNext(ServiceLoginResponse value) {
                result.complete(value);
            }

            @Override
            public void onError(Throwable t) {
                result.completeExceptionally(t);
            }

            @Override
            public void onCompleted() {

            }
        });
        return result.join();
    }
}
