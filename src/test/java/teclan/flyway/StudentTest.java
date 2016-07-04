package teclan.flyway;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.javalite.activejdbc.LazyList;
import org.junit.Test;

import teclan.flyway.db.DataSource;
import teclan.flyway.db.Database;
import teclan.flyway.model.Student;

public class StudentTest {

    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static final String URL_TEMPLATE = "jdbc:mysql://%s:%d/%s";

    private static final String TYPE     = "mysql";
    private static final String HOST     = "127.0.0.1";
    private static final int    PORT     = 3306;
    private static final String DB_NAME  = "testdb";
    private static final String SCHEMA   = "testdb";
    private static final String USER     = "root";
    private static final String PASSWORD = "123456";

    @Test
    public void createTest() {

        DataSource dataSource = new DataSource(TYPE, HOST, PORT, DB_NAME,
                SCHEMA, USER, PASSWORD, DRIVER_CLASS, URL_TEMPLATE);

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        try {
            Database database = new Database(dataSource);

            // database.setMigrateClean(true);
            database.initDb(dataSource);
            // database.setMigrateClean(false);

            database.openDatabase();

            Student p = new Student();
            p.set("name", "Declan");
            p.set("enty_time", dateFormat.format(new Date()));
            p.set("age", 100);
            p.set("sex", "男");
            p.saveIt();

            LazyList<Student> list = Student.findAll();

            for (Student stu : list) {
                System.out.println(stu.toString());
            }

            // Student.deleteAll();

            database.closeDatabase();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
