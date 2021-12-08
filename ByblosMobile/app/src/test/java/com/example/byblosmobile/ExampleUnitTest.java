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