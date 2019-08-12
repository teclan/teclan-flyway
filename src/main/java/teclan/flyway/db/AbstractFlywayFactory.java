package teclan.flyway.db;

public abstract class AbstractFlywayFactory {

    private String type;
    private String driver;
    private String url;
    private String user;
    private String password;

    protected  String getType(){
        return null;
    }

    protected abstract String getDriver();

    protected abstract String getUrl();

    protected abstract String getUser();

    protected abstract String getPassword();




    public void flyway(){
        DataSource dataSource = new DataSource(getDriver(),getUrl(),getUser(),getPassword());
        Database.initDb(dataSource);
    }

}
