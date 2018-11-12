package de.thro.inf.prg3.a07.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

class SampleTest {

	private final Logger logger;

	public SampleTest() {
		logger = LogManager.getLogger();
	}

	@Test
	void sampleTest() {
		logger.info("Hello from sample test");
	}
}
