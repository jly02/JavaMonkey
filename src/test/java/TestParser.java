import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import javamonkey.ast.LetStatement;
import javamonkey.ast.Program;
import javamonkey.ast.Statement;
import javamonkey.lexer.Lexer;
import javamonkey.parser.Parser;

public class TestParser {
    @Test
    public void TestLetStatements() {
        String input =
        """
        let x = 5;
        let y = 10;
        let foobar = 838383;
        """;

        Lexer l = new Lexer(input);
        Parser p = new Parser(l);
        Program program = p.parse();

        // Check basic properties are met.
        assertNotNull(program);
        assertEquals(3, program.statements.size());

        String[] expectedIdent = {"x", "y", "foobar"};
        for (int i = 0; i < expectedIdent.length; i++) {
            Statement stmt = program.statements.get(i);
            String ident = expectedIdent[i];
            testLetStatement(stmt, ident);
        }
    }

    private void testLetStatement(Statement stmt, String ident) {
        assertEquals("s is not 'let', got " + stmt.tokenLiteral(), stmt.tokenLiteral(), "let");

        assertThat(stmt, instanceOf(LetStatement.class));

        LetStatement letStmt = (LetStatement) stmt;
        assertEquals(ident, letStmt.name.value);

        assertEquals(ident, letStmt.name.tokenLiteral());
    }
}
