package xyz.ocooleast.auth.jwt.encryption;

import io.jsonwebtoken.JweHeader;
import io.jsonwebtoken.LocatorAdapter;
import lombok.RequiredArgsConstructor;
import xyz.ocooleast.auth.jwt.KeyProvider;

import java.security.Key;
import java.security.PrivateKey;

@RequiredArgsConstructor
public class JweKeyLocator extends LocatorAdapter<Key> {

    private final KeyProvider<PrivateKey> privateKeyProvider;

    @Override
    protected Key locate(JweHeader header) {
        return privateKeyProvider.get(header.getKeyId());
    }

}
