package xyz.ocooleast.auth.jwt;

import java.security.Key;

@FunctionalInterface
public interface KeyFactory<T extends Key> {

    T create(String kid);

}
