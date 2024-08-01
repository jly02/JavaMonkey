package javamonkey.parser;

import java.util.ArrayList;

import javamonkey.ast.Program;
import javamonkey.ast.Statement;
import javamonkey.lexer.Lexer;
import javamonkey.token.Token;

public class Parser {
    public Lexer l;
    public Token curToken;
    public Token peekToken;

    public Parser(Lexer l) {
        this.l = l;
        this.nextToken();
        this.nextToken();
    }

    public final void nextToken() {
        this.curToken = this.peekToken;
        this.peekToken = this.l.nextToken();
    }

    /**
     * Parse an the input string defined by the lexer in this object.
     * 
     * @return an AST root Program, the parsed program
     */
    public Program parse() {
        Program p = new Program();
        p.statements = new ArrayList<>();

        while (!this.curToken.type.equals(Token.EOF)) {
            Statement stmt = this.parseStatement();
            if (stmt != null) {
                p.statements.add(stmt);
            }

            this.nextToken();
        }

        return p;
    }

    // Helper method to parse statements.
    private Statement parseStatement() {
        return switch (this.curToken.type) {
            case Token.LET -> this.parseLetStatement();
            default -> null;
        };
    }

    // Helper method to parse let statements.
    private Statement parseLetStatement() {
        // TODO: implement this method (bottom p. 40)
        return null;
    }
}
