package xyz.ocooleast.auth.jwt;

@FunctionalInterface
public interface TokenDeserializer<T extends Token> {

    T deserialize(String token);

}
