import main.java.memoranda.database.entities.GymClassEntity;
import main.java.memoranda.gym.Gym;
import main.java.memoranda.gym.Response;

import org.junit.*;
import java.util.ArrayList;

public class TestEnrollAndUnenroll {

    @AfterClass
    public static void tearDown() {
        //TODO clean up any changes gym instance made to the real db
    }

    @Test
    public void testUnEnrollingUnknownUserIsFailure() {
        Gym gym = Gym.getInstance();
        Response response = gym.unenrollCustomer("nonexistantemailformadeupuser@gmail.com", 3333);
        assert(response.isFailure());
    }

    @Test
    public void testEnrollingKnownUserIsSucess() {
        Gym gym = Gym.getInstance();
        gym.createCustomer("sdlfjkj2345223523452@yahoo.com", "John", "smith", "foo");

        Response response = gym.enrollUser(1, "sdlfjkj2345223523452@yahoo.com");
        assert(response.isSuccess());
        Response enrollTest = gym.getClassesUserEnrolledInByEmail("sdlfjkj2345223523452@yahoo.com");
        ArrayList<GymClassEntity> classesEnrolled = (ArrayList<GymClassEntity>)enrollTest.getValue();
        assert(classesEnrolled.size() == 1);
        gym.deleteUser("sdlfjkj2345223523452@yahoo.com");
    }

    @Test
    public void testUnEnrollingKnownUserIsSuccess() {
        Gym gym = Gym.getInstance();
        gym.createCustomer("sdlfjkj2345223523452@yahoo.com", "John", "smith", "foo");

        gym.enrollUser(1, "sdlfjkj2345223523452@yahoo.com");

        Response unenRollResponse = gym.unenrollCustomer("sdlfjkj2345223523452@yahoo.com", 1);
        assert(unenRollResponse.isSuccess());

        gym.deleteUser("sdlfjkj2345223523452@yahoo.com");
    }

    @Test
    public void testEnrollUserInManyClasses() {
        Gym gym = Gym.getInstance();
        gym.createCustomer("sdlfjkj2345223523452@yahoo.com", "John", "smith", "foo");

        gym.enrollUser(1, "sdlfjkj2345223523452@yahoo.com");
        gym.enrollUser(2, "sdlfjkj2345223523452@yahoo.com");
        gym.enrollUser(3, "sdlfjkj2345223523452@yahoo.com");

        Response enrollTest = gym.getClassesUserEnrolledInByEmail("sdlfjkj2345223523452@yahoo.com");
        ArrayList<GymClassEntity> classesEnrolled = (ArrayList<GymClassEntity>)enrollTest.getValue();
        assert(classesEnrolled.size() == 3);

        gym.deleteUser("sdlfjkj2345223523452@yahoo.com");
    }

}