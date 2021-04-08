/*
 * MIT License
 * 
 * Copyright (c) 2018-2020 Fabio Lima
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.f4b6a3.uuid.codec.base.function;

import java.util.UUID;
import java.util.function.Function;

import com.github.f4b6a3.uuid.codec.base.BaseN;
import com.github.f4b6a3.uuid.exception.UuidCodecException;

/**
 * Abstract function to be extended by all decoder functions of this package.
 * 
 * If the base-n is case insensitive, it decodes in lower case and upper case.
 * 
 * See: https://tools.ietf.org/html/rfc4648
 */
public abstract class BaseNDecoder implements Function<String, UUID> {

	protected final BaseN base;

	// if case insensitive:
	// alphabet 0 is lower case
	// alphabet 1 is upper case
	protected final char[][] alphabets = new char[2][];

	// ASCII map:
	// each char is mapped to a value
	protected final long[] map = new long[128];

	/**
	 * @param base     an enumeration that represents the base-n encoding
	 * @param alphabet a string that contains the base-n alphabet
	 */
	public BaseNDecoder(BaseN base, String alphabet) {

		this.base = base;

		if (base.isInsensitive()) {
			// if case insensitive...
			this.alphabets[0] = alphabet.toLowerCase().toCharArray();
			this.alphabets[1] = alphabet.toUpperCase().toCharArray();
		} else {
			// else: the alphabets are equal
			this.alphabets[0] = alphabet.toCharArray();
			this.alphabets[1] = alphabet.toCharArray();
		}

		// initialize the map with -1
		for (int i = 0; i < this.map.length; i++) {
			this.map[i] = -1;
		}
		// map the alphabets chars to values
		for (int i = 0; i < this.alphabets[0].length; i++) {
			this.map[this.alphabets[0][i]] = i;
			this.map[this.alphabets[1][i]] = i;
		}
	}

	protected char[] toCharArray(String string) {
		char[] chars = string == null ? null : string.toCharArray();
		validate(chars);
		return chars;
	}

	private void validate(char[] chars) {
		if (chars == null || chars.length != base.getLength()) {
			throw new UuidCodecException("Invalid string: \"" + (chars == null ? null : new String(chars)) + "\"");
		}
		for (int i = 0; i < chars.length; i++) {
			boolean found = false;
			for (int j = 0; j < alphabets[0].length; j++) {
				// check if the char is in one of the lower and upper alphabets
				if (chars[i] == alphabets[0][j] || chars[i] == alphabets[1][j]) {
					found = true;
				}
			}
			if (!found) {
				throw new UuidCodecException("Invalid string: \"" + (new String(chars)) + "\"");
			}
		}
	}
}