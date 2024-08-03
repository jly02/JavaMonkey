package javamonkey.ast;

import javamonkey.token.Token;

public class LetStatement implements Statement {
    public Token token;
    public Identifier name;
    public Expression value;

    public LetStatement(Token token, Identifier name, Expression value) {
        this.token = token;
        this.name = name;
        this.value = value;
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
          .append(" ")
          .append(this.name.toString())
          .append(" = ");

        if (this.value != null) {
            sb.append(this.value.toString());
        }

        sb.append(";");
        return sb.toString();
    }
}
