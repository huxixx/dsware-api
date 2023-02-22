package com.dsware.om.client.common;

import com.dsware.om.client.exception.DSwareErrorCode;
import com.dsware.om.client.exception.DSwareException;
import com.dsware.om.client.util.CommonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;

/**
 * 该类主要封装socket连接的建立
 *
 * @author z00194581
 * @version [V100R001C00]
 * @see        [相关类/方法]
 * @since [产品/模块版本]
 */
public class MessageDispatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDispatcher.class);

    /**
     * 单例对象
     */
    private static MessageDispatcher instance = null;

    /**
     * 连接端口号
     */
    private int port = Constants.API_PORT;

    /**
     * 连接超时时间
     */
    private int timeout = Constants.CHANNEL_TIME_OUT;

    private int readtimeout = Constants.CHANNEL_READ_TIME_OUT;

    private int allRetryTime = Constants.SEND_MESSAGE_RETRY_TIME;

    private int retrySleepTime = Constants.SEND_MESSAGE_SLEEP_TIME;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * 获取单例对象方法
     *
     * @return MessageDispatcher
     */
    public static synchronized MessageDispatcher getInstance() {
        if (null == instance) {
            instance = new MessageDispatcher();
        }

        return instance;
    }

    private void closeSocket(Socket socket) {
        if (null != socket) {
            try {
                socket.close();
            } catch (IOException e) {
                LOGGER.error("Close socket failed.");
            }
        }
    }

    private void closeDos(DataOutputStream dos) {
        if (null != dos) {
            try {
                dos.close();
            } catch (IOException e) {
                LOGGER.error("Close dos failed.");
            }
        }
    }

    private void closeDis(DataInputStream dis) {
        if (null != dis) {
            try {
                dis.close();
            } catch (IOException e) {
                LOGGER.error("Close dis failed.");
            }
        }
    }

    private ResponseMessage sendMessageToOneAgent(String agentIp, RequestMessage request) {
        ResponseMessage response = null;
        Socket socket = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;

        try {
            if (port == DSwareSSLContext.getInstance().getAgentSSLPort()) {
                SSLContext sslContext = DSwareSSLContext.getInstance().getSslContext();
                socket = sslContext.getSocketFactory().createSocket();
            } else {
                socket = new Socket();
            }
            InetSocketAddress addr = new InetSocketAddress(agentIp, port);
            // 设置该socket的连接超时时间 3000ms
            socket.connect(addr, timeout);
            // 设置该socket的读数据超时时间 35s
            socket.setSoTimeout(readtimeout);

            // 获取输出流，用于客户端向服务器端发送数据
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());

            ByteBuffer buffer = CommonUtils.messageToBuffer(request, ByteOrder.BIG_ENDIAN);
            dos.write(buffer.array());
            dos.flush();

            response = CommonUtils.readMsgFromChannel(socket, dis, ByteOrder.LITTLE_ENDIAN);

            // LOGGER.debug("[sendMessageToAgent] ResponseMsg:" + response);
            int ret = response.getHeader();
            if (Constants.RET_SUCCESS == ret) {
                return response;
            } else {
                LOGGER.error("Failed to send message to vbs, agent = {}, errorCode = {}", agentIp, ret);
                return response;
            }

        } catch (SocketTimeoutException e) {
            LOGGER.error("socket connect timeout, ip=" + agentIp, e);
        } catch (IOException e) {
            LOGGER.error("Send message to agent io error, ip=" + agentIp + ", msg=" + request);
            LOGGER.error("socket IOException:" + e);
        } catch (Exception e) {
            LOGGER.error("Failed to send message to agent=" + agentIp + ", msg=" + request);
            LOGGER.error("Original exception:" + e);
        } finally {
            closeSocket(socket);
            closeDos(dos);
            closeDis(dis);
        }
        LOGGER.error("[sendMessageToOneAgent] no response messsage from one agent!");
        return null;
    }

    private boolean isNeedRetryCode(DSwareErrorCode errorCodeRet) {
        boolean ret = false;
        if (DSwareErrorCode.NO_RESPONSE_ERROR == errorCodeRet
                || DSwareErrorCode.AGENT_ERR_NO_AVALIABLE_VBS == errorCodeRet
                || DSwareErrorCode.AGENT_CONNECT_ERROR == errorCodeRet) {
            ret = true;
            return ret;
        }

        if (DSwareErrorCode.SOCKET_CONNECT_TIMEOUT == errorCodeRet
                || DSwareErrorCode.SOCKET_READ_TIMEOUT == errorCodeRet
                || DSwareErrorCode.COMMUNICATE_AGENT_ERROR == errorCodeRet
                || DSwareErrorCode.DSW_VBS_UNKNOW_MAJOR_VBS == errorCodeRet) {
            ret = true;
            return ret;
        }

        return ret;
    }

    /**
     *  发送消息到agent
     *
     * @param request   请求消息体
     * @param   agentIp 发送的agent的ip
     * @return CommonMessage   返回消息体
     */
    public ResponseMessage sendMessageToAgent(String[] agentIp, RequestMessage request) {
        // 入参校验
        if (null == agentIp || null == request) {
            LOGGER.error("[sendMessageToAgent] input agent ip or request is null!");
            throw new DSwareException(DSwareErrorCode.INVALID_PARAMETER);
        }
        ResponseMessage response = null;
        // LOGGER.debug("[sendMessageToAgent] RequestMsg:" + request);
        int retryTime = 0;
        while (retryTime < allRetryTime) {
            for (int i = 0; i < agentIp.length; i++) {
                response = sendMessageToOneAgent(agentIp[i], request);
                if (null != response) {
                    int ret = response.getHeader();
                    if (Constants.RET_SUCCESS == ret) {
                        return response;
                    }
                    DSwareErrorCode errorCodeRet = DSwareErrorCode.toEnum(ret);
                    if (!isNeedRetryCode(errorCodeRet)) {
                        return response;
                    }
                }
            }

            LOGGER.info("vbs is starting,retry time: {}", retryTime);

            try {
                TimeUnit.SECONDS.sleep(retrySleepTime);
            } catch (InterruptedException e) {
                LOGGER.error("Sleep exception : " + e.getMessage());
            }

            retryTime = retryTime + 1;
        }

        //  如果获取多次结果为null，则定义为内部错误
        LOGGER.error("[sendMessageToAgent] no response messsage from agent!");
        throw new DSwareException(DSwareErrorCode.NO_RESPONSE_ERROR);
    }
}
