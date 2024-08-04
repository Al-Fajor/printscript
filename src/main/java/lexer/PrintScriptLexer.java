package lexer;

import model.BaseTokenTypes;
import model.Token;
import model.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrintScriptLexer implements Lexer{

    @Override
    public List<Token> lex(String input) {
        Scanner scanner = new Scanner();
        Tokenizer tokenizer = new Tokenizer();
        return tokenizer.tokenize(input);
    }

}
