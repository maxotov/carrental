package kz.project.carrental.util;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Provides the necessary methods to deal with the md5 hash.
 */
public class MD5Util {

    private static final Logger LOGGER = Logger.getLogger(MD5Util.class);

    /**
     * Returns hash md5 of string.
     *
     * @param str string to get hash MD5
     * @return hash MD5 of str
     * @throws IllegalArgumentException - if passed str parameter is null.
     */
    public static String getHashMD5(String str) {
        if (str == null){
            throw new IllegalArgumentException("String can't be null");
        }
        MessageDigest md5;
        StringBuffer hexString = new StringBuffer();
        try {
            md5 = MessageDigest.getInstance("md5");
            md5.reset();
            md5.update(str.getBytes());
            byte messageDigest[] = md5.digest();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(null, e);
            return e.toString();
        }
        return hexString.toString();
    }
}
