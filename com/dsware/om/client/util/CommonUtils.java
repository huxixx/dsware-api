/*
 * File Name:  Util.java
 * Copyright:  Copyright  1998-2009, Huawei Technologies Co., Ltd.  ALL Rights Reserved.
 *             Warning: This computer software sourcecode is protected by copyright law
 *             and international treaties. Unauthorized reproduction or distribution
 *             of this sourcecode, or any portion of it, may result in severe civil and
 *             criminal penalties, and will be prosecuted to the maximum extent
 *             possible under the law.
 * Description: <description>
 * Modifier:    j55771
 * Modifytime:  2012-6-4
 */
package com.dsware.om.client.util;

import com.dsware.om.client.common.CommonMessage;
import com.dsware.om.client.common.Constants;
import com.dsware.om.client.common.ResponseMessage;
import com.dsware.om.client.exception.DSwareErrorCode;
import com.dsware.om.client.exception.DSwareException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Title: Util.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright 1988-2013,  All rights reserved</p>
 * <p>Company: Huawei Technologies Co., Ltd.</p>
 *
 * @author j55771
 * @version [V100R001C00, 2012-6-4]
 */
public final class CommonUtils {
    /**
     * java byte 类型字节数
     */
    public static final byte BYTE_JAVA_BYTES = 1;

    /**
     * java short 类型字节数
     */
    public static final byte SHORT_JAVA_BYTES = 2;

    /**
     * java int 类型字节数
     */
    public static final byte INT_JAVA_BYTES = 4;

    /**
     * java float 类型字节数
     */
    public static final byte FLOAT_JAVA_BYTES = 4;

    /**
     * java long 类型字节数
     */
    public static final byte LONG_JAVA_BYTES = 8;

    /**
     * java double 类型字节数
     */
    public static final byte DOUBLE_JAVA_BYTES = 8;

    private static final byte ONE_BYTE_BITS = 8;

    private static final byte TWO_BYTE_BITS = ONE_BYTE_BITS * 2;

    private static final byte THREE_BYTE_BITS = ONE_BYTE_BITS * 3;

    private static final byte FOUR_BYTE_BITS = ONE_BYTE_BITS * 4;

    private static final byte FIVE_BYTE_BITS = ONE_BYTE_BITS * 5;

    private static final byte SIX_BYTE_BITS = ONE_BYTE_BITS * 6;

    private static final byte SEVEN_BYTE_BITS = ONE_BYTE_BITS * 7;

    private static final byte BYTE_MAX_VALUE = -1;

    private static final int NET_BUFFER_SIZE = 1024 * 2;

    private static final int NET_BUFFER_START_POS = 0;

    private static final int NET_MSG_HEADER_FIELD_LEN = 4;

    private static final int NET_MSG_LENGTH_FIELD_LEN = 4;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

    /**
     * 简单ip匹配正则表达式
     */
    private static final String IP_VERIFY_REGEX =
            "^((1?\\d?\\d|(2([0-4]\\d|5[0-5])))\\.){3}(1?\\d?\\d|(2([0-4]\\d|5[0-5])))$";

    private CommonUtils() {}

    /**
     * 〈判断字符串是否为整型〉
     * 〈功能详细描述〉
     *
     * @param number   字符串
     * @return boolean 是整型返回true，否则返回false
     * @see [类、类#方法、类#成员]
     */
    public static boolean isInteger(String number) {
        if (null == number) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]+");
        number = Normalizer.normalize(number, Form.NFKC);
        return pattern.matcher(number).matches();
    }

    /**
     * 〈获取API Jar包的父路径信息〉
     * 〈功能详细描述〉
     *
     * @return String Jar包的父路径信息
     * @see [类、类#方法、类#成员]
     */
    public static String getApiJarParentDir() {
        String path = "" + CommonUtils.class.getResource("CommonUtils.class").getPath();
        path = path.replace('\\', '/');
        if (path.startsWith("file:/")) {
            path = path.substring("file:/".length() - 1);
        }
        path = path.split("jar")[0];
        int splitLastIndex = path.lastIndexOf("/");
        path = path.substring(0, splitLastIndex);
        splitLastIndex = path.lastIndexOf("/");
        return path.substring(0, splitLastIndex);
    }

    /**
     * <通用消息转换成bytebuffer>
     *
     * @param msg           消息
     * @param peerOrder     字节序
     * @return ByteBuffer
     */
    public static ByteBuffer messageToBuffer(CommonMessage msg, ByteOrder peerOrder) {
        LOGGER.debug("messageToBuffer begin");

        if (null == msg) {
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR, "[messageToBuffer] parameter 'msg' is null");
        }

        if (null == peerOrder) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[messageToBuffer] parameter 'peerOrder' is null");
        }

        int length = msg.getLength() + NET_MSG_HEADER_FIELD_LEN + NET_MSG_LENGTH_FIELD_LEN;

        ByteBuffer byteBuf = ByteBuffer.allocate(length);

        byteBuf.order(peerOrder);
        byteBuf.putInt(msg.getHeader());

        int msgLen = msg.getLength();
        int versionNum = 1;
        msgLen = msgLen | (versionNum << Constants.NUM24);
        byteBuf.putInt(msgLen);

        if (null != msg.getMsgBody()) {
            byteBuf.put(Arrays.copyOf(msg.getMsgBody(), msg.getLength()));
        }

        byteBuf.flip();
        LOGGER.debug("messageToBuffer end");
        return byteBuf;
    }

    /**
     *
     * <从socket上读取数据>
     *
     * @param channel       socket channel
     * @param len           读取的数据长度 字节数
     * @return ByteBuffer
     */
    private static ByteBuffer readRawByteFromChannel(Socket socket, DataInputStream dis, int len) {
        LOGGER.debug("readRawByteFromChannel begin");

        final int bufSize = (len >= NET_BUFFER_SIZE) ? NET_BUFFER_SIZE : len;

        ByteBuffer buf = ByteBuffer.allocate(len);
        ByteBuffer recvBuf = ByteBuffer.allocate(bufSize);

        int recvLen = 0;
        int total = 0;

        while (total < len) {
            try {
                // 阻塞式读取
                recvLen = dis.read(recvBuf.array());
            } catch (SocketTimeoutException e) {
                LOGGER.error(
                        "[readRawByteFromChannel] failed to read from socket caused by socket_read_timeout "
                                + e.getMessage());
                throw new DSwareException(
                        DSwareErrorCode.SOCKET_READ_TIMEOUT,
                        e,
                        "failed to read from socket caused by socket_read_timeout" + socket.getRemoteSocketAddress());
            } catch (Exception e) {
                LOGGER.error("[readRawByteFromChannel] failed to read from socket caused by Exception");
                throw new DSwareException(
                        DSwareErrorCode.INTERNAL_ERROR,
                        e,
                        "failed to read from socket" + socket.getRemoteSocketAddress());
            }

            // 读出错则直接报错
            if (0 >= recvLen) {
                LOGGER.error(
                        "[readRawByteFromChannel] Failed to read from Agent "
                                + socket.getRemoteSocketAddress()
                                + " current recv len="
                                + total);
                throw new DSwareException(DSwareErrorCode.COMMUNICATE_AGENT_ERROR, "read from agent error");
            } else {
                System.arraycopy(recvBuf.array(), 0, buf.array(), total, recvLen);
                total += recvLen;
                recvBuf.clear();
            }
        }
        buf.position(NET_BUFFER_START_POS);
        LOGGER.debug("readRawByteFromChannel end");
        return buf;
    }

    /**
     *
     * <从socket读去一个消息>
     *
     * @param socket socket连接
     * @param din socket的输入流
     * @param peerOrder order endian
     * @return ResponseMessage
     */
    public static ResponseMessage readMsgFromChannel(Socket socket, DataInputStream din, ByteOrder peerOrder) {
        LOGGER.debug("readMsgFromChannel begin");

        if (null == socket) {
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR, "[ResponseMessage] parameter 'socket' is null");
        }
        if (null == din) {
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR, "[ResponseMessage] parameter 'din' is null");
        }
        if (null == peerOrder) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[ResponseMessage] parameter 'peerOrder' is null");
        }

        ResponseMessage msg = new ResponseMessage();
        ByteBuffer header =
                CommonUtils.readRawByteFromChannel(socket, din, NET_MSG_HEADER_FIELD_LEN + NET_MSG_LENGTH_FIELD_LEN);
        // header 不会为空
        header.position(NET_BUFFER_START_POS);
        header.order(peerOrder);

        msg.setHeader(header.getInt());
        long length = unsignedIntToLong(header.getInt());

        if (length <= 0) {
            LOGGER.error("Message length is 0 , no message body");
            return msg;
        }

        if (length > msg.getMaxMsgSize()) {
            LOGGER.error("Message length more than 32M , no message body");
            return msg;
        }

        msg.setLength((int) length);

        ByteBuffer body = CommonUtils.readRawByteFromChannel(socket, din, (int) length);
        // body 不会为空

        msg.setMsgBody(body.array());
        msg.resetPos();
        LOGGER.debug("readMsgFromChannel end");
        return msg;
    }

    /**
     *
     * <关闭socket>
     *
     * @param channel socket channel
     */
    public static void closeChannel(SocketChannel channel) {
        if (null != channel) {
            try {
                channel.close();
            } catch (IOException e) {
                LOGGER.error("close channel exception.");
            }
        }
    }

    /**
     *
     * <Agent侧无符号int转为long>
     *
     * @param value source uint32 stored in byte
     * @return long
     */
    public static long unsignedIntToLong(int value) {
        // 注 位移运算默认安装int来计算的
        long lBase = 1L;
        if (value < 0) {
            return (lBase << FOUR_BYTE_BITS) + value;
        } else {
            return value;
        }
    }

    /**
     *
     * <Agent侧无符号short转为int>
     *
     * @param value source uint16 stored in byte
     * @return int
     */
    public static int unsignedShortToInt(short value) {
        if (value < 0) {
            return (1 << TWO_BYTE_BITS) + value;
        } else {
            return value;
        }
    }

    /**
     *
     * <Agent侧无符号byte转为int>
     *
     * @param value source uint8 stored in byte
     * @return int
     */
    public static int unsignedByteToInt(byte value) {
        if (value < 0) {
            return (1 << ONE_BYTE_BITS) + value;
        } else {
            return value;
        }
    }

    /**
     *
     * <Agent侧无符号byte转为short>
     *
     * @param value source uint8 stored in byte
     * @return short
     */
    public static short unsignedByteToShort(byte value) {
        if (value < 0) {
            return (short) ((1 << ONE_BYTE_BITS) + value);
        } else {
            return value;
        }
    }

    /**
     *
     * <基本数据类型转换为字节数组>
     * <p>byte类型</p>
     *
     * @param value          值
     * @param peerOrder     字节序
     * @return byte[]
     */
    public static byte[] byteToByteArray(byte value, ByteOrder peerOrder) {
        if (null == peerOrder) {
            LOGGER.error("peerOrder is null");
            return new byte[0];
        }

        byte[] array = new byte[BYTE_JAVA_BYTES];
        array[NET_BUFFER_START_POS] = value;
        return array;
    }

    /**
     *
     * <基本数据类型转换为字节数组>
     * <p>short类型</p>
     *
     * @param value          值
     * @param peerOrder     字节序
     * @return byte[]
     */
    public static byte[] shortToByteArray(short value, ByteOrder peerOrder) {
        if (null == peerOrder) {
            LOGGER.error("peerOrder is null");
            return new byte[0];
        }

        byte[] array = new byte[SHORT_JAVA_BYTES];
        byte pos = 0;
        if (ByteOrder.BIG_ENDIAN == peerOrder) {
            array[pos++] = (byte) (value >> ONE_BYTE_BITS);
            array[pos] = (byte) value;
        } else if (ByteOrder.LITTLE_ENDIAN == peerOrder) {
            array[pos++] = (byte) value;
            array[pos] = (byte) (value >> ONE_BYTE_BITS);
        } else {
            LOGGER.error("peerOrder=" + peerOrder + " is invlaid.");
            return new byte[0];
        }
        return array;
    }

    /**
     *
     * <基本数据类型转换为字节数组>
     * <p>int类型</p>
     *
     * @param value          值
     * @param peerOrder     字节序
     * @return byte[]
     */
    public static byte[] intToByteArray(int value, ByteOrder peerOrder) {
        if (null == peerOrder) {
            LOGGER.error("peerOrder is null");
            return new byte[0];
        }

        byte[] array = new byte[INT_JAVA_BYTES];
        byte pos = 0;
        if (ByteOrder.BIG_ENDIAN == peerOrder) {
            array[pos++] = (byte) (value >> THREE_BYTE_BITS);
            array[pos++] = (byte) (value >> TWO_BYTE_BITS);
            array[pos++] = (byte) (value >> ONE_BYTE_BITS);
            array[pos] = (byte) value;
        } else if (ByteOrder.LITTLE_ENDIAN == peerOrder) {
            array[pos++] = (byte) value;
            array[pos++] = (byte) (value >> ONE_BYTE_BITS);
            array[pos++] = (byte) (value >> TWO_BYTE_BITS);
            array[pos] = (byte) (value >> THREE_BYTE_BITS);
        } else {
            LOGGER.error("peerOrder=" + peerOrder + " is invlaid.");
            return new byte[0];
        }
        return array;
    }

    /**
     *
     * <基本数据类型转换为字节数组>
     * <p>long类型</p>
     *
     * @param value          值
     * @param peerOrder     字节序
     * @return byte[]
     */
    public static byte[] longToByteArray(long value, ByteOrder peerOrder) {
        if (null == peerOrder) {
            LOGGER.error("peerOrder is null");
            return new byte[0];
        }

        byte[] array = new byte[LONG_JAVA_BYTES];
        byte pos = 0;
        if (ByteOrder.BIG_ENDIAN == peerOrder) {
            array[pos++] = (byte) ((value >> SEVEN_BYTE_BITS) & BYTE_MAX_VALUE);
            array[pos++] = (byte) ((value >> SIX_BYTE_BITS) & BYTE_MAX_VALUE);
            array[pos++] = (byte) ((value >> FIVE_BYTE_BITS) & BYTE_MAX_VALUE);
            array[pos++] = (byte) ((value >> FOUR_BYTE_BITS) & BYTE_MAX_VALUE);
            array[pos++] = (byte) ((value >> THREE_BYTE_BITS) & BYTE_MAX_VALUE);
            array[pos++] = (byte) ((value >> TWO_BYTE_BITS) & BYTE_MAX_VALUE);
            array[pos++] = (byte) ((value >> ONE_BYTE_BITS) & BYTE_MAX_VALUE);
            array[pos] = (byte) (value & BYTE_MAX_VALUE);
        } else if (ByteOrder.LITTLE_ENDIAN == peerOrder) {
            array[pos++] = (byte) (value & BYTE_MAX_VALUE);
            array[pos++] = (byte) ((value >> ONE_BYTE_BITS) & BYTE_MAX_VALUE);
            array[pos++] = (byte) ((value >> TWO_BYTE_BITS) & BYTE_MAX_VALUE);
            array[pos++] = (byte) ((value >> THREE_BYTE_BITS) & BYTE_MAX_VALUE);
            array[pos++] = (byte) ((value >> FOUR_BYTE_BITS) & BYTE_MAX_VALUE);
            array[pos++] = (byte) ((value >> FIVE_BYTE_BITS) & BYTE_MAX_VALUE);
            array[pos++] = (byte) ((value >> SIX_BYTE_BITS) & BYTE_MAX_VALUE);
            array[pos] = (byte) ((value >> SEVEN_BYTE_BITS) & BYTE_MAX_VALUE);
        } else {
            LOGGER.error("peerOrder=" + peerOrder + " is invlaid.");
            return new byte[0];
        }
        return array;
    }

    /**
     *
     * <基本数据类型转换为字节数组>
     * <p>float类型</p>
     *
     * @param value          值
     * @param peerOrder     字节序
     * @return byte[]
     */
    public static byte[] floatToByteArray(float value, ByteOrder peerOrder) {
        if (null == peerOrder) {
            LOGGER.error("peerOrder is null");
            return new byte[0];
        }

        return intToByteArray(Float.floatToRawIntBits(value), peerOrder);
    }

    /**
     *
     * <基本数据类型转换为字节数组>
     * <p>double类型</p>
     *
     * @param value          值
     * @param peerOrder     字节序
     * @return byte[]
     */
    public static byte[] doubleToByteArray(double value, ByteOrder peerOrder) {
        if (null == peerOrder) {
            LOGGER.error("peerOrder is null");
            return new byte[0];
        }

        return longToByteArray(Double.doubleToRawLongBits(value), peerOrder);
    }

    /**
     *
     * <基本数据类型转换为字节数组>
     * <p>string类型</p>
     *
     * @param value          值
     * @param peerOrder     字节序
     * @return byte[]
     */
    public static byte[] stringToByteArray(String value, ByteOrder peerOrder) {
        if (null == value) {
            LOGGER.error("value is null");
            return new byte[0];
        }

        if (null == peerOrder) {
            LOGGER.error("peerOrder is null");
            return new byte[0];
        }

        return value.getBytes(Charset.forName("UTF-8"));
    }

    /**
     *
     * <基本数据类型转换为字节数组>
     * <p>char[]类型</p>
     *
     * @param value          值
     * @param peerOrder     字节序
     * @return byte[]
     */
    public static byte[] charArrayToByteArray(char[] value, ByteOrder peerOrder) {
        if (null == value) {
            LOGGER.error("value is null");
            return new byte[0];
        }

        if (null == peerOrder) {
            LOGGER.error("peerOrder is null");
            return new byte[0];
        }

        return new String(value).getBytes(Charset.forName("UTF-8"));
    }

    /**
     *
     * <byte 数组转为Byte>
     *
     * @param value     字节数组值
     * @param peerOrder 字节序
     * @return Byte
     */
    public static Byte byteArrayToByte(byte[] value, ByteOrder peerOrder) {
        if (null == value) {
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToByte] parameter 'value' is null");
        }

        if (null == peerOrder) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToByte] parameter 'peerOrder' is null");
        }

        if (BYTE_JAVA_BYTES != value.length) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToByte] byte[] length=" + value.length + " is invalid");
        }

        byte bv = value[BYTE_JAVA_BYTES - 1];

        return Byte.valueOf(bv);
    }

    /**
     *
     * <byte 数组转为Short>
     *
     * @param value     字节数组值
     * @param peerOrder 字节序
     * @return Short
     */
    public static Short byteArrayToShort(byte[] value, ByteOrder peerOrder) {
        if (null == value) {
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToShort] parameter 'value' is null");
        }

        if (null == peerOrder) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToShort] parameter 'peerOrder' is null");
        }

        if (SHORT_JAVA_BYTES != value.length) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToShort] byte[] length=" + value.length + " is invalid");
        }

        byte pos = 0;
        short sv = 0;
        short sBase = 0;
        if (ByteOrder.BIG_ENDIAN == peerOrder) {
            for (int i = SHORT_JAVA_BYTES - 1; i >= 0; i--) {
                sBase = CommonUtils.unsignedByteToShort(value[pos++]);
                sv |= sBase << (i * ONE_BYTE_BITS);
            }
        } else if (ByteOrder.LITTLE_ENDIAN == peerOrder) {
            for (int i = 0; i < SHORT_JAVA_BYTES; i++) {
                sBase = CommonUtils.unsignedByteToShort(value[pos++]);
                sv |= sBase << (i * ONE_BYTE_BITS);
            }
        } else {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToShort] peerOrder=" + value.length + " is invlaid.");
        }

        return Short.valueOf(sv);
    }

    /**
     *
     * <byte 数组转为Integer>
     *
     * @param value     字节数组值
     * @param peerOrder 字节序
     * @return Integer
     */
    public static Integer byteArrayToInt(byte[] value, ByteOrder peerOrder) {
        if (null == value) {
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToInt] value is null");
        }

        if (null == peerOrder) {
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToInt] peerOrder is null");
        }

        if (INT_JAVA_BYTES != value.length) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToInt] byte[] length=" + value.length + " is invalid");
        }

        int pos = 0;
        int iv = 0;
        int iBase = 0;
        if (ByteOrder.BIG_ENDIAN == peerOrder) {
            for (int i = INT_JAVA_BYTES - 1; i >= 0; i--) {
                iBase = CommonUtils.unsignedByteToShort(value[pos++]);
                iv |= iBase << (i * ONE_BYTE_BITS);
            }
        } else if (ByteOrder.LITTLE_ENDIAN == peerOrder) {
            for (int i = 0; i < INT_JAVA_BYTES; i++) {
                iBase = CommonUtils.unsignedByteToShort(value[pos++]);
                iv |= iBase << (i * ONE_BYTE_BITS);
            }
        } else {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToInt] peerOrder=" + peerOrder + " is invlaid.");
        }

        return Integer.valueOf(iv);
    }

    /**
     *
     * <byte 数组转为Long>
     *
     * @param value     字节数组值
     * @param peerOrder 字节序
     * @return Long
     */
    public static Long byteArrayToLong(byte[] value, ByteOrder peerOrder) {
        if (null == value) {
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToLong] parameter 'value' is null");
        }

        if (null == peerOrder) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToLong] parameter 'peerOrder' is null");
        }

        if (LONG_JAVA_BYTES != value.length) {
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToLong] parameter 'length' is null");
        }

        int pos = 0;
        long lv = 0L;
        long lBase = 0L;
        if (ByteOrder.BIG_ENDIAN == peerOrder) {
            for (int i = LONG_JAVA_BYTES - 1; i >= 0; i--) {
                lBase = CommonUtils.unsignedByteToShort(value[pos++]);
                lv |= lBase << (i * ONE_BYTE_BITS);
            }
        } else if (ByteOrder.LITTLE_ENDIAN == peerOrder) {
            for (int i = 0; i < LONG_JAVA_BYTES; i++) {
                lBase = CommonUtils.unsignedByteToShort(value[pos++]);
                lv |= lBase << (i * ONE_BYTE_BITS);
            }
        } else {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToLong] peerOrder=" + peerOrder + " is invlaid.");
        }

        return Long.valueOf(lv);
    }

    /**
     *
     * <byte 数组转为Double>
     *
     * @param value     字节数组值
     * @param peerOrder 字节序
     * @return Double
     */
    public static Double byteArrayToDouble(byte[] value, ByteOrder peerOrder) {
        if (null == value) {
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToDouble] parameter 'value' is null");
        }

        if (null == peerOrder) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToDouble] parameter 'peerOrder' is null");
        }

        if (DOUBLE_JAVA_BYTES != value.length) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR,
                    "[byteArrayToDouble] parameter 'value' length != " + DOUBLE_JAVA_BYTES);
        }

        Long lv = byteArrayToLong(value, peerOrder);
        return Double.longBitsToDouble(lv);
    }

    /**
     *
     * <byte 数组转为Float>
     *
     * @param value     字节数组值
     * @param peerOrder 字节序
     * @return Float
     */
    public static Float byteArrayToFloat(byte[] value, ByteOrder peerOrder) {
        if (null == value) {
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToFloat] parameter 'value' is null");
        }

        if (null == peerOrder) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToFloat] parameter 'peerOrder' is null");
        }

        if (FLOAT_JAVA_BYTES != value.length) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR,
                    "[byteArrayToFloat] parameter 'value' length != " + FLOAT_JAVA_BYTES);
        }

        Integer iv = byteArrayToInt(value, peerOrder);
        return Float.intBitsToFloat(iv);
    }

    /**
     *
     * <byte 数组转为String>
     *
     * @param value     字节数组值
     * @param peerOrder 字节序
     * @return String
     */
    public static String byteArrayToString(byte[] value, ByteOrder peerOrder) {
        if (null == value) {
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToString] parameter 'value' is null");
        }

        if (null == peerOrder) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToString] parameter 'peerOrder' is null");
        }

        if (value.length <= 0) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToString] parameter 'value' length <=0");
        }

        return new String(value, Charset.forName("UTF-8")).trim();
    }

    /**
     *
     * <byte 数组转为char[]>
     *
     * @param value     字节数组值
     * @param peerOrder 字节序
     * @return char[]
     */
    public static char[] byteArrayToCharArray(byte[] value, ByteOrder peerOrder) {
        if (null == value) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToCharArray] parameter 'value' is null");
        }

        if (null == peerOrder) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToCharArray] parameter 'peerOrder' is null");
        }

        return new String(value, Charset.forName("UTF-8")).trim().toCharArray();
    }

    /**
     *
     * <休眠>
     *
     * @param millis 休眠时间(单位: 毫秒)
     */
    public static void sleepTime(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR, e, "sleep interrupted");
        }
    }

    /**
     * 字符串判空
     * 其中字符串数组有一个字符串为空返回true, 都不为空返回false, 适合单个字符串判空
     *
     * @param args    字符串数组
     * @return  boolean
     */
    public static boolean isStrEmpty(String... args) {
        for (String temp : args) {
            if (null == temp || temp.isEmpty() || temp.trim().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    /**
     * 字符串判空
     * 其中字符串数组有一个字符串不为空或空串返回true, 都为空返回falss, 适合单个字符串判空
     *
     * @param args    字符串数组
     * @return  boolean
     */
    public static boolean isStrNotEmpty(String... args) {
        for (String temp : args) {
            if (null != temp && (!temp.isEmpty() && !temp.trim().isEmpty())) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * 判断命名是否规范
     *
     * @param maxLenth  命名最大长度
     * @param   nameList    名字字符串列表
     */
    public static void checkNameValid(int maxLenth, String... nameList) {
        if (null == nameList || nameList.length == 0) {
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR, "[checkNameValid] parameter nameList is empty");
        }

        for (String temp : nameList) {
            if (null == temp || temp.isEmpty()) {
                LOGGER.error("[checkNameValid] Invalid name, parameter is null.");
                throw new DSwareException(
                        DSwareErrorCode.NAME_INVALID_ERROR, "invalid name:" + temp + " parameter is null.");
            } else if (temp.length() > maxLenth) {
                LOGGER.error(
                        "[checkNameValid] Invalid name, parameter length: " + temp.length() + " maxLength:" + maxLenth);
                throw new DSwareException(
                        DSwareErrorCode.NAME_INVALID_ERROR,
                        "invalid name:" + temp + " parameter length: " + temp.length() + " maxLength: " + maxLenth);
            }
        }
    }

    /**
     * 简单ip格式验证
     *
     * @param ipList    ip
     */
    public static void checkIP(String... ipList) {
        if (null == ipList || ipList.length == 0 || ipList.length > Constants.NUM3) {
            throw new DSwareException(DSwareErrorCode.IP_INVALID_ERROR, "[checkIP] parameter ipList is empty!");
        }

        for (String ip : ipList) {
            if (null == ip) {
                LOGGER.error("[checkIP] IP is null");
                throw new DSwareException(DSwareErrorCode.IP_INVALID_ERROR, "ip is null");
            }
            Pattern pattern = Pattern.compile(IP_VERIFY_REGEX);
            ip = Normalizer.normalize(ip, Form.NFKC);
            Matcher matcher = pattern.matcher(ip);
            if (!matcher.matches()) {
                LOGGER.error("[checkIP] IP is invalid, ip=" + ip);
                throw new DSwareException(DSwareErrorCode.IP_INVALID_ERROR, "invalid ip:" + ip);
            }
        }
    }

    /**
     * 校验批量查询的卷的数量
     *
     * @param pageSize  批量查询卷数目
     */
    public static void checkPageSize(int pageSize) {
        if (pageSize <= 0) {
            throw new DSwareException(DSwareErrorCode.INVALID_PARAMETER);
        }

        if (pageSize > Constants.MAX_QUERY_VOLUME_BATCH_PAGE_SIZE) {
            throw new DSwareException(
                    DSwareErrorCode.DSW_ERROR_VBS_QUERY_VOLUME_BATCH_EXCEED_MAX, "page size:" + pageSize);
        }
    }

    /**
     * 校验标记类参数
     *
     * @param offset    镜像相对于卷的偏移值
     * @exception/throws   DSwareException 运行时异常
     */
    public static void checkLLDVolumeOffset(int offset) {
        if (Constants.VBS_LLD_VOLUME_OFFSET_0 != offset
                && Constants.VBS_LLD_VOLUME_OFFSET_4K != offset
                && Constants.VBS_LLD_VOLUME_OFFSET_1M != offset) {
            LOGGER.error("[checkLLDVOlumeOffset] invalid parameter, offset:" + offset);
            throw new DSwareException(DSwareErrorCode.INVALID_PARAMETER);
        }
    }

    /**
     * 校验标记类参数
     *
     * @param flagValue    标记类参数值
     * @param min          参数允许最小值
     * @param   max        参数允许最大值
     * @exception/throws   DSwareException 运行时异常
     */
    public static void checkParameterRange(int flagValue, Integer min, Integer max) {
        if (null == min && null == max) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[checkParameterRange] Parameter min and max is null");
        }

        if ((null == max && flagValue < min) || (null == min && flagValue > max)) {
            LOGGER.error("[checkParameterRange] invalid parameter, value:" + flagValue);
            throw new DSwareException(DSwareErrorCode.INVALID_PARAMETER);
        }

        if (null != max && null != min && (flagValue < min || flagValue > max)) {
            LOGGER.error("[checkParameterRange] invalid parameter, value:" + flagValue);
            throw new DSwareException(DSwareErrorCode.INVALID_PARAMETER);
        }
    }

    /**
     * 校验池id参数
     *
     * @param flagValue    标记类参数值
     * @param min          参数允许最小值
     * @param   max        参数允许最大值
     * @exception/throws   DSwareException 运行时异常
     */
    public static void checkPoolIdRange(int flagValue, Integer min, Integer max) {
        if (null == min && null == max) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[checkParameterRange] Parameter min and max is null");
        }
        int value = flagValue & Constants.NUM_0X0FFF;
        
        if (null != max && null != min && (value < min || value > max)) {
            LOGGER.error("[checkParameterRange] invalid parameter, value:" + flagValue + " value:" + value);
            throw new DSwareException(DSwareErrorCode.INVALID_PARAMETER);
        }
    }

    /**
     * 检查List集合中字符串有效性
     *
     * @param maxLenth List中字符串最大长度
     * @param list List集合
     */
    public static void checkList(int maxLenth, List<Object> list) {
        if (null == list) {
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR, "[checkListEmpty] parameter list is empty");
        }
        for (Object iter : list) {
            checkNameValid(maxLenth, (String) iter);
        }
    }

    /**
     * <关闭输入输出流>
     * <功能详细描述>
     *
     * @param stream 输入输出流
     * @see [类、类#方法、类#成员]
     */
    public static void closeStream(Closeable stream) {
        if (null == stream) {
            return;
        }
        try {
            stream.close();
        } catch (IOException e) {
            LOGGER.error("close stream error.");
        }
    }

    /**
     * 转换ip字符串成int型
     *
     * @param ip    IP字符串
     * @return  int IP整数值
     */
    public static int ipToInt(String ip) {
        String[] ips = ip.split("\\.");
        return (Integer.parseInt(ips[0]) << Constants.NUM24)
                + (Integer.parseInt(ips[Constants.NUM1]) << Constants.NUM16)
                + (Integer.parseInt(ips[Constants.NUM2]) << Constants.NUM8)
                + Integer.parseInt(ips[Constants.NUM3]);
    }

    /**
     * int 转换成Ip
     *
     * @param ip    IP整型值
     * @return  String  IP字符串
     */
    public static String intToIp(int ip) {
        return ((ip >> Constants.NUM24) & Constants.NUM255)
                + "."
                + ((ip >> Constants.NUM16) & Constants.NUM255)
                + "."
                + ((ip >> Constants.NUM8) & Constants.NUM255)
                + "."
                + (ip & Constants.NUM255);
    }

    /**
     * 判断是否长整数
     *
     * @param number    字符串
     * @return  boolean  true  输入为长整数，false，输入不是长整数
     */
    public static boolean isLong(String number) {
        try {
            Long.parseLong(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
