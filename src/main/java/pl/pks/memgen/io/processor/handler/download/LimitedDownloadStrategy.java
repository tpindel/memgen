package pl.pks.memgen.io.processor.handler.download;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.io.IOUtils.copy;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.google.common.io.LimitInputStream;

public class LimitedDownloadStrategy implements DownloadStrategy {

    private final long limit;

    public LimitedDownloadStrategy(long limit) {
        checkArgument(limit > 0);
        this.limit = limit;
    }

    @Override
    public long download(InputStream input, OutputStream output) {
        checkNotNull(input);
        checkNotNull(output);
        LimitInputStream limitInputStream = new LimitInputStream(input, limit);
        try {
            return copy(limitInputStream, output);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

}
