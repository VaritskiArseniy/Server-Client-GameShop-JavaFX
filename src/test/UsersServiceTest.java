package test;

import org.junit.*;
import org.junit.rules.ExpectedException;
import mainClasses.User;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UsersServiceTest {

    private UsersService usersService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void globalSetUp() {
        System.out.println("Initial setup...");
        System.out.println("Code executes only once");
    }

    @Before
    public void setUp() {
        System.out.println("Code executes before each test method");
        User user1 = new User("John", "123");
        User user2 = new User("Alice", "1111");
        User user3 = new User("Melinda", "13");
        List<User> usersList = new ArrayList<>();
        usersList.add(user1);
        usersList.add(user2);
        usersList.add(user3);
        usersService = new UsersService(usersList);
    }

    @Test
    public void whenCreateNewUserThenReturnListWithNewUser() throws Exception {
        assertThat(usersService.getUser().size(), is(3));
        usersService.createNewUser("New User","1111");
        assertThat(usersService.getUser().size(), is(4));
    }

    @Test
    public void whenRemoveUserWhenRemoveUserByName(){
        usersService.removeUser("Melinda");
        List<User> usersList = usersService.getUser();
        assertThat(usersList.size(), is(3));
    }

    @Test
    public void whenCreateNewUserWithoutLoginThenThrowCustomFieldException() throws Exception {
        thrown.expect(CustomFieldException.class);
        thrown.expectMessage("Login could not be empty or null");
        usersService.createNewUser(null, "1111");
    }

    @Test
    public void whenCreateNewUserPasswordThenThrowCustomFieldException() throws Exception {
        thrown.expect(CustomFieldException.class);
        thrown.expectMessage("Password not be null");
        usersService.createNewUser("Dave", null);
    }



    @AfterClass
    public static void tearDown() {
        System.out.println("Tests finished");
    }

    @After
    public void afterMethod() {
        System.out.println("Code executes after each test method");
    }
}