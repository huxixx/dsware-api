package com.dsware.om.client.exception;

/**
 * DSware异常代码
 *
 * @author     z00194581
 * @version    [V100R001C00]
 * @see        [相关类/方法]
 * @since      [产品/模块版本]
 */
public class DSwareException extends RuntimeException {
    /**
     * 异常序列号
     */
    private static final long serialVersionUID = 5674449369706827434L;

    /**
     * 错误码
     */
    private DSwareErrorCode error;

    /**
     * 异常描述
     */
    private String description;

    /**
     * <默认构造函数>
     */
    public DSwareException() {}

    /**
     * <默认构造函数>
     *
     * @param   error    错误码
     */
    public DSwareException(DSwareErrorCode error) {
        this.setError(error);
    }

    /**
     * <默认构造函数>
     *
     * @param   error   错误码
     * @param   des     异常描述
     */
    public DSwareException(DSwareErrorCode error, String des) {
        this.setError(error);
        this.setDescription(des);
    }

    /**
     * <默认构造函数>
     *
     * @param e 异常
     * @param   des 异常描述
     */
    public DSwareException(Exception e, String des) {
        super(des, e);
        this.setDescription(des);
    }

    /**
     * <默认构造函数>
     *
     * @param   error   错误码
     * @param   e       其他异常
     * @param   des     异常描述
     */
    public DSwareException(DSwareErrorCode error, Exception e, String des) {
        super(e);
        this.setError(error);
        this.setDescription(des);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setError(DSwareErrorCode error) {
        this.error = error;
    }

    public DSwareErrorCode getError() {
        return error;
    }

    /**
     * 重写toString方法
     *
     * @return  String
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("DSwareException ").append(this.description);
        if (null != this.error) {
            buffer.append("//").append(error.toString());
        }
        return buffer.toString();
    }
}
