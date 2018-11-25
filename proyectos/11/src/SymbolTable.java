import java.util.HashMap;
/**
 * Esta clase permite crear la tabla de símbolos.
 * 
 * @author Stiven Ramírez Arango
 * @version 1.0
 */
public abstract class SymbolTable {

    protected HashMap<String, SymbolInfo> tablaSimbolos;
    private static final HashMap<String, String> segmentosCorrelacionados = new HashMap<String, String>();

    static {
        segmentosCorrelacionados.put("field", "this");
        segmentosCorrelacionados.put("static", "static");
        segmentosCorrelacionados.put("var", "local");
        segmentosCorrelacionados.put("argument", "argument");
    }

    /**
     * Constructor de la clase SymbolTable.
     */
    public SymbolTable() {
        tablaSimbolos = new HashMap<String, SymbolInfo>();
    }

    /**
     * Método abstracto que permite agregar símbolos en la tabla a medida que recorre
     * el archivo .jack y lo va clasificando si es global o si es local.
     * @param identificador
     * @param tipo
     * @param segmento
     */
    public abstract void agregarSimbolo(String identificador, String tipo, String segmento);

    /**
     * Método que verifica si el identificador ingresado está en la tabla de símbolos o no.
     * @param identificador
     * @return True si lo encuentra, de lo contrario False.
     */
    public boolean contains(String identificador) {
        return tablaSimbolos.containsKey(identificador);
    }

    /**
     * Método que obtiene el segmento del lenguaje de máquina virtual a partir de su identificador
     * y verifica si este se encuentra correlacionado con otro segmento de memoria.
     * @param identificador
     * @return VMSegment Segmento de memoria correlacionado.
     */
    public String getVMSegmento(String identificador) {
        SymbolInfo info = tablaSimbolos.get(identificador);
        String segmento = info.getSegment();
        String VMSegment = segmentosCorrelacionados.get(segmento);
        if (VMSegment == null) {
            System.err.println("ERROR: El segmento de memoria no existe.");
            System.exit(1);
        }
        return VMSegment;
    }

    /**
     * Método que permite obtener la posición del identificador en la tabla de símbolos.
     * @param identificador
     * @return position Posición obtenida en la tabla de símbolos.
     */
    public int getPosition(String identificador) {
        SymbolInfo info = tablaSimbolos.get(identificador);
        int position = info.getPosition();
        return position;
    }

    /**
     * Método que verifica si el identificador ingresado es una variable primitiva o no.
     * @param identificador
     * @return True si es una variable primitiva, de lo contrario False.
     */
    public boolean esPrimitivo(String identificador) {
        SymbolInfo info = tablaSimbolos.get(identificador);
        String tipo = info.getType();
        if (tipo.equals("int") || tipo.equals("boolean") || tipo.equals("char")) {
            return true;
        }
        return false;
    }

    /**
     * Método que permite obtener el tipo de identificador que se encuentra en la tabla
     * de símbolos.
     * @param identificador
     * @return tipo Nombre del tipo de identificador.
     */
    public String getType(String identificador) {
        SymbolInfo info = tablaSimbolos.get(identificador);
        String tipo = info.getType();
        return tipo;
    }

    /**
     * Método que recorre toda la tabla de símbolos y la imprime con
     * su respectiva información de tipo, segmento y posición.
     */
    public void printTable() {
        for (HashMap.Entry<String, SymbolInfo> entry: tablaSimbolos.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            entry.getValue().imprimirInfo();
        }
    }
}