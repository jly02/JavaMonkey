package javamonkey.repl;

import java.util.Scanner;

import javamonkey.lexer.Lexer;
import javamonkey.token.Token;

public class Repl {
    public static final String PROMPT = ">> ";
    
    public static void start(Scanner input) {
        String line;
        while (true) {
            System.out.print(PROMPT);
            line = input.nextLine();
            if (line.equals("")) {
                return;
            }

            Lexer l = new Lexer(line);
            Token tok = l.nextToken();
            while (!tok.type.equals(Token.EOF)) {
                System.out.println(tok);
                tok = l.nextToken();
            }
        }
    }
}
