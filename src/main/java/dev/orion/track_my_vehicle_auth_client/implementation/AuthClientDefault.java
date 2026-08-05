package dev.orion.track_my_vehicle_auth_client.implementation;

import dev.orion.grpc.auth.private_client.AuthPrivateServiceGrpc;
import dev.orion.grpc.auth.private_client.PermissionCheckRequest;
import dev.orion.grpc.auth.private_client.PermissionCheckResponse;
import dev.orion.grpc.auth.public_client.*;
import dev.orion.track_my_vehicle_auth_client.AuthClient;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class AuthClientDefault implements  AuthClient{

    private final AuthPublicServiceGrpc.AuthPublicServiceStub publicStub;
    private final AuthPrivateServiceGrpc.AuthPrivateServiceStub privateStub;

    @Override
    public PermissionCheckResponse checkPermission(PermissionCheckRequest request) {
        var result = new CompletableFuture<PermissionCheckResponse>();
        privateStub.checkPermission(request, new StreamObserver<PermissionCheckResponse>() {
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
        publicStub.s2sLogin(request, new StreamObserver<ServiceLoginResponse>() {
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

    @Override
    public ServiceLoginResponse renewS2STokenPair(RenewTokenRequest request) {
        var result = new CompletableFuture<ServiceLoginResponse>();
        publicStub.renewS2STokenPair(request, new StreamObserver<ServiceLoginResponse>() {
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
