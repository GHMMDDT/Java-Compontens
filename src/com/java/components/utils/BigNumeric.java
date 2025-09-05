package com.java.components.utils;

import com.java.components.lang.CharacterArrayBuilder;
import com.java.components.lang.CharacterSequence;

// Created by: NoobHack, Baconman & AOtherSimpleUser:

public class BigNumeric {
	public CharacterArrayBuilder numeric = new CharacterArrayBuilder();
	char prefix;

	public BigNumeric(CharacterSequence num) {
		validNumber(num);

		numeric.append(num);
	}

	public BigNumeric addition(CharacterSequence numeric) {
		char[] target = numeric.getCharacterArray();

		validNumber(numeric);

		int i = this.numeric.getSize() - 1;
		int j = numeric.getSize() - 1;
		int carry = 0;

		CharacterArrayBuilder sb = new CharacterArrayBuilder();

		while (i >= 0 || j >= 0 || carry > 0) {
			if (this.numeric.getCharacterAt(Math.max(i, 0)) == '_') i++;
			if (target[Math.max(j, 0)] == '_') j++;
			int digit1 = (i >= 0) ? this.numeric.getCharacterAt(i) - '0' : 0;
			int digit2 = (j >= 0) ? target[j] - '0' : 0;

			int sum = digit1 + digit2 + carry;
			carry = sum / 10;
			sb.append(sum % 10);

			i--;
			j--;
		}

		this.numeric = sb.reverse();

		return this;
	}

	public BigNumeric subtract(CharacterSequence numeric) {
		char[] target = numeric.getCharacterArray();

		validNumber(numeric);

		int i = this.numeric.getSize() - 1;
		int j = numeric.getSize() - 1;

		CharacterArrayBuilder sb = new CharacterArrayBuilder();
		int borrow = 0;

		while (i >= 0 || j >= 0) {
			if (this.numeric.getCharacterAt(i) == '_') i++;
			if (target[Math.max(j, 0)] == '_') j++;
			int digit1 = (i >= 0) ? this.numeric.getCharacterAt(i) - '0' : 0;
			int digit2 = (j >= 0) ? target[j] - '0' : 0;

			digit1 -= borrow;
			borrow = 0;

			if (digit1 < digit2) {
				digit1 += 10;
				borrow = 1;
			}

			int diff = digit1 - digit2;
			sb.append(diff);

			i--;
			j--;
		}

		while (sb.getSize() > 1 && sb.getCharacterAt(sb.getSize() - 1) == '0') {
			sb.deleteCharacterAt(sb.getSize() - 1);
		}

		this.numeric = sb.reverse();
		return this;
	}

	public BigNumeric multiplication(CharacterSequence numeric) {
		char[] target = numeric.getCharacterArray();

		validNumber(numeric);

		CharacterArrayBuilder result = new CharacterArrayBuilder(new char[] { '0' });

		int zeros = 0;
		int i = this.numeric.getSize() - 1;
		int j = numeric.getSize() - 1;
		for ( ; j >= 0; j--) {
			if (target[Math.max(j, 0)] == '_') j++;
			int d2 = target[j] - '0';
			int carry = 0;

			CharacterArrayBuilder partial = new CharacterArrayBuilder();

			for (; i >= 0; i--) {
				if (this.numeric.getCharacterAt(i) == '_') i++;
				int d1 = this.numeric.getCharacterAt(i) - '0';
				int prod = d1 * d2 + carry;
				carry = prod / 10;
				partial.append(prod % 10);
			}

			if (carry > 0) {
				partial.append(carry);
			}

			partial.reverse();
			partial.append("0".repeat(Math.max(0, zeros)));

			result = addStrings(result, partial);

			zeros++;
		}

		this.numeric = new CharacterArrayBuilder(result);
		return this;
	}

	private CharacterArrayBuilder addStrings(CharacterArrayBuilder a, CharacterArrayBuilder b) {
		int i = a.getSize() - 1;
		int j = b.getSize() - 1;
		int carry = 0;

		CharacterArrayBuilder sb = new CharacterArrayBuilder();

		while (i >= 0 || j >= 0 || carry > 0) {
			if (a.getCharacterAt(Math.max(i, 0)) == '_') i++;
			if (b.getCharacterAt(Math.max(j, 0)) == '_') j++;
			int d1 = (i >= 0) ? a.getCharacterAt(i) - '0' : 0;
			int d2 = (j >= 0) ? b.getCharacterAt(j) - '0' : 0;

			int sum = d1 + d2 + carry;
			carry = sum / 10;
			sb.append(sum % 10);

			i--;
			j--;
		}

		return sb.reverse();
	}

	public BigNumeric divide(CharacterSequence numeric) {
		char[] divisorArr = numeric.getCharacterArray();

		validNumber(numeric);

		if (numeric.equals(new CharacterArrayBuilder(new char[] { '0' }))) {
			throw new ArithmeticException("Division by zero");
		}

		CharacterArrayBuilder quotient = new CharacterArrayBuilder();

		BigNumeric remainder = new BigNumeric(new CharacterArrayBuilder(new char[] { '0' }));

		for (int i = 0; i < this.numeric.getSize(); i++) {
			remainder.numeric.append(this.numeric.getCharacterAt(i));

			while (remainder.numeric.getSize() > 1 && remainder.numeric.getCharacterAt(0) == '0') {
				remainder.numeric.deleteCharacterAt(0);
			}

			int count = 0;
			while (compareAbs(remainder, numeric) >= 0) {
				remainder.subtract(new CharacterArrayBuilder(numeric));
				count++;
			}

			quotient.append((char) ('0' + count));
		}

		while (quotient.getSize() > 1 && quotient.getCharacterAt(0) == '0') {
			quotient.deleteCharacterAt(0);
		}

		this.numeric = quotient;
		return this;
	}

	private int compareAbs(BigNumeric a, CharacterSequence b) {
		if (a.numeric.getSize() != b.getSize()) {
			return Integer.compare(a.numeric.getSize(), b.getSize());
		}
		for (int i = 0; i < a.numeric.getSize(); i++) {
			if (a.numeric.getCharacterAt(i) != b.getCharacterAt(i)) {
				return Character.compare(a.numeric.getCharacterAt(i), b.getCharacterAt(i));
			}
		}
		return 0;
	}

	public void validNumber(CharacterSequence cs) {
		for (char c : cs.getCharacterArray()) {
			if (!((c >= '0' && c <= '9') || c == '_' || c == '-')) {
				throw new IllegalArgumentException("The character of you numeric-string contains a: " + c + " is not valid");
			}
		}
	}

	public boolean isGreaterThan(CharacterSequence cs) {
		validNumber(cs);


		boolean thisNegative = this.numeric.getCharacterArray()[0] == '-';
		boolean otherNegative = cs.getCharacterArray()[0] == '-';

		if (thisNegative && !otherNegative) return true;
		if (!thisNegative && otherNegative) return false;

		if (!thisNegative & !otherNegative) {
			if (this.numeric.getSize() < cs.getSize()) return false;
			if (this.numeric.getSize() > cs.getSize()) return true;

			for (int i = 0;i < this.numeric.getSize(); i++) {
				char c = this.numeric.getCharacterArray()[i];
				char c2 = cs.getCharacterArray()[i];

				if (c == c2) continue;

				return c > c2;
			}
		}

		if (thisNegative & otherNegative) {
			if (this.numeric.getSize() - 1 < cs.getSize() - 1) return false;
			if (this.numeric.getSize() - 1 > cs.getSize() - 1) return true;

			for (int i = 0;i < this.numeric.getSize(); i++) {
				char c = this.numeric.getCharacterArray()[i];
				char c2 = cs.getCharacterArray()[i];

				if (c == c2) continue;

				return c > c2;
			}
		}

		return false;
	}

	public boolean isLessThan(CharacterSequence cs) {
		return !isGreaterThan(cs);
	}


	@Override
	public String toString() {
		numeric.append(prefix);
		return numeric.toString();
	}
}
