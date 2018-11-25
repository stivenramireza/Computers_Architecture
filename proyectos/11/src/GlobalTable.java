/**
 * Esta clase permite identificar las variables globales en el lenguaje Jack.
 * 
 * @author Stiven Ramírez Arango
 * @version 1.0
 */
public class GlobalTable extends SymbolTable {

    int contadorField, contadorStatic;

    /**
     * Constructor de la clase GlobalTable.
     */
    public GlobalTable() {
        super();
        contadorField = 0;
        contadorStatic = 0;
    }

    /**
     * Método que permite añadir los símbolos a la tabla global según el identificador,
     * el tipo y el segmento al que pertenecen.
     * @param identificador
     * @param tipo
     * @param segmento
     */
    public void agregarSimbolo(String identificador, String tipo, String segmento) {
        if (segmento.equals("field")) {
            SymbolInfo info = new SymbolInfo(tipo, segmento, contadorField);
            contadorField++;
            tablaSimbolos.put(identificador, info);
        } else if (segmento.equals("static")) {
            SymbolInfo info = new SymbolInfo(tipo, segmento, contadorStatic);
            contadorStatic++;
            tablaSimbolos.put(identificador, info);
        } else {
            System.err.println("ERROR: La variable: " + identificador + " de tipo " + tipo + " del segmento " + segmento + 
                                "no es una variable global.");
            System.exit(1);            
        }
    }

    /**
     * Método que cuenta la cantidad de fields existentes en la clase.
     * @return contadorField Número de fields en la tabla global.
     */
    public int numeroFields() {
        return contadorField;
    }

    /**
     * Método que cuenta la cantidad de static existentes en la clase.
     * @return contadorField Número de static en la tabla global.
     */
    public int numeroStatic() {
        return contadorStatic;
    }
}