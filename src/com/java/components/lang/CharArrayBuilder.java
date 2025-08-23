package com.java.components.lang;

import java.awt.*;

/*
 * Created by: ASimplerUser, ByUser, TheCreator & AOtherUser
 */
public class CharArrayBuilder {
	// -------------------- Field : Started -------------------- \\

	private char[] buffer;
	private int size;

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

		this.size = this.buffer.length;
	}

	public CharArrayBuilder(
			char[] target,
			int offset
	) {
		this.buffer = new char[target.length - offset];

		System.arraycopy(target, offset, this.buffer, 0, this.buffer.length);

		this.size = this.buffer.length;
	}

	public CharArrayBuilder(char[] target) {
		this.buffer = new char[target.length];

		System.arraycopy(target, 0, this.buffer, 0, this.buffer.length);

		this.size = this.buffer.length;
	}

	public CharArrayBuilder(String target) {
		char[] subTarget = target.toCharArray();

		this.buffer = new char[subTarget.length];

		System.arraycopy(subTarget, 0, this.buffer, 0, this.buffer.length);

		this.size = this.buffer.length;
	}

	public CharArrayBuilder(int preCapacity) {
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
			newBuffer = new char[Math.max((int) (buffer.length * 1.75), size + text.length)];
		} else {
			newBuffer = new char[this.buffer.length];
		}
		System.arraycopy(this.buffer, 0, newBuffer, 0, this.size);
		System.arraycopy(text, 0, newBuffer, this.size, text.length);

		if (onAppendedWatcherListener != null) {
			onAppendedWatcherListener.onAppendedWatcher(this.buffer, this.size, newBuffer, this.size + newBuffer.length, text);
		}

		this.buffer = newBuffer;
		this.size += text.length;

		return this;
	}

	public CharArrayBuilder append(char text) {
		char[] newBuffer;
		if (this.size + 1 >= this.buffer.length) {
			newBuffer = new char[Math.max((int) (buffer.length * 1.75), size + 1)];
		} else {
			newBuffer = new char[this.buffer.length];
		}
		System.arraycopy(this.buffer, 0, newBuffer, 0, this.size);
		newBuffer[this.size] = text;

		if (onAppendedWatcherListener != null) {
			onAppendedWatcherListener.onAppendedWatcher(this.buffer, this.size, newBuffer, this.size + newBuffer.length, new char[] { text });
		}

		this.buffer = newBuffer;
		this.size += 1;

		return this;
	}

	public CharArrayBuilder append(String text) {
		if (text == null || text.isEmpty()) return this;

		char[] subText = text.toCharArray();

		char[] newBuffer;
		if (this.size + subText.length >= this.buffer.length) {
			newBuffer = new char[Math.max((int) (buffer.length * 1.75), size + subText.length)];
		} else {
			newBuffer = new char[this.buffer.length];
		}
		System.arraycopy(this.buffer, 0, newBuffer, 0, this.size);
		System.arraycopy(subText, 0, newBuffer, this.size, subText.length);

		if (onAppendedWatcherListener != null) {
			onAppendedWatcherListener.onAppendedWatcher(this.buffer, this.size, newBuffer, this.size + newBuffer.length, subText);
		}

		this.buffer = newBuffer;
		this.size += text.length();

		return this;
	}

	public CharArrayBuilder append(boolean text) {
		char[] newBuffer;
		char[] subText = text ? new char[] { 't', 'r', 'u', 'e' } : new char[] { 'f', 'a', 'l', 's', 'e' };
		if (this.size + subText.length >= this.buffer.length) {
			newBuffer = new char[Math.max((int) (buffer.length * 1.75), size + subText.length)];
		} else {
			newBuffer = new char[this.buffer.length];
		}
		System.arraycopy(this.buffer, 0, newBuffer, 0, this.size);
		System.arraycopy(subText, 0, newBuffer, this.size, subText.length);

		if (onAppendedWatcherListener != null) {
			onAppendedWatcherListener.onAppendedWatcher(this.buffer, this.size, newBuffer, this.size + newBuffer.length, subText);
		}

		this.buffer = newBuffer;
		this.size += subText.length;

		return this;
	}

	public CharArrayBuilder append(Number text) {
		char[] preText = String.valueOf(text).toCharArray();

		char[] newBuffer;
		if (this.size + preText.length >= this.buffer.length) {
			newBuffer = new char[Math.max((int) (buffer.length * 1.75), size + preText.length)];
		} else {
			newBuffer = new char[this.buffer.length];
		}
		System.arraycopy(this.buffer, 0, newBuffer, 0, this.size);
		System.arraycopy(preText, 0, newBuffer, this.size, preText.length);

		if (onAppendedWatcherListener != null) {
			onAppendedWatcherListener.onAppendedWatcher(this.buffer, this.size, newBuffer, this.size + newBuffer.length, preText);
		}

		this.buffer = newBuffer;
		this.size += preText.length;

		return this;
	}

	public CharArrayBuilder setOnAppendedWatcherListener(
			OnAppendedWatcherListener onAppendedWatcherListener
	) {
		this.onAppendedWatcherListener = onAppendedWatcherListener;
		return this;
	}

	public OnAppendedWatcherListener getOnAppendedWatcherListener() {
		return this.onAppendedWatcherListener;
	}

	public CharArrayBuilder replace(OnTargetListener target, int offset, OnReplacementListener replacement, int count, int start, int end) {
		int counter = 1;
		for (; start < end; start++) {
			if (target.onTarget(this.buffer[start])) {
				if (offset == 0) {
					if (count != 0) {
						this.buffer[start] = replacement.onReplacement(this.buffer[start], counter);
						counter++;
						count--;
						continue;
					}
				}
				offset--;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(OnTargetListener target, int offset, OnReplacementListener replacement, int start, int end) {
		int counter = 1;
		for (; start < end; start++) {
			if (target.onTarget(this.buffer[start])) {
				if (offset == 0) {
					this.buffer[start] = replacement.onReplacement(this.buffer[start], counter);
					counter++;
					continue;
				}
				offset--;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(OnTargetListener target, OnReplacementListener replacement, int start, int end) {
		int counter = 1;
		for (; start < end; start++) {
			if (target.onTarget(this.buffer[start])) {
				this.buffer[start] = replacement.onReplacement(this.buffer[start], counter);
				counter++;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(OnTargetListener target, int offset, OnReplacementListener replacement, int count) {
		int counter = 1;
		for (int start = 0; start < this.size; start++) {
			if (target.onTarget(this.buffer[start])) {
				if (offset == 0) {
					if (count != 0) {
						this.buffer[start] = replacement.onReplacement(this.buffer[start], counter);
						counter++;
						count--;
						continue;
					}
				}
				offset--;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(OnTargetListener target, int offset, OnReplacementListener replacement) {
		int counter = 1;
		for (int start = 0; start < this.size; start++) {
			if (target.onTarget(this.buffer[start])) {
				if (offset == 0) {
					this.buffer[start] = replacement.onReplacement(this.buffer[start], counter);
					counter++;
					continue;
				}
				offset--;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(OnTargetListener target, OnReplacementListener replacement, int count) {
		int counter = 1;
		for (int start = 0; start < this.size; start++) {
			if (target.onTarget(this.buffer[start])) {
				if (count != 0) {
					this.buffer[start] = replacement.onReplacement(this.buffer[start], counter);
					counter++;
					count--;
				}
			}
		}
		return this;
	}

	public CharArrayBuilder replace(OnTargetListener target, OnReplacementListener replacement) {
		int counter = 1;
		for (int start = 0; start < this.size; start++) {
			if (target.onTarget(this.buffer[start])) {
				this.buffer[start] = replacement.onReplacement(this.buffer[start], counter);
				counter++;
			}
		}
		return this;
	}

	// A replace

	public CharArrayBuilder replace(char target, int offset, OnReplacementListener replacement, int count, int start, int end) {
		int counter = 1;
		for (; start < end; start++) {
			if (target == this.buffer[start]) {
				if (offset == 0) {
					if (count != 0) {
						this.buffer[start] = replacement.onReplacement(this.buffer[start], counter);
						counter++;
						count--;
						continue;
					}
				}
				offset--;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(char target, int offset, OnReplacementListener replacement, int start, int end) {
		int counter = 1;
		for (; start < end; start++) {
			if (target == this.buffer[start]) {
				if (offset == 0) {
					this.buffer[start] = replacement.onReplacement(this.buffer[start], counter);
					counter++;
					continue;
				}
				offset--;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(char target, OnReplacementListener replacement, int start, int end) {
		int counter = 1;
		for (; start < end; start++) {
			if (target == this.buffer[start]) {
				this.buffer[start] = replacement.onReplacement(this.buffer[start], counter);
				counter++;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(char target, int offset, OnReplacementListener replacement, int count) {
		int counter = 1;
		for (int start = 0; start < this.size; start++) {
			if (target == this.buffer[start]) {
				if (offset == 0) {
					if (count != 0) {
						this.buffer[start] = replacement.onReplacement(this.buffer[start], counter);
						counter++;
						count--;
						continue;
					}
				}
				offset--;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(char target, int offset, OnReplacementListener replacement) {
		int counter = 1;
		for (int start = 0; start < this.size; start++) {
			if (target == this.buffer[start]) {
				if (offset == 0) {
					this.buffer[start] = replacement.onReplacement(this.buffer[start], counter);
					counter++;
					continue;
				}
				offset--;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(char target, OnReplacementListener replacement, int count) {
		int counter = 1;
		for (int start = 0; start < this.size; start++) {
			if (target == this.buffer[start]) {
				if (count != 0) {
					this.buffer[start] = replacement.onReplacement(this.buffer[start], counter);
					counter++;
					count--;
				}
			}
		}
		return this;
	}

	public CharArrayBuilder replace(char target, OnReplacementListener replacement) {
		int counter = 1;
		for (int start = 0; start < this.size; start++) {
			if (target == this.buffer[start]) {
				this.buffer[start] = replacement.onReplacement(this.buffer[start], counter);
				counter++;
			}
		}
		return this;
	}

	// B Replace:

	public CharArrayBuilder replace(OnTargetListener target, int offset, char replacement, int count, int start, int end) {
		for (; start < end; start++) {
			if (target.onTarget(this.buffer[start])) {
				if (offset == 0) {
					if (count != 0) {
						this.buffer[start] = replacement;
						count--;
						continue;
					}
				}
				offset--;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(OnTargetListener target, int offset, char replacement, int start, int end) {
		for (; start < end; start++) {
			if (target.onTarget(this.buffer[start])) {
				if (offset == 0) {
					this.buffer[start] = replacement;
					continue;
				}
				offset--;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(OnTargetListener target, char replacement, int start, int end) {
		for (; start < end; start++) {
			if (target.onTarget(this.buffer[start])) {
				this.buffer[start] = replacement;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(OnTargetListener target, int offset, char replacement, int count) {
		for (int start = 0; start < this.size; start++) {
			if (target.onTarget(this.buffer[start])) {
				if (offset == 0) {
					if (count != 0) {
						this.buffer[start] = replacement;
						count--;
						continue;
					}
				}
				offset--;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(OnTargetListener target, int offset, char replacement) {
		for (int start = 0; start < this.size; start++) {
			if (target.onTarget(this.buffer[start])) {
				if (offset == 0) {
					this.buffer[start] = replacement;
					continue;
				}
				offset--;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(OnTargetListener target, char replacement, int count) {
		for (int start = 0; start < this.size; start++) {
			if (target.onTarget(this.buffer[start])) {
				if (count != 0) {
					this.buffer[start] = replacement;
					count--;
				}
			}
		}
		return this;
	}

	public CharArrayBuilder replace(OnTargetListener target, char replacement) {
		for (int start = 0; start < this.size; start++) {
			if (target.onTarget(this.buffer[start])) {
				this.buffer[start] = replacement;
			}
		}
		return this;
	}

	// C Replace:

	public CharArrayBuilder replace(char target, int offset, char replacement, int count, int start, int end) {
		for (; start < end; start++) {
			if (target == this.buffer[start]) {
				if (offset == 0) {
					if (count != 0) {
						this.buffer[start] = replacement;
						count--;
						continue;
					}
				}
				offset--;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(char target, int offset, char replacement, int start, int end) {
		for (; start < end; start++) {
			if (target == this.buffer[start]) {
				if (offset == 0) {
					this.buffer[start] = replacement;
					continue;
				}
				offset--;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(char target, char replacement, int start, int end) {
		for (; start < end; start++) {
			if (target == this.buffer[start]) {
				this.buffer[start] = replacement;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(char target, int offset, char replacement, int count) {
		for (int start = 0; start < this.size; start++) {
			if (target == this.buffer[start]) {
				if (offset == 0) {
					if (count != 0) {
						this.buffer[start] = replacement;
						count--;
						continue;
					}
				}
				offset--;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(char target, int offset, char replacement) {
		for (int start = 0; start < this.size; start++) {
			if (target == this.buffer[start]) {
				if (offset == 0) {
					this.buffer[start] = replacement;
					continue;
				}
				offset--;
			}
		}
		return this;
	}

	public CharArrayBuilder replace(char target, char replacement, int count) {
		for (int start = 0; start < this.size; start++) {
			if (target == this.buffer[start]) {
				if (count != 0) {
					this.buffer[start] = replacement;
					count--;
				}
			}
		}
		return this;
	}

	public CharArrayBuilder replace(char target, char replacement) {
		for (int start = 0; start < this.size; start++) {
			if (target == this.buffer[start]) {
				this.buffer[start] = replacement;
			}
		}
		return this;
	}

	public int[] indexOfAll(OnIndexListener target, int start, int end) {
		int[] preIndex = new int[8];
		int index = 0;
		int resizeCount = 0;
		int moreSpace;

		for (;start < end; start++) {
			if (target.onIndex(this.buffer[start])) {
				if (index >= preIndex.length) {
					moreSpace = resizeCount >= 15 ? preIndex.length * 7 / 2 :
							resizeCount >= 35 ? preIndex.length * 8 :
							resizeCount >= 75 ? preIndex.length * 25 :
							preIndex.length * 7 / 4;
					int[] newPreIndex = new int[moreSpace];
					System.arraycopy(preIndex, 0, newPreIndex, 0, preIndex.length);
					preIndex = newPreIndex;
					resizeCount++;
				}
				preIndex[index] = start;
				index++;
			}
		}

		int[] newPreIndex = new int[index];
		System.arraycopy(preIndex, 0, newPreIndex, 0, index);

		return newPreIndex;
	}

	public int[] indexOfAll(OnIndexListener target, int end) {
		int[] preIndex = new int[8];
		int index = 0;
		int resizeCount = 0;
		int moreSpace;

		for (int start = 0; start < end; start++) {
			if (target.onIndex(this.buffer[start])) {
				if (index >= preIndex.length) {
					moreSpace = resizeCount >= 15 ? preIndex.length * 7 / 2 :
							resizeCount >= 35 ? preIndex.length * 8 :
							resizeCount >= 75 ? preIndex.length * 25 :
							preIndex.length * 7 / 4;
					int[] newPreIndex = new int[moreSpace];
					System.arraycopy(preIndex, 0, newPreIndex, 0, preIndex.length);
					preIndex = newPreIndex;
					resizeCount++;
				}
				preIndex[index] = start;
				index++;
			}
		}

		int[] newPreIndex = new int[index];
		System.arraycopy(preIndex, 0, newPreIndex, 0, index);

		return newPreIndex;
	}

	public int[] indexOfAll(OnIndexListener target) {
		int[] preIndex = new int[8];
		int index = 0;
		int resizeCount = 0;
		int moreSpace;

		for (int start = 0; start < this.size; start++) {
			if (target.onIndex(this.buffer[start])) {
				if (index >= preIndex.length) {
					moreSpace = resizeCount >= 15 ? preIndex.length * 7 / 2 :
							resizeCount >= 35 ? preIndex.length * 8 :
							resizeCount >= 75 ? preIndex.length * 25 :
							preIndex.length * 7 / 4;
					int[] newPreIndex = new int[moreSpace];
					System.arraycopy(preIndex, 0, newPreIndex, 0, preIndex.length);
					preIndex = newPreIndex;
					resizeCount++;
				}
				preIndex[index] = start;
				index++;
			}
		}

		int[] newPreIndex = new int[index];
		System.arraycopy(preIndex, 0, newPreIndex, 0, index);

		return newPreIndex;
	}

	public int[] indexOfAll(char target, int start, int end) {
		int[] preIndex = new int[8];
		int index = 0;
		int resizeCount = 0;
		int moreSpace;

		for (;start < end; start++) {
			if (target == this.buffer[start]) {
				if (index >= preIndex.length) {
					moreSpace = resizeCount >= 15 ? preIndex.length * 7 / 2 :
							resizeCount >= 35 ? preIndex.length * 8 :
							resizeCount >= 75 ? preIndex.length * 25 :
							preIndex.length * 7 / 4;
					int[] newPreIndex = new int[moreSpace];
					System.arraycopy(preIndex, 0, newPreIndex, 0, preIndex.length);
					preIndex = newPreIndex;
					resizeCount++;
				}
				preIndex[index] = start;
				index++;
			}
		}

		int[] newPreIndex = new int[index];
		System.arraycopy(preIndex, 0, newPreIndex, 0, index);

		return newPreIndex;
	}

	public int[] indexOfAll(char target, int end) {
		int[] preIndex = new int[8];
		int index = 0;
		int resizeCount = 0;
		int moreSpace;

		for (int start = 0; start < end; start++) {
			if (target == this.buffer[start]) {
				if (index >= preIndex.length) {
					moreSpace = resizeCount >= 15 ? preIndex.length * 7 / 2 :
							resizeCount >= 35 ? preIndex.length * 8 :
							resizeCount >= 75 ? preIndex.length * 25 :
							preIndex.length * 7 / 4;
					int[] newPreIndex = new int[moreSpace];
					System.arraycopy(preIndex, 0, newPreIndex, 0, preIndex.length);
					preIndex = newPreIndex;
					resizeCount++;
				}
				preIndex[index] = start;
				index++;
			}
		}

		int[] newPreIndex = new int[index];
		System.arraycopy(preIndex, 0, newPreIndex, 0, index);

		return newPreIndex;
	}

	public int[] indexOfAll(char target) {
		int[] preIndex = new int[8];
		int index = 0;
		int resizeCount = 0;
		int moreSpace;

		for (int start = 0; start < this.size; start++) {
			if (target == this.buffer[start]) {
				if (index >= preIndex.length) {
					moreSpace = resizeCount >= 15 ? preIndex.length * 7 / 2 :
							resizeCount >= 35 ? preIndex.length * 8 :
							resizeCount >= 75 ? preIndex.length * 25 :
							preIndex.length * 7 / 4;
					int[] newPreIndex = new int[moreSpace];
					System.arraycopy(preIndex, 0, newPreIndex, 0, preIndex.length);
					preIndex = newPreIndex;
					resizeCount++;
				}
				preIndex[index] = start;
				index++;
			}
		}

		int[] newPreIndex = new int[index];
		System.arraycopy(preIndex, 0, newPreIndex, 0, index);

		return newPreIndex;
	}

	public int indexOfFirst(OnIndexListener target, int start, int end) {
		for (;start < end; start++) {
			if (target.onIndex(this.buffer[start])) {
				return start;
			}
		}
		return -1;
	}

	public int indexOfFirst(OnIndexListener target, int end) {
		for (int start = 0; start < end; start++) {
			if (target.onIndex(this.buffer[start])) {
				return start;
			}
		}
		return -1;
	}

	public int indexOfFirst(OnIndexListener target) {
		for (int start = 0; start < this.size; start++) {
			if (target.onIndex(this.buffer[start])) {
				return start;
			}
		}
		return -1;
	}

	public int indexOfFirst(char target, int start, int end) {
		for (;start < end; start++) {
			if (target == this.buffer[start]) {
				return start;
			}
		}
		return -1;
	}

	public int indexOfFirst(char target, int end) {
		for (int start = 0; start < end; start++) {
			if (target == this.buffer[start]) {
				return start;
			}
		}
		return -1;
	}

	public int indexOfFirst(char target) {
		for (int start = 0; start < this.size; start++) {
			if (target == this.buffer[start]) {
				return start;
			}
		}
		return -1;
	}

	public int indexOfLast(OnIndexListener target, int start, int end) {
		start = (this.size - start) - 1;
		int tmp = end == 0 ? 0 : (end - this.size);
		end = tmp >= 0 ? tmp : -tmp;
		for (;start > end; start--) {
			if (target.onIndex(this.buffer[start])) {
				return start;
			}
		}
		return -1;
	}

	public int indexOfLast(OnIndexListener target, int end) {
		int start = this.size - 1;
		int tmp = end == 0 ? 0 : (end - this.size);
		end = tmp >= 0 ? tmp : -tmp;
		for (;start > end; start--) {
			if (target.onIndex(this.buffer[start])) {
				return start;
			}
		}
		return -1;
	}

	public int indexOfLast(OnIndexListener target) {
		int start = this.size - 1;
		int end = 0;
		for (;start > end; start--) {
			if (target.onIndex(this.buffer[start])) {
				return start;
			}
		}
		return -1;
	}

	public int indexOfLast(char target, int start, int end) {
		start = (this.size - start) - 1;
		System.out.println(start + ": start");
		int tmp = end == 0 ? 0 : (end - this.size);
		end = tmp >= 0 ? tmp : -tmp;
		for (;start > end; start--) {
			if (target == this.buffer[start]) {
				return start;
			}
		}
		return -1;
	}

	public int indexOfLast(char target, int end) {
		int start = this.size - 1;
		int tmp = end == 0 ? 0 : (end - this.size);
		end = tmp >= 0 ? tmp : -tmp;
		for (;start > end; start--) {
			if (target == this.buffer[start]) {
				return start;
			}
		}
		return -1;
	}

	public int indexOfLast(char target) {
		int start = this.size - 1;
		int end = 0;
		for (;start > end; start--) {
			if (target == this.buffer[start]) {
				return start;
			}
		}
		return -1;
	}

	public boolean contains(OnContainsListener target) {
		for (int start = 0; start < this.size; start++) {
			if (target.onContains(this.buffer[start])) {
				return true;
			}
		}
		return false;
	}

	public boolean contains(char target) {
		for (int start = 0; start < this.size; start++) {
			if (target == this.buffer[start]) {
				return true;
			}
		}
		return false;
	}

	public CharArrayBuilder repeat(int count) {
		char[] target = new char[this.size * count];
		int oldSize = 0;
		int realSize = this.size;

		for (int i = 0; i < count; i++) {
			System.arraycopy(this.buffer, 0, target, oldSize, realSize);
			oldSize += this.size;
		}

		this.buffer = target;
		this.size = this.size * count;

		return this;
	}

	public CharArrayBuilder deleteCharacterAt(int index) {
		this.buffer[index] = '\0'; // de quita la letra, pero no se remueve
		return this;
	}

	public char getCharacterAt(int index) {
		return this.buffer[index];
	}

	public int getSize() {
		return this.size;
	}

	public int getLength() {
		return this.buffer.length;
	}

	public char[] getBuffer() {
		return this.buffer;
	}

	@Override
	public String toString() {
		return new String(buffer, 0, this.size);
	}

	// -------------------- Functions : Ended -------------------- \\

	// -------------------- Class : Start -------------------- \\

	public static abstract class OnAppendedWatcherListener {
		public abstract void onAppendedWatcher(char[] old, int oldSize, char[] current, int currentSize, char[] insert);
	}

	public static abstract class OnReplacementListener {
		public abstract char onReplacement(char target, int count);
	}

	public static abstract class OnTargetListener {
		public abstract boolean onTarget(char current);
	}

	public static abstract class OnIndexListener {
		public abstract boolean onIndex(char current);
	}

	public static abstract class OnContainsListener {
		public abstract boolean onContains(char current);
	}

	// -------------------- Class : Ended -------------------- \\
}
