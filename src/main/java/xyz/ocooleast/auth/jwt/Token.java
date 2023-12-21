package xyz.ocooleast.auth.jwt;

import java.util.Map;

public interface Token {

    Map<String, Object> toClaims();

}
