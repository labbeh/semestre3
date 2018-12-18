import bsh.Interpreter;
public class Test
{
public static void main(String[] a)
{Interpreter interpreter = new Interpreter();
try
{
interpreter.eval("tab = new int[5]");
interpreter.eval("tab[0] = 6");
interpreter.eval("a = 5");
interpreter.eval("result = (a+tab[0])");
System.out.println( interpreter.get("a") );
System.out.println( interpreter.get("result") );
interpreter.eval("result = (7* (a+2)) > 2");
System.out.println( interpreter.get("result") );
}
catch(Exception e)
{
System.out.println ( "expression invalide" );
}
}
}