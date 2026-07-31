package dev.orion.track_my_vehicle_auth_client.aop;

import dev.orion.track_my_vehicle_auth_client.AuthClient;
import dev.orion.grpc.auth.private_client.PermissionCheckRequest;
import dev.orion.track_my_vehicle_auth_client.annotations.PreAuthorizeController;import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

@Aspect
@RequiredArgsConstructor
public class PreAuthorizeControllerAspect {

    private final AuthClient authClient;

    @Around("@annotation(preAuthorizeController)")
    public Object preAuthorizeController(ProceedingJoinPoint joinPoint, PreAuthorizeController preAuthorizeController) throws Throwable {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("You must be logged in to access this resource.");
        }
        var request = PermissionCheckRequest.newBuilder()
                .setUsername(authentication.getName())
                .setResource(preAuthorizeController.resource())
                .setAction(preAuthorizeController.action())
                .build();
        var response = authClient.checkPermission(request);

        if (!response.getIsGranted()){
            throw new AccessDeniedException("You have no permission to access this resource.");
        }
        return joinPoint.proceed();
    }
}
