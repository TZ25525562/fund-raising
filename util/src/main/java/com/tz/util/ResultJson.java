package com.tz.util;

/**
 * 统一Ajax返回数据的格式
 * @param <T>
 */
public class ResultJson<T>{

    public static final String SUCCESS="success";
    public static final String FAIL="fail";

    //返回的操作结果
    private String result;

    //返回的信息
    private String message;

    //返回的数据
    private T data;


    /**
     * 返回操作结果为成功，不带数据
     * @return
     */
    public static <E> ResultJson<E> successWithoutData(){
        return new ResultJson<E>(ResultJson.SUCCESS,null,null);
    }

    /**
     * 返回操作结果为成功，携带数据
     * @param data
     * @return
     */
    public static <E> ResultJson<E> successWithData(E data){
        return new ResultJson<E>(ResultJson.SUCCESS,null,data);
    }

    /**
     * 返回操作结果为失败，不带数据携带失败信息
     * @param message
     * @return
     */
    public static <E> ResultJson<E> failWithData(String message){
        return new ResultJson<E>(ResultJson.FAIL,message,null);
    }


    public ResultJson() {
    }

    public ResultJson(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
