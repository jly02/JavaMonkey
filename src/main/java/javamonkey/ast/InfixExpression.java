package javamonkey.ast;

import javamonkey.token.Token;

public class InfixExpression implements Expression {
    public Token token; // the infix token
    public String op;
    public Expression left;
    public Expression right;

    public InfixExpression(Token token, String op, Expression left, Expression right) {
        this.token = token;
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public String tokenLiteral() { return this.token.literal; }

    @Override
    public void expressionNode() { }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}
