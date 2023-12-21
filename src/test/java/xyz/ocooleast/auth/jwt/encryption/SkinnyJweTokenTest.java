package xyz.ocooleast.auth.jwt.encryption;

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

class SkinnyJweTokenTest {

    private final Map<String, PrivateKey> privateKeyStore = new HashMap<>();

    private final JweSerializer jweSerializer = new JweSerializer(publicKeyFactory());

    private final JweKeyLocator jweKeyLocator = new JweKeyLocator(privateKeyProvider());

    private final SkinnyJweDeserializer jweDeserializer = new SkinnyJweDeserializer(jweKeyLocator);

    @Test
    void testSerializeAndDeserialize() {
        var expected = createSkinnyJwt();
        var serialized = jweSerializer.serialize(expected);
        var actual = jweDeserializer.deserialize(serialized);
        assertEquals(expected, actual);
    }

    private KeyFactory<PublicKey> publicKeyFactory() {
        return kid -> {
            var keys = Jwts.SIG.RS256.keyPair().build();
            privateKeyStore.put(kid, keys.getPrivate());
            return keys.getPublic();
        };
    }

    private KeyProvider<PrivateKey> privateKeyProvider() {
        return privateKeyStore::get;
    }

}