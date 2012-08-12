package pl.pks.memgen.io.processor.handler.downscale;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.Pipe;

public class Im4jDownscaleStrategy implements DownscaleStrategy {

    @Override
    public void downscale(InputStream input, OutputStream output, Integer width, Integer height) {
        Pipe pipe = new Pipe(input, output);
        IMOperation operation = resize(width, height);

        ConvertCmd cmd = new ConvertCmd();
        cmd.setInputProvider(pipe);
        cmd.setOutputConsumer(pipe);
        try {
            cmd.run(operation);
        } catch (IOException | InterruptedException | IM4JavaException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private IMOperation resize(Integer width, Integer height) {
        IMOperation operation = new IMOperation();
        operation.addImage("-");
        operation.resize(width, height);
        operation.addImage("jpg:-");
        return operation;
    }
}
