package javamonkey.token;

public class Token {
    // TOKEN TYPES
    public static final String ILLEGAL = "ILLEGAL";
    public static final String EOF     = "EOF";

    // Identifiers and literals
    public static final String IDENT = "IDENT";
    public static final String INT   = "INT";

    // Operators
    public static final String ASSIGN = "=";
    public static final String PLUS   = "+";

    // Delimiters
    public static final String COMMA     = ",";
    public static final String SEMICOLON = ";";

    public static final String LPAREN = "(";
    public static final String RPAREN = ")";
    public static final String LBRACE = "{";
    public static final String RBRACE = "}";

    // Keywords
    public static final String FUNCTION = "FUNCTION";
    public static final String LET      = "LET";

    String type;
    String literal;


}
