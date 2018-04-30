package top.linjt.shiro.chapter6.util;

import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.HashService;
import top.linjt.shiro.chapter6.pojo.User;

public class EncryptPassword {

    private static final int ITERATIONS = 2;
    private static final String ALGORITHM_NAME = "MD5";
//    private static final String SALT = "qazwsxsedcdrfvtgbh";


    public static void encryptPassword(User user) {

        HashService service = new DefaultHashService();
        HashRequest request = new HashRequest.Builder()
                .setIterations(ITERATIONS)
                .setAlgorithmName(ALGORITHM_NAME)
                .setSalt(user.getUsername() + user.getSalt())
                .setSource(user.getPassword())
                .build();
        String hash =  service.computeHash(request).toHex();

        user.setPassword(hash);
    }
}