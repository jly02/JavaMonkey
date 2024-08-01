package javamonkey.ast;

import java.util.ArrayList;

public class Program {
    public ArrayList<Statement> statements;

    public String tokenLiteral() {
        if (!this.statements.isEmpty()) {
            return this.statements.get(0).tokenLiteral();
        } else {
            return "";
        }
    }
}
