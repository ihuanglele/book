import com.ihuanglele.book.Runner;
import com.ihuanglele.book.store.IStore;
import com.ihuanglele.book.store.SqliteStore;
import com.ihuanglele.book.strategy.AbstractSite;
import com.ihuanglele.book.strategy.QisuuLa;
import com.ihuanglele.book.util.Tool;

class Main {

    public static void main(String[] args) {
//        IStore store = new SqliteStore();
//        Runner runner = new Runner();
//        runner.setSiteName("QisuuLa");
//        runner.setStore(store);
//        runner.setStart("1");
//        runner.run();

        Tool.save("nihaoya 1","text");
        Tool.save("nihaoya 2","text");
        Tool.save("nihaoya 3","text");
        Tool.save("nihaoya 4","text");
        Tool.closeFileWriter();
    }

}
