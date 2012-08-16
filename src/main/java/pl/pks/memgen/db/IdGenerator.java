package pl.pks.memgen.db;

import java.util.UUID;

public class IdGenerator {

    public String generate() {
        return UUID.randomUUID().toString();

    }
}
