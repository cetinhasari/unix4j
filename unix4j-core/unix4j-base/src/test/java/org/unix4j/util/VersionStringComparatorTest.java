package org.unix4j.util;

import org.junit.Test;
import org.unix4j.util.sort.VersionStringComparator;

import java.util.Comparator;

/**
 * Unit test for {@link VersionStringComparator}.
 */
public class VersionStringComparatorTest extends AbstractStringComparatorTest {

	@Override
	protected Comparator<? super String> initComparator() {
		return VersionStringComparator.INSTANCE;
	}

	@Test
	public void testEqual() {
		assertEqual("V1", "V1");
		assertEqual("V1.1", "V1.1");
		assertEqual("V1:1", "V1:1");
		assertEqual("V-1", "V-1");

		assertEqual(" V1", "V1");
		assertEqual("V1.1", "V1.1 ");
		assertEqual("V1:1\n", "V1:1\t");
		assertEqual("    V-1  ", "   V-1 ");
	}

	@Test
	public void testLargeIntegers() {
		assertEqual("873948723948192384791283491283749812374981273948170984320548739845", "873948723948192384791283491283749812374981273948170984320548739845");
		assertSmaller("873948723948192384791283491283749812374981273948170984320548739845", "873948723948192384791283491283749812374981273948170984320548739846");
		assertGreater("V-873948723948192384791283491283749812374981273948170984320548739845", "V-873948723948192384791283491283749812374981273948170984320548739844");

		assertEqual("V49502948350938450983540698094560934506983405968.873948723948192384791283491283749812374981273948170984320548739845", "V49502948350938450983540698094560934506983405968.873948723948192384791283491283749812374981273948170984320548739845");
		assertSmaller("V49502948350938450983540698094560934506983405968.873948723948192384791283491283749812374981273948170984320548739845", "V49502948350938450983540698094560934506983405968.873948723948192384791283491283749812374981273948170984320548739846");
		assertGreater("V-0049502948350938450983540698094560934506983405968.873948723948192384791283491283749812374981273948170984320548739845", "V-0049502948350938450983540698094560934506983405968.873948723948192384791283491283749812374981273948170984320548739844");
	}


	@Test
	public void testMultiGroupStrings() {
		assertGreater("1:000.005", "1:000.004");
		assertSmaller("1:000.005", "1:000.006");
		assertGreater("11:000.005", "7:000.005");
		assertSmaller("9:000.005", "20:000.005");
		assertGreater("1-0-0-0-0-0-5", "1-0-0-0-0-0-4");
		assertSmaller("1-0-0-0-0-0-5", "1-0-0-0-0-0-6");
		assertGreater("1-0-0-0-0-0-5", "1-0-0-0-0-0");
		assertGreater("1-0-0-0-0-0-abc", "1-0-0-0-0-0-aba");
		assertGreater("1-0-0-0-0-0-abc", "1-0-0-0-0-0-ab");
		assertSmaller("1-0-0-0-0-0-012", "1-0-0-0-0-0-abc");
		assertSmaller("1-0-0-0-0-0-012", "1-0-0-0-0-0-___");
	}

	@Test
	public void testPrefixedVersionStrings() {
		assertGreater("V:1.23.456", "V:1.23.455");
		assertGreater("V:1.23.456", "V:1.22.457");
		assertGreater("V:1.23.456", "V:0.23.456");
		assertGreater("Z:1.23.456", "V:1.23.456");

		assertGreater("V_1.2.456", "V_1.01.456");
		assertGreater("V_1.22.456", "V_1.11.456");
		assertGreater("V_1.22.456", "V_0.22.456");
		assertGreater("V_9.22.456", "V_8.22.456");
		assertGreater("V_11.22.456", "V_9.22.456");
	}
}