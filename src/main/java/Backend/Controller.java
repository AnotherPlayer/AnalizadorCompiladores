/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import UI.Ventana;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.Symbol;

/**
 *
 * @author Jorge
 */
public class Controller {
    // Metodos De Uso Regular.
    private String route = "./src/main/java/Backend/";
    private String[] flexFiles = {route+"LexerCup.flex",route+"lex.flex"};
    private String[] cupParams = {"-parser","Sintax",route+"Sintax.cup"};
    private ArrayList<String> currentOpenFile;
    private Ventana ventana;
    
    public Controller(Ventana v){
        // El controller maneja todos los lexers.
        this.ventana = v;
        try{
            jflex.Main.generate(flexFiles);
            java_cup.Main.main(cupParams);
            moveFiles();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public ArrayList fileRead(String path){
        ArrayList<String> temp = new ArrayList<String>();
        try {
            Runnable runnable = new Runnable(){
                public void run(){
                    try{
                        BufferedReader br = new BufferedReader(new FileReader(path));
                        String line = "";
                        while((line = br.readLine())!=null){
                            temp.add(line);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            };
            Thread fileRead = new Thread(runnable);
            fileRead.run();
            currentOpenFile = temp;
            return temp;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }
    
    public void openWebsite(String url){
        try{
            Desktop.getDesktop().browse(new URL(url).toURI());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void saveResult(String completeResult, String path){
        try {
            Runnable runnable = new Runnable(){
                public void run(){
                    try{
                        FileWriter fw = new FileWriter(path+".txt");
                        fw.write(completeResult);
                        fw.close();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            };
            Thread fileSave = new Thread(runnable);
            fileSave.run();
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    private void moveFiles() {       
        if(Files.exists(Paths.get("sym.java"))){
            try{
                try {
                    Files.move(Paths.get("sym.java"), Paths.get(route + "sym.java"), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("sym.java Moved");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        if (Files.exists(Paths.get("Sintax.java"))){
            try{
                try {
                    Files.move(Paths.get("Sintax.java"), Paths.get(route + "Sintax.java"), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Sintax.java Moved.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }  
    }
    
    public String AnalizadorLexico(String expresion){
        int cont = 1;
        String resultado = "LINEA " + cont + "\t\tSIMBOLO\n";
        try{
            String expr = expresion;
            Lexer lexer = new Lexer(new StringReader(expr));
            while (true) {
                Tokens token = lexer.yylex();
                if (token == null) {
                    return resultado;
                }
                switch (token) {
                    case Linea:
                        cont++;
                        resultado += "LINEA " + cont + "\n";
                        break;
                    case Comillas:
                        resultado += "  <Comillas>\t\t" + lexer.lexeme + "\n";
                        break;
                    case Cadena:
                        resultado += "  <Tipo de dato>\t" + lexer.lexeme + "\n";
                        break;
                    case T_dato:
                        resultado += "  <Tipo de dato>\t" + lexer.lexeme + "\n";
                        break;
                    case If:
                        resultado += "  <Reservada if>\t" + lexer.lexeme + "\n";
                        break;
                    case Else:
                        resultado += "  <Reservada else>\t" + lexer.lexeme + "\n";
                        break;
                    case Do:
                        resultado += "  <Reservada do>\t" + lexer.lexeme + "\n";
                        break;
                    case While:
                        resultado += "  <Reservada while>\t" + lexer.lexeme + "\n";
                        break;
                    case For:
                        resultado += "  <Reservada while>\t" + lexer.lexeme + "\n";
                        break;
                    case Igual:
                        resultado += "  <Operador igual>\t" + lexer.lexeme + "\n";
                        break;
                    case Suma:
                        resultado += "  <Operador suma>\t" + lexer.lexeme + "\n";
                        break;
                    case Resta:
                        resultado += "  <Operador resta>\t" + lexer.lexeme + "\n";
                        break;
                    case Multiplicacion:
                        resultado += "  <Operador multiplicacion>\t" + lexer.lexeme + "\n";
                        break;
                    case Division:
                        resultado += "  <Operador division>\t" + lexer.lexeme + "\n";
                        break;
                    case Op_logico:
                        resultado += "  <Operador logico>\t" + lexer.lexeme + "\n";
                        break;
                    case Op_incremento:
                        resultado += "  <Operador incremento>\t" + lexer.lexeme + "\n";
                        break;
                    case Op_relacional:
                        resultado += "  <Operador relacional>\t" + lexer.lexeme + "\n";
                        break;
                    case Op_atribucion:
                        resultado += "  <Operador atribucion>\t" + lexer.lexeme + "\n";
                        break;
                    case Op_booleano:
                        resultado += "  <Operador booleano>\t" + lexer.lexeme + "\n";
                        break;
                    case Parentesis_a:
                        resultado += "  <Parentesis de apertura>\t" + lexer.lexeme + "\n";
                        break;
                    case Parentesis_c:
                        resultado += "  <Parentesis de cierre>\t" + lexer.lexeme + "\n";
                        break;
                    case Llave_a:
                        resultado += "  <Llave de apertura>\t" + lexer.lexeme + "\n";
                        break;
                    case Llave_c:
                        resultado += "  <Llave de cierre>\t" + lexer.lexeme + "\n";
                        break;
                    case Corchete_a:
                        resultado += "  <Corchete de apertura>\t" + lexer.lexeme + "\n";
                        break;
                    case Corchete_c:
                        resultado += "  <Corchete de cierre>\t" + lexer.lexeme + "\n";
                        break;
                    case Main:
                        resultado += "  <Reservada main>\t" + lexer.lexeme + "\n";
                        break;
                    case P_coma:
                        resultado += "  <Punto y coma>\t" + lexer.lexeme + "\n";
                        break;
                    case Identificador:
                        resultado += "  <Identificador>\t\t" + lexer.lexeme + "\n";
                        break;
                    case Numero:
                        resultado += "  <Numero>\t\t" + lexer.lexeme + "\n";
                        break;
                    case ERROR:
                        resultado += "  <Simbolo no definido>\n";
                        break;
                    default:
                        resultado += "  < " + lexer.lexeme + " >\n";
                        break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public String analizarSintaxis(String texto){
        Sintax s = new Sintax(new LexerCup(new StringReader(texto)));
        String resultado = "";
        try{
            s.parse();
            resultado = "Analisis Correcto";
            return resultado;
        }catch(Exception e){
            Symbol sym = s.getS();
            resultado = "Error de Sintaxis. Linea: " + (sym.right + 1) + " Columna: "+(sym.left + 1);
        }
        return resultado;
    }
}
