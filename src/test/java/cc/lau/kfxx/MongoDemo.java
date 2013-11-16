package cc.lau.kfxx;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MongoDemo {
    public static void main(String[] args) {
        try {
            MongoClient client = new MongoClient("211.71.76.165", 27017);
            for (String name : client.getDatabaseNames()) {
                System.out.println("dbName: " + name);
            }
            
            DB db = client.getDB("tutorial");
            // 查询所有的聚集集合
            for (String name : db.getCollectionNames()) {
                System.out.println("collectionName: " + name);
            }

            DBCollection users = db.getCollection("test");

            // 查询所有的数据
            DBCursor cur = users.find();
            if (cur.hasNext()) {
                System.out.println(cur.next());
            }

            DBObject user = new BasicDBObject();
            user.put("name", "hoojo4");
            user.put("age", 24);

            // users.save(user)保存，getN()获取影响行数

            System.out.println("effect rows: " + users.save(user).getN());

            // 扩展字段，随意添加字段，不影响现有数据

            user.put("sex", "男");
            // System.out.println(users.save(user).getN());

            List<DBObject> list = new ArrayList<DBObject>();
            list.add(user);
            DBObject user2 = new BasicDBObject("name", "lucy");
            user.put("age", 22);
            list.add(user2);
            // 添加List集合
            // users.insert(list);
            // 查询下数据，看看是否添加成功
            System.out.println("count: " + users.count());

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
