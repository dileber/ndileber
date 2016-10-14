package com.drcosu.ndileber.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by H2 on 2016/10/14.
 */
public class HSafe {

    public static enum HashMethod {
        sha1("SHA1"), sha512("SHA-512"), md5("MD5");
        final MessageDigest method;

        private HashMethod(String mn) {
            MessageDigest m = null;
            try {
                m = MessageDigest.getInstance(mn);
            } catch (NoSuchAlgorithmException ex) {

            } finally {
                method = m;
            }
        }
    }

    static final char[] byteToStr = new char[] { '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    // 7c4a8d09ca3762af61e59520943dc26494f8941b
    // e10adc3949ba59abbe56e057f20f883e
    // 7c4a8d09ca3762af61e59520943dc26494f8941b
    // e10adc3949ba59abbe56e057f20f883e
    public static String getHashValue(String str) {
        return getHashValue(str, HashMethod.md5);
    }

    public static String getHashValue(byte[] str, HashMethod sh1) {
        MessageDigest md = sh1.method;
        md.reset();
        md.update(str);
        final byte[] resultByte = md.digest();
        return encodeHex(resultByte);
    }

    public static String getHashValue(String str, HashMethod sh1) {
        MessageDigest md = sh1.method;
        md.reset();
        md.update(str.getBytes());
        final byte[] resultByte = md.digest();
        return encodeHex(resultByte);
    }

    public static String encodeHex(byte[] in) {
        int n = in.length, k = n * 2;
        int size = k >= 512 ? 512 : k;
        char[] re = new char[size];
        if (n > 0) {
            StringBuilder sb = new StringBuilder();
            int cur = 0;
            for (int i = 0; i < n; ++i) {
                int hc = (in[i] >> 4) & 0x0f;
                int lc = in[i] & 0x0f;
                re[cur] = byteToStr[hc];
                re[cur + 1] = byteToStr[lc];
                if (cur >= size) {
                    sb.append(re, 0, cur);
                    cur = 0;
                    // Arrays.fill(re, val);
                } else {
                    cur += 2;
                }
            }
            if (cur > 0) {
                sb.append(re, 0, cur);
            }
            return sb.toString();
        }
        return "";
    }

    // public static String asHex(byte[] b) {
    // StringBuffer buf = new StringBuffer();
    // for (int i = 0; i < b.length; i++) {
    // buf.append(Integer.toString((b[i] & 0xff) + 0x100, 16)
    // .substring(1));
    // }
    // String result = buf.toString();
    // return result;
    // }

    public static String getFileHashCode(String loc)
            throws FileNotFoundException, IOException {
        return getFileHashCode(loc, HashMethod.md5);
    }

    public static String getFileHashCode(String loc, HashMethod sh1)
            throws FileNotFoundException, IOException {
        MessageDigest md = sh1.method;
        FileInputStream fis = new FileInputStream(loc);
        try {
            byte[] dataBytes = new byte[1024];
            int nread = 0;
            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
            ;
            byte[] mdbytes = md.digest();
            // convert the byte to hex format
            // StringBuilder sb = new StringBuilder("");
            // for (int i = 0; i < mdbytes.length; i++) {
            // sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100,
            // 16).substring(1));
            // }
            return encodeHex(mdbytes);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

}
