package xyz.ocooleast.auth.jwt;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@Builder
public class SkinnyJwt implements Token {

    private String tokenId;

    private String subject;

    private Date issuedAt;

    private Date expiresAt;

    @Override
    public Map<String, Object> toClaims() {
        return Map.of(
                "jti", tokenId,
                "sub", subject,
                "iat", issuedAt,
                "exp", expiresAt
        );
    }

}
