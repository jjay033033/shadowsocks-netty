package org.netty.util;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * 
 * @author LMoon
 * @date 2018年1月25日
 *
 */
public final class CommonUtil {
    private CommonUtil() {
    }

    public static byte[] md5Digest(byte[] input) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            return md5.digest(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] concat(byte[] a, byte[] b) {
        byte[] result = Arrays.copyOf(a, a.length + b.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

}
