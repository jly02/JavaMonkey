package javamonkey.ast;

public interface Node {
    /**
     * Returns a string representing the literal token in this node.
     * 
     * @return the literal stored in the node token
     */
    String tokenLiteral();
}
