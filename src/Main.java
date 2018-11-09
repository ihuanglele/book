import com.ihuanglele.book.Runner;
import com.ihuanglele.book.store.FileStore;
import com.ihuanglele.book.store.IStore;
import com.ihuanglele.book.store.SqliteStore;
import com.ihuanglele.book.strategy.AbstractSite;
import com.ihuanglele.book.strategy.QisuuLa;
import com.ihuanglele.book.util.Tool;

public class Main {

    public static void main(String[] args) {
        AbstractSite site = new QisuuLa();
        IStore store = new SqliteStore();
        Runner runner = new Runner();
        runner.setSite(site);
        runner.setStore(store);
        runner.setStart("1");
        runner.run();
    }

}
