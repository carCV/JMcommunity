package com.jmlee.community.util;

import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jmLee
 */
public final class JasyptUtil {

    private static final Logger logger = LoggerFactory.getLogger(JasyptUtil.class);

    private static final String salt = "jmlee**";
    private static BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();

    static {
        basicTextEncryptor.setPassword(salt);
    }

    private JasyptUtil() {}

    /**
     * 明文加密
     * @param plainText
     * @return
     */
    public static String encode(String plainText) {
        String cipherText = basicTextEncryptor.encrypt(plainText);
        return cipherText;
    }

    /**
     * 解密
     * @param cipherText
     * @return
     */
    public static String decode(String cipherText) {
        cipherText = "ENC(" + cipherText +")";
        if (PropertyValueEncryptionUtils.isEncryptedValue(cipherText)) {
            String plainText = PropertyValueEncryptionUtils.decrypt(cipherText,basicTextEncryptor);
            return plainText;
        }

        logger.info("解密失败");
        return "";
    }



}
