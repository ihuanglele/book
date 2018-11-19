package com.ihuanglele.book;

import com.ihuanglele.book.exception.PageErrorException;
import com.ihuanglele.book.exception.StopException;
import com.ihuanglele.book.store.IStore;
import com.ihuanglele.book.strategy.AbstractSite;
import com.ihuanglele.book.util.Config;
import com.ihuanglele.book.util.Tool;

/**
 * Created by ihuanglele on 2018/11/8.
 */
public class Runner {

    private String start;
    private IStore store;
    private String siteName;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void run() {
        Boolean isRun = true;
        AbstractSite site = null;
        while (isRun){
            try {
                site = AbstractSite.start(siteName,start);
                store.save(site.getBook());
                Tool.save("saved Book" + start, "bookSave");
                start = site.getNextPageId(start);
                site.clean();
            } catch (PageErrorException e) {
                Tool.save(start + " :保存失败 -> " + e.getMessage(), "bookSave");
                start = site.getNextPageId(start);
                site.clean();
            } catch (StopException e) {
                Tool.log(start + " :保存结束 -> "+e.getMessage());
                isRun = false;
            }
        }
        Tool.log("isRun IS Stopped");
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

    public void autoRun(){
        setSiteName(Config.get("site"));
        setStart(Config.get("start"));

        Class<?> c = null;
        try {
            c = Class.forName("com.ihuanglele.book.store." + Config.get("storeclass"));
            setStore((IStore)c.newInstance());
            run();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Tool.log(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Tool.log(e.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();
            Tool.log(e.getMessage());
        }

    }

}
