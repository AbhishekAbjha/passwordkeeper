package com.coronate.passwordkeeper.util;

import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Crypt {

    private final static String ALGORITHM = "AES";
    private final static String CIPHERALALGORITHM = "AES/ECB/PKCS5Padding";

    public static String encrypt(String encryptionKey, String inputToEncrypt){

        if(encryptionKey == null || inputToEncrypt == null){
            return null;
        }

        try{
            Cipher cipher = Cipher.getInstance(CIPHERALALGORITHM);
            SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(), ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.encodeToString(cipher.doFinal(inputToEncrypt.getBytes()), Base64.DEFAULT);
        }catch(Exception ex){
            Log.e("Error while encrypting", ex.toString());
            return null;
        }
    }

    public static String decrypt(String encryptionKey, String inputToDecrypt){
        try{
            Cipher cipher = Cipher.getInstance(CIPHERALALGORITHM);
            SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(), ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.decode(inputToDecrypt.getBytes(), Base64.DEFAULT)));
        }catch(Exception ex){
            Log.e("Error while decrypting", ex.toString());
            return null;
        }
    }
}
