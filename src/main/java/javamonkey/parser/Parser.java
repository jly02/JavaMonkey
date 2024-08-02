package javamonkey.parser;

import java.util.ArrayList;
import java.util.List;

import javamonkey.ast.Identifier;
import javamonkey.ast.LetStatement;
import javamonkey.ast.Program;
import javamonkey.ast.Statement;
import javamonkey.lexer.Lexer;
import javamonkey.token.Token;

public class Parser {
    public Lexer l;
    public Token curToken;
    public Token peekToken;
    public List<String> errors;

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

        while (!this.curTokenIs(Token.EOF)) {
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
        LetStatement stmt = new LetStatement(this.curToken, null, null);

        if (!this.expectPeek(Token.IDENT)) {
            return null;
        }

        stmt.name = new Identifier(this.curToken, this.curToken.literal);

        if (!this.expectPeek(Token.ASSIGN)) {
            return null;
        }

        // TODO: For now, skipping expressions until reaching semicolon.
        while (!this.curTokenIs(Token.SEMICOLON)) {
            this.nextToken();
        }

        return stmt;
    }

    // Helper method that simplifies long .equals() function call.
    // Checks that the current token is type t.
    private boolean curTokenIs(String t) {
        return this.curToken.type.equals(t);
    }

    // Helper method that simplifies long .equals() function call.
    // Checks that the peek token is type t.
    private boolean peekTokenIs(String t) {
        return this.peekToken.type.equals(t);
    }

    // Helper method that advances the parser if the peek token 
    // matches expected token type.
    private boolean expectPeek(String t) {
        if (this.peekTokenIs(t)) {
            this.nextToken();
            return true;
        } else {
            return false;
        }
    }
}
