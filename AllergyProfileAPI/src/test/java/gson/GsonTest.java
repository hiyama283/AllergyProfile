package gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class GsonTest {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        UserTest user1 = new UserTest("bob@jmail.com", null);
        UserTest user2 = new UserTest("john@jmail.com", "john");

        List<UserTest> userTestList = new ArrayList<>();
        userTestList.add(user1);
        userTestList.add(user2);

        System.out.println("ユーザー1: " + gson.toJson(user1));
        System.out.println("ユーザー2: " + gson.toJson(user2));

        System.out.println("ユーザーリスト: " + gson.toJson(userTestList));

        String fromJsonTest1 = "{\"email\": \"john@jemail.com\", \"fullName\": \"john\"";
        String fromJsonTest2 = "{\"jmail\": \"john@jemail.com\", \"fullName\": \"john\"";

        System.out.println("Test1:" + (GsonUtils.fromJson(fromJsonTest1, UserTest.class) instanceof UserTest));
        System.out.println("Test2:" + (GsonUtils.fromJson(fromJsonTest2, UserTest.class) instanceof UserTest));
    }
}
