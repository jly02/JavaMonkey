package javamonkey.ast;

import javamonkey.token.Token;

public class ReturnStatement implements Statement {
    public Token token;
    public Expression returnValue;

    public ReturnStatement(Token token, Expression returnValue) {
        this.token = token;
        this.returnValue = returnValue;
    }

    @Override
    public String tokenLiteral() {
        return this.token.literal;
    }

    @Override
    public void statementNode() { }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.tokenLiteral())
          .append(" ");

        if (this.returnValue != null) {
            sb.append(this.returnValue.toString());
        }

        sb.append(";");
        return sb.toString();
    }
}
