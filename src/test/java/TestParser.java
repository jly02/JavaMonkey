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
import javamonkey.ast.IntegerLiteral;
import javamonkey.ast.LetStatement;
import javamonkey.ast.PrefixExpression;
import javamonkey.ast.Program;
import javamonkey.ast.ReturnStatement;
import javamonkey.ast.Statement;
import javamonkey.lexer.Lexer;
import javamonkey.parser.Parser;

public class TestParser {
    @Test
    public void testLetStatements() {
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
        String input = "foobar;";

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

    @Test
    public void testIntegerLiteralExpression() {
        String input = "5;";

        Lexer l = new Lexer(input);
        Parser p = new Parser(l);
        Program program = p.parse();
        checkParserErrors(p);

        // Check basic properties are met.
        assertNotNull(program);
        assertEquals(1, program.statements.size());

        // Check node is an ExpressionStatement
        assertThat(program.statements.get(0), instanceOf(ExpressionStatement.class));

        // Check that the contained expression is an integer literal
        ExpressionStatement exprStmt = (ExpressionStatement) program.statements.get(0);
        Expression expr = exprStmt.expr;
        testIntegerLiteral(expr, 5);
    }

    @Test
    public void testPrefixExpression() {
        PrefixTest[] tests = {
            new PrefixTest("!5;", "!", 5),
            new PrefixTest("-15;", "-", 15)
        };

        for (PrefixTest test : tests) {
            Lexer l = new Lexer(test.input);
            Parser p = new Parser(l);
            Program program = p.parse();
            checkParserErrors(p);

            // Check basic properties are met.
            assertNotNull(program);
            assertEquals(1, program.statements.size());

            // Check node is an ExpressionStatement
            assertThat(program.statements.get(0), instanceOf(ExpressionStatement.class));

            // Check that the contained expression is a prefix expression
            ExpressionStatement exprStmt = (ExpressionStatement) program.statements.get(0);
            Expression expr = exprStmt.expr;
            assertThat(expr, instanceOf(PrefixExpression.class));

            // Check that the integer literal is correct
            PrefixExpression pe = (PrefixExpression) expr;
            assertEquals(test.op, pe.op);
            testIntegerLiteral(pe.right, test.value);
        }
    }

    private void testLetStatement(Statement stmt, String ident) {
        assertEquals("s is not 'let', got " + stmt.tokenLiteral(), stmt.tokenLiteral(), "let");

        assertThat(stmt, instanceOf(LetStatement.class));

        LetStatement letStmt = (LetStatement) stmt;
        assertEquals(ident, letStmt.name.value);

        assertEquals(ident, letStmt.name.tokenLiteral());
    }

    private void testIntegerLiteral(Expression expr, long value) {
        assertThat(expr, instanceOf(IntegerLiteral.class));

        IntegerLiteral lit = (IntegerLiteral) expr;
        assertEquals(value, lit.value);

        assertEquals(Long.toString(value), lit.tokenLiteral());
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

    // Helper class for organizing prefix expression tests
    private static class PrefixTest {
        public String input;
        public String op;
        public long value;

        public PrefixTest(String input, String op, long value) {
            this.input = input;
            this.op = op;
            this.value = value;
        }
    }
}
