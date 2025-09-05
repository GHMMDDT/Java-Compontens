package com.java.components.lang;

import java.awt.desktop.SystemEventListener;

/*
 * Created by: ASimplerUser, ByUser, TheCreator & AOtherUser
 */
@SuppressWarnings({ "all" })
public class CharacterArrayBuilder extends CharacterSequence {
	// -------------------- Field : Started -------------------- \\

	private char[] buffer;
	private int size;

	// -------------------- Field : Ended -------------------- \\

	// -------------------- Constructor : Started -------------------- \\

	public CharacterArrayBuilder(char[] target, int offset, int preCapacity) {
		if (offset < 0 || offset >= target.length) throw new IllegalArgumentException("Index of bound: " + offset);

		this.buffer = new char[target.length - offset + preCapacity];

		System.arraycopy(target, offset, this.buffer, 0, target.length - offset);

		this.size = this.buffer.length;
	}

	public CharacterArrayBuilder(char[] target, int offset) {
		this.buffer = new char[target.length - offset];

		System.arraycopy(target, offset, this.buffer, 0, this.buffer.length);

		this.size = this.buffer.length;
	}

	public CharacterArrayBuilder(char[] target) {
		this.buffer = new char[target.length];

		System.arraycopy(target, 0, this.buffer, 0, this.buffer.length);

		this.size = this.buffer.length;
	}

	public CharacterArrayBuilder(String target) {
		char[] subTarget = target.toCharArray();

		this.buffer = new char[subTarget.length];

		System.arraycopy(subTarget, 0, this.buffer, 0, this.buffer.length);

		this.size = this.buffer.length;
	}

	public CharacterArrayBuilder(int preCapacity) {
		this.buffer = new char[preCapacity];
	}

	public CharacterArrayBuilder(CharacterSequence other) {
		this.buffer = other.buffer;
		this.size = other.size;
	}

	public CharacterArrayBuilder() {
		this.buffer = new char[0];
	}

	// -------------------- Constructor : Ended -------------------- \\

	// -------------------- Functions : Started -------------------- \\

	public CharacterArrayBuilder append(char[] text) {
		if (text == null || text.length == 0) return this;

		if (this.size + text.length >= this.buffer.length) {
			char[] newBuffer = new char[Math.max((int) (buffer.length * 1.75), size + text.length)];
			System.arraycopy(this.buffer, 0, newBuffer, 0, this.size);
			System.arraycopy(text, 0, newBuffer, this.size, text.length);

			this.buffer = newBuffer;
			this.size += text.length;
		} else {
			System.arraycopy(text, 0, this.buffer, this.size, text.length);
			this.size += text.length;
		}

		return this;
	}

	public CharacterArrayBuilder append(CharacterSequence text) {
		if (text == null || text.getSize() == 0) return this;

		if (this.size + text.getSize() >= this.buffer.length) {
			char[] newBuffer = new char[Math.max((int) (buffer.length * 1.75), size + text.getSize())];
			System.arraycopy(this.buffer, 0, newBuffer, 0, this.size);
			System.arraycopy(text, 0, newBuffer, this.size, text.getSize());

			this.buffer = newBuffer;
			this.size += text.getSize();
		} else {
			System.arraycopy(text, 0, this.buffer, this.size, text.getSize());
			this.size += text.getSize();
		}

		return this;
	}

	public CharacterArrayBuilder append(char text) {
		if (this.size + 1 >= this.buffer.length) {
			char[] newBuffer = new char[Math.max((int) (buffer.length * 1.75), size + 1)];
			System.arraycopy(this.buffer, 0, newBuffer, 0, this.size);
			System.arraycopy(text, 0, newBuffer, this.size, 1);

			this.buffer = newBuffer;
			this.size += 1;
		} else {
			this.buffer[this.size] = text;
			this.size += 1;
		}

		return this;
	}

	public CharacterArrayBuilder append(String text) {
		if (text == null || text.isEmpty()) return this;

		if (this.size + text.length() >= this.buffer.length) {
			char[] newBuffer = new char[Math.max((int) (buffer.length * 1.75), size + text.length())];
			System.arraycopy(this.buffer, 0, newBuffer, 0, this.size);
			System.arraycopy(text.toCharArray(), 0, newBuffer, this.size, text.length());

			this.buffer = newBuffer;
			this.size += text.length();
		} else {
			System.arraycopy(text.toCharArray(), 0, this.buffer, this.size, text.length());
			this.size += text.length();
		}

		return this;
	}

	public CharacterArrayBuilder append(boolean text) {
		char[] subText = text ? new char[] { 't', 'r', 'u', 'e' } : new char[] { 'f', 'a', 'l', 's', 'e' };

		if (this.size + subText.length >= this.buffer.length) {
			char[] newBuffer = new char[Math.max((int) (buffer.length * 1.75), size + subText.length)];
			System.arraycopy(this.buffer, 0, newBuffer, 0, this.size);
			System.arraycopy(text, 0, newBuffer, this.size, subText.length);

			this.buffer = newBuffer;
			this.size += subText.length;
		} else {
			System.arraycopy(subText, 0, this.buffer, this.size, subText.length);
			this.size += subText.length;
		}

		return this;
	}

	public CharacterArrayBuilder append(Number text) {
		char[] preText = String.valueOf(text).toCharArray();
		if (this.size + preText.length >= this.buffer.length) {
			char[] newBuffer = new char[Math.max((int) (buffer.length * 1.75), size + preText.length)];
			System.arraycopy(this.buffer, 0, newBuffer, 0, this.size);
			System.arraycopy(preText, 0, newBuffer, this.size, preText.length);

			this.buffer = newBuffer;
			this.size += preText.length;
		} else {
			System.arraycopy(preText, 0, this.buffer, this.size, preText.length);
			this.size += preText.length;
		}

		return this;
	}

	public CharacterArrayBuilder replace(OnTargetListener target, int offset, OnReplacementListener replacement, OnCountReplacementListener count, int start, int end, ReplaceDirectionType direction) {
		int[] counter = new int[] { 1 /* count of replace character */, 1 /* count of replacement */ };
		if (direction == ReplaceDirectionType.EndToStart) {
			start = (this.buffer.length - start) - 1;
			int tmp = (end - this.buffer.length);
			end = tmp >= 0 ? tmp : -tmp;
			for (; start > end; start--) {
				if (target.onTarget(this.buffer[start])) {
					if (offset == 0) {
						if (count.count != 0) {
							if (count.onCountReplacement(counter[1])) {
								this.buffer[start] = replacement.onReplacement(this.buffer[start], counter[0]);
								count.count--;
								counter[0]++;
							}
							counter[1]++;
							continue;
						}
						break;
					}
					offset--;
				}
			}
		} else {
			for (; start < end; start++) {
				if (target.onTarget(this.buffer[start])) {
					if (offset == 0) {
						if (count.onCountReplacement(counter[1])) {
							if (count.count != 0) {
								this.buffer[start] = replacement.onReplacement(this.buffer[start], counter[0]);
								counter[0]++;
								count.count--;
							}
							counter[1]++;
							continue;
						}
						break;
					}
					offset--;
				}
			}
		}
		return this;
	}

	public CharacterArrayBuilder replaceFirst(OnTargetListener target, int offset, OnReplaceListener replacement, int start, int end) {
		for (; start < end; start++) {
			if (target.onTarget(this.buffer[start])) {
				if (offset == 0) {
					this.buffer[start] = replacement.onReplace(this.buffer[start]);
					break;
				}
				offset--;
			}
		}
		return this;
	}
	public CharacterArrayBuilder replaceLast(OnTargetListener target, int offset, OnReplaceListener replacement, int start, int end) {
		start = (this.buffer.length - start) - 1;
		int tmp = (end - this.buffer.length);
		end = tmp >= 0 ? tmp : -tmp;
		for (; start > end; start--) {
			if (target.onTarget(this.buffer[start])) {
				if (offset == 0) {
					this.buffer[start] = replacement.onReplace(this.buffer[start]);
					break;
				}
				offset--;
			}
		}
		return this;
	}

	public CharacterArrayBuilder[] split(OnSplitListener target, int start, int end, SplitDirectionType direction) {
		int[] preIndex = new int[8];
		int index = 0;
		int resizeCount = 0;
		int moreSpace;

		for (;start < end; start++) {
			if (target.onSplit(this.buffer[start])) {
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

		CharacterArrayBuilder[] splitter = new CharacterArrayBuilder[index + 1];

		int prev;
		if (direction == SplitDirectionType.EndToStart) {
			prev = this.size;
			for (int i = newPreIndex.length - 1, j = 0; i > -1; i--, j++) {
				splitter[j] = subCharArrayBuilder(newPreIndex[i] + 1, prev);
				prev = newPreIndex[i];
			}
			splitter[newPreIndex.length] = subCharArrayBuilder(0, prev);
		} else if (direction == SplitDirectionType.StartToEnd) {
			prev = 0;
			for (int i = 0; i < newPreIndex.length; i++) {
				splitter[i] = subCharArrayBuilder(prev, newPreIndex[i]);
				prev = newPreIndex[i] + 1;
			}
			splitter[newPreIndex.length] = subCharArrayBuilder(prev, this.buffer.length);
		}

		return splitter;
	}

	public int[] indexOfAll(OnIndexListener target, int offset, int start, int end, IndexDirectionType direction) {
		int[] preIndex = new int[8];
		int index = 0;
		int resizeCount = 0;
		int moreSpace;

		if (direction == IndexDirectionType.EndToStart) {
			start = (this.buffer.length - start) - 1;
			int tmp = (end - this.buffer.length);
			end = tmp >= 0 ? tmp : -tmp;
			for (; start > end; start--) {
				if (target.onIndex(this.buffer[start])) {
					if (offset == 0) {
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
						continue;
					}
					offset--;
				}
			}
		} else if (direction == IndexDirectionType.StartToEnd) {
			for (;start < end; start++) {
				if (target.onIndex(this.buffer[start])) {
					if (offset == 0) {
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
						continue;
					}
					offset--;
				}
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

	public CharacterArrayBuilder subCharArrayBuilder(int start, int end) {
		char[] sub = new char[end - start];
		System.arraycopy(this.buffer, start, sub, 0, end - start);
		return new CharacterArrayBuilder(sub);
	}

	public boolean contains(OnContainsListener target) {
		for (int start = 0; start < this.size; start++) {
			if (target.onContains(this.buffer[start])) {
				return true;
			}
		}
		return false;
	}

	public CharacterArrayBuilder repeat(int count) {
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

	public CharacterArrayBuilder deleteCharacterAt(int index) {
		char[] newBuffer = new char[this.size - 1];

		System.arraycopy(this.buffer, 0, newBuffer, 0, index);
		System.arraycopy(this.buffer, index, newBuffer, index, this.size - index);

		this.buffer = newBuffer;
		this.size = this.buffer.length;

		return this;
	}

	public CharacterArrayBuilder reverse() {
		char[] newBuffer = new char[this.size];

		for (int i = 0; i < this.size; i++) {
			newBuffer[i] = this.buffer[(this.size - 1) - i];
		}

		this.buffer = newBuffer;
		return this;
	}

	public char getCharacterAt(int index) {
		return this.buffer[index];
	}

	public int getLength() {
		return this.buffer.length;
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public String toString() {
		return new String(buffer, 0, this.size);
	}

	@Override
	public char[] getCharacterArray() {
		return this.buffer;
	}

	// -------------------- Functions : Ended -------------------- \\

	// -------------------- Class : Start -------------------- \\

	public static abstract class OnReplacementListener {
		public abstract char onReplacement(char target, int count);
	}

	public static abstract class OnReplaceListener {
		public abstract char onReplace(char target);
	}

	public static abstract class OnCountReplacementListener {
		private int count;

		protected OnCountReplacementListener(int count) {
			this.count = count;
		}

		public abstract boolean onCountReplacement(int count);
	}

	public static abstract class OnTargetListener {
		public abstract boolean onTarget(char current);
	}

	public static abstract class OnOffsetTargetListener {
		private int offset;

		protected OnOffsetTargetListener(int offset) {
			this.offset = offset;
		}

		public abstract boolean onOffsetTarget(int count);
	}

	public static abstract class OnSplitListener {
		public abstract boolean onSplit(char current);
	}

	public static abstract class OnIndexListener {
		public abstract boolean onIndex(char current);
	}

	public static abstract class OnContainsListener {
		public abstract boolean onContains(char current);
	}

	// -------------------- Class : Ended -------------------- \\

	// -------------------- Enum : Start -------------------- \\

	public enum ReplaceDirectionType {
		StartToEnd,
		EndToStart;
	}

	public enum IndexDirectionType {
		StartToEnd,
		EndToStart;
	}

	public enum SplitDirectionType {
		StartToEnd,
		EndToStart;
	}

	// -------------------- Enum : Ended -------------------- \\
}
