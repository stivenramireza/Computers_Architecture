import java.io.*;
import java.text.*;

public class Parser{

    public BufferedReader readFile;
	public commandType comType;
	public int nextMemory = 16;
	public String strLine;
	public String array[];
	public int counterLines;

    public enum commandType{
		A_COMMAND,L_COMMAND,C_COMMAND
	}

	public Parser(String file){
		counterLines=0;
		FileInputStream input= null;
		try{
			input = new FileInputStream(file);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}try{
			int content = 0 ;
			while((content = input.read()) != -1){
				strLine+=(char) content;
			}
		}catch(IOException e){
			e.printStackTrace();
		}

		DataInputStream in = new DataInputStream(input);
		readFile=new BufferedReader(new InputStreamReader(in));

        // Se eliminan los comentarios por cada linea
		strLine = removeComments(strLine);
		array = strLine.split("\n");

		// Se eliminan los espacios en blanco por cada linea
		for(int i=0;i<array.length;++i){
			array[i]=array[i].trim();
		}
    }
    
	// Se verifica si hay mas comandos en la entrada
	public boolean hasMoreCommands(){
		if(counterLines != array.length){
			return true;
		}else{
			return false;
		}
	}
	public void reset(){
		counterLines=0;
	}
	public void advance(){
		counterLines++;
    }

	// Se determina el tipo de instruccion
	public commandType commandType(){
		if(array[counterLines].startsWith("(")){
			return comType = commandType.L_COMMAND;
		}else if (array[counterLines].startsWith("@")){
			return comType = commandType.A_COMMAND;
		}else{
			return commandType.C_COMMAND;	
		}
	}

	public String symbol(){
		String etiqueta = "";
		if(array[counterLines].startsWith("@")){
			etiqueta = array[counterLines];
			etiqueta = etiqueta.replaceAll("@", "");

		}else if (array[counterLines].startsWith("(")){
			etiqueta = array[counterLines];
	        etiqueta = etiqueta.replaceAll("\\((.*?)\\)", "$1");
		}
		return etiqueta;
	}

    // Retorna el dest
	public String dest(){
		if(array[counterLines].contains("=")) {
	    	String dest = array[counterLines];
	    	int temp = dest.lastIndexOf("=");
	    	dest = dest.substring(0, temp);
	   	 	return dest;
		}
		return null;
    }
    
    // Retorna el comp
	public String comp() {
	String comp = array[counterLines];
	if(array[counterLines].contains("=")) {
	    int temp = comp.lastIndexOf("=");
	    comp = comp.substring(temp+1, comp.length());
	}else if(array[counterLines].contains(";")) {
	    int temp = comp.lastIndexOf(";");
	    comp = comp.substring(0, temp);
	}
	return comp;
    }

    // Retorna el salto de instruccio
	public String jump(){
		if(array[counterLines].contains(";")){
			String jump = array[counterLines];
	    	int temp = jump.lastIndexOf(";");
	        return jump.substring(temp+1, jump.length());

		}
		return null;
	}

	// Se eliminan todos los comentarios del file con una expresion regular
	public String removeComments(String file){
		String temp = file.replaceAll("//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/|(?m)^[ \t]*\r?\n|null|\t", "");
		temp = temp.replaceAll("(?m)^[ \t]*\r?\n", "");
		return temp;
	}

    // Si se recibe un @# o @etiqueta o @variable, se reemplaza lo que hay
    // despues del arroba por el valor en codigo binario
	public String convertToBinary(int valor){
		String binary = Integer.toBinaryString(valor);
		return binary;
	}

    // Mirar si el numero ingresado corresponde a un llamado al registro A
    // o a una variable
	public boolean checkN(String number){
		NumberFormat nmft = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		nmft.parse(number,pos);
		if(number.length()==pos.getIndex()){
			return true;
		}else{
			return false;
		}
	}

    // Poner ceros a la izquierda de los bits
	public String addZeros(String number){
		StringBuilder stb = new StringBuilder();
		for(int i = 16-number.length();i>0;i--){
			stb.append('0');
		}
		stb.append(number);
		String result = stb.toString();
		return result;
	}

}