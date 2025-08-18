package com.java.components.lang;

import java.util.Arrays;

/*
 * Created by: ASimplerUser, TheCreator & AOtherUser
 */
public class CharArrayBuilder {
	// -------------------- Field : Started -------------------- \\

	private char[] buffer;
	private int size;

	private OnAppendedChangedListener onAppendedChangedListener;
	private OnAppendedListener onAppendedListener;
	private OnAppendedWatcherListener onAppendedWatcherListener;

	// -------------------- Field : Ended -------------------- \\

	// -------------------- Constructor : Started -------------------- \\

	public CharArrayBuilder(
			char[] target,
			int offset,
			int preCapacity
	) {
		if (offset < 0 || offset >= target.length) throw new IllegalArgumentException("Index of bound: " + offset);

		this.buffer = new char[target.length - offset + preCapacity];

		System.arraycopy(target, offset, this.buffer, 0, target.length - offset);
	}

	public CharArrayBuilder(
			char[] target,
			int offset
	) {
		this.buffer = new char[target.length - offset];

		System.arraycopy(target, offset, this.buffer, 0, this.buffer.length);
	}

	public CharArrayBuilder(
			char[] target
	) {
		this.buffer = new char[target.length];

		System.arraycopy(target, 0, this.buffer, 0, this.buffer.length);
	}

	public CharArrayBuilder(
			int preCapacity
	) {
		this.buffer = new char[preCapacity];
	}

	public CharArrayBuilder() {
		this.buffer = new char[0];
	}

	// -------------------- Constructor : Ended -------------------- \\

	// -------------------- Functions : Started -------------------- \\

	public CharArrayBuilder append(char[] text) {
		if (text == null || text.length == 0) return this;

		char[] newBuffer;
		if (this.size + text.length >= this.buffer.length) {
			newBuffer = new char[Math.max(buffer.length * 2, size + text.length)];
		} else {
			newBuffer = new char[this.buffer.length];
		}
		System.arraycopy(this.buffer, 0, newBuffer, 0, this.size);
		System.arraycopy(text, 0, newBuffer, this.size, text.length);

		if (onAppendedChangedListener != null) {
			if (!onAppendedChangedListener.onAppendedChanged(this.buffer, this.size, newBuffer, this.size + newBuffer.length, text)) {
				return this;
			}
		}

		if (onAppendedListener != null) {
			char[] target = onAppendedListener.onAppended(this.buffer, this.size, newBuffer, this.size + newBuffer.length, text);

			if (target != null) {
				newBuffer = target;
			}
		}

		if (onAppendedWatcherListener != null) {
			onAppendedWatcherListener.onAppendedWatcher(this.buffer, this.size, newBuffer, this.size + newBuffer.length, text);
		}

		this.buffer = newBuffer;
		this.size = newBuffer.length;

		return this;
	}

	public CharArrayBuilder append(String text) {
		char[] subText = text.toCharArray();
		if (subText == null || subText.length == 0) return this;

		char[] newBuffer;
		if (this.size + subText.length >= this.buffer.length) {
			newBuffer = new char[Math.max(buffer.length * 2, size + subText.length)];
		} else {
			newBuffer = new char[this.buffer.length];
		}
		System.arraycopy(this.buffer, 0, newBuffer, 0, this.size);
		System.arraycopy(subText, 0, newBuffer, this.size, subText.length);

		if (onAppendedChangedListener != null) {
			if (!onAppendedChangedListener.onAppendedChanged(this.buffer, this.size, newBuffer, this.size + newBuffer.length, subText)) {
				return this;
			}
		}

		if (onAppendedListener != null) {
			char[] target = onAppendedListener.onAppended(this.buffer, this.size, newBuffer, this.size + newBuffer.length, subText);

			if (target != null) {
				newBuffer = target;
			}
		}

		if (onAppendedWatcherListener != null) {
			onAppendedWatcherListener.onAppendedWatcher(this.buffer, this.size, newBuffer, this.size + newBuffer.length, subText);
		}

		this.buffer = newBuffer;
		this.size = newBuffer.length;

		return this;
	}

	public CharArrayBuilder setOnAppendedChangedListener(OnAppendedChangedListener onAppendedChangedListener) {
		this.onAppendedChangedListener = onAppendedChangedListener;
		return this;
	}

	public OnAppendedChangedListener getOnAppendedChangedListener() {
		return onAppendedChangedListener;
	}

	public CharArrayBuilder setOnAppendedListener(OnAppendedListener onAppendedListener) {
		this.onAppendedListener = onAppendedListener;
		return this;
	}

	public OnAppendedListener getOnAppendedListener() {
		return onAppendedListener;
	}

	public CharArrayBuilder setOnAppendedWatcherListener(OnAppendedWatcherListener onAppendedWatcherListener) {
		this.onAppendedWatcherListener = onAppendedWatcherListener;
		return this;
	}

	public OnAppendedWatcherListener getOnAppendedWatcherListener() {
		return onAppendedWatcherListener;
	}

	@Override
	public String toString() {
		return new String(buffer, 0, this.size);
	}

	// -------------------- Functions : Ended -------------------- \\

	// -------------------- Class : Start -------------------- \\

	public static abstract class OnAppendedChangedListener {
		public abstract boolean onAppendedChanged(char[] old, int oldSize, char[] current, int currentSize, char[] insert);
	}

	public static abstract class OnAppendedListener {
		public abstract char[] onAppended(char[] old, int oldSize, char[] current, int currentSize, char[] insert);
	}

	public static abstract class OnAppendedWatcherListener {
		public abstract void onAppendedWatcher(char[] old, int oldSize, char[] current, int currentSize, char[] insert);
	}

	// -------------------- Class : Ended -------------------- \\
}
