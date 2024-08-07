package javamonkey.lexer;

import javamonkey.token.Token;

public class Lexer {
    // Class fields
    public String input;
    public int position;
    public int readPosition;
    public char ch;

    /**
     * Constructs a new lexer object.
     * 
     * @param input the input string
     */
    public Lexer(String input) {
        this.input = input;
        this.readPosition = 0;
        this.readChar();
    }

    /**
     * Reads the next token in the input string.
     * 
     * @return a Token containing the next token type and literal
     */
    public Token nextToken() {
        this.skipWhiteSpace();

        boolean numOrIdent = false;
        String lit = Character.toString(ch);
        Token tok = switch (ch) {
            case '=' -> {
                // check if just '=' or '=='
                if (this.peekChar() == '=') {
                    char chr = this.ch;
                    this.readChar();
                    lit = Character.toString(chr) + this.ch;
                    yield new Token(Token.EQ, lit);
                } else {
                    yield new Token(Token.ASSIGN, lit);
                }
            }
            case '!' -> {
                // check if just '!' or '!='
                if (this.peekChar() == '=') {
                    char chr = this.ch;
                    this.readChar();
                    lit = Character.toString(chr) + this.ch;
                    yield new Token(Token.NOT_EQ, lit);
                } else {
                    yield new Token(Token.BANG, lit);
                }
            }
            case '+' -> new Token(Token.PLUS, lit);
            case '-' -> new Token(Token.MINUS, lit);
            case '/' -> new Token(Token.SLASH, lit);
            case '*' -> new Token(Token.ASTERISK, lit);
            case '<' -> new Token(Token.LT, lit);
            case '>' -> new Token(Token.GT, lit);
            case ';' -> new Token(Token.SEMICOLON, lit);
            case '(' -> new Token(Token.LPAREN, lit);
            case ')' -> new Token(Token.RPAREN, lit);
            case ',' -> new Token(Token.COMMA, lit);
            case '{' -> new Token(Token.LBRACE, lit);
            case '}' -> new Token(Token.RBRACE, lit);
            case 0 -> new Token(Token.EOF, "");
            default -> {
                if (Character.isLetter(ch)) {
                    lit = this.readIdentifier();
                    String type = Token.lookUpIdent(lit);
                    numOrIdent = true;
                    yield new Token(type, lit);
                } else if (Character.isDigit(ch)) {
                    lit = this.readNumber();
                    numOrIdent = true;
                    yield new Token(Token.INT, lit);
                } else {
                    yield new Token(Token.ILLEGAL, lit);
                }
            }
        };

        // do not advance lexer if just read identifier or number
        if (!numOrIdent) this.readChar();
        return tok;
    }

    // Helper method for reading the next character
    // in the input string.
    private void readChar() {
        if (readPosition >= input.length()) {
            this.ch = 0;
        } else {
            this.ch = input.charAt(readPosition);
        }

        this.position = readPosition;
        this.readPosition++;
    }

    // Helper method for reading the next character
    // without advancing the lexer.
    private char peekChar() {
        if (readPosition >= input.length()) {
            return 0;
        } else {
            return input.charAt(readPosition);
        }
    }

    // Helper method for reading general identifiers.
    private String readIdentifier() {
        int pos = this.position;
        while (Character.isLetter(ch)) {
            this.readChar();
        }

        return input.substring(pos, this.position);
    }

    // Helper method for reading numbers.
    private String readNumber() {
        int pos = this.position;
        while (Character.isDigit(ch)) {
            this.readChar();
        }

        return input.substring(pos, this.position);
    }

    // Helper method to eat whitespace.
    private void skipWhiteSpace() {
        while (Character.isWhitespace(ch)) {
            this.readChar();
        }
    }
}
