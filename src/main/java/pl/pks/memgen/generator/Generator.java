package pl.pks.memgen.generator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import pl.pks.memgen.api.Figure;
import pl.pks.memgen.api.Meme;
import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.generator.transformer.FigureTransformer;
import pl.pks.memgen.generator.transformer.TransformerException;
import pl.pks.memgen.io.ImageDownloader;
import pl.pks.memgen.io.UploadedImage;

public class Generator {

    private final ImageDownloader figureDownloader;
    private final StorageService storageService;
    private final FigureTransformer figureTransformer;

    public Generator(ImageDownloader imageDownloader,
                     StorageService storageService,
                     FigureTransformer imageTransformer) {
        this.figureDownloader = imageDownloader;
        this.storageService = storageService;
        this.figureTransformer = imageTransformer;
    }

    public String generate(Meme meme) {
        Figure figure = storageService.findOneMeme(meme.getId());

        try (InputStream input = figureDownloader.getData(figure.getUrl())) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            figureTransformer.transform(meme, input, output);
            byte[] generatedMeme = output.toByteArray();
            ByteArrayInputStream memeInputStream = new ByteArrayInputStream(
                generatedMeme);
            Figure savedImage = saveMeme(figure, generatedMeme.length, memeInputStream);
            return savedImage.getId();
        } catch (IOException | TransformerException e) {
            throw new GeneratorException(e, meme.getId());
        }
    }

    private Meme saveMeme(Figure figure, long contentLength, ByteArrayInputStream memeInputStream) {
        String contentType = storageService.findContentType(figure.getId());
        UploadedImage uploadedImage = new UploadedImage(contentType, contentLength, memeInputStream);
        return storageService.saveMeme(uploadedImage);
    }
}
