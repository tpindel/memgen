package pl.pks.memgen.db;

import java.io.InputStream;
import java.util.List;
import pl.pks.memgen.api.Figure;
import com.amazonaws.services.s3.model.ObjectMetadata;

public interface FigureStorageService {

    List<Figure> findAll();

    Figure findOne(String id);

    Figure save(ObjectMetadata objectMetadata, InputStream inputStream);

    String findContentType(String id);
}
