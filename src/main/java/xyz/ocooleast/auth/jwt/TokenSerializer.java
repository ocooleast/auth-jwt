package xyz.ocooleast.auth.jwt;

@FunctionalInterface
public interface TokenSerializer {

    <T extends Token> String serialize(T token);

}
