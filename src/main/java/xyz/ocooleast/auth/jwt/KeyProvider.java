package xyz.ocooleast.auth.jwt;

import java.security.Key;

@FunctionalInterface
public interface KeyProvider<T extends Key> {

    T get(String kid);

}
