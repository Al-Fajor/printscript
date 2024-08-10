package org.example.result;

import org.example.AstComponent;

import java.util.List;

public class SyntaxSuccess implements SyntaxResult{
  List<AstComponent> components;
  public SyntaxSuccess(List<AstComponent> components) {
    this.components = components;
  }


  @Override
  public boolean isFailure() {
    return false;
  }

  @Override
  public List<AstComponent> getComponents() {
    return components;
  }
}