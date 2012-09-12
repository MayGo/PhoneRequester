
package ee.proexpert.phonerequester.service;
import ee.proexpert.phonerequester.model.PhoneReq;

/**
 * Provides some basic methods for controlling the flow of Twitter messages.
 * 
 * @author Your Name Here
 * @version 1.0
 * 
 */
public interface PhoneRequestService {

	/**
	 * Retrieve the already polled Twitter messages. Keep in mind this
	 * method does not perform the actual Twitter search. It merely returns all
	 * the Tweets that were previously polled through Spring Integration and
	 * which have been cached for returning those to the web-frontend. */
	PhoneReq getLastPhoneReq();

	public void doWebQuery();
}
