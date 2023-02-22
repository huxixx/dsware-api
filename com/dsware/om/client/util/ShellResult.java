/*
 * 文 件 名:  ShellResult.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  l00168478
 * 修改时间:  Dec 25, 2012
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.dsware.om.client.util;

import java.util.List;

/**
 * <执行shell脚本的返回结果>
 * <功能详细描述>
 *
 * @author  l00168478
 * @version  [V100R003C00, 2012-12-28]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ShellResult {
    /**
     * 超时退出
     */
    public static final int TIMEOUT = 13;

    /**
     * 发送异常
     */
    public static final int ERROR = 1;

    /**
     * 执行成功返回值
     */
    public static final int SUCCESS = 0;

    /**
     * 脚本退出时的值
     */
    private int exitValue;

    /**
     * 脚本返回值(执行脚本后再屏幕上打印的消息)
     */
    private List<String> returnValue;

    /**
     * <获取脚本退出时返回的值>
     * <功能详细描述>
     *
     * @return int 脚本退出时的返回值
     * @see [类、类#方法、类#成员]
     */
    public int getExitValue() {
        return exitValue;
    }

    /**
     * <设置脚本退出时返回的值>
     * <功能详细描述>
     *
     * @param exitValue  退出时返回的值
     * @see [类、类#方法、类#成员]
     */
    public void setExitValue(int exitValue) {
        this.exitValue = exitValue;
    }

    /**
     * <获取脚本返回值(执行脚本后再屏幕上打印的消息)>
     * <功能详细描述>
     *
     * @return 脚本返回值(执行脚本后再屏幕上打印的消息)
     * @see [类、类#方法、类#成员]
     */
    public List<String> getReturnValue() {
        return returnValue;
    }

    /**
     * <设置脚本返回值(执行脚本后再屏幕上打印的消息)>
     * <功能详细描述>
     *
     * @param returnValue 脚本返回值(执行脚本后再屏幕上打印的消息)
     * @see [类、类#方法、类#成员]
     */
    public void setReturnValue(List<String> returnValue) {
        this.returnValue = returnValue;
    }
}
