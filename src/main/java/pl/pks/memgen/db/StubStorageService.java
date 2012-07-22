package pl.pks.memgen.db;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import pl.pks.memgen.api.Figure;
import pl.pks.memgen.api.Meme;
import com.amazonaws.services.s3.model.ObjectMetadata;

public class StubStorageService implements StorageService {

    @Override
    public List<Figure> findAllFigures() {
        List<Figure> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list.add(new Figure("123", "http://placehold.it/250x250"));
        }
        return list;
    }

    @Override
    public Figure findOneFigure(String id) {
        return new Figure("123", "https://dl.dropbox.com/u/1114182/memgen/philosoraptor.jpg");
    }

    @Override
    public Figure saveFigure(ObjectMetadata objectMetadata, InputStream inputStream) {
        return new Figure("123", "http://placehold.it/250x250");
    }

    @Override
    public String findContentType(String id) {
        return "image/jpeg";
    }

    @Override
    public List<Meme> findAllMemes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Meme saveMeme(ObjectMetadata objectMetadata, InputStream inputStream) {
        return new Meme("123", "https://dl.dropbox.com/u/1114182/memgen/philosoraptor.jpg", null, null);
    }

    @Override
    public Meme findOneMeme(String id) {
        return new Meme("123", "https://dl.dropbox.com/u/1114182/memgen/philosoraptor.jpg", null, null);
    }
}
