package pl.pks.memgen.memgenerator.impl;

import java.io.IOException;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import pl.pks.memgen.api.Meme;
import pl.pks.memgen.api.Figure;
import pl.pks.memgen.db.FigureStorageService;
import pl.pks.memgen.io.ImageDownloader;
import pl.pks.memgen.memgenerator.MemGenerator;

public class MemGeneratorImpl implements MemGenerator {

    private final ImageDownloader imageDownloader;
    private final FigureStorageService storageService;

    public MemGeneratorImpl(ImageDownloader imageDownloader, FigureStorageService storageService) {
        this.imageDownloader = imageDownloader;
        this.storageService = storageService;
    }

    @Override
    public String generate(Meme meme) {
        Figure figure = storageService.findOne(meme.getId());
        String imagePath = imageDownloader.download(figure.getUrl(), figure.getId());

        IMOperation operation = new IMOperation();
        operation.addImage(imagePath);
        operation.gravity("north");
        operation.font("Comic Sans MS");
        operation.fill("white");
        operation.pointsize(50);
        operation.annotate(0, 0, 0, 0, meme.getTopTitle());
        operation.gravity("south");
        operation.font("Comic Sans MS");
        operation.fill("white");
        operation.pointsize(50);
        operation.annotate(0, 0, 0, 0, meme.getBottomTitle());
        operation.addImage("/tmp/generatedmeme.jpg");

        ConvertCmd cmd = new ConvertCmd();
        try {
            cmd.run(operation);
        } catch (IOException | InterruptedException | IM4JavaException e) {
            throw new RuntimeException(e);
        }

        // TODO: persist in amazon storageService.save(meme.getUrl(), objectMetadata, inputStream);

        return imagePath;
    }

}
