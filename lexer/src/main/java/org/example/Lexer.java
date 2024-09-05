package org.example;

import java.util.Iterator;

public interface Lexer {
	Result lex(Iterator<String> input);
}
