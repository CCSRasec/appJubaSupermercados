package jubasupermercados.jubasupermercados.jubasupermercados;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Cryptografa {
    public static String encrypt(String plainText, String password)    throws
            NoSuchAlgorithmException,
            InvalidKeySpecException,
            NoSuchPaddingException,
            InvalidParameterSpecException,
            IllegalBlockSizeException,
            BadPaddingException,
            UnsupportedEncodingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException
    {
        String senha = null;
        byte[] saltBytes = senha.getBytes("UTF-8");
        String initializationVector = null;
        byte[] ivBytes = initializationVector.getBytes("UTF-8");
        SecretKeyFactory factory =   SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        int pswdIterations = 0;
        int keySize = 0;
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                saltBytes,
                pswdIterations,
                keySize
        );

        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(ivBytes));
        byte[] encryptedTextBytes =  cipher.doFinal(plainText.getBytes("UTF-8"));
        return Base64.encodeToString(encryptedTextBytes, 0);

    }
}
