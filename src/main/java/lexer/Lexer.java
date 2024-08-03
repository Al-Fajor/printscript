package lexer;

import model.Token;

import java.util.List;

public interface Lexer {
    List<Token> lex(String input);
}
