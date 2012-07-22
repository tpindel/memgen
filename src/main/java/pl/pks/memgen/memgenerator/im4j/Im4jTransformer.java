package pl.pks.memgen.memgenerator.im4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.Pipe;
import pl.pks.memgen.api.Meme;
import pl.pks.memgen.memgenerator.FigureTransformer;

public class Im4jTransformer implements FigureTransformer {

    @Override
    public void transform(Meme meme, InputStream input, OutputStream output) {
        Pipe pipe = new Pipe(input, output);
        IMOperation operation = prepareMemeGenerationOperation(meme);

        ConvertCmd cmd = new ConvertCmd();
        cmd.setInputProvider(pipe);
        cmd.setOutputConsumer(pipe);
        try {
            cmd.run(operation);
        } catch (IOException | InterruptedException | IM4JavaException e) {
            throw new Im4jTransformException(e);
        }
    }

    private IMOperation prepareMemeGenerationOperation(Meme meme) {
        IMOperation operation = new IMOperation();
        operation.addImage("-");
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
        operation.addImage("jpg:-");
        return operation;
    }

}
