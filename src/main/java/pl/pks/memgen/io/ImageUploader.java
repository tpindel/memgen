package pl.pks.memgen.io;

import pl.pks.memgen.api.Meme;

public interface ImageUploader {

    Meme upload(String url);

}
