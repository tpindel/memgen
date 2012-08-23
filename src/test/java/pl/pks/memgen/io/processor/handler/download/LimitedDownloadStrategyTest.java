package pl.pks.memgen.io.processor.handler.download;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class LimitedDownloadStrategyTest {

    private static final long MAX_FILE_SIZE = 1024l;
    private DownloadStrategy strategy = new LimitedDownloadStrategy(MAX_FILE_SIZE);

    @Test
    public void shouldNotExceedLimit() {
        // given
        // phiolosoraptor.jpg has 19.3kB
        final InputStream TOO_BIG_FILE_STREAM = getClass().getClassLoader().getResourceAsStream("philosoraptor.jpg");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        // when
        long numberOfCopiedBytes = strategy.download(TOO_BIG_FILE_STREAM, output);
        // then
        assertThat(numberOfCopiedBytes).isLessThanOrEqualTo(MAX_FILE_SIZE);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRaiseExceptionIfNullInputGiven() {
        final InputStream NULL_INPUT_STREAM = null;
        final ByteArrayOutputStream output = new ByteArrayOutputStream();

        strategy.download(NULL_INPUT_STREAM, output);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRaiseExceptionIfNullOutputGiven() {
        final OutputStream NULL_OUTPUT_STREAM = null;
        final InputStream input = IOUtils.toInputStream("test input");

        strategy.download(input, NULL_OUTPUT_STREAM);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateIfNonNegativeLimit() {
        new LimitedDownloadStrategy(0l);
    }
}
