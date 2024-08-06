package javamonkey.ast;

import javamonkey.token.Token;

public class IntegerLiteral implements Expression {
    public Token token;
    public long value;

    public IntegerLiteral(Token token, long value) {
        this.token = token;
        this.value = value;
    }

    @Override
    public String tokenLiteral() {
        return this.token.literal;
    }

    @Override
    public void expressionNode() { }

    @Override
    public String toString() {
        return this.token.literal;
    }
}
