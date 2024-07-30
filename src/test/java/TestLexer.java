import org.junit.Assert;
import org.junit.Test;

import javamonkey.lexer.Lexer;
import javamonkey.lexer.Lexer.TokTuple;
import javamonkey.token.Token;

public class TestLexer {
    @Test
    void LexRandomString() {
        String input = "=+(){},;";
        Lexer l = new Lexer(input);

        TokTuple[] expected = {
            l.new TokTuple(Token.ASSIGN, "="),
            l.new TokTuple(Token.PLUS, "+"),
            l.new TokTuple(Token.LPAREN, "("),
            l.new TokTuple(Token.RPAREN, ")"),
            l.new TokTuple(Token.LBRACE, "{"),
            l.new TokTuple(Token.RBRACE, "}"),
            l.new TokTuple(Token.COMMA, ","),
            l.new TokTuple(Token.SEMICOLON, ";"),
            l.new TokTuple(Token.EOF, "EOF")
        };

        for (TokTuple token : expected) {
            TokTuple next = l.nextToken();
            Assert.assertEquals(token.type, next.type);
            Assert.assertEquals(token.lit, next.lit);
        }
    }
}
