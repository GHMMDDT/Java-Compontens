package com.java.components.lang;

// Creating for ASimplerUser & AOtherUser
public class CharArrayBuilder {
	private char[] oldArray;
	private char[] array;
	private int size;

	private OnAppendedChangedListener onAppendedChangedListener;

	public CharArrayBuilder(
			char[] text,
			int offset
	) {
		if (offset <= -1 || offset >= text.length) throw new IllegalArgumentException("Offset not valid: " + offset);

		this.array = new char[text.length - offset];

		System.arraycopy(text, offset, this.array, 0, this.array.length);

		this.size = this.array.length;
	}

	public CharArrayBuilder() {
		this.array = new char[0];
		this.size = 0;
	}

	public void expandCapacity(
			int newCapacity
	) {
		int newCap = Math.max((int)(this.array.length * 1.5), this.array.length + newCapacity);
		char[] temporally = new char[newCap];
		System.arraycopy(this.array, 0, temporally, 0, this.size - newCapacity);
		this.array = temporally;
	}

	public CharArrayBuilder append(
			char[] text
	) {
		this.oldArray = this.array;

		this.size = this.size + text.length;

		if (this.size >= this.array.length) expandCapacity(text.length);

		System.arraycopy(text, 0, this.array, this.size - text.length, text.length);

		if (onAppendedChangedListener != null) onAppendedChangedListener.onAppendChanged(this.oldArray, this.array, text);

		return this;
	}

	public CharArrayBuilder append(boolean text) {
		this.oldArray = this.array;

		if (text) {
			this.size = this.size + 4;
			if (this.size >= this.array.length) expandCapacity(4);
			this.array[this.size - 4] = 't';
			this.array[this.size - 3] = 'r';
			this.array[this.size - 2] = 'u';
		} else {
			this.size = this.size + 5;
			if (this.size >= this.array.length) expandCapacity(5);
			this.array[this.size - 5] = 'f';
			this.array[this.size - 4] = 'a';
			this.array[this.size - 3] = 'l';
			this.array[this.size - 2] = 's';
		}
		this.array[this.size - 1] = 'e';

		if (onAppendedChangedListener != null) onAppendedChangedListener.onAppendChanged(this.oldArray, this.array, text ? new char[] { 't', 'r', 'u', 'e' } : new char[] { 'f', 'a', 'l', 's', 'e' });

		return this;
	}

	public CharArrayBuilder append(
			char text
	) {
		this.oldArray = this.array;

		this.size = this.size + 1;

		if (this.size >= this.array.length) expandCapacity(1);

		this.array[this.size - 1] = text;

		if (onAppendedChangedListener != null) onAppendedChangedListener.onAppendChanged(this.oldArray, this.array, new char[] { text });

		return this;
	}

	public CharArrayBuilder append(
			String text
	) {
		this.oldArray = this.array;

		this.size = this.size + text.length();
		if (this.size > this.array.length) {
			expandCapacity(text.length());
		}
		text.getChars(0, text.length(), this.array, this.size - text.length());

		if (onAppendedChangedListener != null) onAppendedChangedListener.onAppendChanged(this.oldArray, this.array, text.toCharArray());

		return this;
	}

	public CharArrayBuilder append(
			Number text
	) {
		String subText = String.valueOf(text);
		this.oldArray = this.array;

		this.size = this.size + subText.length();
		if (this.size > this.array.length) {
			expandCapacity(subText.length());
		}
		subText.getChars(0, subText.length(), this.array, this.size - subText.length());

		if (onAppendedChangedListener != null) onAppendedChangedListener.onAppendChanged(this.oldArray, this.array, subText.toCharArray());

		return this;
	}

	public CharArrayBuilder append(
			byte text
	) {
		return this.append((Number) text);
	}

	public CharArrayBuilder append(
			short text
	) {
		return this.append((Number) text);
	}

	public CharArrayBuilder append(
			int text
	) {
		return this.append((Number) text);
	}

	public CharArrayBuilder append(
			long text
	) {
		return this.append((Number) text);
	}

	public CharArrayBuilder append(
			float text
	) {
		return this.append((Number) text);
	}

	public CharArrayBuilder append(
			double text
	) {
		return this.append((Number) text);
	}

	public static abstract class OnAppendedChangedListener {
		public abstract void onAppendChanged(
				char[] oldChar,
				char[] newChar,
				char[] insert
		);
	}

	public CharArrayBuilder setOnAppendedChangedListener(
			OnAppendedChangedListener onAppendedChangedListener
	) {
		this.onAppendedChangedListener = onAppendedChangedListener;
		return this;
	}

	public OnAppendedChangedListener getOnAppendedChangedListener() {
		return onAppendedChangedListener;
	}



	public int getSize() {
		return size;
	}

	public int getLength() {
		return this.array.length;
	}

	public int getCapacity() {
		return this.array.length - this.size;
	}

	@Override
	public String toString() {
		return new String(this.array, 0, this.size);
	}
}
