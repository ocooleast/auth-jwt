package xyz.ocooleast.auth.jwt.signature;

import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.LocatorAdapter;
import lombok.RequiredArgsConstructor;
import xyz.ocooleast.auth.jwt.KeyProvider;

import java.security.Key;
import java.security.PublicKey;

@RequiredArgsConstructor
public class JwsKeyLocator extends LocatorAdapter<Key> {

    private final KeyProvider<PublicKey> publicKeyProvider;

    @Override
    protected Key locate(JwsHeader header) {
        return publicKeyProvider.get(header.getKeyId());
    }

}
