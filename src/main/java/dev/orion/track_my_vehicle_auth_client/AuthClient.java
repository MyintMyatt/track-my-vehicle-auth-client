package dev.orion.track_my_vehicle_auth_client;

import dev.orion.grpc.auth.private_client.PermissionCheckRequest;
import dev.orion.grpc.auth.private_client.PermissionCheckResponse;
import dev.orion.grpc.auth.public_client.RenewTokenRequest;
import dev.orion.grpc.auth.public_client.ServiceLoginRequest;
import dev.orion.grpc.auth.public_client.ServiceLoginResponse;

public interface AuthClient {

    ///
    /// check permission
    ///
    PermissionCheckResponse checkPermission(PermissionCheckRequest request);
    ///
    /// service to service authentication
    ///
    ServiceLoginResponse s2sLogin(ServiceLoginRequest request);

    ///
    /// service to service refresh token
    ///
    ServiceLoginResponse renewS2STokenPair(RenewTokenRequest request);


}
