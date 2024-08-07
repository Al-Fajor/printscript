package lexer;

import lexer.scanresult.ScanResult;
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
        ScanResult scanResult = scanner.scan(input);
        if (!scanResult.isSuccessful()) throw new IllegalArgumentException();
        Tokenizer tokenizer = new Tokenizer();
        return tokenizer.tokenize(input);
    }

}
