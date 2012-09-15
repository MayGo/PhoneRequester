package ee.proexpert.phonerequester.service;

import ee.proexpert.phonerequester.model.PhoneReq;

/**
 * Provides some methods for retrieving legacy "phone service" files and keeps
 * last PhoneReq instance.
 * 
 * @author Maigo Erit
 * @version 1.0
 * 
 */
public interface PhoneRequestService {

	/**
	 * Retrieve already polled PhoneReq. This is the last one retrieved over
	 * web.
	 * 
	 * @return PhoneReq
	 */
	PhoneReq getLastPhoneReq();

	/**
	 * Retrieves file from web and parses file contents and saves it as PhoneReq
	 * object, so getLastPhoneReq function can retrieve it.
	 */
	public void doWebQuery();
}
