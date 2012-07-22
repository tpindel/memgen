package pl.pks.memgen.db;

import java.io.InputStream;
import java.util.List;
import pl.pks.memgen.api.Figure;
import pl.pks.memgen.api.Meme;
import com.amazonaws.services.s3.model.ObjectMetadata;

public interface StorageService {

    List<Figure> findAllFigures();

    Figure findOneFigure(String id);

    Figure saveFigure(ObjectMetadata objectMetadata, InputStream inputStream);

    List<Meme> findAllMemes();

    Meme saveMeme(ObjectMetadata objectMetadata, InputStream inputStream);

    String findContentType(String id);

    Meme findOneMeme(String id);
}
