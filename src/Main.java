import com.java.components.lang.CharArrayBuilder;
import com.java.components.utils.BigNumeric;

import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		System.out.println("Welcome to Java Components");
		System.out.println();
		System.out.println("Created by: MDDT (Modification design Developer Team) powered by: GHMMDDT | 1.0 Alpha");

		long start = System.currentTimeMillis();
		CharArrayBuilder target = new CharArrayBuilder();
		for (int i = 0; i < 100_000; i++) target.append("Hello, Wordl!");
		target.indexOfAll(new CharArrayBuilder.OnIndexListener() {
			@Override
			public boolean onIndex(char current) {
				if (current == ' ' || current == ',' || current == '!') return true;
				return false;
			}
		});
		long end = System.currentTimeMillis();

		System.out.println("Time: " + (end - start) + "ms"); // Output: Time: 40900ms
	}
}