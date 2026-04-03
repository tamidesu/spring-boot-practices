package kz.com.java_component.users;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserGravatar {

    public static String getGravatarUrlFromEmail(String email) {
        return String.format("https://www.gravatar.com/avatar/%s?d=wavatar", md5Hex(email));
    }

    private static String md5Hex(String message) {
        try {
            byte[] bytes = MessageDigest.getInstance("MD5")
                    .digest(message.getBytes(Charset.forName("CP1252")));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
