package kz.project.carrental.util;

import junit.framework.TestCase;

public class InputUtilTest extends TestCase {

    private final String[] VALID_INT_VALUES = {"1", "0", "-100", "3253"};
    private final String[] INVALID_INT_VALUES = {"1-", "-0-", "d100", "32k53"};

    public InputUtilTest(String name) {
        super(name);
    }

    public void testIsInt() throws Exception {
        for (String value : VALID_INT_VALUES) {
            assertEquals(InputUtil.isInt(value), true);
        }

        for (String value : INVALID_INT_VALUES) {
            assertEquals(InputUtil.isInt(value), false);
        }
    }

    public void testToInt() throws Exception {
        for (String value : VALID_INT_VALUES) {
            assertNotNull(InputUtil.strToInt(value));
            assertEquals(InputUtil.strToInt(value), Integer.valueOf(value));
        }

        for (String value : INVALID_INT_VALUES) {
            assertNull(InputUtil.strToInt(value));
        }

    }

    //TODO test strToTimestamp
}
