package org.mt.ev.infrastructure.utils;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeyCloakProvider {

    private static String keycloakUrl;
    private static String realmName;
    private static String realmMaster;
    private static String clientId;
    private static String clientSecret;
    private static String consoleUser;
    private static String consolePassword;

    @Value("${jwt.keycloak-url}")
    public void setKeycloakUrl(String keycloakUrl) {
        KeyCloakProvider.keycloakUrl = keycloakUrl;
    }

    @Value("${jwt.realm-name}")
    public void setRealmName(String realmName) {
        KeyCloakProvider.realmName = realmName;
    }

    @Value("${jwt.realm-master}")
    public void setRealmMaster(String realmMaster) {
        KeyCloakProvider.realmMaster = realmMaster;
    }

    @Value("${jwt.client-id}")
    public void setClientId(String clientId) {
        KeyCloakProvider.clientId = clientId;
    }

    @Value("${jwt.client-secret}")
    public void setClientSecret(String clientSecret) {
        KeyCloakProvider.clientSecret = clientSecret;
    }

    @Value("${jwt.console-user}")
    public void setConsoleUser(String consoleUser) {
        KeyCloakProvider.consoleUser = consoleUser;
    }

    @Value("${jwt.console-password}")
    public void setConsolePassword(String consolePassword) {
        KeyCloakProvider.consolePassword = consolePassword;
    }

    public static RealmResource getRealmResource() {
        Keycloak keycloak = KeycloakBuilder
                .builder()
                .serverUrl(keycloakUrl)
                .realm(realmMaster)
                .clientId("admin-cli")
                .username(consoleUser)
                .password(consolePassword)
                .clientSecret(clientSecret)
                .resteasyClient(new ResteasyClientBuilderImpl()
                        .connectionPoolSize(10)
                        .build()
                )
                .build();
        return keycloak.realm(realmName);
    }

    public static UsersResource getUserResource() {
        return getRealmResource().users();
    }

    public static String getClientId() {
        return clientId;
    }
}