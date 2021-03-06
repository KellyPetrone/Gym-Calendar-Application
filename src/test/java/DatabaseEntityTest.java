package test.java;

import main.java.memoranda.database.entities.BeltEntity;
import main.java.memoranda.database.entities.EnrolledUserEntity;
import main.java.memoranda.database.entities.GymClassEntity;
import main.java.memoranda.database.entities.RoleEntity;
import main.java.memoranda.database.entities.TrainerAvailabilityEntity;
import main.java.memoranda.database.entities.UserEntity;
import main.java.memoranda.gym.Gym;
import main.java.memoranda.gym.Response;
import main.java.memoranda.util.Local;
import org.junit.*;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.Assert.*;


/**
 * Tests database Objects
 */
public class DatabaseEntityTest {
    public static EnrolledUserEntity eu1;
    public static BeltEntity be1, be2, be3;
    public static GymClassEntity gce1;
    public static LocalDateTime ldt1;
    public static RoleEntity re1, re2, re3;
    public static TrainerAvailabilityEntity tae1;
    public static UserEntity udt1;
    public static RoleEntity ur1;
    private static String imageUrl;
    public static Gym gym;

    /**
     * Sets up for database object tests
     */
    @BeforeClass
    public static void setUp() {
        be1 = new BeltEntity("");
        ur1 = new RoleEntity(RoleEntity.UserRole.trainer);
        gym = Gym.getInstance();

        gym.deleteUser("admin2@gym.com");
        gym.deleteUser("customer@gym.com");
        gym.deleteUser("trainer@gym.com");
        gym.deleteUser("jim@gym.com");
    }

    /**
     * Tests Belt Entity object
     */
    @Test
    public void beltEntity() {
        be2 = new BeltEntity("black3");
        be3 = new BeltEntity("black3");
        assertEquals(be1.toString(), "white");
        assertEquals(be2.toString(), "black3");
        assertTrue(be2.equals(be3));
    }

    /**
     * Tests Enrolled User Entity
     */
    @Test
    public void enrolledUserEntity()  {
        eu1 = new EnrolledUserEntity(1, "kjpetron@asu.edu");
        int i = eu1.getClassId();
        assertEquals(i, 1);
        String s = eu1.getUserEmail();
        assertEquals(s, "kjpetron@asu.edu");
        eu1.setClassId(2);
        i = eu1.getClassId();
        assertEquals(i, 2);
        eu1.setUserEmail("admin@gym.com");
        assertEquals(eu1.getUserEmail(), "admin@gym.com");
    }

    /**
     * Tests Gym Class Entity
     */
    @Test
    public void gymClassEntity() throws SQLException {
        ldt1 = LocalDateTime.now();
        gce1 = new GymClassEntity(1, 1, ldt1, ldt1,
                "kjpetron@asu.edu",
                10, be1,
                "admin@gym.com");
        assertEquals(1, gce1.getId());
        assertEquals(be1, gce1.getMinBeltEntityRequired());
        assertEquals("kjpetron@asu.edu", gce1.getTrainerEmail());
        assertEquals("admin@gym.com", gce1.getCreatedByEmail());
        assertEquals(1, gce1.getRoomNumber());
        assertEquals(10, gce1.getMaxClassSize());
        assertEquals(ldt1, gce1.getStartDateTime());
        assertEquals(ldt1, gce1.getEndDateTime());
        assertTrue(gce1.equals(gce1));
    }

    /**
     * Tests User Entity
     */
    @Test
    public void userEntity()  {
        udt1 = new UserEntity("fname", "lname", "password", "mail@mail.com",
                new RoleEntity(RoleEntity.UserRole.trainer));
        assertEquals("fname", udt1.getFirstName());
        assertEquals("lname", udt1.getLastName());
        assertEquals("password", udt1.getPassword());
        assertEquals("mail@mail.com", udt1.getEmail());
        assertEquals(ur1, udt1.getRole());

    }

    /**
     * Tests Role Entity
     */
    @Test
    public void roleEntity()  {
        re1 = new RoleEntity(RoleEntity.UserRole.trainer);
        re2 = new RoleEntity(RoleEntity.UserRole.admin);
        re3 = new RoleEntity(RoleEntity.UserRole.customer);
        assertEquals("trainer", re1.toString());
        assertEquals("admin", re2.toString());
        assertEquals("customer", re3.toString());

    }

    /**
     * Tests trainer availbility Entity
     */
    @Test
    public void trainerAvilibilitySetters(){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(1);
        tae1 = new TrainerAvailabilityEntity(LocalDateTime.now(), LocalDateTime.now());
        tae1.setStartDateTime(start);
        tae1.setEndDateTime(end);

        assertEquals(start, tae1.getStartDateTime());
        assertEquals(end, tae1.getEndDateTime());
    }

    /**
     * Tests times and conversions for use with the DB.
     */
    @Test
    public void getTimeAndConvert() {
        String[] times = Local.getTimes();
        String[] actualTimes = new String[]{"0500", "0600", "0700", "0800", "0900",
                "1000", "1100", "1200", "1300", "1400", "1500", "1600", "1700", "1800"
                , "1900", "2000", "2100"};
        assertEquals(times, actualTimes);
        double time = 6.0;
        assertEquals(time, Local.getDoubleTime("0600"), 0.0);
    }

    /**
     * Ensures max class sizes is returning properly for DB formatting.
     */
    @Test
    public void getClassSizes() {
        String classSize[] =
                {"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        assertEquals(classSize, Local.getMaxClassSize());
    }

    @Test
    public void createAdminTest() {
        Response r;
        r = gym.createAdmin("admin2@gym.com", "admin", "gym", "1234");
        assertTrue(r.isSuccess());
        r = gym.createAdmin("admin2@gym.com", "admin", "gym", "1234");
        assertTrue(r.isFailure());

        gym.deleteUser("admin2@gym.com");
    }

    @Test
    public void createCustomerTest() {
        Response r;
        r = gym.createAdmin("customer@gym.com", "customer", "gym", "1234");
        assertTrue(r.isSuccess());
        r = gym.createAdmin("customer@gym.com", "customer", "gym", "1234");
        assertTrue(r.isFailure());

        gym.deleteUser("customer@gym.com");
        gym.deleteUser("jim@gym.com");
    }

    @Test
    public void createTrainerTest() {
        Response r;
        r = gym.createAdmin("trainer@gym.com", "trainer", "gym", "1234");
        assertTrue(r.isSuccess());
        r = gym.createAdmin("trainer@gym.com", "trainer", "gym", "1234");
        assertTrue(r.isFailure());

        gym.deleteUser("trainer@gym.com");
    }
}
