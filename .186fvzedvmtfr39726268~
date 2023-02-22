/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2013-2021. All rights reserved.
 */

package com.dsware.om.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;

/**
 * AES256加密解密,使用openssl命令行的AES256加密解密算法实现
 *
 * @author z53810
 * @version [V100R003C00, 2013-01-23]
 */
public final class AES256Util {
    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AES256Util.class);

    /**
     * 十六进制255
     */
    private static final int HEX_255 = 0xFF;

    /**
     * 十进制2
     */
    private static final int DECIMAL_2 = 2;

    /**
     * 十进制16
     */
    private static final int DECIMAL_16 = 16;

    /**
     * 用于兼容老版本的IV长度
     */
    public static final int OLD_IV_LENGTH = 8;

    /**
     * AES加密算法的IV长度
     */
    public static final int AES_IV_LENGTH = 16;

    /**
     * 密钥长度
     */
    private static final int KEY_LENGTH = 64;

    /**
     * 代码中硬编码的根密钥的部分内容
     */
    private static final String KEY_PART = "4A806B6522A2B3B1129A9DDD6B4E84E305590EF0226356779E8C2B82E984FF4C"
        + "088043ABD011B7663F1BE5C65E0797BCE3295C7A21EA5044C4E9F2F60046B2FC";

    /**
     * 根密钥的部分内容的字符集，0-9A-Z，用于加密场景下生成随机的根密钥
     */
    private static final char[] KEY_PART_CHARACTER_SET = "0123456789ABCDEF".toCharArray();

    /**
     * 根密钥的部分内容的长度， 由于是16进制的字符串，所以是根密钥长度的2倍，128
     */
    private static final int KEY_PART_LENGTH = KEY_LENGTH * DECIMAL_2;

    /**
     * 根密钥
     */
    private static String sslKey = "";

    /**
     * 构造方法，用于消除checkstyle:
     * 工具类使用私有构造方法，可以禁止实例化
     */
    private AES256Util() {
    }

    /**
     * 初始化根密钥，使用AES256Util解密之前，需要先调用此方法
     *
     * @param keyPart 根密钥的部分内容，来自dsware-api.properties配置文件的ssl.key.part配置项
     */
    public static void init(String keyPart) {
        if (keyPart == null || keyPart.length() < KEY_PART_LENGTH) {
            LOGGER.error(" AES256Util init error");
            return;
        }
        char[] keyPartHexCharArray1 = KEY_PART.toCharArray();
        char[] keyPartHexCharArray2 = keyPart.toCharArray();
        byte[] keyHex = new byte[KEY_LENGTH];
        int[] keyPart1 = new int[KEY_LENGTH];
        int[] keyPart2 = new int[KEY_LENGTH];
        for (int i = 0; i < KEY_LENGTH; i++) {
            keyPart1[i] = Integer.valueOf(String.valueOf(keyPartHexCharArray1, i * DECIMAL_2, DECIMAL_2), DECIMAL_16);
            keyPart2[i] = Integer.valueOf(String.valueOf(keyPartHexCharArray2, i * DECIMAL_2, DECIMAL_2), DECIMAL_16);
            keyHex[i] = (byte) (keyPart1[i] ^ keyPart2[i]);
        }
        sslKey = new String(keyHex, StandardCharsets.UTF_8);
    }

    /**
     * 初始化根密钥，使用AES256Util加密之前，需要先调用此方法
     * 需要将返回值保存到dsware-api.properties配置文件的ssl.key.part配置项中，解密时需要用到该配置项
     *
     * @return 根密钥的部分内容，随机生成，长度为64字节（128个16进制字符，字符集0-9A-Z）
     */
    public static String init() {
        // 随机生成新的根密钥，64个16进制字符，字符集0-9A-Z，再根据根密钥计算生成对应的根密钥的部分内容
        SecureRandom random = new SecureRandom();
        StringBuilder sbSslKey = new StringBuilder();
        StringBuilder sbKeyPart = new StringBuilder();
        int[] keyHex = new int[KEY_LENGTH];
        char[] keyPartHexCharArray1 = KEY_PART.toCharArray();
        int[] keyPart1 = new int[KEY_LENGTH];
        byte[] keyPart2 = new byte[KEY_LENGTH];
        for (int i = 0; i < KEY_LENGTH; i++) {
            char c = KEY_PART_CHARACTER_SET[random.nextInt(KEY_PART_CHARACTER_SET.length)];
            sbSslKey.append(c);
            keyHex[i] = c;
            keyPart1[i] = Integer.valueOf(String.valueOf(keyPartHexCharArray1, i * DECIMAL_2, DECIMAL_2), DECIMAL_16);
            keyPart2[i] = (byte) (keyPart1[i] ^ keyHex[i]);
            String hex = Integer.toHexString(keyPart2[i] & HEX_255);
            if (hex.length() == 1) {
                sbKeyPart.append('0');
            }
            sbKeyPart.append(hex.toUpperCase(Locale.US));
        }
        sslKey = sbSslKey.toString();
        return sbKeyPart.toString();
    }

    /**
     * openssl解密
     *
     * @param encryptContent 需要解密的密文
     * @param ivLength 需要解密的密文的IV长度
     * @return 解密结果
     */
    public static String decryptAesByShell(String encryptContent, int ivLength) {
        LOGGER.debug("Enter decryptAesByShell");
        // 解密结果
        String eResult = "";
        if (null == encryptContent) {
            LOGGER.error(" AES256Util decryptAesByShell error, encryptContent is null");
            return eResult;
        }
        if (ivLength != OLD_IV_LENGTH && ivLength != AES_IV_LENGTH) {
            LOGGER.error(" AES256Util decryptAesByShell error, IV length is invalid");
            return eResult;
        }
        String key = getSSLKey();
        // Shell解密的命令信息
        StringBuilder command = new StringBuilder();
        String ivValue = encryptContent.substring(0, ivLength * DECIMAL_2);
        command.append("echo '")
            .append(encryptContent.substring(ivLength * DECIMAL_2))
            .append("' | openssl aes-256-cbc -d -K ");
        command.append(key).append(" -iv ").append(ivValue).append(" -base64");
        // 执行shell命令进行加密
        String[] commands = new String[] {"bash", "-c", command.toString()};
        // 执行shell命令进行加密
        ShellResult result = ShellUtils.runShell(commands);
        if (null == result) {
            LOGGER.error("Execute decrypt shell inner error");
            return eResult;
        }
        if (ShellResult.SUCCESS != result.getExitValue()) {
            LOGGER.error("Execute decrypt shell, return value:" + result.getExitValue());
            return eResult;
        }
        List<String> resultList = result.getReturnValue();
        if (null == resultList || resultList.isEmpty()) {
            LOGGER.error("Execute decrypt shell inner error, resultList is null or size is zero");
            return eResult;
        }
        return resultList.get(0);
    }

    /**
     * openssl加密
     * 存在安全问题，请不要再使用
     * 仅用于FSM代码构建编译兼容，FSM代码适配修改后会删除该方法
     *
     * @Deprecated
     * @param content 待加密的内容
     * @return String 加密后的内容
     */
    public static String encryptAesByShell(String content) {
        return encryptAesByShellInner(content, OLD_IV_LENGTH);
    }

    /**
     * openssl加密
     * 使用该加密方法dsware-api.properties文件中的密码，需要确保在保存新密文的同时，保存密文对应的IV长度为AES_IV_LENGTH
     *
     * @param content 待加密的内容
     * @return String 加密后的内容
     */
    public static String encryptAesByShellNew(String content) {
        return encryptAesByShellInner(content, AES_IV_LENGTH);
    }

    /**
     * openssl加密
     *
     * @param content 待加密的内容
     * @param ivLength 加密使用的IV长度
     * @return String 加密后的内容
     */
    public static String encryptAesByShellInner(String content, int ivLength) {
        LOGGER.debug("Enter encryptAesByShell");

        // 加密结果
        String eResult = "";
        if (null == content) {
            LOGGER.error(" AES256Util encryptAesByShell error");
            return eResult;
        }

        String key = getSSLKey();
        // Shell加密的命令信息
        StringBuilder command = new StringBuilder();
        command.append("echo ").append(ShellUtils.getEscapeString(content)).append(" | openssl aes-256-cbc -K ");
        command.append(key);
        String ivValue = generateShellIV(ivLength);
        command.append(" -iv ").append(ivValue).append(" -base64");
        // 执行shell命令进行加密
        ShellResult result = ShellUtils.runShell(command.toString());
        if (null == result) {
            LOGGER.error("Execute encrypt shell inner error");
            return eResult;
        }
        if (ShellResult.SUCCESS != result.getExitValue()) {
            LOGGER.error("Execute encrypt shell, return value:" + result.getExitValue());
            return eResult;
        }
        List<String> resultList = result.getReturnValue();
        if (null == resultList || resultList.isEmpty()) {
            LOGGER.error("Execute encrypt shell inner error, resultList is null or size is zero");
            return eResult;
        }
        return ivValue + resultList.get(0);
    }

    /**
     * 生成随机IV
     *
     * @param ivLength IV的长度
     * @return 随机IV值，长度为ivLength字节（ivLength*2个16进制字符，字符集0-9A-Z）
     */
    private static String generateShellIV(int ivLength) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ivLength * DECIMAL_2; i++) {
            sb.append(KEY_PART_CHARACTER_SET[random.nextInt(KEY_PART_CHARACTER_SET.length)]);
        }
        return sb.toString();
    }

    /**
     * 将二进制转换十六进制
     *
     * @param buf 字节数组
     * @return 字符串
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & HEX_255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase(Locale.US));
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr 字符串
     * @return 字节数组
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return new byte[0];
        }
        byte[] result = new byte[hexStr.length() / DECIMAL_2];
        for (int i = 0; i < hexStr.length() / DECIMAL_2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * DECIMAL_2, i * DECIMAL_2 + 1), DECIMAL_16);
            int low = Integer.parseInt(hexStr.substring(i * DECIMAL_2 + 1, i * DECIMAL_2 + DECIMAL_2), DECIMAL_16);
            result[i] = (byte) (high * DECIMAL_16 + low);
        }
        return result;
    }

    /**
     * 获取AES加解密密钥
     *
     * @return 密钥
     */
    private static String getSSLKey() {
        return sslKey;
    }
}
