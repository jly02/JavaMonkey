package javamonkey.parser;

import javamonkey.ast.Program;
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

    public Program parse() {
        return null;
    }
}
