package features;

import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.noear.snack.ONode;
import org.noear.wood.WoodConfig;
import org.noear.wood.cache.ICacheServiceEx;
import org.noear.wood.cache.LocalCache;
import org.noear.mongox.MgContext;
import features.model.TagCountsM;
import features.model.UserModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author noear 2021/2/6 created
 */
public class MongoTest3 {
    String url = "mongodb://admin:admin@localhost";
    MgContext db = new MgContext(url, "demo");

    @BeforeAll
    public static void bef() {
        WoodConfig.isUsingUnderlineColumnName = false;
    }

    @Test
    public void test1() {
        List<UserModel> mapList = db.table("user")
                .whereBtw("userId", 10, 20)
                .orderByAsc("userId")
                .limit(10)
                .selectList(UserModel.class);

        assert mapList.size() == 10;
        assert mapList.get(0).userId == 10;
    }

    @Test
    public void test12() {
        ICacheServiceEx cache = new LocalCache();

        for (int i = 0; i < 3; i++) {
            List<UserModel> mapList = db.table("user")
                    .whereBtw("userId", 10, 20)
                    .orderByAsc("userId")
                    .limit(10)
                    .caching(cache)
                    .selectList(UserModel.class);

            assert mapList.size() == 10;
            assert mapList.get(0).userId == 10;
        }
    }

    @Test
    public void test2() {
        List<UserModel> mapList = db.table("user")
                .whereIn("userId", Arrays.asList(3, 4))
                .orderByAsc("userId")
                .limit(10)
                .selectList(UserModel.class);

        assert mapList.size() > 2;
        assert mapList.get(0).userId == 3;
    }

    @Test
    public void test22() {
        List<UserModel> mapList = db.table("user")
                .whereNlk("name", "^no")
                .orderByAsc("userId")
                .limit(10)
                .selectList(UserModel.class);

        System.out.println(mapList);
        assert mapList.size() == 0;
    }

    //@Test
    public void test3() {
        //需要服务器开启脚本能力
        List<UserModel> mapList = db.table("user")
                .whereScript("this.userId==3")
                .orderByAsc("userId")
                .limit(10)
                .selectList(UserModel.class);

        assert mapList.size() > 2;
        assert mapList.get(0).userId == 3;
    }

    @Test
    public void test4() {
        List<UserModel> mapList = db.table("user")
                .whereMod("userId", 3, 1)
                .orderByAsc("userId")
                .limit(10)
                .selectList(UserModel.class);

        assert mapList.size() > 2;
        assert mapList.get(0).userId == 1;
    }

    @Test
    public void test6() {
        //
        //like group by
        //
        String filed = "userId";

        List<Document> filter = Arrays.asList(new Document("$group",
                new Document("_id",
                        new Document("tag", "$" + filed)
                                .append("counts", new Document("$sum", 1)))));


        AggregateIterable<Document> docList = db.mongo().getCollection("user").aggregate(filter);


        List<TagCountsM> tagCountsList = new ArrayList<>();
        ONode oNode = new ONode();
        for (Document doc : docList) {
            TagCountsM tc = oNode.fill(doc).get("_id").toObject(TagCountsM.class);
            tagCountsList.add(tc);
        }

        //[{tag:"a",counts:12}]
        System.out.println(tagCountsList);

        assert tagCountsList.size() > 0;

    }
}
