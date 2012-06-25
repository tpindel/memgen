package pl.pks.memgen.db;

import java.util.ArrayList;
import java.util.List;
import pl.pks.memgen.api.Meme;

public class StubStorageService implements StorageService {

    @Override
    public List<Meme> findAll() {
        List<Meme> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list.add(new Meme("http://placehold.it/250x250"));
        }
        return list;
    }

    @Override
    public Meme save(String url) {
        return new Meme("http://placehold.it/250x250");
    }

}
