package dev.orion.track_my_vehicle_auth_client;

import dev.orion.commons.client.auth.AuthClient;
import dev.orion.grpc.auth.client.PermissionCheckRequest;
import dev.orion.grpc.auth.client.PermissionCheckResponse;
import dev.orion.grpc.auth.client.PermissionCheckServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;


import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class AuthClientDefault implements AuthClient {

    private final PermissionCheckServiceGrpc.PermissionCheckServiceStub stub;

    @Override
    public CompletableFuture<PermissionCheckResponse> checkPermission(PermissionCheckRequest request) {
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
        return result;
    }
}
