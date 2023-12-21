# JWT signature and encryption library (jwtk/jjwt wrapper)
> [!TIP]
> see more about [jwtk/jjwt](https://github.com/jwtk/jjwt)

[![build](https://github.com/ocooleast/auth-jwt/actions/workflows/build.yaml/badge.svg?branch=main)](https://github.com/ocooleast/auth-jwt/actions/workflows/build.yaml)
[![coverage](.github/badges/jacoco.svg)](https://github.com/ocooleast/auth-jwt/actions/workflows/jacoco.yaml)

## guide
### implement KeyFactory

```java
@RequiredArgsConstructor
class PrivateKeyFactory implements KeyFactory<PrivateKey> {

    /**
     * Some repository for storing public keys
     */
    private final PublicKeyRepositury publicKeyRepository;

    /**
     * @return {@link java.security.PrivateKey} for sign JWT
     */
    public PrivateKey create(String kid) {
        var keys = Jwts.SIG.RS256.keyPair().build();
        publicKeyRepository.save(kid, keys.getPublic());
        return keys.getPrivate();
    }
}
```

### implement KeyProvider

```java
@RequiredArgsConstructor
class PublicKeyProvider implements KeyProvider<PrivateKey> {

    /**
     * Some repository for storing public keys
     */
    private final PublicKeyRepositury publicKeyRepository;

    /**
     * @return {@link java.security.PublicKey} for parse JWT
     */
    public PublicKey get(String kid) {
        return publicKeyRepository.get(kid);
    }
}
```

> [!IMPORTANT]
> use the private key to sign the token and the public key to parse it

> [!IMPORTANT]
> in the case of encryption, you must use a public key for encryption and a private key for decryption

### start

```java
public static void main(String[] args) {
    var publicKeyRepository = new PublicKeyRepositury();
    var keyFactory = new PrivateKeyFactory(publicKeyRepository);
    var keyProvider = new PublicKeyProvider(publicKeyRepository);
    var keyLocator = new JwsKeyLocator(keyProvider);
    var serializer = new JwsSerializer(keyFactory);             //use to sign token
    var deserializer = new SkinnyJwsDeserializer(keyLocator);   //use to parse token
    var issuedAt = Instant.now();
    var token = SkinnyJwt.builder()
            .tokenId(UUID.randomUUID().toString())
            .subject("some_username")
            .issuedAt(Date.from(issuedAt))
            .expiresAt(Date.from(issuedAt.plusSeconds(60 * 30))) //30 minutes
            .build();
    var serializedToken = serializer.serialize(token);
    System.out.println(serializedToken);
}
```
