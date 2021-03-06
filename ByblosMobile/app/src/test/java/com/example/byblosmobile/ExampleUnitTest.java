package com.example.byblosmobile;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    Context mMockContext;

    @Test
    public void validateLoginValues(){
        //LoginActivity test = new LoginActivity();
        boolean test1= LoginActivity.validateUsernameValue("E");
        boolean test2= LoginActivity.validatePasswordValue("F");
        assertThat(test1, is(true));
        assertThat(test2, is(true));

        boolean test3= LoginActivity.validateUsernameValue("");
        boolean test4= LoginActivity.validatePasswordValue("");

        assertThat(test3, is(false));
        assertThat(test4, is(false));
    }

    @Test
    public void validateRatingValues(){
        //LoginActivity test = new LoginActivity();
        String test0= CustomerRateBranch.ratingValueValidation("1");
        String test1= CustomerRateBranch.ratingValueValidation("E");
        String test2= CustomerRateBranch.ratingValueValidation("9");
        String test3= CustomerRateBranch.ratingValueValidation("");
        assertEquals("Thank you for the feedback😃", test0);
        assertEquals("rating can only be 1 to 5!", test1);
        assertEquals("rating can only be 1 to 5!", test2);
        assertEquals("content require to fill!", test3);
    }

    @Test
    public void validateServiceRequestValues(){
        //LoginActivity test = new LoginActivity();

        assertEquals("Request added",
                CustomerMakeRequests.serviceRequestValueValidation(
                        "T", "T", "t@t.com", "T", "99", "T"));
        assertEquals("content require to fill!",
                CustomerMakeRequests.serviceRequestValueValidation(
                        "", "T", "t@t.com", "T", "99", "T"));
        assertEquals("content require to fill!",
                CustomerMakeRequests.serviceRequestValueValidation(
                        "T", "", "t@t.com", "T", "99", "T"));
        assertEquals("content require to fill!",
                CustomerMakeRequests.serviceRequestValueValidation(
                        "T", "T", "", "T", "99", "T"));
        assertEquals("content require to fill!",
                CustomerMakeRequests.serviceRequestValueValidation(
                        "T", "T", "t@t.com", "", "99", "T"));
        assertEquals("content require to fill!",
                CustomerMakeRequests.serviceRequestValueValidation(
                        "T", "T", "t@t.com", "T", "", "T"));
        assertEquals("content require to fill!",
                CustomerMakeRequests.serviceRequestValueValidation(
                        "T", "T", "t@t.com", "T", "99", ""));
        assertEquals("Invalid!",
                CustomerMakeRequests.serviceRequestValueValidation(
                        "T", "T", "T", "T", "99", "T"));
        assertEquals("Invalid!",
                CustomerMakeRequests.serviceRequestValueValidation(
                        "T", "T", "t@t.com", "T", "a", "T"));
        assertEquals("Invalid!",
                CustomerMakeRequests.serviceRequestValueValidation(
                        "T", "T", "t@t.com", "T@", "99", "T"));
    }

    @Test
    public void validateEmployeeProfileEditValues(){
        //LoginActivity test = new LoginActivity();

        assertEquals("Success!",
                EmployeeProfileEdit.employeeProfileValueValidate(
                        "T", "T", "99"));
        assertEquals("* content require to fill!",
                EmployeeProfileEdit.employeeProfileValueValidate(
                        "", "T", "99"));
        assertEquals("* content require to fill!",
                EmployeeProfileEdit.employeeProfileValueValidate(
                        "T", "", "99"));
        assertEquals("* content require to fill!",
                EmployeeProfileEdit.employeeProfileValueValidate(
                        "T", "T", ""));
        assertEquals("Phone number can only be 0 to 9, and Address can not include any symbols!",
                EmployeeProfileEdit.employeeProfileValueValidate(
                        "T", "T", "99123fr"));
        assertEquals("Phone number can only be 0 to 9, and Address can not include any symbols!",
                EmployeeProfileEdit.employeeProfileValueValidate(
                        "T", "r2332r", "e"));
        assertEquals("Phone number can only be 0 to 9, and Address can not include any symbols!",
                EmployeeProfileEdit.employeeProfileValueValidate(
                        "T", "r23$32r", "9932131"));
    }

    @Test
    public void validateAdminServiceValues(){
        //LoginActivity test = new LoginActivity();

        assertEquals("valid",
                ServiceActivityForAdmin.serviceValueValidation(
                        "T", "99"));
        assertEquals("Name cannot just be a number",
                ServiceActivityForAdmin.serviceValueValidation(
                        "31", "99"));
        assertEquals("Name cannot just be a number",
                ServiceActivityForAdmin.serviceValueValidation(
                        "-31", "99"));
        assertEquals("Rate has to be a positive number",
                ServiceActivityForAdmin.serviceValueValidation(
                        "T",  "ewafafe"));
        assertEquals("Rate has to be a positive number",
                ServiceActivityForAdmin.serviceValueValidation(
                        "T", "99123fr"));
        assertEquals("Rate has to be a positive number",
                ServiceActivityForAdmin.serviceValueValidation(
                        "T", "-111"));
        assertEquals("Rate has to be a positive number",
                ServiceActivityForAdmin.serviceValueValidation(
                        "T", "23$32"));
    }

    @Test
    public void validateAdminAddProductValues(){
        //LoginActivity test = new LoginActivity();

        assertEquals("Service added",
                ServiceActivityForAdmin.addProductValueValidation(
                        "T", "99", "x"));
        assertEquals("Please enter all required fields",
                ServiceActivityForAdmin.addProductValueValidation(
                        "", "99", "x"));
        assertEquals("Please enter all required fields",
                ServiceActivityForAdmin.addProductValueValidation(
                        "T", "", "x"));
        assertEquals("Please enter all required fields",
                ServiceActivityForAdmin.addProductValueValidation(
                        "T", "99", ""));
        assertEquals("Name cannot just be a number",
                ServiceActivityForAdmin.addProductValueValidation(
                        "31", "99", "x"));
        assertEquals("Name cannot just be a number",
                ServiceActivityForAdmin.addProductValueValidation(
                        "-31", "99", "x"));
        assertEquals("Rate has to be a positive number",
                ServiceActivityForAdmin.addProductValueValidation(
                        "T",  "ewafafe", "x"));
        assertEquals("Rate has to be a positive number",
                ServiceActivityForAdmin.addProductValueValidation(
                        "T", "99123fr", "x"));
        assertEquals("Rate has to be a positive number",
                ServiceActivityForAdmin.addProductValueValidation(
                        "T", "-111", "x"));
        assertEquals("Rate has to be a positive number",
                ServiceActivityForAdmin.addProductValueValidation(
                        "T", "23$32", "x"));
    }

    @Test
    public void serviceClassIsCorrect() {
        Service test = new Service("e","1","req","E");
        assertEquals("e RATE: 1", test.toString());
        assertEquals("1", test.getRate());
        assertEquals("E", test.getBranch());
        assertEquals("e", test.getName());
        assertEquals("req", test.getRequiredInfo());
        test.setName("a");
        test.setBranch("A");
        assertEquals("A", test.getBranch());
        assertEquals("a", test.getName());
    }

    @Test
    public void employeeClassIsCorrect() {
        Employee test = new Employee("e","1","employee","E", "1111111111", null,null,null);
        assertEquals("employee", test.getUserType());
        assertEquals("e", test.getBranchUsername());
        assertEquals("e", test.getBranchName());
        assertEquals("E", test.getBranchAddress());
        assertEquals("1111111111", test.getBranchPhoneNumber());
        test.setBranchName("a");
        test.setBranchAddress("A");
        test.setBranchPhoneNumber("9999999999");
        assertEquals("A", test.getBranchAddress());
        assertEquals("a", test.getBranchName());
        assertEquals("9999999999", test.getBranchPhoneNumber());

    }

    @Test
    public void timeSlotClassIsCorrect() {
        TimeSlot test = new TimeSlot("Monday",9,17);
        assertEquals("Monday, from: 9 to 17", test.toString());
        assertEquals("Monday", test.getDay());
        assertEquals(9, test.getStartHour());
        assertEquals(17, test.getEndHour());
        test.setDay("Friday");
        test.setStartHour(10);
        test.setFinishHour(16);
        assertEquals("Friday, from: 10 to 16", test.toString());
        assertEquals("Friday", test.getDay());
        assertEquals(10, test.getStartHour());
        assertEquals(16, test.getEndHour());
    }

    @Test
    public void requestClassIsCorrect() {
        Request test = new Request("Tow","Dave", "E","x","rejected");
        assertEquals("Tow Requests for E Status: rejected", test.toString());
        assertEquals("Tow Requests from Dave Status: rejected", test.toStringEmployeeSide());
        assertEquals("Tow", test.getServiceName());
        assertEquals("Dave", test.getCustomerName());
        assertEquals("E", test.getBranchName());
        assertEquals("x", test.getExtraInfo());
        assertEquals("rejected", test.getStatus());
        test.setStatus("accepted");
        assertEquals("Tow Requests for E Status: accepted", test.toString());
        assertEquals("Tow Requests from Dave Status: accepted", test.toStringEmployeeSide());
        assertEquals("accepted", test.getStatus());
    }
}