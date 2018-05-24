package net.pubnative.opengdprclient.util;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class Crypto {

    private static final String TAG = Crypto.class.getSimpleName();

    /**
     * Encrypts the given input string using SHA-1 algorithm
     *
     * @param input String to be encrypted
     * @return Encrypted string
     */
    public static String sha1(String input) {
        String result = "";
        if (!TextUtils.isEmpty(input)) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-1");
                byte[] bytes = input.getBytes("UTF-8");
                digest.update(bytes, 0, bytes.length);
                bytes = digest.digest();
                for (final byte b : bytes) {
                    stringBuilder.append(String.format("%02X", b));
                }
                result = stringBuilder.toString().toLowerCase(Locale.US);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Encrypts the given input string using SHA-256 algorithm
     *
     * @param input String to be encrypted
     * @return Encrypted string
     */
    public static String sha256(String input) {
        String result = "";
        if (!TextUtils.isEmpty(input)) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(input.getBytes());
                byte[] bytes = md.digest();
                for (byte byt : bytes) {
                    stringBuilder.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
                }
                result = stringBuilder.toString().toLowerCase(Locale.US);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Encrypts the given input string using md5 algorithm
     *
     * @param input String to be encrypted
     * @return Encrypted string
     */
    public static String md5(String input) {
        String result = "";

        if (!TextUtils.isEmpty(input)) {
            try {
                // Create MD5 Hash
                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.update(input.getBytes());
                byte messageDigest[] = digest.digest();
                // Create Hex String
                StringBuilder hexString = new StringBuilder();
                for (int i = 0; i < messageDigest.length; i++) {
                    String h = Integer.toHexString(0xFF & messageDigest[i]);
                    while (h.length() < 2) {
                        h = "0" + h;
                    }
                    hexString.append(h);
                }
                result = hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
