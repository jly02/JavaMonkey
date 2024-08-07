import static org.junit.Assert.assertEquals;
import org.junit.Test;

import javamonkey.lexer.Lexer;
import javamonkey.token.Token;

public class TestLexer {
    @Test
    public void testNextToken() {
        String input = "=+(){},;";
        Lexer l = new Lexer(input);

        Token[] expected = {
            new Token(Token.ASSIGN, "="),
            new Token(Token.PLUS, "+"),
            new Token(Token.LPAREN, "("),
            new Token(Token.RPAREN, ")"),
            new Token(Token.LBRACE, "{"),
            new Token(Token.RBRACE, "}"),
            new Token(Token.COMMA, ","),
            new Token(Token.SEMICOLON, ";"),
            new Token(Token.EOF, "")
        };

        for (Token token : expected) {
            Token next = l.nextToken();
            assertEquals(token.type, next.type);
            assertEquals(token.literal, next.literal);
        }
    }

    @Test 
    public void testNextTokenLargerInput() {
        String input =
        """
        let five = 5 ;
        let ten = 10 ;
        
        let add = fn(x, y) {
            x + y;
        };

        let result = add(five, ten);
        """;

        Lexer l = new Lexer(input);

        Token[] expected = {
            new Token(Token.LET, "let"),
            new Token(Token.IDENT, "five"),
            new Token(Token.ASSIGN, "="),
            new Token(Token.INT, "5"),
            new Token(Token.SEMICOLON, ";"),
            new Token(Token.LET, "let"),
            new Token(Token.IDENT, "ten"),
            new Token(Token.ASSIGN, "="),
            new Token(Token.INT, "10"),
            new Token(Token.SEMICOLON, ";"),
            new Token(Token.LET, "let"),
            new Token(Token.IDENT, "add"),
            new Token(Token.ASSIGN, "="),
            new Token(Token.FUNCTION, "fn"),
            new Token(Token.LPAREN, "("),
            new Token(Token.IDENT, "x"),
            new Token(Token.COMMA, ","),
            new Token(Token.IDENT, "y"),
            new Token(Token.RPAREN, ")"),
            new Token(Token.LBRACE, "{"),
            new Token(Token.IDENT, "x"),
            new Token(Token.PLUS, "+"),
            new Token(Token.IDENT, "y"),
            new Token(Token.SEMICOLON, ";"),
            new Token(Token.RBRACE, "}"),
            new Token(Token.SEMICOLON, ";"),
            new Token(Token.LET, "let"),
            new Token(Token.IDENT, "result"),
            new Token(Token.ASSIGN, "="),
            new Token(Token.IDENT, "add"),
            new Token(Token.LPAREN, "("),
            new Token(Token.IDENT, "five"),
            new Token(Token.COMMA, ","),
            new Token(Token.IDENT, "ten"),
            new Token(Token.RPAREN, ")"),
            new Token(Token.SEMICOLON, ";"),
            new Token(Token.EOF, "")
        };

        for (Token token : expected) {
            Token next = l.nextToken();
            assertEquals(token.type, next.type);
            assertEquals(token.literal, next.literal);
        }
    }

    @Test
    public void testNextTokenGarbledInput() {
        String input =
        """
        let five = 5 ;
        let ten = 10 ;
        
        let add = fn(x, y) {
            x + y;
        };

        let result = add(five, ten);
        !-/*5
        5 < 10 > 5
        """;

        Lexer l = new Lexer(input);

        Token[] expected = {
            new Token(Token.LET, "let"),
            new Token(Token.IDENT, "five"),
            new Token(Token.ASSIGN, "="),
            new Token(Token.INT, "5"),
            new Token(Token.SEMICOLON, ";"),
            new Token(Token.LET, "let"),
            new Token(Token.IDENT, "ten"),
            new Token(Token.ASSIGN, "="),
            new Token(Token.INT, "10"),
            new Token(Token.SEMICOLON, ";"),
            new Token(Token.LET, "let"),
            new Token(Token.IDENT, "add"),
            new Token(Token.ASSIGN, "="),
            new Token(Token.FUNCTION, "fn"),
            new Token(Token.LPAREN, "("),
            new Token(Token.IDENT, "x"),
            new Token(Token.COMMA, ","),
            new Token(Token.IDENT, "y"),
            new Token(Token.RPAREN, ")"),
            new Token(Token.LBRACE, "{"),
            new Token(Token.IDENT, "x"),
            new Token(Token.PLUS, "+"),
            new Token(Token.IDENT, "y"),
            new Token(Token.SEMICOLON, ";"),
            new Token(Token.RBRACE, "}"),
            new Token(Token.SEMICOLON, ";"),
            new Token(Token.LET, "let"),
            new Token(Token.IDENT, "result"),
            new Token(Token.ASSIGN, "="),
            new Token(Token.IDENT, "add"),
            new Token(Token.LPAREN, "("),
            new Token(Token.IDENT, "five"),
            new Token(Token.COMMA, ","),
            new Token(Token.IDENT, "ten"),
            new Token(Token.RPAREN, ")"),
            new Token(Token.SEMICOLON, ";"),
            new Token(Token.BANG, "!"),
            new Token(Token.MINUS, "-"),
            new Token(Token.SLASH, "/"),
            new Token(Token.ASTERISK, "*"),
            new Token(Token.INT, "5"),
            new Token(Token.INT, "5"),
            new Token(Token.LT, "<"),
            new Token(Token.INT, "10"),
            new Token(Token.GT, ">"),
            new Token(Token.INT, "5"),
            new Token(Token.EOF, ""),
        };

        for (Token token : expected) {
            Token next = l.nextToken();
            assertEquals(token.type, next.type);
            assertEquals(token.literal, next.literal);
        }
    }

    @Test
    public void testNextTokenControlFlow() {
        String input =
        """
        let five = 5 ;
        let ten = 10 ;
        
        let add = fn(x, y) {
            x + y;
        };

        let result = add(five, ten);
        !-/*5
        5 < 10 > 5

        if (5 < 10) {
            return true;
        } else {
            return false;
        }

        10 == 10
        10 != 9
        """;

        Lexer l = new Lexer(input);

        Token[] expected = {
            new Token(Token.LET, "let"),
            new Token(Token.IDENT, "five"),
            new Token(Token.ASSIGN, "="),
            new Token(Token.INT, "5"),
            new Token(Token.SEMICOLON, ";"),
            new Token(Token.LET, "let"),
            new Token(Token.IDENT, "ten"),
            new Token(Token.ASSIGN, "="),
            new Token(Token.INT, "10"),
            new Token(Token.SEMICOLON, ";"),
            new Token(Token.LET, "let"),
            new Token(Token.IDENT, "add"),
            new Token(Token.ASSIGN, "="),
            new Token(Token.FUNCTION, "fn"),
            new Token(Token.LPAREN, "("),
            new Token(Token.IDENT, "x"),
            new Token(Token.COMMA, ","),
            new Token(Token.IDENT, "y"),
            new Token(Token.RPAREN, ")"),
            new Token(Token.LBRACE, "{"),
            new Token(Token.IDENT, "x"),
            new Token(Token.PLUS, "+"),
            new Token(Token.IDENT, "y"),
            new Token(Token.SEMICOLON, ";"),
            new Token(Token.RBRACE, "}"),
            new Token(Token.SEMICOLON, ";"),
            new Token(Token.LET, "let"),
            new Token(Token.IDENT, "result"),
            new Token(Token.ASSIGN, "="),
            new Token(Token.IDENT, "add"),
            new Token(Token.LPAREN, "("),
            new Token(Token.IDENT, "five"),
            new Token(Token.COMMA, ","),
            new Token(Token.IDENT, "ten"),
            new Token(Token.RPAREN, ")"),
            new Token(Token.SEMICOLON, ";"),
            new Token(Token.BANG, "!"),
            new Token(Token.MINUS, "-"),
            new Token(Token.SLASH, "/"),
            new Token(Token.ASTERISK, "*"),
            new Token(Token.INT, "5"),
            new Token(Token.INT, "5"),
            new Token(Token.LT, "<"),
            new Token(Token.INT, "10"),
            new Token(Token.GT, ">"),
            new Token(Token.INT, "5"),
            new Token(Token.IF, "if"),
            new Token(Token.LPAREN, "("),
            new Token(Token.INT, "5"),
            new Token(Token.LT, "<"),
            new Token(Token.INT, "10"),
            new Token(Token.RPAREN, ")"),
            new Token(Token.LBRACE, "{"),
            new Token(Token.RETURN, "return"),
            new Token(Token.TRUE, "true"),
            new Token(Token.SEMICOLON, ";"),
            new Token(Token.RBRACE, "}"),
            new Token(Token.ELSE, "else"),
            new Token(Token.LBRACE, "{"),
            new Token(Token.RETURN, "return"),
            new Token(Token.FALSE, "false"),
            new Token(Token.SEMICOLON, ";"),
            new Token(Token.RBRACE, "}"),
            new Token(Token.INT, "10"),
            new Token(Token.EQ, "=="),
            new Token(Token.INT, "10"),
            new Token(Token.INT, "10"),
            new Token(Token.NOT_EQ, "!="),
            new Token(Token.INT, "9"),
            new Token(Token.EOF, ""),
        };

        for (Token token : expected) {
            Token next = l.nextToken();
            assertEquals(token.type, next.type);
            assertEquals(token.literal, next.literal);
        }
    }
}
