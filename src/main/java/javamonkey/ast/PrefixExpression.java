package javamonkey.ast;

import javamonkey.token.Token;

public class PrefixExpression implements Expression {
    public Token token; // the prefix token
    public String op;
    public Expression right;

    public PrefixExpression(Token token, String op, Expression right) {
        this.token = token;
        this.op = op;
        this.right = right;
    }

    @Override
    public String tokenLiteral() {
        return this.token.literal;
    }

    @Override
    public void expressionNode() { }
    
    @Override
    public String toString() {
        return "(" + this.op + this.right.toString() + ")";
    }
}
