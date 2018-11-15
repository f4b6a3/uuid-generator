package com.github.f4b6a3.uuid.strategy.msb;

public class SequentialMostSignificantBitsStrategy implements MostSignificantBitsStrategy {

	/**
	 * Returns the timestamp bits of the UUID in the natural order of bytes.
	 * 
	 * @param timestamp
	 */
	@Override
	public long getMostSignificantBits(long timestamp, long counter) {
		
		long himid = (timestamp & 0x0ffffffffffff000L) << 4;
		long low = (timestamp & 0x0000000000000fffL);
		
		return (himid | low);
	}

}