package xyz.ocooleast.auth.jwt.encryption;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Locator;
import lombok.RequiredArgsConstructor;
import xyz.ocooleast.auth.jwt.SkinnyJwt;
import xyz.ocooleast.auth.jwt.TokenDeserializer;

import java.security.Key;

@RequiredArgsConstructor
public class SkinnyJweDeserializer implements TokenDeserializer<SkinnyJwt> {

    private final Locator<Key> keyLocator;

    @Override
    public SkinnyJwt deserialize(String token) {
        var payload = Jwts.parser().keyLocator(keyLocator).build().parseEncryptedClaims(token).getPayload();
        return SkinnyJwt.builder()
                .tokenId(payload.getId())
                .subject(payload.getSubject())
                .issuedAt(payload.getIssuedAt())
                .expiresAt(payload.getExpiration())
                .build();
    }

}
