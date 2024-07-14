package com.oyeks.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Service
public class EncryptionDecryptService {

    @Value("${app.secret.key}")
    private String key;

    public String encryptMessage(String message) {
        try {
            SecretKey secretKey = generateKey(key);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(message.getBytes("UTF-8"));
            return new String(org.bouncycastle.util.encoders.Base64.encode(encryptedBytes));
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String decryptMessage(String encryptedText) {
        try {
            SecretKey secretKey = generateKey(key);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encryptedBytes = org.bouncycastle.util.encoders.Base64.decode(encryptedText.getBytes());
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, "UTF-8");
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public SecretKey generateKey(String key) throws Exception {
        byte[] keyBytes = key.getBytes("UTF-8");
        return new SecretKeySpec(keyBytes, "AES");
    }
}
