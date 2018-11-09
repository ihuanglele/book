package com.ihuanglele.book.exception;

/**
 * Created by ihuanglele on 2018/11/8.
 */
public class PageErrorException extends StopException {
    public PageErrorException(String msg){
        super(msg);
    }

    public PageErrorException(){
        super();
    }
}
