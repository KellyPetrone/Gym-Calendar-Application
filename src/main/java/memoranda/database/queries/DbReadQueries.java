package main.java.memoranda.database.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import main.java.memoranda.database.entities.BeltEntity;
import main.java.memoranda.database.entities.GymClassEntity;
import main.java.memoranda.database.entities.RoleEntity;
import main.java.memoranda.database.entities.TrainerAvailabilityEntity;
import main.java.memoranda.database.entities.UserEntity;
import main.java.memoranda.database.util.EnforcedConnection;
import main.java.memoranda.database.util.SqlConstants;

/*
Class for all read related queries
 */
public class DbReadQueries {

    private String _dbUrl;


    public DbReadQueries(String dbUrl) {
        this._dbUrl = dbUrl;
    }

    /*
    gets all of a USER's information based on the email provided
     */
    public UserEntity getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM user WHERE Email=?";

        Connection conn = EnforcedConnection.getEnforcedCon(_dbUrl);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        //////////////////////////////////
        if (!rs.next()) {
            return null;
        }
        //////////////////////////////////
        UserEntity userEntity = _getUserFromResultSet(rs);

        pstmt.close();
        conn.close();
        return userEntity;
    }

    /**
     * Gets the number of students enrolled in a class. Used to determine if a class
     * is full or not.
     * @param id The unique identifier of the Class
     * @return The amount of students enrolled in the class
     * @throws SQLException
     */
    public int getNumberOfStudentsEnrolledInClass(int id) throws SQLException {
        String sql = "SELECT COUNT(UserEmail) AS total FROM ENROLLEDUSER " +
                "INNER JOIN GYMCLASS on GYMCLASS.Id = ENROLLEDUSER.ClassId " +
                "WHERE ENROLLEDUSER.ClassId = ?";
        Connection conn = EnforcedConnection.getEnforcedCon(_dbUrl);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) {
            return 0;
        }
        int total = rs.getInt("total");
        conn.close();
        pstmt.close();
        return total;
    }

    public String getUserImage(String email) throws SQLException {
        String sql = "SELECT ImageURL FROM USERIMAGE WHERE Email=?";
        Connection conn = EnforcedConnection.getEnforcedCon(_dbUrl);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        while (!rs.next()) {
            return "";
        }
        String url = rs.getString(1);
        conn.close();
        pstmt.close();
        return url;

    }

    /*
    gets all of a USER's information based on the email provided
    */
    public ArrayList<GymClassEntity> getEnrolledClassByEmailAndDate(String email, LocalDate date)
        throws SQLException {
        String sql = "SELECT * FROM GYMCLASS " +
            "INNER JOIN ENROLLEDUSER on ENROLLEDUSER.ClassId = GYMCLASS.Id " +
            "WHERE ENROLLEDUSER.UserEmail=?" +
            "AND GYMCLASS.StartDate=?";
        Connection conn = EnforcedConnection.getEnforcedCon(_dbUrl);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, email);
        String strDate = date.format(SqlConstants.DBDATEFORMAT);
        pstmt.setString(2, strDate);
        ResultSet rs = pstmt.executeQuery();

        ArrayList<GymClassEntity> gymClasses = new ArrayList<>();
        while (rs.next()) {
            gymClasses.add(_getGymClassFromResultSet(rs));
        }
        pstmt.close();
        conn.close();
        if (gymClasses.size() == 0) {
            return null;
        } else {
            return gymClasses;
        }
    }

    /*
    returns list of all users in the USER table
     */
    public ArrayList<UserEntity> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM user;";

        Connection conn = EnforcedConnection.getEnforcedCon(_dbUrl);
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        ArrayList<UserEntity> users = new ArrayList<>();
        while (rs.next()) {
            users.add(_getUserFromResultSet(rs));
        }
        statement.close();
        conn.close();
        return users;
    }

    /*
    returns all classes a specific user is enrolled in
     */
    public ArrayList<GymClassEntity> getClassesUserEnrolledInByEmail(String email)
        throws SQLException {
        String sql = "SELECT * FROM GYMCLASS " +
            "INNER JOIN ENROLLEDUSER on ENROLLEDUSER.ClassId = GYMCLASS.Id " +
            "WHERE ENROLLEDUSER.UserEmail=?";

        Connection conn = EnforcedConnection.getEnforcedCon(_dbUrl);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();

        ArrayList<GymClassEntity> gymClasses = new ArrayList<>();
        while (rs.next()) {
            gymClasses.add(_getGymClassFromResultSet(rs));
        }
        pstmt.close();
        conn.close();
        return gymClasses;
    }

    /*
    returns all of a specific trainers Availability, based on email
     */
    public ArrayList<TrainerAvailabilityEntity> getTrainerDateTimeAvailabilityByEmail(String email)
        throws SQLException {

        String sql = "SELECT * FROM TRAINERAVAILABILITY WHERE TRAINERAVAILABILITY.TrainerEmail=?";

        Connection conn = EnforcedConnection.getEnforcedCon(_dbUrl);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();

        ArrayList<TrainerAvailabilityEntity> trainerAvailabilities = new ArrayList<>();
        while (rs.next()) {

            String startDate = rs.getString("StartDate");
            Double startTime = rs.getDouble("StartTime");

            LocalDateTime startDateTime = _getLocalDateTimeFromDbFields(startDate, startTime);

            String stopDate = rs.getString("StartDate");
            Double endTime =  rs.getDouble("EndTime");

            LocalDateTime stopDateTime = _getLocalDateTimeFromDbFields(stopDate,endTime);
            trainerAvailabilities.add(new TrainerAvailabilityEntity(startDateTime, stopDateTime));

        }

        pstmt.close();
        conn.close();
        return trainerAvailabilities;
    }

    /*
    returns all classes on a specific date, expects the date to be in the format MM/dd/yyyy
     */
    public ArrayList<GymClassEntity> getAllClassesByDate(LocalDate localDate) throws SQLException {
        String strDate = localDate.format(SqlConstants.DBDATEFORMAT);

        String sql = "SELECT * FROM GYMCLASS WHERE StartDate=?";

        Connection conn = EnforcedConnection.getEnforcedCon(_dbUrl);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, strDate);
        ResultSet rs = pstmt.executeQuery();

        ArrayList<GymClassEntity> gymClasses = new ArrayList<>();
        while (rs.next()) {
            gymClasses.add(_getGymClassFromResultSet(rs));
        }

        pstmt.close();
        conn.close();
        return gymClasses;
    }

    /*
    gets all users based on a role provided, ex: get all admins, etc.
     */
    public ArrayList<UserEntity> getAllUsersOfCertainRole(RoleEntity role) throws SQLException {
        String sql = "SELECT * FROM USER WHERE Role=?";

        Connection conn = EnforcedConnection.getEnforcedCon(_dbUrl);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, role.userRole.name().toLowerCase());
        ResultSet rs = pstmt.executeQuery();

        ArrayList<UserEntity> users = new ArrayList<>();
        while (rs.next()) {
            users.add(_getUserFromResultSet(rs));
        }

        pstmt.close();
        conn.close();
        return users;
    }

    /*
    returns all classes that a trainer is leading, queried by email
     */
    public ArrayList<GymClassEntity> getAllClassesTrainerIsTeachingByEmail(String email)
        throws SQLException {
        String sql = "SELECT * FROM GYMCLASS WHERE GYMCLASS.TrainerEmail=?";

        Connection conn = EnforcedConnection.getEnforcedCon(_dbUrl);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();

        ArrayList<GymClassEntity> classesTrainerIsTeaching = new ArrayList<>();
        while (rs.next()) {
            classesTrainerIsTeaching.add(_getGymClassFromResultSet(rs));
        }

        pstmt.close();
        conn.close();
        return classesTrainerIsTeaching;
    }

    /**
     * Gets classes by the date and time.
     *
     * @param localDate date to search
     * @param time      time to search
     * @param room      room to search
     * @return ArrayList<GymClassEntity>
     * @throws SQLException
     */
    public ArrayList<GymClassEntity> getAllClassesByDateTime(LocalDate localDate, double time,
                                                             int room) throws SQLException {
        String strDate = localDate.format(SqlConstants.DBDATEFORMAT);

        String sql = "SELECT * FROM GYMCLASS WHERE StartDate=?" +
            "AND StartTime=?" +
            "AND RoomNumber=?";

        Connection conn = EnforcedConnection.getEnforcedCon(_dbUrl);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, strDate);
        pstmt.setDouble(2, time);
        pstmt.setInt(3, room);

        ResultSet rs = pstmt.executeQuery();

        ArrayList<GymClassEntity> gymClasses = new ArrayList<>();
        while (rs.next()) {
            gymClasses.add(_getGymClassFromResultSet(rs));
        }

        pstmt.close();
        conn.close();
        return gymClasses;
    }

    /**
     * Parses the classes for 1 class.
     * @param localDate the localdate
     * @param startTime the start time
     * @param roomNumber the room number.
     * @return
     * @throws SQLException throws this exception.
     */
    public GymClassEntity getClass(String localDate, double startTime, int roomNumber)
        throws SQLException {
        String sql = "SELECT * FROM GYMCLASS " +
            "WHERE RoomNumber=? AND " +
            "StartDate=? AND " +
            "StartTime=?;";

        Connection conn = EnforcedConnection.getEnforcedCon(_dbUrl);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, roomNumber);
        pstmt.setString(2, localDate);
        pstmt.setDouble(3, startTime);

        ResultSet rs = pstmt.executeQuery();
        //////////////////////////////////
        if (!rs.next()) {
            return null;
        }
        //////////////////////////////////
        GymClassEntity gce = _getGymClassFromResultSet(rs);

        pstmt.close();
        conn.close();
        return gce;
    }

    /*
helper method for creating and returning a GymClassEntity from a result set
 */
    private GymClassEntity _getGymClassFromResultSet(ResultSet rs) throws SQLException {

        LocalDateTime startDateTime = _getLocalDateTimeFromDbFields(
            rs.getString("StartDate"),
            rs.getDouble("StartTime"));

        LocalDateTime endDateTime = _getLocalDateTimeFromDbFields(
            rs.getString("StartDate"),
            rs.getDouble("EndTime"));

        BeltEntity minBeltRequired = new BeltEntity(
            BeltEntity.Rank.valueOf(rs.getString("MinBeltRequired")));

        return new GymClassEntity(
            rs.getInt("Id"),
            rs.getInt("RoomNumber"),
            startDateTime,
            endDateTime,
            rs.getString("TrainerEmail"),
            rs.getInt("MaxClassSize"),
            minBeltRequired,
            rs.getString("CreatedByEmail")
        );
    }

    /*
    helper method for getting a LocalDateTime from a string representing the date with the format
    MM/dd/yyyy and a double, which represents the time on a 24 hour period.  Ex: 13.59 is 13:59,
    which is 1:59pm
     */
    private LocalDateTime _getLocalDateTimeFromDbFields(String strDate, double time) {
        //get LocalDate

        LocalDate localDate = LocalDate.parse(strDate, SqlConstants.DBDATEFORMAT);// this is a bug
/*
        LocalDate d = LocalDate.parse(strDate);//this is the fix
        String formatedStr = d.format(SqlConstants.DBDATEFORMAT);
        LocalDate localDate =  LocalDate.parse(formatedStr,SqlConstants.DBDATEFORMAT);*/


        //get LocalTime
        String strTime = String.valueOf(time);
        String[] timeParts = strTime.split("\\.");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);
        //need to account for a multiple of 10 minutes (10, 20, 30, 40) which will only be shown as x.1, x.2, x.3, etc.
        if (timeParts[1].length() == 1) {
            minutes *= 10;
        }
        LocalTime localTime = LocalTime.of(hours, minutes);

        return LocalDateTime.of(localDate, localTime);
    }

    /**
     * private method used for parsing the result set.
     *
     * @param rs Result set to parse.
     * @return returns null if the the rs set is empty, otherwise returns UserEntity.
     * @throws SQLException sql  exception.
     */
    private UserEntity _getUserFromResultSet(ResultSet rs) throws SQLException {

        String strBelt = rs.getString("Belt");
        BeltEntity belt = null;
        if (strBelt != null) {
            belt = new BeltEntity(BeltEntity.Rank.valueOf(strBelt.toLowerCase()));
        }
        String strTrainingBelt = rs.getString("TrainingBelt");
        BeltEntity trainingBelt = null;
        if (strTrainingBelt != null) {
            trainingBelt = new BeltEntity(BeltEntity.Rank.valueOf(strTrainingBelt.toLowerCase()));
        }
        String strRole = rs.getString("Role");
        RoleEntity role = new RoleEntity(RoleEntity.UserRole.valueOf(strRole.toLowerCase()));

        return new UserEntity(rs.getString("FirstName"),
            rs.getString("LastName"),
            rs.getString("Password"),
            rs.getString("Email"),
            role,
            belt,
            trainingBelt);
    }
}
