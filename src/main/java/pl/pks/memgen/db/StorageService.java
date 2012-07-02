package pl.pks.memgen.db;

import java.util.List;
import pl.pks.memgen.api.Meme;

public interface StorageService {

    List<Meme> findAll();

    Meme save(String url);

    Meme findOne(String id);
}
