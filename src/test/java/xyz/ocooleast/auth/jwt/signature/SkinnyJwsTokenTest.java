package xyz.ocooleast.auth.jwt.signature;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import xyz.ocooleast.auth.jwt.KeyFactory;
import xyz.ocooleast.auth.jwt.KeyProvider;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static xyz.ocooleast.auth.jwt.util.TokenTestFactory.createSkinnyJwt;

class SkinnyJwsTokenTest {

    private final Map<String, PublicKey> publicKeyStore = new HashMap<>();

    private final JwsSerializer jwsSerializer = new JwsSerializer(privateKeyFactory());

    private final JwsKeyLocator jwsKeyLocator = new JwsKeyLocator(publicKeyProvider());

    private final SkinnyJwsDeserializer jwsDeserializer = new SkinnyJwsDeserializer(jwsKeyLocator);

    @Test
    void testSerializeAndDeserialize() {
        var expected = createSkinnyJwt();
        var serialized = jwsSerializer.serialize(expected);
        var actual = jwsDeserializer.deserialize(serialized);
        assertEquals(expected, actual);
    }

    private KeyFactory<PrivateKey> privateKeyFactory() {
        return kid -> {
            var keys = Jwts.SIG.RS256.keyPair().build();
            publicKeyStore.put(kid, keys.getPublic());
            return keys.getPrivate();
        };
    }

    private KeyProvider<PublicKey> publicKeyProvider() {
        return publicKeyStore::get;
    }

}