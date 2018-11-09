package com.ihuanglele.book.exception;

/**
 * Created by ihuanglele on 2018/11/8.
 */
public class PageErrorException extends Exception {
    public PageErrorException(String msg){super(msg);}
    public PageErrorException(){
        super("PageError");
    }
}
