package test;

import mainClasses.Staff;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StaffsServiceTest {

    private StaffService staffsService;

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
        Staff staff1 = new Staff("John", "123");
        Staff staff2 = new Staff("Alice", "1111");
        Staff staff3 = new Staff("Melinda", "13");
        List<Staff> staffsList = new ArrayList<>();
        staffsList.add(staff1);
        staffsList.add(staff2);
        staffsList.add(staff3);
        staffsService = new StaffService(staffsList);
    }

    @Test
    public void whenCreateNewStaffThenReturnListWithNewStaff() throws Exception {
        assertThat(staffsService.getStaff().size(), is(3));
        staffsService.createNewStaff("New Staff","1111");
        assertThat(staffsService.getStaff().size(), is(4));
    }

    @Test
    public void whenRemoveStaffWhenRemoveStaffByName(){
        staffsService.removeStaff("Melinda");
        List<Staff> staffsList = staffsService.getStaff();
        assertThat(staffsList.size(), is(3));
    }

    @Test
    public void whenCreateNewStaffWithoutLoginThenThrowCustomFieldException() throws Exception {
        thrown.expect(CustomFieldException.class);
        thrown.expectMessage("Login could not be empty or null");
        staffsService.createNewStaff(null, "1111");
    }

    @Test
    public void whenCreateNewStaffPasswordThenThrowCustomFieldException() throws Exception {
        thrown.expect(CustomFieldException.class);
        thrown.expectMessage("Password not be null");
        staffsService.createNewStaff("Dave", null);
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