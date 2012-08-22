package pl.pks.memgen.io;

import java.io.IOException;
import java.io.InputStream;
import pl.pks.memgen.api.Figure;
import pl.pks.memgen.db.StorageService;
import pl.pks.memgen.io.processor.ImageProcessor;
import com.yammer.dropwizard.logging.Log;

public class FigureUploader {

    private static final Log LOG = Log.forClass(FigureUploader.class);

    private final StorageService storageService;
    private final ImageProcessor imageProcessor;
    private final ImageDownloader imageDownloader;

    public FigureUploader(StorageService storageService,
                          ImageProcessor imageProcessor, ImageDownloader imageDownloader) {
        this.storageService = storageService;
        this.imageProcessor = imageProcessor;
        this.imageDownloader = imageDownloader;
    }

    public Figure fromLink(String url) {
        try {
            String contentType = imageDownloader.getContentType(url);
            long contentLength = imageDownloader.getContentLength(url);
            InputStream dataInputStream = imageDownloader.getData(url);
            UploadedImage uploadedImage = new UploadedImage(contentType, contentLength, dataInputStream);
            return persist(uploadedImage);

        } catch (IllegalArgumentException | IOException e) {
            LOG.error(e.getMessage(), "Could not process {}", url);
            throw new ImageUploadException();
        }
    }

    public Figure fromDisk(InputStream uploadedInputStream, String contentType) {
        try {
            UploadedImage uploadedImage = new UploadedImage(contentType, 0, uploadedInputStream);
            return persist(uploadedImage);
        } catch (IllegalArgumentException e) {
            LOG.error(e, "Could not download the file from a disk");
            throw new ImageUploadException();
        }
    }

    private Figure persist(UploadedImage uploadedImage) {
        UploadedImage processed = imageProcessor.handle(uploadedImage);
        return storageService.saveFigure(processed);
    }

}
