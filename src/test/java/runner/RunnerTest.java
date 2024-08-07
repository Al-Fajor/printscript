package runner;


import org.junit.jupiter.api.Test;

class RunnerTest {

  Runner runner = new Runner();
  @Test
  public void testRunner(){
    runner.run("let x: number = 5; println(x);");
    runner.run("println(1+2);");
    runner.run("let a: string = \"hello\"; println(a);");
  }

  @Test
  public void testRunner2(){
    runner.run("let x: number = 5; " +
      "println(x - 1);"+
      "println(x + 1);");
  }
}