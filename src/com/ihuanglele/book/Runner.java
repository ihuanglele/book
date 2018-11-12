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
        while (isRun){
            AbstractSite site = null;
            try {
                site = AbstractSite.start(siteName,start);
                store.save(site.getBook());
                Tool.save("saved Book" + start, "bookSave");
                start = site.getNextPageId();
                site.clean();
            } catch (PageErrorException e) {
                Tool.save(start + " :保存失败 -> " + e.getMessage(), "bookSave");
                if(null != site){
                    start = site.getNextPageId();
                    Tool.save(start + " :保存失败 -> " + e.getMessage(), "bookSave");
                    site.clean();
                }else {
                    isRun = false;
                    Tool.log("Stop Unexpected");
                }
            } catch (StopException e) {
                Tool.log("Stop");
                isRun = false;
            }
        }
        Tool.closeFileWriter();
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
