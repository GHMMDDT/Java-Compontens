import com.java.components.lang.CharacterArrayBuilder;

import java.io.IOException;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) throws IOException {
		System.out.println("Welcome to Java Components");
		System.out.println();
		System.out.println("Created by: MDDT (Modification Design Developer Team) powered by: GHMMDDT | 1.0 Alpha");

		CharacterArrayBuilder builder = new CharacterArrayBuilder();
		builder.append("Created by: MDDT (Modification Design Developer Team) powered by: GHMMDDT | 1.0 Alpha");

		CharacterArrayBuilder.OnSplitListener listener = new CharacterArrayBuilder.OnSplitListener() {
			@Override
			public boolean onSplit(char current) {
				return current == ' ';
			}
		};

		System.out.println("Text: " + Arrays.toString(builder.split(listener, 0, builder.getSize(), CharacterArrayBuilder.SplitDirectionType.EndToStart)) + " // reverse");
		System.out.println("Text: " + Arrays.toString(builder.split(listener, 0, builder.getSize(), CharacterArrayBuilder.SplitDirectionType.StartToEnd)) + " // normal");
	}
}
/* Ouput:
Welcome to Java Components

Created by: MDDT (Modification Design Developer Team) powered by: GHMMDDT | 1.0 Alpha
Text: [Alpha, 1.0, |, GHMMDDT, by:, powered, Team), Developer, Design, (Modification, MDDT, by:, Created] // reverse
Text: [Created, by:, MDDT, (Modification, Design, Developer, Team), powered, by:, GHMMDDT, |, 1.0, Alpha] // normal
 */