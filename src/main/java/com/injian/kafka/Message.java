package com.injian.kafka;

/**
 * @author Zhang Xinyu
 * @version 1.0
 * @date 2020/4/9 16:35
 */
public class Message {
    private String id;

    //消息值
    private String Msg;
    //重试次数
    private int retries = 0;
    //允许消费时间戳,默认值为当前时间戳
    private long timestamp = System.currentTimeMillis();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
