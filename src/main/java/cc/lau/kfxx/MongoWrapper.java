
package cc.lau.kfxx;

import com.mongodb.MongoClient;

import java.net.UnknownHostException;

public class MongoWrapper {
    private MongoClient client;

    private static MongoWrapper mw = new MongoWrapper();

    private MongoWrapper() {
        init();
    }

    public void init() {
        try {
            client = new MongoClient("211.71.76.165", 27017);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        client.close();
    }

    public static MongoWrapper getInstance() {
        return mw;
    }

    public MongoClient getClient() {
        return client;
    }

}
