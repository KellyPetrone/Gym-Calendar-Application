package test.java;

import main.java.memoranda.database.entities.EnrolledUserEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Tests database Objects
 */
public class EnrolledUserEntityTest {

    /**
     * Tests Belt Entity object
     */
    @Test
    public void enrolledUserEntity() {
        EnrolledUserEntity eu1 = new EnrolledUserEntity(1,"admin@gym.com");
        assertEquals(1,eu1.getClassId());
        assertEquals("admin@gym.com",eu1.getUserEmail());
    }


}
