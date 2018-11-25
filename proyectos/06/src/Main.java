import java.io.*;

 public class Main{
	private static String comp, dest, jump;
 	private static int counter = 0;
 	private static int posRAM = 16;
 	
 	public static void main(String args[]){
 		if(args.length !=1){ 
 			System.err.println("Parametro invalido");
		}else{
			String fileName= args[0].substring(0,args[0].indexOf('.'));
			String FileOut = fileName + ".hack";
			SymbolTable symbolTable = new SymbolTable();
			Code binary = new Code();
			Parser parser = new Parser(args[0]);
	    	File out = new File(FileOut);
	    	FileWriter filew = null;
	    try {
		  filew = new FileWriter(out.getAbsoluteFile());
	    } catch (IOException e) {
		  e.printStackTrace();
	    }
	    BufferedWriter bufw = new BufferedWriter(filew);

        // Se hace la primera pasada al codigo Assembler verificando si
        // hay etiquetas para añadirlas a la tabla de simbolos
	    while(parser.hasMoreCommands()){
	    	if(parser.commandType() == Parser.commandType.L_COMMAND){
	    		String label = parser.symbol();
	    		symbolTable.addEntry(label, Integer.toString(counter));
	    	}else{
	    		counter++;
	    	}
	    	parser.advance();
	    }
		parser.reset();
		
		// Se hace la segunda pasada al codigo Assembler para comenzar
		// a traducir las instrucciones a binario
	    while(parser.hasMoreCommands()){
	    	if(parser.commandType() == Parser.commandType.A_COMMAND){
	    		if(parser.array[parser.counterLines].startsWith("@")){
	    			String tmp =parser.symbol();
	    			if(parser.checkN(tmp)){
	    				int number = Integer.parseInt(tmp);
	    				if(number < 0){
	    					System.out.println("ERROR: No numeros negativos");
	    					System.exit(0);
	    				}
	    				tmp = parser.convertToBinary(number);
	    				tmp = parser.addZeros(tmp);
	    				try{
							bufw.write(tmp + '\n');
	    				}catch(IOException e){
	    					e.printStackTrace();
	    				}
	    			}else{
	    			// Se verifica si estan en la tabla de simbolos
	    			if(!symbolTable.contains(tmp)){
	    				symbolTable.addEntry(tmp, Integer.toString(posRAM));
	    				posRAM++;
	    			}
	    			if(symbolTable.contains(tmp)){
	    				String temp2 = symbolTable.getAddress(tmp);
	    				int number = Integer.parseInt(temp2);
	    				temp2=parser.convertToBinary(number);
	    				temp2=parser.addZeros(temp2);
	    				try{
	    					bufw.write(temp2 + '\n');
	    				}catch(IOException e){
	    					e.printStackTrace();
	    				}

	    			}
	    			}
	    		}
	    	}

	    	if(parser.commandType()== Parser.commandType.C_COMMAND){
	    		if(parser.array[parser.counterLines].contains("=")){
	    			dest=binary.getDest(parser.dest());
	    			comp=binary.getComp(parser.comp());
	    			jump=binary.getJump("NULL");
	    			if(dest == null || jump == null){
	    				System.out.println("ERROR: Null en el dest y jump");
			    		System.exit(0);
	    			}try{
						bufw.write("111"+ comp + dest + jump + '\n');
	    			}catch(IOException e){
	    				e.printStackTrace();
	    			}
	    		}else if(parser.array[parser.counterLines].contains(";")){
	    			dest = binary.getDest("NULL");
					comp = binary.getComp(parser.comp());
					jump = binary.getJump(parser.jump());
					if (dest == null || comp == null) {
			    		System.out.println("ERROR: Null en el dest y comp");
			    		System.exit(0);
					}try{
						bufw.write("111"+ comp + dest + jump + '\n');
					}catch(IOException e){
						e.printStackTrace();
					}

	    		}
	    	}
	    	parser.advance();
		}
		parser.reset();
		try{
	    	bufw.close();
	    }catch(IOException e){
	    	e.printStackTrace();
	    }

 	}
 }
}