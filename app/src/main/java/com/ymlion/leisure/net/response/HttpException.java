package com.ymlion.leisure.net.response;

/**
 * @author ymlion
 * @date 2016/7/13
 */

public class HttpException extends Exception {

    public static final int NET_EXCEPTION = 1;
    public static final int DATA_EXCEPTION = 2;
    public static final int OTHER_EXCEPTION = -1;

    private int type;
    private String message;

    public HttpException(Throwable throwable, int type) {
        super(throwable);
        this.type = type;
    }

    public HttpException(Throwable throwable, int type, String message) {
        super(throwable);
        this.type = type;
        this.message = message;
    }

    public HttpException(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return message == null ? super.getMessage() : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
