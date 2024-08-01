package javamonkey.ast;

public class Program {
    Statement[] statements;

    public String tokenLiteral() {
        if (this.statements.length > 0) {
            return this.statements[0].tokenLiteral();
        } else {
            return "";
        }
    }
}
