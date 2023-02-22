package com.dsware.om.client.common;

import com.dsware.om.client.exception.DSwareErrorCode;
import com.dsware.om.client.exception.DSwareException;
import com.dsware.om.client.util.CommonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * <Description>
 *
 * @author  j55771
 * @version  [V100R001C00, 2012-6-8]
 */
public class CommonMessage {
    private static final int DEFAULT_START_POS = 0;

    private static final int DEFAULT_MSG_BODY_SIZE = 128;

    private static final int MAX_MSG_BODY_SIZE = 32 * 1024 * 1024;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonMessage.class);

    private int header;

    private int length;

    private byte[] msgBody;

    private int pos;

    /** <Default constructor>
     */
    public CommonMessage() {
        this.header = '\0';
        this.length = 0;
        this.pos = 0;
        this.msgBody = new byte[0];
    }

    /**
     * <Default constructor>
     *
     * @param header  command code or result code
     * @param length  length of msgBody
     * @param msgBody concrete message data zone
     */
    public CommonMessage(int header, int length, byte[] msgBody) {
        this.header = header;
        if (length <= 0) {
            this.length = 0;
            this.pos = 0;
            this.msgBody = new byte[0];
        } else {
            addByteArrayToMsgBody(msgBody, length);
        }
    }

    /**
     *
     * <复位消息byte偏移位置>
     * <p>消息偏移位置设置为起始位置</p>
     */
    public void resetPos() {
        pos = DEFAULT_START_POS;
    }

    /**
     *
     * <获取消息byte偏移位置>
     *
     * @param pos 偏移位置
     */
    public void setPos(int pos) {
        this.pos = pos;
    }

    /**
     *
     * <获取消息byte偏移位置>
     *
     * @return 当前pos位置
     */
    public int getPos() {
        return pos;
    }

    /**
     * <设置消息头>
     *
     * @return  header
     */
    public int getHeader() {
        return header;
    }

    /**
     * <获取消息头>
     *
     * @param header message header
     */
    public void setHeader(int header) {
        this.header = header;
    }

    /**
     * <获取消息长度>
     *
     * @return  length
     */
    public int getLength() {
        return length;
    }

    /**
     * <设置消息长度>
     *
     * @param length message len
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * <获取消息体>
     *
     * @return  msgBody
     */
    public byte[] getMsgBody() {
        return Arrays.copyOf(msgBody, msgBody.length);
    }

    /**
     * <设置消息体>
     *
     * @param msgBody message body
     */
    public void setMsgBody(byte[] msgBody) {
        this.msgBody = Arrays.copyOf(msgBody, msgBody.length);
    }

    /* * {@inheritDoc} */
    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder
                .append("CommonMessage [header=")
                .append(header)
                .append(", length=")
                .append(length)
                .append(", msgBody=")
                .append(Arrays.toString(msgBody))
                .append(']');
        return strBuilder.toString();
    }

    /**
     *
     * <构造消息体: 增加一个c语言int8类型数据到消息体>
     *
     * @param data          加入消息体的值
     * @param peerOrder     字节序
     */
    public void addByteToMsgBody(int data, ByteOrder peerOrder) {
        if (null == peerOrder) {
            peerOrder = ByteOrder.BIG_ENDIAN;
        }
        if (data > Byte.MAX_VALUE || data < Byte.MIN_VALUE) {
            throw new DSwareException(DSwareErrorCode.MAKE_MSG_ERROR, "[addByteToMsgBody] input data overflow");
        }
        byte int8 = (byte) data;
        byte[] array = CommonUtils.byteToByteArray(int8, peerOrder);
        if (null == array || array.length == 0) {
            throw new DSwareException(
                    DSwareErrorCode.MAKE_MSG_ERROR,
                    "[addByteToMsgBody] Failed to transform byte:[" + data + "] to byteArray");
        }

        addByteArrayToMsgBody(array, array.length);
    }

    /**
     *
     * <构造消息体: 增加一个c语言int16类型数据到消息体>
     *
     * @param data          加入消息体的值
     * @param peerOrder     字节序
     */
    public void addShortToMsgBody(int data, ByteOrder peerOrder) {
        if (null == peerOrder) {
            peerOrder = ByteOrder.BIG_ENDIAN;
        }
        if (data > Short.MAX_VALUE || data < Short.MIN_VALUE) {
            throw new DSwareException(DSwareErrorCode.MAKE_MSG_ERROR, "[addByteToMsgBody] input data overflow");
        }
        short int16 = (short) data;
        byte[] array = CommonUtils.shortToByteArray(int16, peerOrder);
        if (null == array || array.length == 0) {
            throw new DSwareException(
                    DSwareErrorCode.MAKE_MSG_ERROR, "[addShortToMsgBody] Failed to shortToByteArray value=" + int16);
        }

        addByteArrayToMsgBody(array, array.length);
    }

    /**
     *
     * <构造消息体: 增加一个int类型数据到消息体>
     *
     * @param data          加入消息体的值
     * @param peerOrder     字节序
     */
    public void addIntToMsgBody(int data, ByteOrder peerOrder) {
        if (null == peerOrder) {
            peerOrder = ByteOrder.BIG_ENDIAN;
        }

        byte[] array = CommonUtils.intToByteArray(data, peerOrder);
        if (null == array || array.length == 0) {
            throw new DSwareException(
                    DSwareErrorCode.MAKE_MSG_ERROR, "[addIntToMsgBody] Failed to int double=" + data + " to msg");
        }

        addByteArrayToMsgBody(array, array.length);
    }

    /**
     *
     * <构造消息体: 增加一个long类型数据到消息体>
     *
     * @param data          加入消息体的值
     * @param peerOrder     字节序
     */
    public void addLongToMsgBody(long data, ByteOrder peerOrder) {
        if (null == peerOrder) {
            peerOrder = ByteOrder.BIG_ENDIAN;
        }

        byte[] array = CommonUtils.longToByteArray(data, peerOrder);
        if (null == array || array.length == 0) {
            throw new DSwareException(
                    DSwareErrorCode.MAKE_MSG_ERROR, "[addLongToMsgBody] Failed to add long=" + data + " to msg");
        }

        addByteArrayToMsgBody(array, array.length);
    }

    /**
     *
     * <构造消息体: 增加一个float类型数据到消息体>
     *
     * @param data          加入消息体的值
     * @param peerOrder     字节序
     */
    public void addFloatToMsgBody(float data, ByteOrder peerOrder) {
        if (null == peerOrder) {
            peerOrder = ByteOrder.BIG_ENDIAN;
        }

        byte[] array = CommonUtils.floatToByteArray(data, peerOrder);
        if (null == array || array.length == 0) {
            throw new DSwareException(
                    DSwareErrorCode.MAKE_MSG_ERROR, "[addFloatToMsgBody] Failed to add float=" + data + " to msg");
        }

        addByteArrayToMsgBody(array, array.length);
    }

    /**
     *
     * <构造消息体: 增加一个double类型数据到消息体>
     *
     * @param data          加入消息体的值
     * @param peerOrder     字节序
     */
    public void addDoubleToMsgBody(double data, ByteOrder peerOrder) {
        if (null == peerOrder) {
            peerOrder = ByteOrder.BIG_ENDIAN;
        }

        byte[] array = CommonUtils.doubleToByteArray(data, peerOrder);
        if (null == array || array.length == 0) {
            throw new DSwareException(
                    DSwareErrorCode.MAKE_MSG_ERROR, "[addDoubleToMsgBody] Failed to add double=" + data + " to msg");
        }

        addByteArrayToMsgBody(array, array.length);
    }

    /**
     *
     * <构造消息体: 增加一个string类型数据到消息体>
     *
     * @param data          加入消息体的值
     * @param peerOrder     字节序
     */
    public void addStringToMsgBody(String data, ByteOrder peerOrder) {
        if (null == data || data.isEmpty()) {
            throw new DSwareException(DSwareErrorCode.MAKE_MSG_ERROR, "[addStringToMsgBody] parameter data is null");
        }

        if (null == peerOrder) {
            peerOrder = ByteOrder.BIG_ENDIAN;
        }

        byte[] array = CommonUtils.stringToByteArray(data, peerOrder);
        if (null == array || array.length == 0) {
            throw new DSwareException(
                    DSwareErrorCode.MAKE_MSG_ERROR, "[addStringToMsgBody] Failed to add string=" + data + " to msg");
        }

        addByteArrayToMsgBody(array, array.length);
    }

    /**
     *
     * <构造消息体: 增加一个char[]类型数据到消息体>
     *
     * @param data          加入消息体的值
     * @param peerOrder     字节序
     */
    public void addCharArrayToMsgBody(char[] data, ByteOrder peerOrder) {
        if (null == data) {
            throw new DSwareException(DSwareErrorCode.MAKE_MSG_ERROR, "[addCharArrayToMsgBody] parameter data is null");
        }

        if (null == peerOrder) {
            peerOrder = ByteOrder.BIG_ENDIAN;
        }

        byte[] array = CommonUtils.charArrayToByteArray(data, peerOrder);
        if (null == array || array.length == 0) {
            throw new DSwareException(
                    DSwareErrorCode.MAKE_MSG_ERROR, "Failed to add char[]=" + String.copyValueOf(data) + " to msg");
        }
        addByteArrayToMsgBody(array, array.length);
    }

    /**
     *
     * <构造消息体: 增加一个string类型数据到消息体>
     *
     * @param data          加入消息体的值
     * @param maxBytes      最大要写入的byte数(如果data转为byte之后,长度大于maxBytes,将按照maxBytes截取,如果小于maxBytes,将填充0)
     * @param peerOrder     字节序
     */
    public void addStringToMsgBody(String data, int maxBytes, ByteOrder peerOrder) {
        if (null == data || data.isEmpty()) {
            throw new DSwareException(DSwareErrorCode.MAKE_MSG_ERROR, "[addStringToMsgBody] parameter data is null");
        }

        if (maxBytes <= 0) {
            throw new DSwareException(
                    DSwareErrorCode.MAKE_MSG_ERROR, "[addStringToMsgBody] parameter maxBytes=" + maxBytes + " <= 0");
        }

        if (null == peerOrder) {
            peerOrder = ByteOrder.BIG_ENDIAN;
        }

        byte[] array = CommonUtils.stringToByteArray(data, peerOrder);
        if (null == array || array.length == 0) {
            throw new DSwareException(
                    DSwareErrorCode.MAKE_MSG_ERROR, "[addStringToMsgBody] Failed to add String=" + data + " to msg");
        }

        addByteArrayToMsgBody(array, maxBytes);
    }

    /**
     *
     * <构造消息体: 增加一个char[]类型数据到消息体>
     *
     * @param data          加入消息体的值
     * @param maxBytes      最大要写入的byte数(如果data转为byte之后,长度大于maxBytes,将按照maxBytes截取,如果小于maxBytes,将填充0)
     * @param peerOrder     字节序
     */
    public void addCharArrayToMsgBody(char[] data, int maxBytes, ByteOrder peerOrder) {
        if (null == data) {
            throw new DSwareException(DSwareErrorCode.MAKE_MSG_ERROR, "[addCharArrayToMsgBody] parameter data is null");
        }

        if (maxBytes <= 0) {
            throw new DSwareException(
                    DSwareErrorCode.MAKE_MSG_ERROR, "[addCharArrayToMsgBody] parameter maxBytes <= 0");
        }

        if (null == peerOrder) {
            peerOrder = ByteOrder.BIG_ENDIAN;
        }

        byte[] array = CommonUtils.charArrayToByteArray(data, peerOrder);
        if (null == array || array.length == 0) {
            throw new DSwareException(
                    DSwareErrorCode.MAKE_MSG_ERROR,
                    "[addCharArrayToMsgBody] Failed to add char[]=" + String.copyValueOf(data) + " to msg");
        }
        addByteArrayToMsgBody(array, maxBytes);
    }

    /**
     *
     * <添加字节数组到消息体中>
     *
     * @param value         字节数组
     * @param maxBytes      最大要写入的byte数(如果value长度大于maxBytes,将按照maxBytes截取,如果小于maxBytes,将填充0)
     */
    public final void addByteArrayToMsgBody(byte[] value, int maxBytes) {
        if (null == value) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[addByteArrayToMsgBody] parameter 'value' is null");
        }

        if (maxBytes <= 0) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[addByteArrayToMsgBody] parameter maxBytes <= 0");
        }

        if (length + maxBytes > MAX_MSG_BODY_SIZE) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR,
                    "[addByteArrayToMsgBody] message body will beyond maxSize="
                            + MAX_MSG_BODY_SIZE
                            + ", curLength="
                            + length
                            + ", newAddLength="
                            + maxBytes);
        }

        byte[] addByteArray = Arrays.copyOf(value, maxBytes);
        int curLen = 0;
        if (null != msgBody) {
            curLen = msgBody.length;
        }

        if (curLen < length + maxBytes) {
            int maxLen = calcMsgbodyMaxLen(maxBytes);
            byte[] newArray = new byte[maxLen];
            if (null != msgBody) {
                System.arraycopy(msgBody, DEFAULT_START_POS, newArray, DEFAULT_START_POS, pos);
            }
            msgBody = newArray;
        }

        System.arraycopy(addByteArray, DEFAULT_START_POS, msgBody, pos, addByteArray.length);
        pos += addByteArray.length;
        length += addByteArray.length;
    }

    /**
     *
     * <计算新消息体长度 按照原有长度的2倍来增加>
     *
     * @param addLen    需要增加的消息长度
     * @return
     */
    private int calcMsgbodyMaxLen(int addLen) {
        int baseLen = DEFAULT_MSG_BODY_SIZE;
        if (null != msgBody) {
            baseLen = (msgBody.length > DEFAULT_MSG_BODY_SIZE) ? msgBody.length : DEFAULT_MSG_BODY_SIZE;
        }

        if (addLen < 0) {
            return baseLen;
        }

        int maxLen = 0;
        for (int idx = 1; idx < Short.MAX_VALUE; idx++) {
            maxLen = idx * baseLen;
            if (maxLen > MAX_MSG_BODY_SIZE) {
                return MAX_MSG_BODY_SIZE;
            }

            if (maxLen > length + addLen) {
                return maxLen;
            }
        }

        return MAX_MSG_BODY_SIZE;
    }

    /**
     *
     * <解析消息体，从消息体中获取一个Byte>
     *
     * @param peerOrder 字节序
     * @return Byte
     */
    public Byte getByteFromMsgBody(ByteOrder peerOrder) {
        if (null == peerOrder) {
            peerOrder = ByteOrder.BIG_ENDIAN;
        }

        if (pos + CommonUtils.BYTE_JAVA_BYTES > length) {
            LOGGER.error(
                "[getByteFromMsgBody] Message remain is less than a byte length, pos:{}getLength:{} msg length:{}", pos,
                CommonUtils.BYTE_JAVA_BYTES, length);
            throw new DSwareException(DSwareErrorCode.PARSE_MSG_ERROR);
        }

        byte[] array = new byte[CommonUtils.BYTE_JAVA_BYTES];

        System.arraycopy(msgBody, pos, array, DEFAULT_START_POS, CommonUtils.BYTE_JAVA_BYTES);
        pos += CommonUtils.BYTE_JAVA_BYTES;

        return CommonUtils.byteArrayToByte(array, peerOrder);
    }

    /**
     *
     * <解析消息体，从消息体中获取一个Short>
     *
     * @param peerOrder 字节序
     * @return Short
     */
    public Short getShortFromMsgBody(ByteOrder peerOrder) {
        if (null == peerOrder) {
            peerOrder = ByteOrder.BIG_ENDIAN;
        }

        if (pos + CommonUtils.SHORT_JAVA_BYTES > length) {
            LOGGER.error(
                "[getShortFromMsgBody] Message remain is less than a short length, pos:{}getLength:{} msg length:{}",
                pos, CommonUtils.SHORT_JAVA_BYTES, length);
            throw new DSwareException(DSwareErrorCode.PARSE_MSG_ERROR);
        }

        byte[] array = new byte[CommonUtils.SHORT_JAVA_BYTES];

        System.arraycopy(msgBody, pos, array, DEFAULT_START_POS, CommonUtils.SHORT_JAVA_BYTES);
        pos += CommonUtils.SHORT_JAVA_BYTES;

        return CommonUtils.byteArrayToShort(array, peerOrder);
    }

    /**
     *
     * <解析消息体，从消息体中获取一个Int>
     *
     * @param peerOrder 字节序
     * @return Integer
     */
    public Integer getIntegerFromMsgBody(ByteOrder peerOrder) {
        if (null == peerOrder) {
            peerOrder = ByteOrder.BIG_ENDIAN;
        }

        if (pos + CommonUtils.INT_JAVA_BYTES > length) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR,
                    "[getIntegerFromMsgBody] Message remain is less than a int length, pos:"
                            + pos
                            + "getLength:"
                            + CommonUtils.INT_JAVA_BYTES
                            + " msg length:"
                            + length);
        }

        byte[] array = new byte[CommonUtils.INT_JAVA_BYTES];

        System.arraycopy(msgBody, pos, array, DEFAULT_START_POS, CommonUtils.INT_JAVA_BYTES);
        pos += CommonUtils.INT_JAVA_BYTES;

        return CommonUtils.byteArrayToInt(array, peerOrder);
    }

    /**
     *
     * <解析消息体，从消息体中获取一个Long>
     *
     * @param peerOrder 字节序
     * @return Long
     */
    public Long getLongFromMsgBody(ByteOrder peerOrder) {
        if (null == peerOrder) {
            peerOrder = ByteOrder.BIG_ENDIAN;
        }

        if (pos + CommonUtils.LONG_JAVA_BYTES > length) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR,
                    "[getLongFromMsgBody] Message remain is less than a int length, pos:"
                            + pos
                            + "getLength:"
                            + CommonUtils.LONG_JAVA_BYTES
                            + " msg length:"
                            + length);
        }

        byte[] array = new byte[CommonUtils.LONG_JAVA_BYTES];

        System.arraycopy(msgBody, pos, array, DEFAULT_START_POS, CommonUtils.LONG_JAVA_BYTES);
        pos += CommonUtils.LONG_JAVA_BYTES;

        return CommonUtils.byteArrayToLong(array, peerOrder);
    }

    /**
     *
     * <解析消息体，从消息体中获取一个Float>
     *
     * @param peerOrder 字节序
     * @return Float
     */
    public Float getFloatFromMsgBody(ByteOrder peerOrder) {
        if (null == peerOrder) {
            peerOrder = ByteOrder.BIG_ENDIAN;
        }

        if (pos + CommonUtils.FLOAT_JAVA_BYTES > length) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR,
                    "[getFloatFromMsgBody] Message remains is less than a float length, pos:"
                            + pos
                            + "getLength:"
                            + CommonUtils.FLOAT_JAVA_BYTES
                            + " msg length:"
                            + length);
        }

        byte[] array = new byte[CommonUtils.FLOAT_JAVA_BYTES];

        System.arraycopy(msgBody, pos, array, DEFAULT_START_POS, CommonUtils.FLOAT_JAVA_BYTES);
        pos += CommonUtils.FLOAT_JAVA_BYTES;

        return CommonUtils.byteArrayToFloat(array, peerOrder);
    }

    /**
     *
     * <解析消息体，从消息体中获取一个Double>
     *
     * @param peerOrder 字节序
     * @return Double
     */
    public Double getDoubleFromMsgBody(ByteOrder peerOrder) {
        if (null == peerOrder) {
            peerOrder = ByteOrder.BIG_ENDIAN;
        }

        if (pos + CommonUtils.DOUBLE_JAVA_BYTES > length) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR,
                    "[getDoubleFromMsgBody] Message remains is less than a double length, pos:"
                            + pos
                            + "getLength:"
                            + CommonUtils.DOUBLE_JAVA_BYTES
                            + " msg length:"
                            + length);
        }

        byte[] array = new byte[CommonUtils.DOUBLE_JAVA_BYTES];

        System.arraycopy(msgBody, pos, array, DEFAULT_START_POS, CommonUtils.DOUBLE_JAVA_BYTES);
        pos += CommonUtils.DOUBLE_JAVA_BYTES;

        return CommonUtils.byteArrayToDouble(array, peerOrder);
    }

    /**
     *
     * <解析消息体，从消息体中获取一个String>
     *
     * @param byteNum   字节个数
     * @param peerOrder 字节序
     * @return String
     */
    public String getStringFromMsgBody(int byteNum, ByteOrder peerOrder) {
        if (null == peerOrder) {
            peerOrder = ByteOrder.BIG_ENDIAN;
        }

        if (byteNum <= 0) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR,
                    "[getStringFromMsgBody] Parameter byteNum invalid, byteNum:" + byteNum);
        }

        if (pos + byteNum > length) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR,
                    "[getStringFromMsgBody] Message remains is less than a string length, pos:"
                            + pos
                            + "getLength:"
                            + byteNum
                            + " msg length:"
                            + length);
        }

        byte[] array = new byte[byteNum];

        System.arraycopy(msgBody, pos, array, DEFAULT_START_POS, byteNum);
        pos += byteNum;

        return CommonUtils.byteArrayToString(array, peerOrder);
    }

    /**
     *
     * <解析消息体，从消息体中获取一个char[]>
     *
     * @param byteNum   字节个数
     * @param peerOrder 字节序
     * @return char[]
     */
    public char[] getCharArrayFromMsgBody(int byteNum, ByteOrder peerOrder) {
        if (null == peerOrder) {
            peerOrder = ByteOrder.BIG_ENDIAN;
        }

        if (byteNum <= 0) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR,
                    "[getCharArrayFromMsgBody] Parameter byteNum invalid, byteNum:" + byteNum);
        }

        if (pos + byteNum > length) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR,
                    "[getCharArrayFromMsgBody] Message remains is less than a CharArray length, pos:"
                            + pos
                            + "getLength:"
                            + byteNum
                            + " msg length:"
                            + length);
        }

        byte[] array = new byte[byteNum];

        System.arraycopy(msgBody, pos, array, DEFAULT_START_POS, byteNum);
        pos += byteNum;

        return CommonUtils.byteArrayToCharArray(array, peerOrder);
    }

    public int getMaxMsgSize() {
        return MAX_MSG_BODY_SIZE;
    }

    /**
     * <获取消息的最大长度>
     *
     * @return  maxMsgBodySize
     */
    public static int getMaxMsgBodySize() {
        return MAX_MSG_BODY_SIZE;
    }
}
