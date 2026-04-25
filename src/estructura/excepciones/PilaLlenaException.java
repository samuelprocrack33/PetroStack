package estructura.excepciones;

public class PilaLlenaException extends Exception {
    public PilaLlenaException(String mensaje) {
        super(mensaje);
    }
}
