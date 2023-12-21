package xyz.ocooleast.auth.jwt.signature;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import xyz.ocooleast.auth.jwt.KeyFactory;
import xyz.ocooleast.auth.jwt.Token;
import xyz.ocooleast.auth.jwt.TokenSerializer;

import java.security.PrivateKey;

import static xyz.ocooleast.auth.jwt.util.Uuids.uuidAsString;

@RequiredArgsConstructor
public class JwsSerializer implements TokenSerializer {

    private final KeyFactory<PrivateKey> privateKeyFactory;

    @Override
    public <T extends Token> String serialize(T token) {
        var kid = uuidAsString();
        var key = privateKeyFactory.create(kid);
        return Jwts.builder()
                .header().keyId(kid).and()
                .claims().add(token.toClaims()).and()
                .signWith(key)
                .compact();
    }

}
