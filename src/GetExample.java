import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetExample {

    private OkHttpClient client = new OkHttpClient();

    public void run (String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
//                .addHeader("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1")
                .build();

        Response response = client.newCall(request).execute();
        String r = response.body().string();
        System.out.println(r);
    }

    static {
        System.out.println("static block");
    }

}