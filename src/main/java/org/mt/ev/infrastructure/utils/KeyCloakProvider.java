package org.mt.ev.infrastructure.utils;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.stereotype.Component;

@Component
public class KeyCloakProvider {


    private static final String KEYCLOAK_URL = "http://localhost:9090/auth";
    private static final String REALM_NAME = "spring-boot-realm-dev";
    private static final String REALM_MASTER = "master";
    private static final String ADMIN_CLI = "admin-cli";
    private static final String USER_CONSOLE = "admin";
    private static final String USER_PASSWORD = "123";
    private static final String CLIENT_SECRET = "xUpik2N3ezAzRy05HSn4YtPKLNJ1tZWT";

    public static RealmResource getRealmResource(){
        Keycloak keycloak = KeycloakBuilder
                .builder()
                .serverUrl(KEYCLOAK_URL)
                .realm(REALM_MASTER)
                .clientId(ADMIN_CLI)
                .username(USER_CONSOLE)
                .password(USER_PASSWORD)
                .clientSecret(CLIENT_SECRET)
                .resteasyClient(new ResteasyClientBuilderImpl()
                        .connectionPoolSize(10)
                        .build()
                )
                .build();
        return keycloak.realm(REALM_NAME);
    }


    public static UsersResource getUserResource(){
        RealmResource realmResource = getRealmResource();
        return realmResource.users();
    }

}
