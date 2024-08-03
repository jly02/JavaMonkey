package javamonkey.ast;

import javamonkey.token.Token;

public class Identifier implements Expression {
    public Token token;
    public String value;

    public Identifier(Token token, String value) {
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
        return this.value;
    }
}
