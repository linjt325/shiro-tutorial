package top.linjt.shiro.chapter5;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.hash.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.Key;

public class CodecAndCryptoTest {
    private final String STR = "hello world!";
    private final String SALT = "121";


    /*
    base64转字符串
     */
    @Test
    public void base64ToString() {
        String base64 = Base64.encodeToString(STR.getBytes());

        String str2 = Base64.decodeToString(base64);

        assert str2.equals(STR) : "不对";
    }

    /*
    hex转字符串
    */
    @Test
    public void hexToString() {
        String hex = Hex.encodeToString(STR.getBytes());
        String str2 = new String(Hex.decode(hex));
        assert str2.equals(STR) : "不对!";
    }

    /*
    将字符串按md5算法进行散列计算
     */
    @Test
    public void MD5() throws UnsupportedEncodingException {

        Md5Hash md5Hash = new Md5Hash(STR.getBytes(), SALT);
        System.out.println(md5Hash.toString());
    }

    /*
    SHA256散列计算
     */
    @Test
    public void SHA256() {
        Sha256Hash sha = new Sha256Hash(STR, SALT);
        System.out.println(sha);
    }

    /*
    shiro提供通用实现
    可以指定散列计算的算法,和盐
     */
    @Test
    public void simpleHash() {
        SimpleHash simpleHash = new SimpleHash("SHA-256", STR, SALT);
        System.out.println(simpleHash.toString());
    }

    /*
    shiro 提供hashservice
    可以对hashRequest进一步进行散列计算
     */
    @Test
    public void hashService() {
        DefaultHashService hashService = new DefaultHashService();//默认 SHA-512
//        hashService.setHashAlgorithmName("MD5");
//        hashService.setPrivateSalt(new SimpleByteSource(SALT)); //私盐 默认 无
//        hashService.setHashIterations(1);//生成hash值 迭代次数
//        hashService.setGeneratePublicSalt(true); //设置是否自动生成公盐 默认false
//        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator()); //用于生成公盐   此为默认值

        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5")
                .setSalt(SALT)
                .setSource(STR)
                .setIterations(1)
                .build();
        System.out.println(hashService.computeHash(request).toString());
    }

    @Test
    public void testAesCipherService(){
        AesCipherService aesService = new AesCipherService();
        aesService.setKeySize(128);

        Key key = aesService.generateNewKey();

        //encrypt
        String hex = aesService.encrypt(STR.getBytes(), key.getEncoded()).toHex();
        //decrypt
        String str = new String(aesService.decrypt(Hex.decode(hex), key.getEncoded()).getBytes());

        assert str.equals(STR);

    }

}
