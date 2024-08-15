package org.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class RunnerTest {

	Runner runner = new Runner();

	@Test
	public void testRunner() {
		test("let x: number = 5; println(x);", Result.SUCCESS);
		test("println(1+2);", Result.SUCCESS);
		test("let a: string = \"hello\"; println(a);", Result.SUCCESS);
	}

	@Test
	public void testRunner2() {
		test("let x: number = 5; " + "println(x - 1);" + "println(x + 1);", Result.SUCCESS);
	}

	@Test
	public void testRunner3() {
		test(
				"let x: number = 1 + (2 + (3 + 4));" + "println(x);" + "println(x + 1);",
				Result.SUCCESS);
	}

	@Test
	public void testRunner4() {
		test("println(-1);", Result.SUCCESS);
	}

	@Test
	public void testRunner5() {
		test("let x: number = 1 - (2 - (3-4));" + "println(x);", Result.SUCCESS);
	}

	@Test
	public void testRunner6() {
		//    test("let 1a: number = 2;", Result.FAIL);
		//    test("println(10)", Result.SUCCESS);
		test("let x: number = -1;" + "println(x);" + "x = 2;" + "println(x);", Result.SUCCESS);
		test("let b: string;", Result.FAIL);
	}

	private Executable run(String code) {
		return () -> runner.run(code);
	}

	private void test(String code, Result expected) {
		if (expected.equals(Result.SUCCESS)) {
			assertDoesNotThrow(run(code));
		}
		if (expected.equals(Result.FAIL)) {
			assertThrows(RuntimeException.class, run(code));
		}
	}

	private enum Result {
		SUCCESS,
		FAIL
	}
}
