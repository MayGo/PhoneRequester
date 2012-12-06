/**
 * 
 */
package ee.proexpert.phonerequester.service.impl;

import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import ee.proexpert.phonerequester.model.PhoneReq;
import ee.proexpert.phonerequester.service.PhoneRequestService;


/**
 * @author Maigo Erit
 *
 */
@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class DefaultPhoneRequestServiceTest {

	
	@Autowired
	private PhoneRequestService phoneRequestService;
	/**
	 * Test if all available test PhoneReq strings are parseable.
	 * Test method for {@link ee.proexpert.phonerequester.service.impl.DefaultPhoneRequestService#parseRow()}.
	 */
	@Test
	public void testParseRow() {
		PhoneReq p1=phoneRequestService.parseRow("A0551234567          JE 20111023160008001200E");
		assertNotNull(p1);
		
		PhoneReq p2=phoneRequestService.parseRow("");
		assertNull(p2);
		
		PhoneReq p3=phoneRequestService.parseRow("A0551234555          JII20111111215900001200K0552212211     0506669999                                                                                               Rein Ratas                                                                                                                                                      ");
		assertNotNull(p3);
		
		PhoneReq p4=phoneRequestService.parseRow("P0502234569          EE 201201012359        E");
		assertNotNull(p4);
		
		PhoneReq p5=phoneRequestService.parseRow("A0551234569          JII20111023235908001200K0551111111     0509999999                                                                                               Jaan Juurikas       Peeter                                                                                                                                      ");
		assertNotNull(p5);
		
		PhoneReq p6=phoneRequestService.parseRow("09384950845u43 4350 485940 03485948 545849058 ");
		assertNull(p6);
	}
	//TODO: Test if parse result is correct.
}
