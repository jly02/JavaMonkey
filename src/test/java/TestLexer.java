import org.junit.Assert;
import org.junit.Test;

import javamonkey.lexer.Lexer;
import javamonkey.token.Token;

public class TestLexer {
    @Test
    public void LexRandomString() {
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
            new Token(Token.EOF, "EOF")
        };

        for (Token token : expected) {
            Token next = l.nextToken();
            Assert.assertEquals(token.type, next.type);
            Assert.assertEquals(token.literal, next.literal);
        }
    }
}
