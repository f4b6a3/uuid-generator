package com.github.f4b6a3.uuid.strategy.timestamp;

import org.junit.Test;

import com.github.f4b6a3.uuid.exception.UuidCreatorException;
import com.github.f4b6a3.uuid.strategy.timestamp.DefaultTimestampStrategy;
import com.github.f4b6a3.uuid.util.UuidTimeUtil;

import static org.junit.Assert.*;

public class DefaultTimestampStrategyTest {

	@Test
	public void testNextForTheCounterIncrement() {

		// It should increment if the new timestamp is EQUAL TO the old timestamp
		long oldTimestamp = UuidTimeUtil.getCurrentTimestamp();
		long newTimestamp = oldTimestamp;
		DefaultTimestampStrategy timestampCounter = new DefaultTimestampStrategy();
		timestampCounter.getNextCounter(oldTimestamp);
		long oldCounter = timestampCounter.getNextCounter(oldTimestamp);
		long newCounter = timestampCounter.getNextCounter(newTimestamp);
		assertEquals(oldCounter + 1, newCounter);

		// It shouldn't increment if the new timestamp is LOWER THAN the old timestamp
		oldTimestamp = UuidTimeUtil.getCurrentTimestamp();
		newTimestamp = oldTimestamp - 1;
		timestampCounter = new DefaultTimestampStrategy();
		timestampCounter.getNextCounter(oldTimestamp);
		oldCounter = timestampCounter.getNextCounter(oldTimestamp);
		newCounter = timestampCounter.getNextCounter(newTimestamp);
		assertEquals(oldCounter, newCounter);

		// It shouldn't increment if the new timestamp is GREATER THAN the old timestamp
		oldTimestamp = UuidTimeUtil.getCurrentTimestamp();
		newTimestamp = oldTimestamp + 1;
		timestampCounter = new DefaultTimestampStrategy();
		timestampCounter.getNextCounter(oldTimestamp);
		oldCounter = timestampCounter.getNextCounter(oldTimestamp);
		newCounter = timestampCounter.getNextCounter(newTimestamp);
		assertEquals(oldCounter, newCounter);
	}

	@Test
	public void testNextCounterAnOverrunExceptionShouldBeThrown() {

		long timestamp = UuidTimeUtil.getCurrentTimestamp();
		DefaultTimestampStrategy timestampStrategy = new DefaultTimestampStrategy();

		long offset = timestampStrategy.getNextCounter(timestamp);
		long max = DefaultTimestampStrategy.TimestampCounter.COUNTER_MAX - offset;

		// Generate MAX values
		for (int i = 0; i < max; i++) {
			timestampStrategy.getNextCounter(timestamp);
		}

		try {
			timestampStrategy.getNextCounter(timestamp);
			fail("It should throw an overrun exception when reaching the maximum value.");
		} catch (UuidCreatorException e) {
			// success
		}
	}
}
