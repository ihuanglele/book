import com.ihuanglele.book.Runner;
import com.ihuanglele.book.store.FileStore;
import com.ihuanglele.book.store.IStore;
import com.ihuanglele.book.strategy.AbstractSite;
import com.ihuanglele.book.strategy.QisuuLa;
import com.ihuanglele.book.util.GetHtmlPage;

public class Main {

    public static void main(String[] args) {
        AbstractSite site = new QisuuLa();
        IStore store = new FileStore();
        Runner runner = new Runner();
        runner.setSite(site);
        runner.setStore(store);
        runner.run();
    }

}
