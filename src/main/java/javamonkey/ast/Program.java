package javamonkey.ast;

import java.util.List;

public class Program {
    public List<Statement> statements;

    public String tokenLiteral() {
        if (!this.statements.isEmpty()) {
            return this.statements.get(0).tokenLiteral();
        } else {
            return "";
        }
    }
}
