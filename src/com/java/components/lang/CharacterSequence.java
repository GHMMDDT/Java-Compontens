package com.java.components.lang;

public abstract class CharacterSequence {
	protected char[] buffer;
	protected int size;

	public abstract char[] getCharacterArray();

	public abstract int getSize();

	public abstract char getCharacterAt(int index);
}
