package org.example;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class PrintScriptFormatterTest {
	private final FormatterTestFramework formatterTestFramework = new FormatterTestFramework();

	@Test
	void defaultRules() throws IOException {
		formatterTestFramework.testRules("src/test/resources/default_rules");
	}

	@Test
	void rules1() throws IOException {
		formatterTestFramework.testRules("src/test/resources/rules1");
	}

	@Test
	void rules2() throws IOException {
		formatterTestFramework.testRules("src/test/resources/rules2");
	}

	@Test
	void singleRule() throws IOException {
		formatterTestFramework.testRules("src/test/resources/single_rule");
	}

	@Test
	void twoRules() throws IOException {
		formatterTestFramework.testRules("src/test/resources/two_rules");
	}

	@Test
	void rulesv11() throws IOException {
		formatterTestFramework.testRules("src/test/resources/rulesv1-1");
	}
}
