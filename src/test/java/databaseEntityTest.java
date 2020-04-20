package test.java;

import main.java.memoranda.database.*;
import org.junit.*;
import java.time.LocalDateTime;
import static org.junit.Assert.*;


/**
 * Tests database Objects
 */
public class databaseEntityTest {
    public static EnrolledUserEntity eu1;
    public static BeltEntity be1, be2, be3;
    public static GymClassEntity gce1;
    public static LocalDateTime ldt1;
    public static TrainerAvailabilityEntity tae1;

    /**
     * Sets up for database object tests
     */
    @BeforeClass
    public static void setUp() {
        be1 = new BeltEntity("");
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
    public void gymClassEntity()  {
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
    }



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


}
