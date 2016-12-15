package com.androidbelieve.drawerwithswipetabs;


import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *Class to provide 128-bit AES Encryption made for Android
 *To use, Create an Instance of the class passing a String argument which will act as the key
 *To encrypt a String, call the encrypt function, which returns the base 64 encoded encrypted String
 *To decrypt a Base64 encoded String, call the decrypt function which will return the original String
 */
public class EncryptDecrypt {

    private SecretKeySpec keySpec;
    private Cipher cipher;

    private byte ivparam[]={86,-33,-30,-93,121,-111,44,-86,103,-5,-109,-24,-9,51,7,-74};        //IvParamter passed to the cipher during encryption/decryption . Keep constant
    private IvParameterSpec ivspec=new IvParameterSpec(ivparam);


    public EncryptDecrypt(String passphrase) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException {
        byte[] bytesOfMessage = passphrase.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(bytesOfMessage);
        keySpec = new SecretKeySpec(Arrays.copyOf(digest,16), "AES");           //Use only the first 128 bits of the key
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    }


    public String encrypt (String plaintxt) throws Exception{
        cipher.init(Cipher.ENCRYPT_MODE, keySpec,ivspec);
        byte[] ciphertxt =  cipher.doFinal(plaintxt.getBytes());
        return Base64.encodeToString(ciphertxt, Base64.DEFAULT);       //Byte Arrays cant be saved So encode to base 64
    }

    public String decrypt (String ciphertxt) throws Exception{         //Not really needed for Android Part
        cipher.init(Cipher.DECRYPT_MODE, keySpec,ivspec);
        String plaintxt =new String( cipher.doFinal(Base64.decode(ciphertxt, Base64.DEFAULT)));
        return plaintxt;
    }

/*
private void encryptinMain()          //An example of how to call this class
    {
        try {
            EncryptDecrypt encryption=new EncryptDecrypt("KARTHIK");
         
            String encryptedmessage=encryption.encrypt("Message to encrypt");

            String decryptmessage=encryption.decrypt(encryptedmessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

}
