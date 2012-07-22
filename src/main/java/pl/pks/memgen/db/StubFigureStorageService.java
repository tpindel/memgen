package pl.pks.memgen.db;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import pl.pks.memgen.api.Figure;
import com.amazonaws.services.s3.model.ObjectMetadata;

public class StubFigureStorageService implements FigureStorageService {

    @Override
    public List<Figure> findAll() {
        List<Figure> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list.add(new Figure("123", "http://placehold.it/250x250"));
        }
        return list;
    }

    @Override
    public Figure findOne(String id) {
        return new Figure("123", "https://dl.dropbox.com/u/1114182/memgen/philosoraptor.jpg");
    }

    @Override
    public Figure save(ObjectMetadata objectMetadata, InputStream inputStream) {
        return new Figure("123", "http://placehold.it/250x250");
    }

    @Override
    public String findContentType(String id) {
        return "image/jpeg";
    }
}
