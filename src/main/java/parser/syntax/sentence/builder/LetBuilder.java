package parser.syntax.sentence.builder;

import model.*;
import parser.syntax.sentence.mapper.TokenMapper;
import parser.syntax.sentence.validator.LetSentenceValidator;
import parser.syntax.sentence.validator.SentenceValidator;

import java.util.List;
import java.util.Map;

import static model.BaseTokenTypes.*;

public class LetBuilder implements SentenceBuilder {

  //LET -> IDENTIFIER("anything") -> COLON-> TYPE("anyType") -> ASSIGNATION -> ANYTHING -> SEMICOLON
  //AST (INORDER) SHOULD BE:
  // Number, Identifier(a), Declaration, Assignation, ANYTHING
  @Override
  public AstComponent buildSentence(List<Token> tokens) {
    final SentenceValidator validator = new LetSentenceValidator();
    if(tokens.getFirst().getType() != LET || !validator.isValidSentence(tokens)) return null;
    return getFinalSentence(tokens);
  }

  private AstComponent getFinalSentence(List<Token> tokens) {
    TokenMapper mapper = new TokenMapper();
    //May need to change method
    Token type = tokens.get(3), identifier = tokens.get(1);

    DeclarationType declarationType = getDeclarationType(type.getValue());

    AstComponent declaration = new Declaration(declarationType, identifier.getValue());
    return new Assignation(declaration, mapper.buildArgument(tokens.subList(5, tokens.size())).getFirst());
  }

    private DeclarationType getDeclarationType(String type) {
        Map<String, DeclarationType> declarationTypeMap = Map.of(
                "number", DeclarationType.NUMBER,
                "string", DeclarationType.STRING,
                "function", DeclarationType.FUNCTION
        );
        return declarationTypeMap.get(type.toLowerCase());
    }

}
