/**
 * Esta clase permite identificar las variables locales en el lenguaje Jack.
 * 
 * @author Stiven Ramírez Arango
 * @version 1.0
 */
public class LocalTable extends SymbolTable {

    int contadorArg, contadorVar;

    /**
     * Constructor de la clase LocalTable.
     */
    public LocalTable() {
        super();
        contadorArg = 0;
        contadorVar = 0;
    }

    /**
     * Método que permite añadir los símbolos a la tabla local según el identificador,
     * el tipo y el segmento al que pertenecen.
     * @param identificador
     * @param tipo
     * @param segmento
     */
    public void agregarSimbolo(String identificador, String tipo, String segmento) {
        if (segmento.equals("argument")) {
            SymbolInfo info = new SymbolInfo(tipo, segmento, contadorArg);
            contadorArg++;
            tablaSimbolos.put(identificador, info);
        } else if (segmento.equals("var")) {
            SymbolInfo info = new SymbolInfo(tipo, segmento, contadorVar);
            contadorVar++;
            tablaSimbolos.put(identificador, info);
        } else {
            System.err.println("ERROR: La variable: " + identificador + " de tipo " + tipo + " del segmento " + segmento + 
                                "no es una variable local.");
            System.exit(1);            
        }
    }

    /**
     * Método que cuenta la cantidad de args existentes en la clase.
     * @return contadorArg Número de args en la tabla local.
     */
    public int numeroArgs() {
        return contadorArg;
    }

    /**
     * Método que cuenta la cantidad de vars existentes en la clase.
     * @return contadorArg Número de vars en la tabla local.
     */
    public int numeroVars() {
        return contadorVar;
    }
}