package pl.pks.memgen.memgenerator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import pl.pks.memgen.api.Figure;
import pl.pks.memgen.api.Meme;
import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.io.FigureDownloader;
import com.amazonaws.services.s3.model.ObjectMetadata;

public class MemGenerator {

    private final FigureDownloader figureDownloader;
    private final StorageService storageService;
    private final FigureTransformer figureTransformer;

    public MemGenerator(FigureDownloader imageDownloader, StorageService storageService,
                        FigureTransformer imageTransformer) {
        this.figureDownloader = imageDownloader;
        this.storageService = storageService;
        this.figureTransformer = imageTransformer;
    }

    public String generate(Meme meme) {
        Figure figure = storageService.findOneMeme(meme.getId());

        try (InputStream input = figureDownloader.download(figure.getUrl())) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            figureTransformer.transform(meme, input, output);
            byte[] generatedMeme = output.toByteArray();
            ByteArrayInputStream memeInputStream = new ByteArrayInputStream(
                generatedMeme);
            Figure savedImage = saveMeme(figure, generatedMeme.length, memeInputStream);
            return savedImage.getId();
        } catch (IOException e) {
            throw new MemeGeneratorException(e);
        }
    }

    private Meme saveMeme(Figure figure, long size, ByteArrayInputStream memeInputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);
        String contentType = storageService.findContentType(figure.getId());
        objectMetadata.setContentType(contentType);
        return storageService.saveMeme(objectMetadata, memeInputStream);
    }
}
