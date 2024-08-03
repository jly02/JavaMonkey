import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import javamonkey.ast.Identifier;
import javamonkey.ast.LetStatement;
import javamonkey.ast.Program;
import javamonkey.token.Token;

public class TestAst {
    @Test
    public void testString() {
        Program prog = new Program();

        // AST for `let myVar = anotherVar;`
        prog.statements = List.of(
            new LetStatement(
                new Token(Token.LET, "let"), 
                new Identifier(
                    new Token(Token.IDENT, "myVar"), 
                    "myVar"
                ), 
                new Identifier(
                    new Token(Token.IDENT, "anotherVar"),
                    "anotherVar"
                )
            )
        );

        assertEquals("let myVar = anotherVar;", prog.toString());
    }
}
