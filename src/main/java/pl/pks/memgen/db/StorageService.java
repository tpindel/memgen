package pl.pks.memgen.db;

import java.util.List;
import pl.pks.memgen.api.Figure;
import pl.pks.memgen.api.Meme;
import pl.pks.memgen.io.UploadedImage;

public interface StorageService {

    List<Figure> findAllFigures();

    Figure findOneFigure(String id);

    Figure saveFigure(UploadedImage uploadedImage);

    List<Meme> findAllMemes();

    String findContentType(String id);

    Meme findOneMeme(String id);

    Meme saveMeme(UploadedImage uploadedImage);
}
