package com.java.components.lang;

public class Numeric extends Number {
	public static final int   MIN_VALUE = 0x80000000;

	public static final int   MAX_VALUE = 0x7fffffff;

	private final int value;

	public Numeric(int value) {
		this.value = value;
	}

	public Numeric(String s) throws NumberFormatException {
		this.value = parseInt(s);
	}

	public byte byteValue() {
		return (byte)value;
	}

	public short shortValue() {
		return (short)value;
	}

	public int intValue() {
		return value;
	}

	public long longValue() {
		return value;
	}

	public float floatValue() {
		return (float) value;
	}

	public double doubleValue() {
		return value;
	}

	public int parseInt(String s) {
		if (s == null || s.isEmpty()) {
			throw new NumberFormatException("Empty or null string");
		}

		int result = 0;
		boolean negative = false;
		int i = 0;

		if (s.charAt(0) == '-') {
			negative = true;
			i = 1;
		} else if (s.charAt(0) == '+') {
			i = 1;
		}

		for (; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c < '0' || c > '9') {
				throw new NumberFormatException("Invalid character: " + c);
			}

			int digit = c - '0';
			result = result * 10 + digit;
		}

		return negative ? -result : result;
	}


}
