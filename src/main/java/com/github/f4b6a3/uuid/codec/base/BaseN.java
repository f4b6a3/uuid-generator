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

package com.github.f4b6a3.uuid.codec.base;

/**
 * Enumeration that lists the base-n encodings of this package.
 */
public enum BaseN {

	BASE_16(16, 32, true), //
	BASE_32(32, 26, true), //
	BASE_64(64, 22, false); // only base-64 is case sensitive

	private int number;
	private int length;
	private boolean insensitive;

	/**
	 * @param number      the base number and also the alphabet size
	 * @param length      the string length of the encoded UUID
	 * @param insensitive a flag indicating whether the base-n is case insensitive
	 */
	BaseN(int number, int length, boolean insensitive) {
		this.number = number;
		this.length = length;
		this.insensitive = insensitive;
	}

	public int getNumber() {
		return number;
	}

	public int getLength() {
		return length;
	}

	public boolean isInsensitive() {
		return insensitive;
	}
}