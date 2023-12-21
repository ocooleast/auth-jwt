package xyz.ocooleast.auth.jwt.encryption;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.KeyAlgorithm;
import lombok.RequiredArgsConstructor;
import xyz.ocooleast.auth.jwt.KeyFactory;
import xyz.ocooleast.auth.jwt.Token;
import xyz.ocooleast.auth.jwt.TokenSerializer;

import java.security.PrivateKey;
import java.security.PublicKey;

import static xyz.ocooleast.auth.jwt.util.Uuids.uuidAsString;

@RequiredArgsConstructor
public class JweSerializer implements TokenSerializer {

    private final KeyFactory<PublicKey> publicKeyFactory;

    private KeyAlgorithm<PublicKey, PrivateKey> keyAlgorithm = Jwts.KEY.RSA_OAEP_256;

    private AeadAlgorithm aeadAlgorithm = Jwts.ENC.A256GCM;

    @Override
    public <T extends Token> String serialize(T token) {
        var kid = uuidAsString();
        var key = publicKeyFactory.create(kid);
        return Jwts.builder()
                .header().keyId(kid).and()
                .claims().add(token.toClaims()).and()
                .encryptWith(key, keyAlgorithm, aeadAlgorithm)
                .compact();
    }

    public JweSerializer signatureAlgorithm(KeyAlgorithm<PublicKey, PrivateKey> keyAlgorithm) {
        this.keyAlgorithm = keyAlgorithm;
        return this;
    }

    public JweSerializer aeadAlgorithm(AeadAlgorithm aeadAlgorithm) {
        this.aeadAlgorithm = aeadAlgorithm;
        return this;
    }

}
