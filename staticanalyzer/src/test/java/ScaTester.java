import org.example.PrintScriptSCA;
import org.example.StaticCodeAnalyzer;

import java.io.IOException;

public class ScaTester {
	StaticCodeAnalyzer staticCodeAnalyzer = new PrintScriptSCA();


	public ScaTester() throws IOException {}

	public void test(String path) {
		assert 1 == 2;
	}
}
