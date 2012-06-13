package pl.pks.memgen.db;

import java.util.List;
import pl.pks.memgen.api.Meme;

public interface StorageService {

    List<Meme> findAll();

}
