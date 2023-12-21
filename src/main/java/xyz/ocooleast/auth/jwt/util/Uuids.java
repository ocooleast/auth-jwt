package xyz.ocooleast.auth.jwt.util;

import java.util.UUID;

public final class Uuids {

    private Uuids() {}

    public static String uuidAsString() {
        return UUID.randomUUID().toString();
    }

}
