package cryptomessenger;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;


public class Crypter implements Serializable {
    private byte[] AES_KEY = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6};
    private byte[] AES_IV = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6};
    private byte[] DES_KEY = {1, 2, 3, 4, 5, 6, 7, 8};
    private byte[] DES_IV = {1, 2, 3, 4, 5, 6, 7, 8};

    private IvParameterSpec iv;
    private SecretKey secretKey;
    private byte[] encryptedMessage = null;
    private String mode = null;
    private String method = null;


    public byte[] encyrpt(String message, String method, String mode) {
        byte[] iv = null;
        byte[] key = null;

        try {
            /**
             * message
             */
            byte[] messageByteArr = message.getBytes();
            /**
             * iv and key for modes
             */
            if (method.equals("AES")) {
                iv = AES_IV;
                key = AES_KEY;
            } else {
                iv = DES_IV;
                key = DES_KEY;
            }

            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            SecretKey secretKey = new SecretKeySpec(key, method);

            Cipher encyrpt = Cipher.getInstance(method + "/" + mode + "/PKCS5Padding");
            encyrpt.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            this.encryptedMessage = encyrpt.doFinal(messageByteArr);
            this.method=method;
            this.mode=mode;
            return encyrpt.doFinal(messageByteArr);
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            System.out.println("[ERROR] Invalid Key Size");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("[ERROR] Argument Length");
            System.exit(0);

        }
        return encryptedMessage;

    }

    public String decyrpt() {
        String decyriptedMessage = null;
        byte[] iv = null;
        byte[] key = null;
        try {
            if (method.equals("AES")) {
                iv = AES_IV;
                key = AES_KEY;
            } else {
                iv = DES_IV;
                key = DES_KEY;
            }
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            SecretKey secretKey = new SecretKeySpec(key, method);

            Cipher cipher = Cipher.getInstance(this.method + "/" + this.mode + "/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

            byte[] original = cipher.doFinal(this.encryptedMessage);
            decyriptedMessage = new String(original);

            return decyriptedMessage;
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            System.out.println("[ERROR] Invalid Key Size");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("[ERROR] Argument Length");
            System.exit(0);

        }
        return decyriptedMessage;
    }

    public byte[] getEncryptedMessage() {
        return encryptedMessage;
    }

    public void setEncryptedMessage(byte[] encryptedMessage) {
        this.encryptedMessage = encryptedMessage;
    }
}
