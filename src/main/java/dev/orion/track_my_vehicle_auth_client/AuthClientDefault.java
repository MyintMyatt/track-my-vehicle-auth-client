package dev.orion.track_my_vehicle_auth_client;

import dev.orion.grpc.auth.client.PermissionCheckRequest;
import dev.orion.grpc.auth.client.PermissionCheckResponse;
import dev.orion.grpc.auth.client.PermissionGrpcServiceGrpc;
import io.grpc.stub.StreamObserver;
import io.netty.util.concurrent.CompleteFuture;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class AuthClientDefault implements AuthClient {

    private final PermissionGrpcServiceGrpc.PermissionGrpcServiceStub stub;

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
