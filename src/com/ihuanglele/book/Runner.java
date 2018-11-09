package com.ihuanglele.book;

import com.ihuanglele.book.exception.StopException;
import com.ihuanglele.book.store.IStore;
import com.ihuanglele.book.strategy.AbstractSite;

/**
 * Created by ihuanglele on 2018/11/8.
 */
public class Runner {
    public AbstractSite getSite() {
        return site;
    }

    public void setSite(AbstractSite site) {
        this.site = site;
    }

    private AbstractSite site;
    private String start;
    private IStore store;

    public void run() {
        try{
            site.setStore(store);
            site.start(start);
        }catch (StopException e){
            e.printStackTrace();
        }
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public IStore getStore() {
        return store;
    }

    public void setStore(IStore store) {
        this.store = store;
    }
}
