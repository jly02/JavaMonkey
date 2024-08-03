import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Test;

import javamonkey.ast.Expression;
import javamonkey.ast.ExpressionStatement;
import javamonkey.ast.Identifier;
import javamonkey.ast.LetStatement;
import javamonkey.ast.Program;
import javamonkey.ast.ReturnStatement;
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
        checkParserErrors(p);

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

    @Test
    public void TestLetStatementsParseErrors() {
        String input =
        """
        let x 5;
        let = 10;
        let 838383;
        """;

        Lexer l = new Lexer(input);
        Parser p = new Parser(l);
        p.parse();
        assertEquals(3, p.errors().size());
    }

    @Test
    public void testReturnStatement() {
        String input =
        """
        return 5;
        return 10;
        return 993322;        
        """;

        Lexer l = new Lexer(input);
        Parser p = new Parser(l);
        Program program = p.parse();
        checkParserErrors(p);

        // Check basic properties are met.
        assertNotNull(program);
        assertEquals(3, program.statements.size());

        for (Statement stmt : program.statements) {
            assertThat(stmt, instanceOf(ReturnStatement.class));
            assertEquals("return", stmt.tokenLiteral());
        }
    }

    @Test
    public void testIdentifierExpression() {
        String input = "foobar";

        Lexer l = new Lexer(input);
        Parser p = new Parser(l);
        Program program = p.parse();
        checkParserErrors(p);

        // Check basic properties are met.
        assertNotNull(program);
        assertEquals(1, program.statements.size());

        // Check node is an ExpressionStatement
        assertThat(program.statements.get(0), instanceOf(ExpressionStatement.class));

        // Check that the contained expression is an identifier
        ExpressionStatement exprStmt = (ExpressionStatement) program.statements.get(0);
        Expression expr = exprStmt.expr;
        assertThat(expr, instanceOf(Identifier.class));

        // Check that the identifier literal is correct
        assertEquals("foobar", ((Identifier) expr).value);
        assertEquals("foobar", expr.tokenLiteral());
    }

    private void testLetStatement(Statement stmt, String ident) {
        assertEquals("s is not 'let', got " + stmt.tokenLiteral(), stmt.tokenLiteral(), "let");

        assertThat(stmt, instanceOf(LetStatement.class));

        LetStatement letStmt = (LetStatement) stmt;
        assertEquals(ident, letStmt.name.value);

        assertEquals(ident, letStmt.name.tokenLiteral());
    }

    private void checkParserErrors(Parser p) {
        List<String> errors = p.errors();
        if (errors.isEmpty()) {
            return;
        }

        System.err.println("Parser has " + errors.size() + " errors.");
        for (String msg : errors) {
            System.err.println(msg);
        }

        fail();
    }
}
