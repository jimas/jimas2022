import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author liuqj
 */
public class HiveConnectManager {
    @Test
    public void testQuery() throws Exception {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        Connection connection = DriverManager.getConnection("jdbc:hive2://node02:10000/jimas_server", "hadoop", "hadoop");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from psn");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id"));
            System.out.println(resultSet.getString("name"));
        }
    }
}
