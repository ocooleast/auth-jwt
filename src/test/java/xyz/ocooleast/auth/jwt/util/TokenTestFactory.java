package xyz.ocooleast.auth.jwt.util;

import xyz.ocooleast.auth.jwt.SkinnyJwt;

import java.time.Instant;
import java.util.Date;

import static xyz.ocooleast.auth.jwt.util.Uuids.uuidAsString;

public final class TokenTestFactory {

    private TokenTestFactory() {}

    public static SkinnyJwt createSkinnyJwt() {
        var issuedAt = Instant.parse("1990-01-01T00:00:00.0Z");
        var expiresAt = Instant.parse("2099-12-31T23:59:59.0Z");
        return SkinnyJwt.builder()
                .tokenId(uuidAsString())
                .subject("ocooleast")
                .issuedAt(Date.from(issuedAt))
                .expiresAt(Date.from(expiresAt))
                .build();
    }

}
