package pl.pks.memgen.db;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import pl.pks.memgen.api.Meme;
import com.amazonaws.services.s3.model.ObjectMetadata;

public class StubStorageService implements StorageService {

    @Override
    public List<Meme> findAll() {
        List<Meme> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list.add(new Meme("123", "http://placehold.it/250x250"));
        }
        return list;
    }

    @Override
    public Meme findOne(String id) {
        return new Meme("123", "https://dl.dropbox.com/u/1114182/memgen/philosoraptor.jpg");
    }

    @Override
    public Meme save(String url, ObjectMetadata objectMetadata, InputStream inputStream) {
        return new Meme("123", "http://placehold.it/250x250");
    }

}
