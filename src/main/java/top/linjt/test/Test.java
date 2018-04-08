package top.linjt.test;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.util.SimpleByteSource;

import java.io.UnsupportedEncodingException;

public class Test {

    private static final String STR = "hello world!";
    private static final String SALT = "121";

    public static void main(String[] args) throws UnsupportedEncodingException {
//        base64ToString();
//        hexToString();

        String[] ss = new String[]{"121","212"
        };
        for (String s : ss) {
            System.out.println(s);
        }
        MD5();
//        SHA256();
//        simpleHash();
        hashService();
    }


    public static void base64ToString(){
        String base64 = Base64.encodeToString(STR.getBytes());

        String str2 = Base64.decodeToString(base64);

        assert str2.equals(STR):"不对";
    }

    public static void hexToString(){
        String hex = Hex.encodeToString(STR.getBytes());
        String str2 = new String(Hex.decode(hex));
        assert str2.equals(STR):"不对!";
    }

    public static void MD5() throws UnsupportedEncodingException {

        Md5Hash md5Hash = new Md5Hash(STR.getBytes(),SALT);
        System.out.println(md5Hash.toString());
    }

    public static  void SHA256(){
        Sha256Hash sha = new Sha256Hash(STR, SALT);
        System.out.println(sha);
    }

    public static void simpleHash(){
        SimpleHash simpleHash = new SimpleHash("SHA-256", STR, SALT);
        System.out.println(simpleHash.toString());
    }

    public static void hashService(){
        DefaultHashService hashService = new DefaultHashService();
//        hashService.setHashAlgorithmName("MD5");
//        hashService.setPrivateSalt(new SimpleByteSource(SALT));
//        hashService.setHashIterations(1);
//        hashService.setGeneratePublicSalt(true);
//        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());

        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5")
                .setSalt(SALT)
                .setSource(STR)
                .setIterations(1)
                .build();
        System.out.println(hashService.computeHash(request).toString());
    }
}
