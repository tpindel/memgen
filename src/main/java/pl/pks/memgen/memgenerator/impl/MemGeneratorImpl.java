package pl.pks.memgen.memgenerator.impl;

import java.io.IOException;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import pl.pks.memgen.api.CaptionedMeme;
import pl.pks.memgen.api.Meme;
import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.io.ImageDownloader;
import pl.pks.memgen.memgenerator.MemGenerator;

public class MemGeneratorImpl implements MemGenerator {

    private final ImageDownloader imageDownloader;
    private final StorageService storageService;

    public MemGeneratorImpl(ImageDownloader imageDownloader, StorageService storageService) {
        this.imageDownloader = imageDownloader;
        this.storageService = storageService;
    }

    @Override
    public String generate(CaptionedMeme captionedMeme) {
        Meme meme = storageService.findOne(captionedMeme.getId());
        String imagePath = imageDownloader.download(meme.getUrl(), meme.getId());

        IMOperation operation = new IMOperation();
        operation.addImage(imagePath);
        operation.gravity("north");
        operation.font("Comic Sans MS");
        operation.fill("white");
        operation.pointsize(50);
        operation.annotate(0, 0, 0, 0, captionedMeme.getTopTitle());
        operation.gravity("south");
        operation.font("Comic Sans MS");
        operation.fill("white");
        operation.pointsize(50);
        operation.annotate(0, 0, 0, 0, captionedMeme.getBottomTitle());
        String generatedMemPath = "/tmp/" + captionedMeme.getId();
        operation.addImage(generatedMemPath);

        ConvertCmd cmd = new ConvertCmd();
        try {
            cmd.run(operation);
        } catch (IOException | InterruptedException | IM4JavaException e) {
            throw new RuntimeException(e);
        }

        return captionedMeme.getId();
    }

}
