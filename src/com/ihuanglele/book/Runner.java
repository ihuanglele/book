package com.ihuanglele.book;

import com.ihuanglele.book.exception.PageErrorException;
import com.ihuanglele.book.exception.StopException;
import com.ihuanglele.book.store.IStore;
import com.ihuanglele.book.strategy.AbstractSite;
import com.ihuanglele.book.util.Tool;

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
        Boolean isRun = true;
        site.setStore(store);
        while (isRun){
            try{
                site.start(start);
                start = site.getNextPageId();
            }catch (StopException e){
                e.printStackTrace();
                isRun = false;
            } catch (PageErrorException e) {
//                e.printStackTrace();
                Tool.log(start + " :保存失败 ->"+e.getMessage());
                start = site.getNextPageId();
                isRun = true;
            }
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
