package com.java.components.utils;

// Created by: NoobHack & AOtherSimpleUser:
public class BigNumeric {
	private StringBuilder numeric = new StringBuilder();

	public BigNumeric(String num) {
		for (char c : num.toCharArray()) {
			if (!(c >= '0' && c <= '9')) {
				throw new IllegalArgumentException("The character of you numeric-string contains a: " + c + " is not valid");
			}
		}

		numeric.append(num);
	}

	public BigNumeric addition(String numeric) {
		int i = this.numeric.length() - 1;
		int j = numeric.length() - 1;
		int carry = 0;

		StringBuilder sb = new StringBuilder();

		while (i >= 0 || j >= 0 || carry > 0) {
			int digit1 = (i >= 0) ? this.numeric.charAt(i) - '0' : 0;
			int digit2 = (j >= 0) ? numeric.charAt(j) - '0' : 0;

			int sum = digit1 + digit2 + carry;
			carry = sum / 10;
			sb.append(sum % 10);

			i--;
			j--;
		}

		this.numeric = sb.reverse();

		return this;
	}

	public BigNumeric subtract(String numeric) {
		int i = this.numeric.length() - 1;
		int j = numeric.length() - 1;

		StringBuilder sb = new StringBuilder();
		int borrow = 0;

		while (i >= 0 || j >= 0) {
			int digit1 = (i >= 0) ? this.numeric.charAt(i) - '0' : 0;
			int digit2 = (j >= 0) ? numeric.charAt(j) - '0' : 0;

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

		while (sb.length() > 1 && sb.charAt(sb.length() - 1) == '0') {
			sb.deleteCharAt(sb.length() - 1);
		}

		this.numeric = sb.reverse();
		return this;
	}

	public BigNumeric multiplication(String second) {
		String num1 = this.numeric.toString();

		String result = "0";

		int zeros = 0;
		for (int j = second.length() - 1 ; j >= 0; j--) {
			int d2 = second.charAt(j) - '0';
			int carry = 0;

			StringBuilder partial = new StringBuilder();

			for (int i = num1.length() - 1; i >= 0; i--) {
				int d1 = num1.charAt(i) - '0';
				int prod = d1 * d2 + carry;
				carry = prod / 10;
				partial.append(prod % 10);
			}

			if (carry > 0) {
				partial.append(carry);
			}

			partial.reverse();
			partial.append("0".repeat(Math.max(0, zeros)));

			result = addStrings(result, partial.toString());

			zeros++;
		}

		this.numeric = new StringBuilder(result);
		return this;
	}

	private String addStrings(String a, String b) {
		int i = a.length() - 1;
		int j = b.length() - 1;
		int carry = 0;
		StringBuilder sb = new StringBuilder();

		while (i >= 0 || j >= 0 || carry > 0) {
			int d1 = (i >= 0) ? a.charAt(i) - '0' : 0;
			int d2 = (j >= 0) ? b.charAt(j) - '0' : 0;

			int sum = d1 + d2 + carry;
			carry = sum / 10;
			sb.append(sum % 10);

			i--;
			j--;
		}

		return sb.reverse().toString();
	}

	@Override
	public String toString() {
		return numeric.toString();
	}
}
