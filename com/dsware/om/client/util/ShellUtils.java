/*
 * 文 件 名:  ShellUtils.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  l00168478
 * 修改时间:  Dec 22, 2012
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.dsware.om.client.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * <执行shell脚本的工具类>
 * <功能详细描述>
 *
 * @author l00168478
 * @version [V100R003C00, 2012-12-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class ShellUtils {
    /**
     * 日志记录器
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ShellUtils.class);

    /**
     * 默认等待时间
     */
    private static final long DEFAULT_WAIT_TIME = 500000L;

    /**
     * 线程睡眠时间
     */
    private static final int THREAD_SLEEP_TIME = 500;

    private ShellUtils() {
    }

    /**
     * <执行传入的shell命令，使用默认的等待时间5s>
     * <功能详细描述>
     *
     * @param cmd 对应的shell命令
     * @return ShellResult 执行shell命令返回的结果
     * @see [类、类#方法、类#成员]
     */
    public static ShellResult runShell(String cmd) {
        return runShell(cmd, DEFAULT_WAIT_TIME);
    }

    /**
     * <执行传入的shell命令，使用默认的等待时间5s>
     * <功能详细描述>
     *
     * @param cmd 对应的shell命令
     * @return ShellResult 执行shell命令返回的结果
     * @see [类、类#方法、类#成员]
     */
    public static ShellResult runShell(String[] cmd) {
        // 入参判断
        if (null == cmd || cmd.length == 0) {
            LOGGER.error("Input command is null");
            return null;
        }
        return runShell(cmd, DEFAULT_WAIT_TIME);
    }

    /**
     * <执行传入的shell命令，最长等待waitTime的时间>
     * <功能详细描述>
     *
     * @param cmd 对应的shell命令
     * @param waitTime 最长等待时间
     * @return ShellResult 执行shell命令返回的结果
     * @see [类、类#方法、类#成员]
     */
    public static ShellResult runShell(String cmd, long waitTime) {
        // 入参判断
        if (null == cmd || cmd.isEmpty()) {
            LOGGER.error("Input command is null");
            return null;
        }

        String[] commands = new String[] {"/bin/sh", "-c", cmd};
        return runShell(commands, waitTime);
    }

    /**
     * <执行传入的shell命令，最长等待waitTime的时间>
     * <功能详细描述>
     *
     * @param cmd 对应的shell命令
     * @param waitTime 最长等待时间
     * @return ShellResult 执行shell命令返回的结果
     * @see [类、类#方法、类#成员]
     */
    public static ShellResult runShell(String[] cmd, long waitTime) {
        // 入参判断
        if (null == cmd || cmd.length == 0) {
            LOGGER.error("Input command is null");
            return null;
        }

        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd); // NOSECURITYRISK
            return getProcessResult(process, waitTime);
        } catch (IOException e) {
            LOGGER.error("shell excute cmd IO error");
            ShellResult result = new ShellResult();
            result.setExitValue(ShellResult.ERROR);
            return result;
        } catch (Exception e) {
            LOGGER.error("excute shell cmd unkown error ", e);
            ShellResult result = new ShellResult();
            result.setExitValue(ShellResult.ERROR);
            return result;
        } finally {
            if (null != process) {
                process.destroy();
            }
        }
    }

    /**
     * <获取Shell脚本执行的结果>
     * <功能详细描述>
     *
     * @param process 执行shell的进程
     * @param waitTime 最大等待时间
     * @return ShellResult Shell执行的结果
     * @see [类、类#方法、类#成员]
     */
    private static ShellResult getProcessResult(Process process, long waitTime) {
        // 开始执行的时间
        int result = -1;
        boolean timeOut = false;
        ShellResult cmdResult = new ShellResult();
        long loopNumber = waitTime / THREAD_SLEEP_TIME;
        long realLoopNumber = 0L;
        try {
            // 循环等待执行结果
            while (true) {
                try {
                    CommonUtils.sleepTime(THREAD_SLEEP_TIME);
                    result = process.exitValue();
                    break;
                } catch (IllegalThreadStateException e) {
                    realLoopNumber++;
                    if (realLoopNumber >= loopNumber) {
                        timeOut = true;
                        break;
                    }
                } catch (Exception e) {
                    realLoopNumber++;
                    if (realLoopNumber >= loopNumber) {
                        timeOut = true;
                        break;
                    }
                }
            }
            // 如果超时，那么直接返回
            if (timeOut) {
                cmdResult.setExitValue(ShellResult.TIMEOUT);
                return cmdResult;
            }
            // 脚本返回结果
            cmdResult.setExitValue(result);
            if (0 != result) {
                cmdResult.setReturnValue(readStream(process.getErrorStream()));
            } else {
                cmdResult.setReturnValue(readStream(process.getInputStream()));
            }
            return cmdResult;
        } catch (Exception e) {
            LOGGER.error("excute shell cmd unkown error", e);
            cmdResult.setExitValue(ShellResult.ERROR);
            return cmdResult;
        }
    }

    /**
     * 将字符串进行转义
     *
     * @param str 输入字符串
     * @return String 转以后的字符串
     */
    public static String getEscapeString(String str) {
        // 入参判断
        if (StringUtils.isBlank(str)) {
            LOGGER.error("Input str is null");
            return null;
        }

        String escape = str;
        // 先替换 \
        escape = StringUtils.replace(escape, "\\", "\\\\");
        // 再替换其他特殊字符
        String[] specials = {"|", ";", "&", "$", ">", "<", "`", "!", "(", ")", " ", "'", "\""};
        for (String special : specials) {
            if (escape.contains(special)) {
                escape = StringUtils.replace(escape, special, "\\" + special);
            }
        }

        return escape;
    }

    /**
     * <从数据流中读取数据信息，返回一个数据列表>
     * <功能详细描述>
     *
     * @param stream 数据流信息
     * @return List<String> 返回的数据列表
     * @see [类、类#方法、类#成员]
     */
    private static List<String> readStream(InputStream stream) {
        BufferedReader reader = null;
        try {
            // 从数据流中读取数据信息
            reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            if (reader.ready()) {
                String line = "";
                List<String> result = new ArrayList<String>();
                while ((line = reader.readLine()) != null) {
                    result.add(line);
                }
                return result;
            }
            return null;
        } catch (IOException e) {
            LOGGER.error("reader error.");
            return null;
        } catch (Exception e) {
            LOGGER.error("unkown error", e);
            return null;
        } finally {
            // 如果阅读器不为空，那么关闭阅读器
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOGGER.error("close reader error");
                }
            }
        }
    }
}
