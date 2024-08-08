package runner;


import org.junit.jupiter.api.Test;

class RunnerTest {

  Runner runner = new Runner();
  @Test
  public void testRunner(){
    runner.run("let x: number = 5; println(x);");
  }
}