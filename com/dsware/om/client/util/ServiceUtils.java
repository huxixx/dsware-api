/*
 * Copyright Notice:
 *      Copyright  1998-2009, Huawei Technologies Co., Ltd.  ALL Rights Reserved.
 *
 *      Warning: This computer software sourcecode is protected by copyright law
 *      and international treaties. Unauthorized reproduction or distribution
 *      of this sourcecode, or any portion of it, may result in severe civil and
 *      criminal penalties, and will be prosecuted to the maximum extent
 *      possible under the law.
 */
/*
 * 文 件 名:  ServiceUtil.java
 * 版 本 号:  V1.0.0
 * 版    权:  Huawei Technologies Co., Ltd. Copyright 1988-2008,  All rights reserved
 * 描    述:  <描述>
 * 作    者:  z00194581
 * 创建日期:  2012-7-20
 */
package com.dsware.om.client.util;

import com.dsware.om.client.bean.DSwareAttribute;
import com.dsware.om.client.bean.DSwareBackupInfo;
import com.dsware.om.client.bean.DSwareBackupInfoReserve;
import com.dsware.om.client.bean.DSwareBitmapVolumeInfo;
import com.dsware.om.client.bean.DSwareBitmapVolumeInfoReserve;
import com.dsware.om.client.bean.DSwarePoolInfo;
import com.dsware.om.client.bean.DSwarePoolInfoReserve;
import com.dsware.om.client.bean.DSwareQosCfgInfo;
import com.dsware.om.client.bean.DSwareQosCfgInfoReserve;
import com.dsware.om.client.bean.DSwareQosInfo;
import com.dsware.om.client.bean.DSwareQosInfoReserve;
import com.dsware.om.client.bean.DSwareQosPoolInfo;
import com.dsware.om.client.bean.DSwareQosPoolInfoReserve;
import com.dsware.om.client.bean.DSwareSnapInfo;
import com.dsware.om.client.bean.DSwareSnapInfoReserve;
import com.dsware.om.client.bean.DSwareVolumeAttachInfo;
import com.dsware.om.client.bean.DSwareVolumeDetailInfo;
import com.dsware.om.client.bean.DSwareVolumeDetailInfoReserve;
import com.dsware.om.client.bean.DSwareVolumeGroupInfo;
import com.dsware.om.client.bean.DSwareVolumeGroupInfoReserve;
import com.dsware.om.client.bean.DSwareVolumeGroupTypeInfo;
import com.dsware.om.client.bean.DSwareVolumeGroupTypeInfoReserve;
import com.dsware.om.client.bean.DSwareVolumeImgcpInfo;
import com.dsware.om.client.bean.DSwareVolumeImgcpInfoReserve;
import com.dsware.om.client.bean.DSwareVolumeInfo;
import com.dsware.om.client.bean.DSwareVolumeInfoReserve;
import com.dsware.om.client.bean.DSwareVolumeMigrateInfo;
import com.dsware.om.client.bean.DSwareVolumeMigrateInfoReserve;
import com.dsware.om.client.bean.SerialAttributes;
import com.dsware.om.client.bean.VbsCliPoolInfo;
import com.dsware.om.client.common.Constants;
import com.dsware.om.client.common.RequestMessage;
import com.dsware.om.client.exception.DSwareErrorCode;
import com.dsware.om.client.exception.DSwareException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 与业务相关的工具类
 *
 * @author     z00194581
 * @version    [V100R001C00]
 */
public final class ServiceUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceUtils.class);

    private ServiceUtils() {}

    /**
     * 将字节流转换成javaBean，前提是该javaBean需要实现属性序列的接口
     *
     * @param srcObj  待转换成的javaBean对象，用实现接口类做形参
     * @param values    原始字节流
     * @param   order   要转换成的字节序
     * @throws IllegalAccessException 非法不可达异常
     * @throws InvocationTargetException 调用异常
     */
    public static void byteArrayToBean(SerialAttributes srcObj, byte[] values, ByteOrder order)
            throws IllegalAccessException, InvocationTargetException {
        // 入参判断
        if (null == values || null == order || null == srcObj) {
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR, "srcObj, values or order is null");
        }
        @SuppressWarnings("rawtypes")
        Class reflectCl = (Class) srcObj.getClass();
        Method[] methods = reflectCl.getMethods();
        List<DSwareAttribute> attributeSerial = srcObj.getAttributeSerial();
        int currentPos = 0;
        for (int i = 0; i < attributeSerial.size(); i++) {
            // 获取属性的字节码
            DSwareAttribute att = attributeSerial.get(i);
            byte[] byteArray = new byte[att.getByteNum()];
            System.arraycopy(values, currentPos, byteArray, 0, att.getByteNum());
            currentPos += att.getByteNum();

            // 获取属性在类中的序号
            int index = getMethodIndex(methods, att.getAttrName());
            if (-1 == index) {
                LOGGER.error(
                        "[byteArrayToBean] Get method index failed, Class:"
                                + srcObj.getClass().getName()
                                + "attribute:{}"
                                + att.getAttrName());
                throw new DSwareException(DSwareErrorCode.NO_SUCH_ATTRIBUTE_ERROR);
            }
            Method setMethod = methods[index];

            switch (att.getAttrType()) {
                case BYTE: {
                    Byte value = CommonUtils.byteArrayToByte(byteArray, order);
                    setMethod.invoke(srcObj, value);
                    LOGGER.debug("[byteArrayToBean] MethodName=" + setMethod.getName() + "\t value=" + value);
                    break;
                }

                case INT: {
                    Integer value = CommonUtils.byteArrayToInt(byteArray, order);
                    setMethod.invoke(srcObj, value);
                    LOGGER.debug("[byteArrayToBean] MethodName=" + setMethod.getName() + "\t value=" + value);
                    break;
                }

                case UINT: {
                    Integer uValue = CommonUtils.byteArrayToInt(byteArray, order);
                    long value = CommonUtils.unsignedIntToLong(uValue);
                    setMethod.invoke(srcObj, value);
                    LOGGER.debug("[byteArrayToBean] MethodName=" + setMethod.getName() + "\t value=" + value);
                    break;
                }

                case SHORT: {
                    short value = CommonUtils.byteArrayToShort(byteArray, order);
                    setMethod.invoke(srcObj, value);
                    LOGGER.debug("[byteArrayToBean] MethodName=" + setMethod.getName() + "\t value=" + value);
                    break;
                }

                case UNSHORT: {
                    short unShort = CommonUtils.byteArrayToShort(byteArray, order);
                    int value = CommonUtils.unsignedShortToInt(unShort);
                    setMethod.invoke(srcObj, value);
                    LOGGER.debug("[byteArrayToBean] MethodName=" + setMethod.getName() + "\t value=" + value);
                    break;
                }

                case STRING: {
                    String value = CommonUtils.byteArrayToString(byteArray, order);
                    setMethod.invoke(srcObj, value);
                    LOGGER.debug("[byteArrayToBean] MethodName=" + setMethod.getName() + "\t value=" + value);
                    break;
                }

                case LONG: {
                    Long value = CommonUtils.byteArrayToLong(byteArray, order);
                    setMethod.invoke(srcObj, value);
                    LOGGER.debug("[byteArrayToBean] MethodName=" + setMethod.getName() + "\t value=" + value);
                    break;
                }

                default:
                    throw new DSwareException(
                            DSwareErrorCode.NO_SUCH_TYPE_ERROR, "[byteArrayToBean] no such type support in method");
            }
        }
    }

    /**
     *  获取成员变量在类中的序列
     *
     * @param ms    公共方法列表
     * @param methodName  公共方法名
     * @return  int 该属性在类中的序列号
     */
    private static int getMethodIndex(Method[] ms, String methodName) {
        for (int i = 0; i < ms.length; i++) {
            String attName = ms[i].getName();
            if (methodName.equalsIgnoreCase(attName)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 从字节数组中获取卷列表信息
     *
     * @param values    字节数组
     * @param order     转换成结果字节序
     * @return  Map<String, DSwareVolumeInfo>
     */
    public static Map<String, DSwareVolumeInfo> byteArrayToVolumeInfoMap(byte[] values, ByteOrder order) {
        if (null == values || null == order) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToVolumeInfoMap] values or order is null");
        }

        Map<String, DSwareVolumeInfo> volumeInfos = new HashMap<String, DSwareVolumeInfo>();
        try {
            int currentPos = Constants.DEFAULT_COPY_START_POS;
            while ((values.length - currentPos) >= Constants.VOLUME_INFO_LENTH) {
                DSwareVolumeInfoReserve volumeInfoReserve = new DSwareVolumeInfoReserve();
                DSwareVolumeInfo volumeInfo = new DSwareVolumeInfo();

                byte[] tempArray = new byte[Constants.VOLUME_INFO_LENTH];
                System.arraycopy(
                        values, currentPos, tempArray, Constants.DEFAULT_COPY_START_POS, Constants.VOLUME_INFO_LENTH);

                byteArrayToBean(volumeInfoReserve, tempArray, order);
                transReservevolumeInfo(volumeInfoReserve, volumeInfo);
                volumeInfos.put(volumeInfo.getVolName(), volumeInfo);
                currentPos += Constants.VOLUME_INFO_LENTH;
            }
        } catch (IllegalAccessException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToVolumeInfoMap] byte array to java bean error");
        } catch (InvocationTargetException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToVolumeInfoMap] byte array to java bean error");
        }
        return volumeInfos;
    }

    /**
     * 从字节数组中获取卷列表信息
     *
     * @param values    字节数组
     * @param order     转换成结果字节序
     * @return  List<DSwareVolumeInfo>
     */
    public static List<DSwareVolumeInfo> byteArrayToVolumeInfoList(byte[] values, ByteOrder order) {
        if (null == values || null == order) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToVolumeInfoMap] values or order is null");
        }

        List<DSwareVolumeInfo> volumeInfos = new ArrayList<DSwareVolumeInfo>();
        try {
            int currentPos = Constants.DEFAULT_COPY_START_POS;
            while ((values.length - currentPos) >= Constants.VOLUME_INFO_LENTH) {
                DSwareVolumeInfoReserve volumeInfoReserve = new DSwareVolumeInfoReserve();
                DSwareVolumeInfo volumeInfo = new DSwareVolumeInfo();

                byte[] tempArray = new byte[Constants.VOLUME_INFO_LENTH];
                System.arraycopy(
                        values, currentPos, tempArray, Constants.DEFAULT_COPY_START_POS, Constants.VOLUME_INFO_LENTH);

                LOGGER.info("tempArray: " + Arrays.toString(tempArray));

                byteArrayToBean(volumeInfoReserve, tempArray, order);
                transReservevolumeInfo(volumeInfoReserve, volumeInfo);

                LOGGER.info(
                        "currentPos "
                                + currentPos
                                + " volumeInfoReserve: "
                                + volumeInfoReserve
                                + " volumeInfo: "
                                + volumeInfo);

                volumeInfos.add(volumeInfo);
                currentPos += Constants.VOLUME_INFO_LENTH;
            }
        } catch (IllegalAccessException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToVolumeInfoList] byte array to java bean error");
        } catch (InvocationTargetException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToVolumeInfoList] byte array to java bean error");
        }
        return volumeInfos;
    }

    /**
     * 从解析出的包含保留字段的DSwareVolumeInfoReserve中提取出DSwareVolumeInfo
     *
     * 从解析出的包含保留字段的DSwareVolumeInfoReserve中提取出DSwareVolumeInfo
     *
     * @param volumeInfoReserve     含保留字段对象
     * @param volumeInfo    不含保留字段对象
     *
     */
    public static void transReservevolumeInfo(DSwareVolumeInfoReserve volumeInfoReserve, DSwareVolumeInfo volumeInfo) {
        if (null == volumeInfoReserve || null == volumeInfo) {
            LOGGER.error("volumeInfoReserve or volumeInfo is null.");
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR);
        }
        volumeInfo.setCreateTime(volumeInfoReserve.getCreateTime());
        volumeInfo.setFatherName(volumeInfoReserve.getFatherName());
        volumeInfo.setPoolId(volumeInfoReserve.getPoolId());
        volumeInfo.setRealSize(volumeInfoReserve.getRealSize());
        volumeInfo.setStatus(volumeInfoReserve.getStatus());
        volumeInfo.setVolName(volumeInfoReserve.getVolName());
        volumeInfo.setVolSize(volumeInfoReserve.getVolSize());
        volumeInfo.setEncryptFlag(volumeInfoReserve.getEncryptFlag());
        volumeInfo.setLunId(volumeInfoReserve.getLunId());
        volumeInfo.setlldProgress(volumeInfoReserve.getlldProgress());
        volumeInfo.setRwRight(volumeInfoReserve.getRwRight());
    }

    /**
     * 为snapInfo剔除保留字段
     *
     * 为snapInfo剔除保留字段
     *
     * @param snapInfoReserve   snapInfoReserve
     * @param snapInfo    snapInfo
     *
     */
    public static void transReserveSnapInfo(DSwareSnapInfoReserve snapInfoReserve, DSwareSnapInfo snapInfo) {
        if (null == snapInfoReserve || null == snapInfo) {
            LOGGER.error("snapInfoReserve or snapInfo is null.");
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR);
        }
        snapInfo.setCreateTime(snapInfoReserve.getCreateTime());
        snapInfo.setDeletePrority(snapInfoReserve.getDeletePrority());
        snapInfo.setFatherName(snapInfoReserve.getFatherName());
        snapInfo.setPoolId(snapInfoReserve.getPoolId());
        snapInfo.setRealSize(snapInfoReserve.getRealSize());
        snapInfo.setSnapName(snapInfoReserve.getSnapName());
        snapInfo.setSnapSize(snapInfoReserve.getSnapSize());
        snapInfo.setStatus(snapInfoReserve.getStatus());
        snapInfo.setEncryptFlag(snapInfoReserve.getEncryptFlag());
        snapInfo.setSmartCacheFlag(snapInfoReserve.getSmartCacheFlag());
        snapInfo.setTreeId(snapInfoReserve.getTreeId());
        snapInfo.setBranchId(snapInfoReserve.getBranchId());
        snapInfo.setSnapId(snapInfoReserve.getSnapId());
    }

    /**
     * 为poolInfo剔除保留字段
     *
     * 为poolInfo剔除保留字段
     *
     * @param poolInfoReserve   poolInfoReserve
     * @param poolInfo    poolInfo
     *
     */
    public static void transReservePoolInfo(DSwarePoolInfoReserve poolInfoReserve, DSwarePoolInfo poolInfo) {
        if (null == poolInfoReserve || null == poolInfo) {
            LOGGER.error("poolInfoReserve or poolInfo is null.");
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR);
        }
        poolInfo.setAllocCapacity(poolInfoReserve.getAllocCapacity());
        poolInfo.setPoolId(poolInfoReserve.getPoolId());
        poolInfo.setTotalCapacity(poolInfoReserve.getTotalCapacity());
        poolInfo.setUsedCapacity(poolInfoReserve.getUsedCapcity());
    }

    /**
     * 为backupInfo剔除保留字段
     *
     * 为backupInfo剔除保留字段
     *
     * @param backupInfoReserve   backupInfoReserve
     * @param backupInfo    backupInfo
     *
     */
    public static void transReserveBackupInfo(DSwareBackupInfoReserve backupInfoReserve, DSwareBackupInfo backupInfo) {
        if (null == backupInfoReserve || null == backupInfo) {
            LOGGER.error("backupInfoReserve or backupInfo is null.");
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR);
        }
        backupInfo.setBitmapSize(backupInfoReserve.getBitmapSize());
        backupInfo.setBlockSize(backupInfoReserve.getBlockSize());
        backupInfo.setSnapSize(backupInfoReserve.getSnapSize());
    }

    /**
     * 为dswareBitmapVolumeInfo剔除保留字段
     *
     * 为dswareBitmapVolumeInfo剔除保留字段
     *
     * @param dswareBitmapVolumeInfoReserve   dswareBitmapVolumeInfoReserve
     * @param dswareBitmapVolumeInfo    dswareBitmapVolumeInfo
     *
     */
    public static void transReserveBitMapVolumeInfo(
            DSwareBitmapVolumeInfoReserve dswareBitmapVolumeInfoReserve,
            DSwareBitmapVolumeInfo dswareBitmapVolumeInfo) {
        if (null == dswareBitmapVolumeInfoReserve || null == dswareBitmapVolumeInfo) {
            LOGGER.error("dswareBitmapVolumeInfoReserve or dswareBitmapVolumeInfo is null.");
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR);
        }
        dswareBitmapVolumeInfo.setBlockSize(dswareBitmapVolumeInfoReserve.getBlockSize());
        dswareBitmapVolumeInfo.setCreateTime(dswareBitmapVolumeInfoReserve.getCreateTime());
        dswareBitmapVolumeInfo.setPoolId(dswareBitmapVolumeInfoReserve.getPoolId());
        dswareBitmapVolumeInfo.setSnapNameFrom(dswareBitmapVolumeInfoReserve.getSnapNameFrom());
        dswareBitmapVolumeInfo.setSnapNameTo(dswareBitmapVolumeInfoReserve.getSnapNameTo());
        dswareBitmapVolumeInfo.setSnapSize(dswareBitmapVolumeInfoReserve.getSnapSize());
        dswareBitmapVolumeInfo.setStatus(dswareBitmapVolumeInfoReserve.getStatus());
        dswareBitmapVolumeInfo.setVolName(dswareBitmapVolumeInfoReserve.getVolName());
        dswareBitmapVolumeInfo.setVolSize(dswareBitmapVolumeInfoReserve.getVolSize());
    }

    /**
     * 从字节数组中获取快照信息列表
     *
     * @param values    字节数组
     * @param order     转换成结果字节序
     * @return  Map<Integer, DSwareSnapInfo>
     */
    public static List<DSwareSnapInfo> byteArrayToSnapInfoList(byte[] values, ByteOrder order) {
        if (null == values || null == order) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToSnapInfoMap] values or order is null");
        }

        List<DSwareSnapInfo> snapInfos = new ArrayList<DSwareSnapInfo>();
        try {
            int currentPos = Constants.DEFAULT_COPY_START_POS;
            while ((values.length - currentPos) >= Constants.SNAP_INFO_LENTH) {
                DSwareSnapInfoReserve snapInfoReserve = new DSwareSnapInfoReserve();
                DSwareSnapInfo snapInfo = new DSwareSnapInfo();
                byte[] tempArray = new byte[Constants.SNAP_INFO_LENTH];
                System.arraycopy(
                        values, currentPos, tempArray, Constants.DEFAULT_COPY_START_POS, Constants.SNAP_INFO_LENTH);
                byteArrayToBean(snapInfoReserve, tempArray, order);
                transReserveSnapInfo(snapInfoReserve, snapInfo);
                snapInfos.add(snapInfo);
                currentPos += Constants.SNAP_INFO_LENTH;
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToSnapInfoList] byte array to bean error");
        }
        return snapInfos;
    }

    /**
     * 从字节数组中获取快照信息列表
     *
     * @param values    字节数组
     * @param order     转换成结果字节序
     * @return  Map<Integer, DSwareSnapInfo>
     */
    public static Map<String, DSwareSnapInfo> byteArrayToSnapInfoMap(byte[] values, ByteOrder order) {
        if (null == values || null == order) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToSnapInfoMap] values or order is null");
        }

        Map<String, DSwareSnapInfo> snapInfos = new HashMap<String, DSwareSnapInfo>();
        try {
            int currentPos = Constants.DEFAULT_COPY_START_POS;
            while ((values.length - currentPos) >= Constants.SNAP_INFO_LENTH) {
                byte[] tempArray = new byte[Constants.SNAP_INFO_LENTH];
                System.arraycopy(
                        values, currentPos, tempArray, Constants.DEFAULT_COPY_START_POS, Constants.SNAP_INFO_LENTH);
                DSwareSnapInfoReserve snapInfoReserve = new DSwareSnapInfoReserve();
                DSwareSnapInfo snapInfo = new DSwareSnapInfo();
                byteArrayToBean(snapInfoReserve, tempArray, order);
                transReserveSnapInfo(snapInfoReserve, snapInfo);
                snapInfos.put(snapInfo.getSnapName(), snapInfo);
                currentPos += Constants.SNAP_INFO_LENTH;
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToSnapInfoMap] byte array to bean error");
        }
        return snapInfos;
    }

    /**
     * 从字节数组中获取资源池信息列表
     *
     * @param values    字节数组
     * @param order     转换成结果字节序
     * @return  Map<Integer, DSwarePoolInfo>
     */
    public static Map<Integer, DSwarePoolInfo> byteArrayToPoolInfoMap(byte[] values, ByteOrder order) {
        if (null == values || null == order) {
            throw new DSwareException(
                    DSwareErrorCode.UNKNOWN_FAILURE, "[byteArrayToPoolInfoMap] values or order is null");
        }

        Map<Integer, DSwarePoolInfo> poolInfos = new HashMap<Integer, DSwarePoolInfo>();
        int currentPos = Constants.DEFAULT_COPY_START_POS;
        try {
            while ((values.length - currentPos) >= Constants.POOL_INFO_LENTH) {
                DSwarePoolInfo poolInfo = new DSwarePoolInfo();
                byte[] tempArray = new byte[Constants.POOL_INFO_LENTH];
                System.arraycopy(
                        values, currentPos, tempArray, Constants.DEFAULT_COPY_START_POS, Constants.POOL_INFO_LENTH);
                byteArrayToBean(poolInfo, tempArray, order);
                poolInfos.put(poolInfo.getPoolId(), poolInfo);
                currentPos += Constants.POOL_INFO_LENTH;
            }
        } catch (IllegalAccessException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToPoolInfoMap] byte array to bean error");
        } catch (InvocationTargetException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToPoolInfoMap] byte array to bean error");
        }

        return poolInfos;
    }

    /**
     * 从字节数组中获取bitmap信息列表
     *
     * @param values    字节数组
     * @param order     转换成结果字节序
     * @return  Map<Integer, DSwarePoolInfo>
     */
    public static Map<String, DSwareBitmapVolumeInfo> byteArrayToBitmapVolumeInfoMap(byte[] values, ByteOrder order) {
        if (null == values || null == order) {
            throw new DSwareException(
                    DSwareErrorCode.UNKNOWN_FAILURE, "[byteArrayToBitmapVolumeInfoMap] values or order is null");
        }

        Map<String, DSwareBitmapVolumeInfo> bitmapVolInfos = new HashMap<String, DSwareBitmapVolumeInfo>();
        int currentPos = Constants.DEFAULT_COPY_START_POS;
        try {
            while ((values.length - currentPos) >= Constants.DSWARE_BITMAP_VOLUME_INFO_LENGTH) {
                DSwareBitmapVolumeInfoReserve dswareBitmapVolumeInfoReserve = new DSwareBitmapVolumeInfoReserve();
                DSwareBitmapVolumeInfo dswareBitmapVolumeInfo = new DSwareBitmapVolumeInfo();
                byte[] tempArray = new byte[Constants.DSWARE_BITMAP_VOLUME_INFO_LENGTH];
                System.arraycopy(
                        values,
                        currentPos,
                        tempArray,
                        Constants.DEFAULT_COPY_START_POS,
                        Constants.DSWARE_BITMAP_VOLUME_INFO_LENGTH);
                byteArrayToBean(dswareBitmapVolumeInfoReserve, tempArray, order);
                transReserveBitMapVolumeInfo(dswareBitmapVolumeInfoReserve, dswareBitmapVolumeInfo);
                bitmapVolInfos.put(dswareBitmapVolumeInfo.getVolName(), dswareBitmapVolumeInfo);
                currentPos += Constants.DSWARE_BITMAP_VOLUME_INFO_LENGTH;
            }
        } catch (IllegalAccessException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToBitmapVolumeInfoMap] byte array to bean error");
        } catch (InvocationTargetException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToBitmapVolumeInfoMap] byte array to bean error");
        }

        return bitmapVolInfos;
    }

    /**
     * 从字节数组中获取bitmap信息列表
     *
     * @param values    字节数组
     * @param order     转换成结果字节序
     * @return List<DSwareBitmapVolumeInfo>
     */
    public static List<DSwareBitmapVolumeInfo> byteArrayToBitmapVolumeInfoList(byte[] values, ByteOrder order) {
        if (null == values || null == order) {
            throw new DSwareException(
                    DSwareErrorCode.UNKNOWN_FAILURE, "[byteArrayToBitmapVolumeInfoMap] values or order is null");
        }

        List<DSwareBitmapVolumeInfo> bitmapVolInfos = new ArrayList<DSwareBitmapVolumeInfo>();
        int currentPos = Constants.DEFAULT_COPY_START_POS;
        try {
            while ((values.length - currentPos) >= Constants.DSWARE_BITMAP_VOLUME_INFO_LENGTH) {
                DSwareBitmapVolumeInfoReserve dswareBitmapVolumeInfoReserve = new DSwareBitmapVolumeInfoReserve();
                DSwareBitmapVolumeInfo dswareBitmapVolumeInfo = new DSwareBitmapVolumeInfo();
                byte[] tempArray = new byte[Constants.DSWARE_BITMAP_VOLUME_INFO_LENGTH];
                System.arraycopy(
                        values,
                        currentPos,
                        tempArray,
                        Constants.DEFAULT_COPY_START_POS,
                        Constants.DSWARE_BITMAP_VOLUME_INFO_LENGTH);
                byteArrayToBean(dswareBitmapVolumeInfoReserve, tempArray, order);
                transReserveBitMapVolumeInfo(dswareBitmapVolumeInfoReserve, dswareBitmapVolumeInfo);
                bitmapVolInfos.add(dswareBitmapVolumeInfo);
                currentPos += Constants.DSWARE_BITMAP_VOLUME_INFO_LENGTH;
            }
        } catch (IllegalAccessException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToBitmapVolumeInfoList] byte array to bean error");
        } catch (InvocationTargetException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToBitmapVolumeInfoList] byte array to bean error");
        }

        return bitmapVolInfos;
    }

    /**
     * 从字节数组中获取挂载点详细信息
     *
     * @param values    字节数组
     * @param order     转换成结果字节序
     * @param volName   卷名
     * @return  Map<Integer, DSwareVolumeAttachInfo>
     */
    public static Map<Integer, DSwareVolumeAttachInfo> byteArrayToVolumeAttachInfoMap(
            byte[] values, ByteOrder order, String volName) {
        if (null == values || null == order || null == volName) {
            throw new DSwareException(
                    DSwareErrorCode.UNKNOWN_FAILURE, "[byteArrayToVolumeAttachInfoMap] values or order is null");
        }

        Map<Integer, DSwareVolumeAttachInfo> volumeAttachInfos = new HashMap<Integer, DSwareVolumeAttachInfo>();
        int currentPos = Constants.DEFAULT_COPY_START_POS;
        try {
            DSwareVolumeAttachInfo volumeAttachInfo = null;
            while ((values.length - currentPos) >= Constants.VOLUME_ATTACH_INFO_LENGTH) {
                volumeAttachInfo = new DSwareVolumeAttachInfo();
                byte[] tempArray = new byte[Constants.VOLUME_ATTACH_INFO_LENGTH];
                System.arraycopy(
                        values,
                        currentPos,
                        tempArray,
                        Constants.DEFAULT_COPY_START_POS,
                        Constants.VOLUME_ATTACH_INFO_LENGTH);
                byteArrayToBean(volumeAttachInfo, tempArray, order);
                volumeAttachInfo.setVolName(volName);
                volumeAttachInfos.put(Integer.valueOf(volumeAttachInfo.getNodeId()), volumeAttachInfo);
                currentPos += Constants.VOLUME_ATTACH_INFO_LENGTH;
            }
        } catch (IllegalAccessException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToVolumeAttachInfoMap] byte array to bean error");
        } catch (InvocationTargetException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToVolumeAttachInfoMap] byte array to bean error");
        }

        return volumeAttachInfos;
    }

    /**
     * 从字节数组中获取查询存储池的信息
     *
     * @param values    字节数组
     * @param order     转换成结果字节序
     * @return  Map<Integer, vbsCliPoolInfos>
     */
    public static Map<Integer, VbsCliPoolInfo> byteArrayToVbsCliPoolInfoMap(byte[] values, ByteOrder order) {
        if (null == values || null == order) {
            throw new DSwareException(
                    DSwareErrorCode.UNKNOWN_FAILURE, "[byteArrayToVbsCliPoolInfoMap] values or order is null");
        }
        LOGGER.info("[byteArrayToVbsCliPoolInfoMap] value.length:" + values.length);

        Map<Integer, VbsCliPoolInfo> vbsCliPoolInfos = new HashMap<Integer, VbsCliPoolInfo>();
        int currentPos = Constants.DEFAULT_COPY_START_POS;
        try {
            VbsCliPoolInfo poolInfo = null;
            while ((values.length - currentPos) >= Constants.QUERY_TEST_ALL_POOL_INFO_LENGTH) {
                poolInfo = new VbsCliPoolInfo();
                byte[] tempArray = new byte[Constants.QUERY_TEST_ALL_POOL_INFO_LENGTH];
                System.arraycopy(
                        values,
                        currentPos,
                        tempArray,
                        Constants.DEFAULT_COPY_START_POS,
                        Constants.QUERY_TEST_ALL_POOL_INFO_LENGTH);
                byteArrayToBean(poolInfo, tempArray, order);
                vbsCliPoolInfos.put((int) poolInfo.getPoolId(), poolInfo);
                currentPos += Constants.QUERY_TEST_ALL_POOL_INFO_LENGTH;
            }
        } catch (IllegalAccessException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToVbsCliPoolInfoMap] byte array to bean error");
        } catch (InvocationTargetException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToVbsCliPoolInfoMap] byte array to bean error");
        }

        return vbsCliPoolInfos;
    }

    /**
     * 根据参数封装请求消息对象
     *
     * @param request   请求消息对象
     * @param order     消息字节序
     * @param   strLen  消息参数中String的固定长度，如果objects参数中不包含String类型，则默认填0
     * @param   objects 请求消息中参数数组，如果该数组中包含有String类型，则长度固定使用strLen
     */
    public static void makeRequest(RequestMessage request, ByteOrder order, int strLen, Object... objects) {
        if (null == request || null == order || null == objects) {
            throw new DSwareException(DSwareErrorCode.UNKNOWN_FAILURE, "request, order or objects is null");
        }
        for (Object o : objects) {
            if (o instanceof Integer) {
                request.addIntToMsgBody((Integer) o, order);
            } else if (o instanceof Byte) {
                request.addByteToMsgBody((Byte) o, order);
            } else if (o instanceof Short) {
                request.addShortToMsgBody((Short) o, order);
            } else if (o instanceof Double) {
                request.addDoubleToMsgBody((Double) o, order);
            } else if (o instanceof Long) {
                request.addLongToMsgBody((Long) o, order);
            } else if (o instanceof Float) {
                request.addFloatToMsgBody((Float) o, order);
            } else if (o instanceof String) {
                request.addStringToMsgBody((String) o, strLen, order);
            } else {
                throw new DSwareException(
                        DSwareErrorCode.NO_SUCH_TYPE_ERROR,
                        "[makeRequest] No such type for requestMessage, value:" + o.toString());
            }
        }
    }

    /**
     *
     * 获取 page Num
     *
     * @param totalNum     总数
     * @param pageSize     每页数目
     * @return  [返回类型说明]
     * @exception/throws [违例类型] [违例说明]
     * @see          [类、类#方法、类#成员]
     */
    public static int getPageNum(int totalNum, int pageSize) {
        int pageNum = 0;
        if (totalNum % pageSize == 0) {
            pageNum = totalNum / pageSize;
        } else {
            pageNum = (totalNum / pageSize) + 1;
        }

        return pageNum;
    }

    /**
     *
     * 从字节源数组中获取指定长度的字节流
     *
     * @param bytesSrc  字节源数组
     * @param srcStart  字节复制开始位置
     * @param   getLenth    要复制的字节流长度
     * @return  byte[]
     * @exception/throws DSwareException  dsware运行时异常
     */
    public static byte[] getSpecifyBytes(byte[] bytesSrc, int srcStart, int getLenth) {
        if (bytesSrc == null) {
            throw new DSwareException(DSwareErrorCode.MSG_FROM_AGENT_INCORRECT, "[getSpecifyBytes] bytesSrc is null");
        }

        if (0 == getLenth) {
            throw new DSwareException(DSwareErrorCode.MSG_FROM_AGENT_INCORRECT, "[getSpecifyBytes] getLength = 0");
        }

        if ((bytesSrc.length - srcStart) < getLenth) {
            throw new DSwareException(
                    DSwareErrorCode.MSG_FROM_AGENT_INCORRECT,
                    "[getSpecifyBytes]  bytesSrc is less than specified lenth, "
                            + "bytesSrc length: "
                            + bytesSrc.length
                            + ", specified lenth:"
                            + getLenth
                            + " bytesSrc:"
                            + Arrays.toString(bytesSrc));
        }

        byte[] array = new byte[getLenth];
        System.arraycopy(bytesSrc, srcStart, array, 0, getLenth);
        return array;
    }

    /**
     * 获取纳秒级别的时间
     *
     * @return long
     */
    public static synchronized long getNanoTime() {
        // 用于获取纳秒最后6位
        long base = Constants.BASE;
        long timestamp = System.currentTimeMillis();
        long value = System.nanoTime();
        value = value % base;
        timestamp = timestamp * base + value;
        return timestamp;
    }

    /**
     * 从解析出的包含保留字段的DSwareVolumeDetailInfoReserve中提取出DSwareVolumeDetailInfo
     *
     * 从解析出的包含保留字段的DSwareVolumeDetailInfoReserve中提取出DSwareVolumeDetailInfo
     *
     * @param volumeDetailInfoReserve     含保留字段对象
     * @param volumeDetailInfo    不含保留字段对象
     *
     */
    public static void transReservevolumeDetailInfo(
            DSwareVolumeDetailInfoReserve volumeDetailInfoReserve, DSwareVolumeDetailInfo volumeDetailInfo) {
        if (null == volumeDetailInfoReserve || null == volumeDetailInfo) {
            LOGGER.error("qosInfoReserve or qosInfo is null.");
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR);
        }
        volumeDetailInfo.setCreateTime(volumeDetailInfoReserve.getCreateTime());
        volumeDetailInfo.setFatherName(volumeDetailInfoReserve.getFatherName());
        volumeDetailInfo.setPoolId(volumeDetailInfoReserve.getPoolId());
        volumeDetailInfo.setRealSize(volumeDetailInfoReserve.getRealSize());
        volumeDetailInfo.setStatus(volumeDetailInfoReserve.getStatus());
        volumeDetailInfo.setVolName(volumeDetailInfoReserve.getVolName());
        volumeDetailInfo.setVolSize(volumeDetailInfoReserve.getVolSize());
        volumeDetailInfo.setWwn(volumeDetailInfoReserve.getWwn());
    }

    /**
     * 从解析出的包含保留字段的DSwareQosInfoReserve中提取出DSwareQosInfo
     *
     * 从解析出的包含保留字段的DSwareQosInfoReserve中提取出DSwareQosInfo
     *
     * @param qosInfoReserve     含保留字段对象
     * @param qosInfo    不含保留字段对象
     *
     */
    public static void transReserveqosInfo(DSwareQosInfoReserve qosInfoReserve, DSwareQosInfo qosInfo) {
        if (null == qosInfoReserve || null == qosInfo) {
            LOGGER.error("qosInfoReserve or qosInfo is null.");
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR);
        }
        qosInfo.setQosName(qosInfoReserve.getQosName());
        qosInfo.setCreateTime(qosInfoReserve.getCreateTime());
        qosInfo.setQosParaNum(qosInfoReserve.getQosParaNum());
    }

    /**
     * 从解析出的包含保留字段的DSwareQosPoolInfoReserve中提取出DSwareQosPoolInfo
     *
     * 从解析出的包含保留字段的DSwareQosPoolInfoReserve中提取出DSwareQosPoolInfo
     *
     * @param qosPoolInfoReserve     含保留字段对象
     * @param qosPoolInfo    不含保留字段对象
     *
     */
    public static void transReserveqosPoolInfo(
            DSwareQosPoolInfoReserve qosPoolInfoReserve, DSwareQosPoolInfo qosPoolInfo) {
        if (null == qosPoolInfoReserve || null == qosPoolInfo) {
            LOGGER.error("qosPoolInfoReserve or qosInfo is null.");
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR);
        }
        qosPoolInfo.setPoolId(qosPoolInfoReserve.getPoolId());
    }

    /**
     * 从解析出的包含保留字段的DSwareQosCfgInfoReserve中提取出DSwareQosCfgInfo
     *
     * @param qosCfgInfoReserve     含保留字段对象
     * @param qosCfgInfo    不含保留字段对象
     */
    public static void transReserveqosCfgInfo(DSwareQosCfgInfoReserve qosCfgInfoReserve, DSwareQosCfgInfo qosCfgInfo) {
        if (null == qosCfgInfoReserve || null == qosCfgInfo) {
            LOGGER.error("qosInfoReserve or qosInfo is null.");
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR);
        }
        qosCfgInfo.setKey(qosCfgInfoReserve.getKey());
        qosCfgInfo.setValue(qosCfgInfoReserve.getValue());
    }

    /**
     * 从字节数组中获取qos列表信息
     *
     * @param values    字节数组
     * @param order     转换成结果字节序
     * @return  Map<String, DSwareQosCfgInfo>  qos配置信息
     */
    public static Map<String, DSwareQosCfgInfo> byteArrayToQosCfgInfoMap(byte[] values, ByteOrder order) {
        if (null == values || null == order) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToQosCfgInfoMap] values or order is null");
        }

        Map<String, DSwareQosCfgInfo> qosCfgInfos = new HashMap<String, DSwareQosCfgInfo>();
        try {
            int currentPos = Constants.DEFAULT_COPY_START_POS;
            while ((values.length - currentPos) >= Constants.QOS_PARA_INFO_LENTH) {
                DSwareQosCfgInfoReserve qosCfgInfoReserve = new DSwareQosCfgInfoReserve();
                DSwareQosCfgInfo qosCfgInfo = new DSwareQosCfgInfo();

                byte[] tempArray = new byte[Constants.QOS_PARA_INFO_LENTH];
                System.arraycopy(
                        values, currentPos, tempArray, Constants.DEFAULT_COPY_START_POS, Constants.QOS_PARA_INFO_LENTH);

                byteArrayToBean(qosCfgInfoReserve, tempArray, order);
                transReserveqosCfgInfo(qosCfgInfoReserve, qosCfgInfo);
                qosCfgInfos.put(qosCfgInfo.getKey(), qosCfgInfo);
                currentPos += Constants.QOS_PARA_INFO_LENTH;
            }
        } catch (IllegalAccessException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToQosCfgInfoMap] byte array to java bean error");
        } catch (InvocationTargetException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToQosCfgInfoMap] byte array to java bean error");
        }
        return qosCfgInfos;
    }

    /**
     * 将byte数组转换成volumedetail信息
     *
     * @param values    字节数组
     * @param order     转换成结果字节序
     * @return  List<DSwareVolumeDetailInfo>  卷配置信息
     */
    public static List<DSwareVolumeDetailInfo> byteArrayToVolumeDetailInfoList(byte[] values, ByteOrder order) {
        if (null == values || null == order) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToVolumeInfoList] values or order is null");
        }
        ArrayList<DSwareVolumeDetailInfo> volumeDetailInfos = new ArrayList<DSwareVolumeDetailInfo>();
        try {
            int currentPos = Constants.DEFAULT_COPY_START_POS;
            while ((values.length - currentPos) >= Constants.VOLUME_DETAIL_INFO_LENGTH) {
                DSwareVolumeDetailInfo volumeDetailInfo = new DSwareVolumeDetailInfo();
                DSwareVolumeDetailInfoReserve volumeDetailInfoReserve = new DSwareVolumeDetailInfoReserve();

                byte[] tempArray = new byte[Constants.VOLUME_DETAIL_INFO_LENGTH];
                System.arraycopy(
                        values,
                        currentPos,
                        tempArray,
                        Constants.DEFAULT_COPY_START_POS,
                        Constants.VOLUME_DETAIL_INFO_LENGTH);

                byteArrayToBean(volumeDetailInfoReserve, tempArray, order);
                transReservevolumeDetailInfo(volumeDetailInfoReserve, volumeDetailInfo);

                volumeDetailInfos.add(volumeDetailInfo);
                currentPos += Constants.VOLUME_DETAIL_INFO_LENGTH;
            }
        } catch (IllegalAccessException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToVolumeInfoList] byte array to java bean error");
        } catch (InvocationTargetException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToVolumeInfoList] byte array to java bean error");
        }
        return volumeDetailInfos;
    }

    /**
     * 将byte数组转换成qosinfo信息
     *
     * @param values    字节数组
     * @param order     转换成结果字节序
     * @param qosInfoLen 自定义和按容量从中提取的长度不同
     * @return  List<DSwareQosInfo>  qos信息
     */
    public static List<DSwareQosInfo> byteArrayToQosInfoList(byte[] values, ByteOrder order, int qosInfoLen) {
        if (null == values || null == order) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToQosInfoList] values or order is null");
        }
        ArrayList<DSwareQosInfo> qosInfos = new ArrayList<DSwareQosInfo>();
        try {
            int currentPos = Constants.DEFAULT_COPY_START_POS;
            while ((values.length - currentPos) >= qosInfoLen) {
                byte[] tempArray = new byte[Constants.QOS_INFO_LENTH];
                System.arraycopy(
                        values, currentPos, tempArray, Constants.DEFAULT_COPY_START_POS, Constants.QOS_INFO_LENTH);
                DSwareQosInfoReserve qosInfoReserve = new DSwareQosInfoReserve();
                DSwareQosInfo qosInfo = new DSwareQosInfo();
                byteArrayToBean(qosInfoReserve, tempArray, ByteOrder.LITTLE_ENDIAN);
                transReserveqosInfo(qosInfoReserve, qosInfo);

                tempArray = new byte[qosInfoLen - Constants.QOS_INFO_LENTH];
                System.arraycopy(values, currentPos + Constants.QOS_INFO_LENTH,
                        tempArray, Constants.DEFAULT_COPY_START_POS,
                        qosInfoLen - Constants.QOS_INFO_LENTH);
                Map<String, DSwareQosCfgInfo> qosCfgInfoMap =
                        byteArrayToQosCfgInfoMap(tempArray, ByteOrder.LITTLE_ENDIAN);
                qosInfo.setQosCfgMap(qosCfgInfoMap);
                qosInfos.add(qosInfo);
                currentPos += qosInfoLen;
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToQosInfoList] byte array to java bean error");
        }
        return qosInfos;
    }

    /**
     * 将byte数组转换成qosinfo信息
     *
     * @param values    字节数组
     * @param order     转换成结果字节序
     * @param qosInfoLen 自定义和按容量从中提取的长度不同
     * @return  DSwareQosInfo  qos信息
     */
    public static DSwareQosInfo byteArrayToQosInfo(byte[] values, ByteOrder order, int qosInfoLen) {
        if (null == values || null == order) {
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToQosInfo] values or order is null");
        }

        DSwareQosInfo qosInfoOne = new DSwareQosInfo();
        try {
            int currentPos = Constants.DEFAULT_COPY_START_POS;
            if ((values.length - currentPos) >= qosInfoLen) {
                DSwareQosInfoReserve qosInfoReserve = new DSwareQosInfoReserve();
                byte[] tempArray = new byte[Constants.QOS_INFO_LENTH];
                System.arraycopy(
                        values, currentPos, tempArray, Constants.DEFAULT_COPY_START_POS, Constants.QOS_INFO_LENTH);
                byteArrayToBean(qosInfoReserve, tempArray, ByteOrder.LITTLE_ENDIAN);
                transReserveqosInfo(qosInfoReserve, qosInfoOne);

                tempArray = new byte[qosInfoLen - Constants.QOS_INFO_LENTH];
                System.arraycopy(values, currentPos + Constants.QOS_INFO_LENTH,
                        tempArray, Constants.DEFAULT_COPY_START_POS, qosInfoLen - Constants.QOS_INFO_LENTH);
                Map<String, DSwareQosCfgInfo> qosCfgInfoMap =
                        byteArrayToQosCfgInfoMap(tempArray, ByteOrder.LITTLE_ENDIAN);
                qosInfoOne.setQosCfgMap(qosCfgInfoMap);
            } else {
                throw new DSwareException(
                        DSwareErrorCode.PARSE_MSG_ERROR,
                        "[byteArrayToQosInfo] length of byte array is smaller than custom strategy");
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToQosInfo] byte array to java bean error");
        }
        return qosInfoOne;
    }

    /**
     *
     *  判断卷迁移的状态
     *
     * @param vol     DSwareVolumeInfo
     * @return  boolean true 属于迁移状态
     *                  false 未属于迁移状态
     */
    private static boolean isMigDstVolume(DSwareVolumeInfo vol) {
        boolean isMigDst = false;
        if (vol.getStatus() == Constants.VBS_NODE_STATUS_DST_MIGRATING) {
            isMigDst = true;
        } else {
            isMigDst = vol.getVolName().contains(Constants.MIG_DST_VOL_PREFIX);
        }
        return isMigDst;
    }

    /**
     *
     *  判断卷删除状态的状态
     *
     * @param vol     DSwareVolumeInfo
     * @return  boolean true 属于删除状态
     *                  false 未属于删除状态
     */
    private static boolean isDelVolume(DSwareVolumeInfo vol) {
        boolean isDelVol = false;
        int volStatus = vol.getStatus();
        if (volStatus == Constants.VBS_NODE_STATUS_PRE_DEL
                || volStatus == Constants.VBS_NODE_STATUS_MARK_DEL
                || volStatus == Constants.VBS_NODE_STATUS_DELETING
                || volStatus == Constants.VBS_NODE_STATUS_DATA_DELETED) {
            isDelVol = true;
        } else {
            isDelVol = vol.getVolName().contains(Constants.DEL_VOL_PREFIX);
        }
        return isDelVol;
    }

    /**
     *
     *  判断Bitmap卷删除状态的状态
     *
     * @param vol     DSwareVolumeInfo
     * @return  boolean true 属于删除状态
     *                  false 未属于删除状态
     */
    private static boolean isDelBitmapVolume(DSwareBitmapVolumeInfo vol) {
        boolean isDelVol = false;
        int volStatus = vol.getStatus();
        if (volStatus == Constants.VBS_NODE_STATUS_PRE_DEL
                || volStatus == Constants.VBS_NODE_STATUS_MARK_DEL
                || volStatus == Constants.VBS_NODE_STATUS_DELETING
                || volStatus == Constants.VBS_NODE_STATUS_DATA_DELETED) {
            isDelVol = true;
        } else {
            isDelVol = vol.getVolName().contains(Constants.DEL_VOL_PREFIX);
        }
        return isDelVol;
    }

    /**
     *
     *  过滤删除的Bitmap卷
     *
     * @param volumeInfoMap     snapshot Map列表
     */
    public static void filterBitmapVolume(Map<String, DSwareBitmapVolumeInfo> volumeInfoMap) {
        if (null == volumeInfoMap) {
            return;
        }
        Iterator<Map.Entry<String, DSwareBitmapVolumeInfo>> it = volumeInfoMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, DSwareBitmapVolumeInfo> entry = it.next();
            DSwareBitmapVolumeInfo vol = entry.getValue();
            if (ServiceUtils.isDelBitmapVolume(vol)) {
                it.remove();
            }
        }
    }

    /**
     *
     *  过滤删除的卷
     *
     * @param volumeInfoMap     snapshot Map列表
     */
    public static void filterVolume(Map<String, DSwareVolumeInfo> volumeInfoMap) {
        if (null == volumeInfoMap) {
            return;
        }
        Iterator<Map.Entry<String, DSwareVolumeInfo>> it = volumeInfoMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, DSwareVolumeInfo> entry = it.next();
            DSwareVolumeInfo vol = entry.getValue();
            if (ServiceUtils.isMigDstVolume(vol) || ServiceUtils.isDelVolume(vol)) {
                it.remove();
            }
        }
    }

    /**
     *
     *  判断卷删除状态的状态
     *
     * @param vol     DSwareVolumeInfo
     * @return  boolean true 属于删除状态
     *                  false 未属于删除状态
     */
    private static boolean isDelSnap(DSwareSnapInfo vol) {
        boolean isDelVol = false;
        int volStatus = vol.getStatus();
        if (volStatus == Constants.VBS_NODE_STATUS_PRE_DEL
                || volStatus == Constants.VBS_NODE_STATUS_MARK_DEL
                || volStatus == Constants.VBS_NODE_STATUS_DELETING
                || volStatus == Constants.VBS_NODE_STATUS_DATA_DELETED) {
            isDelVol = true;
        } else {
            isDelVol = vol.getSnapName().contains(Constants.DEL_VOL_PREFIX);
        }
        return isDelVol;
    }

    /**
     *
     *  过滤删除的快照
     *
     * @param snapshots     snapshot Map列表
     */
    public static void filterSnap(Map<String, DSwareSnapInfo> snapshots) {
        if (null == snapshots) {
            return;
        }
        Iterator<Map.Entry<String, DSwareSnapInfo>> it = snapshots.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, DSwareSnapInfo> entry = it.next();
            DSwareSnapInfo vol = entry.getValue();
            if (isDelSnap(vol)) {
                it.remove();
            }
        }
    }

    /**
     * 判断创卷请求是否是创加密卷请求
     *
     * @param encrypted  是否加密属性<0/1>
     * @param args       字符串数组
     * @return boolean true:加密卷请求 false:请求参数不合法
     */
    public static boolean isCreateEncryptedVolume(int encrypted, String... args) {
        boolean isCreateEncryptedVolume = false;
        if (encrypted != Constants.VBS_VOLUME_NO_ENCRYPTED && encrypted != Constants.VBS_VOLUME_ENCRYPTED) {
            throw new DSwareException(
                    DSwareErrorCode.INVALID_PARAMETER, "[checkCreateVolumeParameter] parameter encrypred is not valid");
        }
        if (encrypted == Constants.VBS_VOLUME_NO_ENCRYPTED) {
            if (!CommonUtils.isStrEmpty(args)) {
                throw new DSwareException(
                        DSwareErrorCode.INVALID_PARAMETER,
                        "[checkCreateVolumeParameter] create volume parameter is not matched");
            }
            isCreateEncryptedVolume = false;
        } else {
            if (CommonUtils.isStrEmpty(args)) {
                throw new DSwareException(
                        DSwareErrorCode.INVALID_PARAMETER,
                        "[checkCreateVolumeParameter] create volume parameter is not matched");
            }
            isCreateEncryptedVolume = true;
        }

        return isCreateEncryptedVolume;
    }

    /**
     * 为DSwareVolumeMigrateInfo剔除保留字段
     *
     * @param migInfoReserve volume migrate info include reserve
     * @param migInfo volume migrate info
     */
    public static void transReserveVolumeMigrateInfo(
            DSwareVolumeMigrateInfoReserve migInfoReserve, DSwareVolumeMigrateInfo migInfo) {
        if (null == migInfoReserve || null == migInfo) {
            LOGGER.error("dswareBitmapVolumeInfoReserve or dswareBitmapVolumeInfo is null.");
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR);
        }

        migInfo.setVolName(migInfoReserve.getVolName());
        migInfo.setVolLunID(migInfoReserve.getVolLunID());
        migInfo.setNodeSize(migInfoReserve.getNodeSize());
        migInfo.setPoolID(migInfoReserve.getPoolID());
        migInfo.setSrcPool(migInfoReserve.getSrcPool());
        migInfo.setDstPool(migInfoReserve.getDstPool());
        migInfo.setPhase(migInfoReserve.getPhase());
        migInfo.setSnapNum(migInfoReserve.getSnapNum());
        migInfo.setProcess(migInfoReserve.getProcess());
        migInfo.setMigratingNodeName(migInfoReserve.getMigratingNodeName());
        migInfo.setNodeLunID(migInfoReserve.getNodeLunID());
        migInfo.setMigTaskStatus(migInfoReserve.getMigTaskStatus());
        migInfo.setCurMigrateIndex(migInfoReserve.getCurMigrateIndex());
        migInfo.setPos(migInfoReserve.getPos());
        migInfo.setMigrateIOError(migInfoReserve.getMigrateIOError());
    }

    /**
     * 校验输入的变长参数是否合法并转换参数，参数大于等于0有效，小于0则抛异常；未传入的key，赋值0
     *
     * @param params 变长参数map
     * @param paraKeyList 参数key列表
     * @param qosType qos类型
     * @return map <key, value>
     */
    public static Map<String, Long> getParaByKeyList(
            Map<String, Object> params, List<String> paraKeyList, int... qosType) {
        Map<String, Long> paraMap = new HashMap<String, Long>();
        for (String key : paraKeyList) {
            // 为空则设置为0, key对应都有值
            if (null == params.get(key) || 0 == String.valueOf(params.get(key)).length()) {
                paraMap.put(key, 0L);
                continue;
            }

            if (!CommonUtils.isLong(String.valueOf(params.get(key)))) {
                throw new DSwareException(DSwareErrorCode.INVALID_PARAMETER);
            }

            long temp = -1;
            Object tempObj = params.get(key);
            if (null != tempObj) {
                temp = Long.parseLong(tempObj.toString());
            }

            if (temp < 0) {
                int type = 0;
                if (1 == qosType.length && qosType[0] > 0) {
                    type = qosType[0];
                }
                if (!(type == Constants.TEMPORARY_QOS
                        && key.equals("writeLimitIOPS")
                        && temp == Constants.FROZEN_QOS)) {
                    throw new DSwareException(DSwareErrorCode.INVALID_PARAMETER);
                }
            }
            paraMap.put(key, temp);
        }
        return paraMap;
    }

    /**
     * 校验变长参数有效性，有参数小于0抛异常，参数全为0（传入参数值为0+未传入key的值赋值为0）返回异常
     *
     * @param custMap 经过转换的全量key：value集合
     * @param qosType qos类型
     * @return true:参数有效 false:参数无效
     */
    public static boolean checkCustomConfig(Map<String, Long> custMap, int qosType) {
        // 自定义配置了任意一个参数，就认为配置是有效的；参数全为0则返false，参数非法
        boolean isConfiged = false;
        for (Map.Entry<String, Long> en : custMap.entrySet()) {
            if (en.getValue() > 0
                    || (qosType == Constants.TEMPORARY_QOS
                            && en.getKey().equals("writeLimitIOPS")
                            && en.getValue() == Constants.FROZEN_QOS)) {
                isConfiged = true;
            } else if (en.getValue() < 0) {
                LOGGER.error(en.getKey() + " parameter invalid.");
                throw new DSwareException(DSwareErrorCode.INVALID_PARAMETER);
            }
        }
        return isConfiged;
    }

    /**
     * 校验变长参数有效性，有参数小于0抛异常，参数全为0（传入参数值为0+未传入key的值赋值为0）也是正常场景
     *
     * @param custMap 经过转换的全量key：value集合
     * @return true:参数有效 false:参数无效
     */
    public static boolean checkCustomConfigNew(Map<String, Long> custMap) {
        // 自定义配置了任意一个参数，就认为配置是有效的；参数全为0则返false，参数非法
        boolean isConfiged = false;
        for (Map.Entry<String, Long> en : custMap.entrySet()) {
            if (en.getValue() >= 0) {
                isConfiged = true;
            } else if (en.getValue() < 0) {
                LOGGER.error(en.getKey() + " parameter invalid.");
                throw new DSwareException(DSwareErrorCode.INVALID_PARAMETER);
            }
        }
        return isConfiged;
    }

    /**
     * 设置集合类型的参数---填充request
     *
     * @param maxKeyLen 参数key的最大长度，填充message用
     * @param request   请求消息
     * @param inputSpecInfo 传入的参数集合
     */
    public static void setListParam(int maxKeyLen, RequestMessage request, List<Object> inputSpecInfo) {
        for (Object param : inputSpecInfo) {
            if (null != param) {
                request.addStringToMsgBody((String) param, maxKeyLen, ByteOrder.BIG_ENDIAN);
                LOGGER.info("value is " + param);
            } else {
                continue;
            }
        }
    }

    /**
     * 设置变长参数个数---填充request
     *
     * @param request   请求消息
     * @param inputSpecInfo 传入的参数key和value map
     * @param paramKeyList  全量参数key集合
     */
    public static void setVariableLenParamLength(
            RequestMessage request, Map<String, Object> inputSpecInfo, List<String> paramKeyList) {
        int paramNum = 0;
        int paramKeyListSize = paramKeyList.size();
        for (int i = 0; i < paramKeyListSize; i++) {
            Object o = inputSpecInfo.get(paramKeyList.get(i));
            if (null != o && ((Long) o).longValue() != 0) {
                paramNum++;
            }
            // keyList中所有key默认值为0
        }
        LOGGER.info("the param len is" + paramNum);
        request.addIntToMsgBody(paramNum, ByteOrder.LITTLE_ENDIAN);
    }

    /**
     * 设置变长参数的key和value---填充request
     *
     * @param maxKeyLen 参数key的最大长度，填充message用
     * @param request   请求消息
     * @param inputSpecInfo 传入的参数key和value map
     * @param paramKeyList  全量参数key集合
     */
    public static void setVariableLenParam(
        int maxKeyLen, RequestMessage request, Map<String, Object> inputSpecInfo, List<String> paramKeyList) {
        for (String key : paramKeyList) {
            long value;

            Object o = inputSpecInfo.get(key);
            if (null != o && (Long) o != 0) {
                value = (Long) o;
            } else {
                continue;
            }
            request.addStringToMsgBody(key, maxKeyLen, ByteOrder.BIG_ENDIAN);
            request.addLongToMsgBody(value, ByteOrder.LITTLE_ENDIAN);
            LOGGER.info("key is {}, value is {}", key, value);
        }
    }

    /**
     * 通用的填充变长参数方法，传入参数中所有key的值都大于或者等于0（部分等于0），否则前面checkCustomConfig校验不过
     *
     * @param maxKeyLen 参数key的最大长度，填充message用
     * @param request 请求消息
     * @param inputSpecInfo 传入的参数key和value map
     * @param paramKeyList 全量参数key集合
     */
    public static void fillReqWithVariableLenParam(
        int maxKeyLen, RequestMessage request, Map<String, Object> inputSpecInfo, List<String> paramKeyList) {
        int paramNum = 0;
        for (String key : paramKeyList) {
            Object o = inputSpecInfo.get(key);
            if (null != o && (Long) o >= 0) {
                paramNum++;
            }
            // keyList中所有key默认值为0
        }
        LOGGER.info("the param len is" + paramNum);
        request.addIntToMsgBody(paramNum, ByteOrder.LITTLE_ENDIAN);
        for (String key : paramKeyList) {
            long value;
            Object o = inputSpecInfo.get(key);
            if (null != o && (Long) o >= 0) {
                value = (Long) o;
            } else {
                continue;
            }
            request.addStringToMsgBody(key, maxKeyLen, ByteOrder.BIG_ENDIAN);
            request.addLongToMsgBody(value, ByteOrder.LITTLE_ENDIAN);
            LOGGER.info("key is {}, value is {}", key, value);
        }
    }

    /**
     * 检查卷组类型QoS策略是否符合规范
     *
     * @param qosSpecInfo QoS规格参数
     */
    public static void checkVolumeGroupQoSValid(Map<String, Object> qosSpecInfo) {
        Map<String, Long> volGroupQoSMap = getParaByKeyList(qosSpecInfo, Constants.QOS_PARA_KEY_OF_VOLUME_GROUP_TYPE);
        boolean isValidCapacityConfig = checkCustomConfigNew(volGroupQoSMap);
        if (!isValidCapacityConfig) {
            LOGGER.error("Volume Group Type QoS parameter conflict.");
            throw new DSwareException(DSwareErrorCode.INVALID_PARAMETER);
        }
    }

    /**
     * 从解析出的包含保留字段的DSwareVolumeGroupInfoReserve中提取出DSwareVolumeGroupInfo
     *
     * @param volGroupInfoReserve 含保留字对象
     * @param volGroupInfo 不含保留字对象
     */
    public static void transReserveVolumeGroupInfo(
            DSwareVolumeGroupInfoReserve volGroupInfoReserve, DSwareVolumeGroupInfo volGroupInfo) {
        if (null == volGroupInfoReserve || null == volGroupInfo) {
            LOGGER.error("volGroupInfoReserve or volGroupInfo is null.");
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR);
        }

        volGroupInfo.setCreateTime(volGroupInfoReserve.getCreateTime());
        volGroupInfo.setIps(volGroupInfoReserve.getIps());
        volGroupInfo.setQosName(volGroupInfoReserve.getQosName());
        volGroupInfo.setVolumeGroupName(volGroupInfoReserve.getVolumeGroupName());
        volGroupInfo.setVolumeGroupTypeName(volGroupInfoReserve.getVolumeGroupTypeName());
    }

    /**
     * 将byte数组转换成DSwareVolumeGroupInfo信息---单个，未用
     *
     * @param values    字节数组
     * @param order     转换成结果字节序
     * @param volGroupLen   卷组长度
     * @return  DSwareVolumeGroupInfo 卷组信息
     */
    public static DSwareVolumeGroupInfo byteArrayToVolumeGroupInfo(byte[] values, ByteOrder order, int volGroupLen) {
        if (null == values || null == order) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToVolumeGroupInfo] values or order is null");
        }

        DSwareVolumeGroupInfo volumeGroupInfo = new DSwareVolumeGroupInfo();
        try {
            int currentPos = Constants.DEFAULT_COPY_START_POS;
            if ((values.length - currentPos) >= volGroupLen) {
                DSwareVolumeGroupInfoReserve volGroupInfoReserve = new DSwareVolumeGroupInfoReserve();
                byte[] tempArray = new byte[Constants.VOLUME_GROUP_INFO_LENGTH];
                System.arraycopy(
                        values,
                        currentPos,
                        tempArray,
                        Constants.DEFAULT_COPY_START_POS,
                        Constants.VOLUME_GROUP_INFO_LENGTH);
                byteArrayToBean(volGroupInfoReserve, tempArray, ByteOrder.LITTLE_ENDIAN);
                transReserveVolumeGroupInfo(volGroupInfoReserve, volumeGroupInfo);

                tempArray = new byte[volGroupLen - Constants.VOLUME_GROUP_INFO_LENGTH];
                System.arraycopy(values, currentPos + Constants.VOLUME_GROUP_INFO_LENGTH,
                        tempArray, Constants.DEFAULT_COPY_START_POS,
                        volGroupLen - Constants.VOLUME_GROUP_INFO_LENGTH);
                Map<String, DSwareQosCfgInfo> qosCfgInfoMap =
                        byteArrayToQosCfgInfoMap(tempArray, ByteOrder.LITTLE_ENDIAN);
                volumeGroupInfo.setQosCfgMap(qosCfgInfoMap);
            } else {
                throw new DSwareException(
                        DSwareErrorCode.PARSE_MSG_ERROR,
                        "[byteArrayToVolumeGroupInfo] length of byte array is smaller than volGroup");
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToVolumeGroupInfo] byte array to java bean error");
        }
        return volumeGroupInfo;
    }

    /**
     * 字节数组转换成卷组信息集合
     *
     * @param values    字节数组
     * @param order     转换成结果字节序
     * @param volGroupLen   卷组长度，包含完整的QoS信息
     * @return  List<DSwareVolumeGroupInfo> 卷组信息
     */
    public static List<DSwareVolumeGroupInfo> byteArrayToVolGroupInfoList(
            byte[] values, ByteOrder order, int volGroupLen) {
        if (null == values || null == order) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToVolGroupInfoList] values or order is null");
        }
        List<DSwareVolumeGroupInfo> volGroupInfoList = new ArrayList<DSwareVolumeGroupInfo>();
        try {
            int currentPos = Constants.DEFAULT_COPY_START_POS;
            while ((values.length - currentPos) >= volGroupLen) {
                DSwareVolumeGroupInfoReserve volGroupInfoReserve = new DSwareVolumeGroupInfoReserve();
                DSwareVolumeGroupInfo volGroupInfo = new DSwareVolumeGroupInfo();
                byte[] tempArray = new byte[Constants.VOLUME_GROUP_INFO_LENGTH];
                System.arraycopy(
                        values,
                        currentPos,
                        tempArray,
                        Constants.DEFAULT_COPY_START_POS,
                        Constants.VOLUME_GROUP_INFO_LENGTH);
                byteArrayToBean(volGroupInfoReserve, tempArray, ByteOrder.LITTLE_ENDIAN);
                transReserveVolumeGroupInfo(volGroupInfoReserve, volGroupInfo);
                LOGGER.info(
                        "currentPos "
                                + currentPos
                                + "volGroupInfoReserve: "
                                + volGroupInfoReserve
                                + "volGroupInfo: "
                                + volGroupInfo);

                tempArray = new byte[volGroupLen - Constants.VOLUME_GROUP_INFO_LENGTH];
                System.arraycopy(values, currentPos + Constants.VOLUME_GROUP_INFO_LENGTH,
                        tempArray, Constants.DEFAULT_COPY_START_POS,
                        volGroupLen - Constants.VOLUME_GROUP_INFO_LENGTH);
                Map<String, DSwareQosCfgInfo> qosCfgInfoMap =
                        byteArrayToQosCfgInfoMap(tempArray, ByteOrder.LITTLE_ENDIAN);
                volGroupInfo.setQosCfgMap(qosCfgInfoMap);
                volGroupInfoList.add(volGroupInfo);
                currentPos += volGroupLen;
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR, e, "[byteArrayToVolGroupInfoList] byte array to java bean error");
        }
        return volGroupInfoList;
    }

    /**
     * 从解析出的包含保留字段的DSwareVolumeGroupTypeInfoReserve中提取出DSwareVolumeGroupTypeInfo
     *
     * @param volGroupTypeInfoReserve 含保留字对象
     * @param volGroupTypeInfo 不含保留字对象
     */
    public static void transReserveVolumeGroupTypeInfo(
            DSwareVolumeGroupTypeInfoReserve volGroupTypeInfoReserve, DSwareVolumeGroupTypeInfo volGroupTypeInfo) {
        if (null == volGroupTypeInfoReserve || null == volGroupTypeInfo) {
            LOGGER.error("volGroupTypeInfoReserve or volGroupTypeInfo is null.");
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR);
        }
        volGroupTypeInfo.setCreateTime(volGroupTypeInfoReserve.getCreateTime());
        volGroupTypeInfo.setVolGroupTypeName(volGroupTypeInfoReserve.getVolGroupTypeName());
    }

    /**
     * 字节数组转换成卷组信息集合
     *
     * @param values    字节数组
     * @param order     转换成结果字节序
     * @param volGroupTypeLen   卷组长度
     * @return   List<DSwareVolumeGroupInfo> 卷组信息
     */
    public static List<DSwareVolumeGroupTypeInfo> byteArrayToVolGroupTypeInfoList(
            byte[] values, ByteOrder order, int volGroupTypeLen) {
        if (null == values || null == order) {
            throw new DSwareException(
                    DSwareErrorCode.INTERNAL_ERROR, "[byteArrayToVolGroupTypeInfoList] values or order is null");
        }
        List<DSwareVolumeGroupTypeInfo> volGroupTypeInfoList = new ArrayList<DSwareVolumeGroupTypeInfo>();
        try {
            int currentPos = Constants.DEFAULT_COPY_START_POS;
            while ((values.length - currentPos) >= volGroupTypeLen) {
                DSwareVolumeGroupTypeInfoReserve volGroupTypeInfoReserve = new DSwareVolumeGroupTypeInfoReserve();
                DSwareVolumeGroupTypeInfo volGroupTypeInfo = new DSwareVolumeGroupTypeInfo();
                byte[] tempArray = new byte[Constants.VOLUME_GROUP_TYPE_INFO_LENGTH];
                System.arraycopy(
                        values,
                        currentPos,
                        tempArray,
                        Constants.DEFAULT_COPY_START_POS,
                        Constants.VOLUME_GROUP_TYPE_INFO_LENGTH);
                byteArrayToBean(volGroupTypeInfoReserve, tempArray, ByteOrder.LITTLE_ENDIAN);
                transReserveVolumeGroupTypeInfo(volGroupTypeInfoReserve, volGroupTypeInfo);
                LOGGER.info(
                        "currentPos "
                                + currentPos
                                + "volGroupTypeInfoReserve: "
                                + volGroupTypeInfoReserve
                                + "volGroupTypeInfo: "
                                + volGroupTypeInfo);

                tempArray = null;

                tempArray = new byte[volGroupTypeLen - Constants.VOLUME_GROUP_TYPE_INFO_LENGTH];
                System.arraycopy(
                        values,
                        currentPos + Constants.VOLUME_GROUP_TYPE_INFO_LENGTH,
                        tempArray,
                        Constants.DEFAULT_COPY_START_POS,
                        volGroupTypeLen - Constants.VOLUME_GROUP_TYPE_INFO_LENGTH);
                Map<String, DSwareQosCfgInfo> qosCfgInfoMap =
                        byteArrayToQosCfgInfoMap(tempArray, ByteOrder.LITTLE_ENDIAN);
                volGroupTypeInfo.setQosCfgMap(qosCfgInfoMap);
                volGroupTypeInfoList.add(volGroupTypeInfo);
                currentPos += volGroupTypeLen;
            }
        } catch (IllegalAccessException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR,
                    e,
                    "[byteArrayToVolGroupTypeInfoList] byte array to java bean error");
        } catch (InvocationTargetException e) {
            throw new DSwareException(
                    DSwareErrorCode.PARSE_MSG_ERROR,
                    e,
                    "[byteArrayToVolGroupTypeInfoList] byte array to java bean error");
        }
        return volGroupTypeInfoList;
    }

    /**
     * 为DSwareVolumeMigrateInfo剔除保留字段
     *
     * @param imgcpInfoReserve volume migrate info include reserve
     * @param imgcpInfo volume migrate info
     */
    public static void transReserveVolumeImgcpInfo(
            DSwareVolumeImgcpInfoReserve imgcpInfoReserve, DSwareVolumeImgcpInfo imgcpInfo) {
        if (null == imgcpInfoReserve || null == imgcpInfo) {
            LOGGER.error("DSwareVolumeImgcpInfoReserve or DSwareVolumeImgcpInfo is null.");
            throw new DSwareException(DSwareErrorCode.INTERNAL_ERROR);
        }

        imgcpInfo.setVolName(imgcpInfoReserve.getVolName());
        imgcpInfo.setVolLunID(imgcpInfoReserve.getVolLunID());
        imgcpInfo.setPoolID(imgcpInfoReserve.getPoolID());
        imgcpInfo.setNodeSize(imgcpInfoReserve.getNodeSize());
        imgcpInfo.setImgcpTaskType(imgcpInfoReserve.getImgcpTaskType());
        imgcpInfo.setTaskID(imgcpInfoReserve.getTaskID());
        imgcpInfo.setImageID(imgcpInfoReserve.getImageID());
        imgcpInfo.setImageSize(imgcpInfoReserve.getImageSize());
        imgcpInfo.setOffset(imgcpInfoReserve.getOffset());
        imgcpInfo.setProcess(imgcpInfoReserve.getProcess());
        imgcpInfo.setPos(imgcpInfoReserve.getPos());
        imgcpInfo.setPhase(imgcpInfoReserve.getPhase());
        imgcpInfo.setImgcpTaskStatus(imgcpInfoReserve.getImgcpTaskStatus());
        imgcpInfo.setImgcpIOErr(imgcpInfoReserve.getImgcpIOErr());
        imgcpInfo.setImgcpOBSErr(imgcpInfoReserve.getImgcpOBSErr());
        imgcpInfo.setImgcpTargetNid(imgcpInfoReserve.getImgcpTargetNid());
    }
}
