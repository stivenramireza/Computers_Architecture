import java.util.HashMap;
/**
 * Esta clase permite identificar el tipo, el segmento y la posición de
 * este en la tabla de símbolos.
 * 
 * @author Stiven Ramírez Arango
 * @version 1.0
 */
public class SymbolInfo {

    private String tipo, segmento;
    private int posicionSegmento;
    
    /**
     * Constructor de la clase SymbolInfo.
     * @param tipo
     * @param segmento
     * @param posicionSegmento
     */
    public SymbolInfo(String tipo, String segmento, int posicionSegmento) {
        this.tipo = tipo;
        this.segmento = segmento;
        this.posicionSegmento = posicionSegmento;
    }

    /**
     * Método que retorna el tipo de símbolo que se va a añadir en la tabla.
     * @return tipo Nombre del tipo de símbolo.
     */
    public String getType() {
        return tipo;
    }

    /**
     * Método que retorna el segmento que se va a añadir en la tabla.
     * @return segmento Nombre del segmento.
     */
    public String getSegment() {
        return segmento;
    }

    /**
     * Método que retorna la posición del segmento que se va a añadir en la tabla.
     * @return posicionSegmento Posición del segmento en la tabla.
     */
    public int getPosition() {
        return posicionSegmento;
    }

    /**
     * Método que imprime la información del símbolo: su tipo, segmento y posición.
     */
    public void imprimirInfo() {
        System.out.println("Tipo: " + tipo + ", Segmento: " + segmento + ", Posición: " + posicionSegmento);
    }
}
