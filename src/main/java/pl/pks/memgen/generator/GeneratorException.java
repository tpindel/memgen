package pl.pks.memgen.generator;

import javax.ws.rs.WebApplicationException;

@SuppressWarnings("serial")
public class GeneratorException extends WebApplicationException {

    private final String figureId;

    public GeneratorException(Exception e, String figureId) {
        super(e);
        this.figureId = figureId;
    }

    public String getFigureId() {
        return figureId;
    }

}
