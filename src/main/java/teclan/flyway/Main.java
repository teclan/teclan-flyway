package teclan.flyway;

import teclan.flyway.db.DataSource;
import teclan.flyway.db.Database;

public class Main {
    public static void main(String[] args) {

		DataSource dataSource = new DataSource("mysql", "com.mysql.jdbc.Driver",
				"jdbc:mysql://10.0.0.222:3306/flyway_test?useUnicode=true&characterEncoding=UTF-8", "flyway_test",
				"root",
				"root");

		Database database = new Database(dataSource);

		database.initDb();
    }
}
