package teclan.flyway.db;

import java.util.HashMap;
import java.util.Map;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.javalite.activejdbc.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import teclan.flyway.utils.Strings;

public class Database {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(Database.class);

    // 连接名
    private String     name;
    private DataSource dataSource;

	private static boolean migrateClean = false;

    // 连接池
    private static Map<String, HikariDataSource> DATA_SOURCES = new HashMap<String, HikariDataSource>();

    public Database(DataSource dataSource) {
        this("default", dataSource);
    }

    public Database(String name, DataSource dataSource) {
        this.name = name;
        this.dataSource = dataSource;
    }

	public void initDb() {
		initDb(dataSource);
	}

	public static void initDb(DataSource dataSource) throws FlywayException {

        String key = generateKeyForPool(dataSource);

        HikariDataSource hikariDataSource = generateHikariDataSource(
                dataSource);

        if (!DATA_SOURCES.containsKey(key)) {
            DATA_SOURCES.put(key, hikariDataSource);
        }

        Flyway flyway = new Flyway();
        flyway.setDataSource(hikariDataSource);
		flyway.setBaselineOnMigrate(true);
        if (migrateClean) {
            flyway.clean();
        }

        try {
            flyway.migrate();
        } catch (FlywayException e) {
            LOGGER.error(
                    "数据库迁移检验失败,表结构可能已经修改,建议迁移之前将 migrateClean 设为 true,如果是生产环境请联系DBA确认后重试 !");
            throw e;
        }
    }


    public static void initDb(javax.sql.DataSource dataSource) throws FlywayException {

        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setBaselineOnMigrate(true);
        if (migrateClean) {
            flyway.clean();
        }

        try {
            flyway.migrate();
        } catch (FlywayException e) {
            LOGGER.error(
                    "数据库迁移检验失败,表结构可能已经修改,建议迁移之前将 migrateClean 设为 true,如果是生产环境请联系DBA确认后重试 !");
            throw e;
        }
    }

	private static String generateKeyForPool(DataSource dataSource) {
        StringBuffer appender = new StringBuffer();

        appender.append(dataSource.getType());
        appender.append(dataSource.getHost());
        appender.append(dataSource.getPort());
        appender.append(dataSource.getDbName());
        appender.append(dataSource.getSchema());
        appender.append(dataSource.getUser());

        return Strings.toHexDigest(appender.toString(), "MD5");
    }


	private static HikariDataSource generateHikariDataSource(DataSource dataSource) {

        HikariConfig config = new HikariConfig();

        config.setDriverClassName(dataSource.getDriverClass());
        config.setJdbcUrl(dataSource.getUrl());
        config.setUsername(dataSource.getUser());
        config.setPassword(dataSource.getPassword());

        return new HikariDataSource(config);
    }

    public void openDatabase() {

		String key = generateKeyForPool(dataSource);

		if (!DATA_SOURCES.containsKey(key)) {
			DATA_SOURCES.put(key, generateHikariDataSource(dataSource));
		}

		new DB(name).open(DATA_SOURCES.get(key));
    }

    public void closeDatabase() {
        new DB(name).close();
    }


    public void openDatabase(String name) {

        String key = generateKeyForPool(dataSource);

        if (!DATA_SOURCES.containsKey(key)) {
            DATA_SOURCES.put(key, generateHikariDataSource(dataSource));
        }

        new DB(name).open(DATA_SOURCES.get(key));
    }

    public void closeDatabase(String name) {
        new DB(name).close();
    }

    /**
     * Clean is a great help in development and test. It will effectively give
     * you a fresh start, by wiping your configured schemas completely clean.
     * All objects (tables, views, procedures, ...) will be dropped.
     * 
     * Needless to say: do not use against your production DB!
     * 
     * @param migrateClean
     */
    public void setMigrateClean(boolean migrateClean) {
        Database.migrateClean = migrateClean;
    }

}
