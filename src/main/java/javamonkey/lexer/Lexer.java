package javamonkey.lexer;

public class Lexer {
    public Lexer(String input) {
        //TODO Auto-generated constructor stub
    }

    public TokTuple nextToken() {
        return new TokTuple("", "");
    }
    
    // Simple string tuple class for holding pairs of
    // "(Token, Literal)"
    public class TokTuple { 
        public final String type; 
        public final String lit; 
        
        public TokTuple(String type, String lit) { 
            this.type = type; 
            this.lit = lit; 
        } 
    } 
}
