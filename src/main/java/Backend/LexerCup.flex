package Backend;
import java_cup.runtime.Symbol;
%%
%class LexerCup
%type java_cup.runtime.Symbol
%cup
%full
%line
%char
L=[a-zA-Z_]+
D=[0-9]+
espacio=[ ,\t,\r,\n]+
%{
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
%}
%%

/* Espacios en blanco */
{espacio} {/*Ignore*/}
/* Comentarios */
( "//"(.)* ) {/*Ignore*/}
/* Comillas */
( "\"" ) {return new Symbol(sym.Comillas,(int) yychar, yyline, yytext());}
/* Tipos de datos */
( byte | char | long | float | double ) {return new Symbol(sym.T_dato, (int)yychar, yyline, yytext());}
/* Tipo de dato Int (Para el main) */
( "int" ) {return new Symbol(sym.Int,(int) yychar, yyline, yytext());}

/* Tipo de dato String */
( String ) {return new Symbol(sym.Cadena,(int) yychar, yyline, yytext());}
/* Palabra reservada If */
( if ) {return new Symbol(sym.If, (int)yychar, yyline, yytext());}
/* Palabra reservada Else */
( else ) {return new Symbol(sym.Else, (int)yychar, yyline, yytext());}
/* Palabra reservada Do */
( do ) {return new Symbol(sym.Do,(int) yychar, yyline, yytext());}
/* Palabra reservada While */
( while ) {return new Symbol(sym.While, (int)yychar, yyline, yytext());}
/* Palabra reservada For */
( for ) {return new Symbol(sym.For, (int)yychar, yyline, yytext());}
/* Operador Igual */
( "=" ) {return new Symbol(sym.Igual, (int)yychar, yyline, yytext());}
/* Operador Suma */
( "+" ) {return new Symbol(sym.Suma, (int)yychar, yyline, yytext());}
/* Operador Resta */
( "-" ) {return new Symbol(sym.Resta, (int)yychar, yyline, yytext());}
/* Operador Multiplicacion */
( "*" ) {return new Symbol(sym.Multiplicacion, (int)yychar, yyline, yytext());}
/* Operador Division */
( "/" ) {return new Symbol(sym.Division, (int)yychar, yyline, yytext());}
/* Operadores logicos */
( "&&" | "||" | "!" | "&" | "|" ) {return new Symbol(sym.Op_logico,(int) yychar, yyline, yytext());}
/*Operadores Relacionales */
( ">" | "<" | "==" | "!=" | ">=" | "<=" | "<<" | ">>" ) {return new Symbol(sym.Op_relacional, (int)yychar, yyline, yytext());}
/* Operadores Atribucion */
( "+=" | "-="  | "*=" | "/=" | "%=" | "=" ) {return new Symbol(sym.Op_atribucion,(int) yychar, yyline, yytext());}
/* Operadores Incremento y decremento */
( "++" | "--" ) {return new Symbol(sym.Op_incremento,(int) yychar, yyline, yytext());}
/*Operadores Booleanos*/
( true | false ) {return new Symbol(sym.Op_booleano, (int)yychar, yyline, yytext());}
/* Parentesis de apertura */
( "(" ) {return new Symbol(sym.Parentesis_a, (int)yychar, yyline, yytext());}
/* Parentesis de cierre */
( ")" ) {return new Symbol(sym.Parentesis_c, (int)yychar, yyline, yytext());}
/* Llave de apertura */
( "{" ) {return new Symbol(sym.Llave_a,(int) yychar, yyline, yytext());}
/* Llave de cierre */
( "}" ) {return new Symbol(sym.Llave_c, (int)yychar, yyline, yytext());}
/* Corchete de apertura */
( "[" ) {return new Symbol(sym.Corchete_a, (int)yychar, yyline, yytext());}
/* Corchete de cierre */
( "]" ) {return new Symbol(sym.Corchete_c, (int)yychar, yyline, yytext());}
/* Marcador de inicio de algoritmo */
( "main" ) {return new Symbol(sym.Main, (int)yychar, yyline, yytext());}
/* Punto y coma */
( ";" ) {return new Symbol(sym.P_coma, (int)yychar, yyline, yytext());}

/* Identificador */
{L}({L}|{D})* {return new Symbol(sym.Identificador, (int)yychar, yyline, yytext());}
/* Numero */
("(-"{D}+")")|{D}+ {return new Symbol(sym.Numero, (int)yychar, yyline, yytext());}
/* Error de analisis */
 . {return new Symbol(sym.ERROR, (int)yychar, yyline, yytext());}