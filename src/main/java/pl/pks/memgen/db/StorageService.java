package pl.pks.memgen.db;

import java.io.InputStream;
import java.util.List;
import pl.pks.memgen.api.Meme;
import com.amazonaws.services.s3.model.ObjectMetadata;

public interface StorageService {

    List<Meme> findAll();

    Meme findOne(String id);

    Meme save(String url, ObjectMetadata objectMetadata, InputStream inputStream);
}
