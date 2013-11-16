
package cc.lau.kfxx;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import org.junit.Before;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVDemo {

    private CSVReader cr;
    private MongoClient client;
    private DB db;
    private DBCollection users;

    @Before
    public void init() {
        try {
            cr = new CSVReader(new FileReader(new File(Constants.Linux.last5k)));
            client = MongoWrapper.getInstance().getClient();
            db = client.getDB("tutorial");
            users = db.getCollection("test");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void lostInsert() {
        int index = 0;
        String[] nextLine, heads = null;
        List<String[]> list = new ArrayList<>();
        try {
            heads = cr.readNext();
            while ((nextLine = cr.readNext()) != null) {
                index++;
                int start = 17900;
                if (index > start && index <= start + 100) {
                    list.add(nextLine);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        CSVWrapper instance = CSVWrapper.newInstance();
        List<DBObject> mobjs = instance.makeMobjs(heads, list);
        System.out.println("错误数据：" + instance.getErrorCount());
        long count = users.count();
        users.insert(mobjs);
        System.out.println("实际插入：" + (users.count() - count));

    }

}
