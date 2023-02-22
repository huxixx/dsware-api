package com.dsware.om.client.common;

import java.util.Arrays;

/**
 * <Description>
 *
 * @author j55771
 * @version [V100R001C00, 2012-6-8]
 */
public class ResponseMessage extends CommonMessage {
  /**
   * <Default constructor>
   */
  public ResponseMessage() {
    super();
  }

  /**
   * <Default constructor>
   *
   * @param result handler result
   * @param len    length of value
   * @param value  concrete message data zone
   */
  public ResponseMessage(int result, int len, byte[] value) {
    super(result, len, value);
  }

  /**
   *
   * <设置返回值>
   *
   * @param result 返回值
   */
  public void setResult(int result) {
    setHeader(result);
  }

  /**
   *
   * <获取返回结果>
   *
   * @return 返回值
   */
  public int getResult() {
    return getHeader();
  }

  /**
   * 重写toString方法
   *
   * @return String
   */
  @Override
  public String toString() {
    return "ResponseMessage [header="
        + getHeader()
        + ", length="
        + this.getLength()
        + ", msgBody="
        + Arrays.toString(this.getMsgBody())
        + "]";
  }
}