import com.ihuanglele.book.Runner;
import com.ihuanglele.book.store.IStore;
import com.ihuanglele.book.store.SqliteStore;

class Main {

    public static void main(String[] args) {
        IStore store = new SqliteStore();
        Runner runner = new Runner();
        runner.setSiteName("QisuuLa");
        runner.setStore(store);
        runner.setStart("1");
        runner.run();
    }

}
