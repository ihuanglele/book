import com.ihuanglele.book.Runner;
import com.ihuanglele.book.util.Config;
import com.ihuanglele.book.util.Tool;

class Main {

    public static void main(String[] args) {
        Config.init(args);
        Runner runner = new Runner();
//        IStore store = new SqliteStore();
//        IStore store = new FileStore();
//        runner.setSiteName("QisuuLa");
//        runner.setStore(store);
//        runner.setStart("586");
        runner.autoRun();
        Tool.closeFileWriter();
    }

}
