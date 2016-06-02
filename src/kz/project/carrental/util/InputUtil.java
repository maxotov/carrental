package kz.project.carrental.util;

import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Simplifies the conversion of string data entered by the user, the data type required.
 */
public class InputUtil {

    private static final Logger LOGGER = Logger.getLogger(InputUtil.class);

    /**
     * Checks whether a string is a integer.
     *
     * @param str string to check.
     * @return true - if str is integer, or else false.
     */
    public static boolean isInt(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            LOGGER.error(null, e);
            return false;
        }

    }

    /**
     * Convert the string representation of the integer to a integer value.
     *
     * @param str string to convert.
     * @return integer value if it is possible to convert a string into a integer, or else null.
     */
    public static Integer strToInt(String str) {
        if (str == null) {
            return null;
        }
        Integer result = null;
        try {
            result = Integer.valueOf(str);
        } catch (NumberFormatException e) {
            LOGGER.error(null, e);
        }
        return result;
    }


    /**
     * Convert the string representation of the double to a double value.
     *
     * @param str string to convert.
     * @return double value if it is possible to convert a string into a double, or else null.
     */
    public static Double strToDouble(String str) {
        if (str == null) {
            return null;
        }
        Double result = null;
        try {
            result = Double.valueOf(str);
        } catch (NumberFormatException e) {
            LOGGER.error(null, e);
        }
        return result;
    }

    /**
     * Convert the string representation of the timestamp to a timestamp value.
     *
     * @param str    string to convert.
     * @param format timestamp format.
     * @return timestamp value if it is possible to convert a string into a timestamp, or else null.
     */
    public static Timestamp strToTimestamp(String str, String format) {
        Timestamp timestamp = null;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            timestamp = new Timestamp(formatter.parse(str).getTime());
        } catch (ParseException e) {
            LOGGER.error(null, e);
        }

        return timestamp;
    }

    /**
     * Convert the string representation of the boolean to a boolean value.
     *
     * @param str string to convert.
     * @return boolean value if it is possible to convert a string into a boolean, or else null.
     */
    public static Boolean strToBoolean(String str) {
        if (str == null) {
            return null;
        }
        Boolean result = Boolean.valueOf(str);
        return result;
    }
}