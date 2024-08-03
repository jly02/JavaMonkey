package javamonkey.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javamonkey.ast.Expression;
import javamonkey.ast.ExpressionStatement;
import javamonkey.ast.Identifier;
import javamonkey.ast.LetStatement;
import javamonkey.ast.Program;
import javamonkey.ast.ReturnStatement;
import javamonkey.ast.Statement;
import javamonkey.lexer.Lexer;
import javamonkey.token.Token;

public class Parser {
    // Precedence constants
    public static final int LOWEST = 1;
    public static final int EQUALS = 2; // ==
    public static final int LTGT   = 3; // < or >
    public static final int SUM    = 4; // +
    public static final int PROD   = 5; // *
    public static final int PREFIX = 6; // -x or !x
    public static final int CALL   = 7; // foo(x)

    // Class fields
    public Lexer l;
    public Token curToken;
    public Token peekToken;
    private final List<String> errors;
    private final Map<String, PrefixParseFn> prefixParseFns;
    private final Map<String, InfixParseFn> infixParseFns;

    /**
     * Constructs a new parser object from a given lexer.
     * 
     * @param l the lexer (with input) to be parsed from
     */
    public Parser(Lexer l) {
        this.l = l;
        this.nextToken();
        this.nextToken();
        this.errors = new ArrayList<>();
        this.prefixParseFns = new HashMap<>();
        this.infixParseFns = new HashMap<>();

        // Register prefixes
        this.registerPrefix(Token.IDENT, () -> { return this.parseIdentifier(); });
    }

    /**
     * Advances the current and peek token.
     */
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

    /**
     * Provides a list of errors that have occured during parsing.
     * 
     * @return a list containing all errors messages accumulated by the parser
     */
    public List<String> errors() {
        return this.errors;
    }

    // Helper method for adding peek-related errors to the parser errors.
    private void peekError(String t) {
        String msg = "expected token <" + t + "> but got <" + this.peekToken.type + ">";
        this.errors.add(msg);
    }

    // Helper method to parse statements.
    private Statement parseStatement() {
        return switch (this.curToken.type) {
            case Token.LET -> this.parseLetStatement();
            case Token.RETURN -> this.parseReturnStatement();
            default -> this.parseExpressionStatement();
        };
    }

    // Helper method to parse let statements.
    private LetStatement parseLetStatement() {
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

    // Helper method to parse return statements.
    private ReturnStatement parseReturnStatement() {
        ReturnStatement stmt = new ReturnStatement(this.curToken, null);

        this.nextToken();

        // TODO: For now, skipping expressions until reaching semicolon.
        while (!this.curTokenIs(Token.SEMICOLON)) {
            this.nextToken();
        }

        return stmt;
    }

    // Helper method to parse expression statements.
    private ExpressionStatement parseExpressionStatement() {
        ExpressionStatement stmt = new ExpressionStatement(this.curToken, null);

        stmt.expr = parseExpression(LOWEST);

        if (this.peekTokenIs(Token.SEMICOLON)) {
            this.nextToken();
        }

        return stmt;
    }

    // Helper method to parse expressions.
    private Expression parseExpression(int precedence) {
        PrefixParseFn prefix = this.prefixParseFns.getOrDefault(this.curToken.type, null);
        if (prefix == null) {
            return null;
        }

        Expression leftExpr = prefix.parse();
        return leftExpr;
    }

    // Helper method to parse identifiers.
    private Expression parseIdentifier() {
        return new Identifier(this.curToken, this.curToken.literal);
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
            this.peekError(t);
            return false;
        }
    }

    // Helper method for registering a prefix token parser entry.
    private void registerPrefix(String tokenType, PrefixParseFn fn) {
        this.prefixParseFns.put(tokenType, fn);
    }

    // Helper method for registering an infix token parser entry.
    private void registerInfix(String tokenType, InfixParseFn fn) {
        this.infixParseFns.put(tokenType, fn);
    }
}

@FunctionalInterface
interface PrefixParseFn {
    Expression parse();
}

@FunctionalInterface
interface InfixParseFn {
    Expression parse(Expression left);
}
