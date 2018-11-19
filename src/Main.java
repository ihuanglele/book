import com.ihuanglele.book.Runner;
import com.ihuanglele.book.store.FileStore;
import com.ihuanglele.book.store.IStore;
import com.ihuanglele.book.store.SqliteStore;
import com.ihuanglele.book.util.Config;
import com.ihuanglele.book.util.Tool;

class Main {

    public static void main(String[] args) {
        Config.init(args);
//        IStore store = new SqliteStore();
//        IStore store = new FileStore();
        Runner runner = new Runner();
//        runner.setSiteName("QisuuLa");
//        runner.setStore(store);
//        runner.setStart("586");
        runner.autoRun();
        Tool.closeFileWriter();
    }

}
