package dev.orion.track_my_vehicle_auth_client.annotations;

import dev.orion.grpc.auth.private_client.Action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuthorizeController {
    String resource();
    Action action();
}
