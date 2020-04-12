package main.java.memoranda.database.util;

import main.java.memoranda.database.BeltEntity;
import main.java.memoranda.database.RoleEntity;

import java.sql.*;

public class DbSetupHelper {

    public static void main(String[] args) {
        //create primary db
        DbSetupHelper dbSetupHelper = new DbSetupHelper();
        dbSetupHelper.createDatabase();
        dbSetupHelper.createNeujahrskranzTables();
        //create test db
        dbSetupHelper.setDbURL(SqlConstants.defaultTestDbLoc);
        dbSetupHelper.createDatabase();
        dbSetupHelper.createNeujahrskranzTables();
        //add sample data to test
        dbSetupHelper.addSampleDataToTestDb(SqlConstants.defaultTestDbLoc);
    }

    private String dbURL;

    public DbSetupHelper() {
        this.dbURL = SqlConstants.defaultDbLoc;
    }
    public DbSetupHelper(String dbURL) {
        this.dbURL = dbURL;
    }
    public String getDbURL() {
        return dbURL;
    }
    public void setDbURL(String dbURL) {
        this.dbURL = dbURL;
    }
    public void createDatabase(){
        try (Connection conn = DriverManager.getConnection(dbURL)) {
            if (conn != null) {
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void closeDatabase(){
        try {
            Connection conn = DriverManager.getConnection(dbURL);
            conn.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("failed to close!");
        }
    }
    public void createTable(String sql, String tableName){
        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table: " + tableName + " was created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void createNeujahrskranzTables(){
        createDatabase();
        System.out.println("Preparing to create tables..note that if the tables already exist they will not be created");
        //create USER
        String userSql = "CREATE TABLE IF NOT EXISTS USER (\n"
                + "    Email text PRIMARY KEY NOT NULL,\n"
                + "    FirstName text NOT NULL,\n"
                + "    LastName text NOT NULL,\n"
                + "    Password text NOT NULL,\n"
                + "    Role text NOT NULL,\n"
                + "    Belt text,\n"
                + "    TrainingBelt text\n"
                + ");";
        createTable(userSql, "User");

        //create GYMCLASS
        String classSql = "CREATE TABLE IF NOT EXISTS GYMCLASS (\n"
                + "    Id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "    RoomNumber integer NOT NULL,\n"
                + "    StartDate text NOT NULL,\n"
                + "    StartTime real NOT NULL,\n"
                + "    EndTime real NOT NULL,\n"
                + "    TrainerEmail text NOT NULL,\n"
                + "    MaxClassSize integer NOT NULL,\n"
                + "    MinBeltRequired text NOT NULL,\n"
                + "    CreatedByEmail text NOT NULL,\n"
                + "    FOREIGN KEY(TrainerEmail) REFERENCES USER(Email) ON DELETE CASCADE,\n"
                + "    FOREIGN KEY(CreatedByEmail) REFERENCES USER(Email) ON DELETE CASCADE\n"
                + ");";
        createTable(classSql, "GymClass");

        //create TRAINERAVAILABILITY
        String trainerAvailSql = "CREATE TABLE IF NOT EXISTS TRAINERAVAILABILITY (\n"
                + "    TrainerEmail text PRIMARY KEY NOT NULL,\n"
                + "    StartDate text NOT NULL,\n"
                + "    StartTime real NOT NULL,\n"
                + "    EndTime real NOT NULL,\n"
                + "    FOREIGN KEY(TrainerEmail) REFERENCES USER(Email) ON DELETE CASCADE\n"
                + ");";
        createTable(trainerAvailSql, "TrainerAvailability");

        //create ENROLLEDUSER
        String enrolledUsersSql = "CREATE TABLE IF NOT EXISTS ENROLLEDUSER (\n"
                + "    ClassId integer PRIMARY KEY NOT NULL,\n"
                + "    UserEmail text NOT NULL,\n"
                + "    FOREIGN KEY(UserEmail) REFERENCES USER(Email) ON DELETE CASCADE\n"
                + "    FOREIGN KEY(ClassId) REFERENCES GYMCLASS(Id) ON DELETE CASCADE\n"
                + ");";
        createTable(enrolledUsersSql, "EnrolledUser");
    }
    public void addSampleDataToTestDb(String databaseURL){
        DbCreateQueries dcq = new DbCreateQueries(databaseURL);
        RoleEntity customer = new RoleEntity(RoleEntity.UserRole.customer);
        RoleEntity admin = new RoleEntity(RoleEntity.UserRole.admin);
        RoleEntity trainer = new RoleEntity(RoleEntity.UserRole.trainer);
        BeltEntity minRequiredBelt = new BeltEntity(BeltEntity.Rank.white);
        BeltEntity blackBelt = new BeltEntity(BeltEntity.Rank.black1);
        dcq.insertUser("kevin@gmail.com","kevin","johnson","foo", customer);
        dcq.insertUser("steve@gmail.com", "steve", "jacobs", "foobar", admin);
        dcq.insertUser("sarah@gmail.com", "sarah", "baker", "abc123", trainer);
        dcq.insertUser("brenda@gmail.com", "brenda", "wiley", "sdfsdf", admin, blackBelt, blackBelt);
        dcq.insertClass(1,"04-11-2020",12.0,13.0,"sarah@gmail.com",
                20,minRequiredBelt,"steve@gmail.com");
        dcq.insertTrainerAvailability("sarah@gmail.com", "04-12-2020", 10.0,14.0);
        dcq.insertEnrolledUser(1, "kevin@gmail.com");

    }
}
