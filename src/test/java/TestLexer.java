import org.junit.Assert;
import org.junit.Test;

import javamonkey.lexer.Lexer;
import javamonkey.token.Token;

public class TestLexer {
    @Test
    public void TestNextToken() {
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
            Assert.assertEquals(token.type, next.type);
            Assert.assertEquals(token.literal, next.literal);
        }
    }

    @Test 
    public void TestNextTokenLargerInput() {
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
            Assert.assertEquals(token.type, next.type);
            Assert.assertEquals(token.literal, next.literal);
        }
    }
}
