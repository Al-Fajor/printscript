package org.example;

public class SemanticAnalyzerObserver implements Observer<Pair<Integer, Integer>> {
	@Override
	public void notifyChange(Pair<Integer, Integer> message) {
		double proportion = (double) message.first() / (double) message.second();

		String percent =
				message.first() < message.second()
						? String.format("%.2f", proportion * 100) + "%\r"
						: String.format("%.2f", proportion * 100) + "%\n";
		//                : "Done!\n";

		String sb =
				"["
						+ "=".repeat((int) (proportion * 20))
						+ ">"
						+ " ".repeat(20 - (int) (proportion * 20))
						+ "] "
						+ percent;

		System.out.print(sb);
	}
}
