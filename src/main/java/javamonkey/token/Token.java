package javamonkey.token;

import java.util.Map;

public class Token {
    // TOKEN TYPES
    public static final String ILLEGAL = "ILLEGAL";
    public static final String EOF     = "EOF";

    // Identifiers and literals
    public static final String IDENT = "IDENT";
    public static final String INT   = "INT";

    // Operators
    public static final String ASSIGN   = "=";
    public static final String PLUS     = "+";
    public static final String MINUS    = "-";
    public static final String BANG     = "!";
    public static final String ASTERISK = "*";
    public static final String SLASH    = "/";

    public static final String LT = "<";
    public static final String GT = ">";

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

    // Special keyword dictionary
    public static final Map<String, String> KEYWORDS = Map.ofEntries(
        Map.entry("fn",  FUNCTION),
        Map.entry("let", LET)
    );

    // Class fields
    public String type;
    public String literal;

    /**
     * Constructs a token
     * 
     * @param type the token type
     * @param literal the token literal
     */
    public Token(String type, String literal) {
        this.type = type;
        this.literal = literal;
    }

    /**
     * Look up identifier token type.
     * 
     * @param ident the identifier name
     * @return the token type
     */
    public static String lookUpIdent(String ident) {
        return KEYWORDS.getOrDefault(ident, IDENT);
    }

    @Override
    public String toString() {
        return "(" + this.type + ", " + this.literal + ")";
    }
}
