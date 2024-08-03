package javamonkey.ast;

import javamonkey.token.Token;

public class ExpressionStatement implements Statement {
    public Token token;
    public Expression expr;

    @Override
    public String tokenLiteral() {
        return this.token.literal;
    }

    @Override
    public void statementNode() { }

    @Override
    public String toString() {
        if (this.expr != null) {
            return this.expr.toString();
        }

        return "";
    }
}
