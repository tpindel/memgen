package pl.pks.memgen.memgenerator.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import pl.pks.memgen.api.Figure;
import pl.pks.memgen.api.Meme;
import pl.pks.memgen.db.FigureStorageService;
import pl.pks.memgen.io.FigureDownloader;
import pl.pks.memgen.memgenerator.FigureTransformer;
import pl.pks.memgen.memgenerator.MemGenerator;
import pl.pks.memgen.memgenerator.MemeGenerationException;
import com.amazonaws.services.s3.model.ObjectMetadata;

public class MemGeneratorImpl implements MemGenerator {

    private final FigureDownloader figureDownloader;
    private final FigureStorageService storageService;
    private final FigureTransformer figureTransformer;

    public MemGeneratorImpl(FigureDownloader imageDownloader, FigureStorageService storageService,
                            FigureTransformer imageTransformer) {
        this.figureDownloader = imageDownloader;
        this.storageService = storageService;
        this.figureTransformer = imageTransformer;
    }

    @Override
    public String generate(Meme meme) {
        Figure figure = storageService.findOne(meme.getId());
        InputStream input = figureDownloader.download(figure.getUrl());
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        figureTransformer.transform(meme, input, output);

        byte[] generatedMeme = output.toByteArray();
        ByteArrayInputStream memeInputStream = new ByteArrayInputStream(
            generatedMeme);

        Figure savedImage = saveMeme(figure, generatedMeme.length, memeInputStream);

        closeStreams(input, output, memeInputStream);
        return savedImage.getUrl();
    }

    private Figure saveMeme(Figure figure, long size, ByteArrayInputStream memeInputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);

        Figure savedImage = storageService.save(figure.getId(), objectMetadata, memeInputStream);
        return savedImage;
    }

    private void closeStreams(InputStream input, ByteArrayOutputStream output, ByteArrayInputStream memeInputStream) {
        try {
            memeInputStream.close();
            output.close();
            input.close();
        } catch (IOException e) {
            throw new MemeGenerationException(e);
        }
    }
}
