package ee.proexpert.phonerequester.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ee.proexpert.phonerequester.model.OverrideNr;
import ee.proexpert.phonerequester.model.PhoneReq;
import ee.proexpert.phonerequester.service.PhoneRequestService;

/**
 * Handles requests for the application home page.
 * 
 * @author Maigo Erit
 * @version 1.0
 * 
 */
@Controller
public class HomeController {

	private static final Logger LOGGER = Logger.getLogger(HomeController.class);

	@Autowired
	private PhoneRequestService phoneRequestService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/")
	public String home(Model model) {
		return "home";
	}

	/**
	 * Get last PhoneReq and return it as JSON object.
	 */
	@RequestMapping(value = "/ajax")
	public @ResponseBody
	Map ajaxCall(Model model) {

		final PhoneReq phoneReq = phoneRequestService.getLastPhoneReq();

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Last PhoneReq:" + phoneReq);
		}

		final SimpleDateFormat dateFormat = new SimpleDateFormat(
				"d. MMMMM yyyy");
		final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		// format everything as is needed in front end. This obj is returned as JSON
		Map map = new HashMap() {
			{
				put("reqNr", phoneReq.reqNr);
				put("phoneNr", phoneReq.phoneNr);
				put("active", phoneReq.active);
				put("activeUntil", dateFormat.format(phoneReq.activeUntil));
				if (phoneReq.activeUntilTime != null)
					put("activeUntilTime", timeFormat.format(new Date(
							phoneReq.activeUntilTime.getTime())));
				put("lang", phoneReq.lang);
				put("xlStatus", phoneReq.xlStatus);
				if (phoneReq.xlActiveSince != null)
					put("xlActiveSince", timeFormat.format(new Date(
							phoneReq.xlActiveSince.getTime())));
				if (phoneReq.xlActiveUntil != null)
					put("xlActiveUntil", timeFormat.format(new Date(
							phoneReq.xlActiveUntil.getTime())));
				put("xlLang", phoneReq.xlLang);
				put("overrideActive", phoneReq.overrideActive);
				if (!phoneReq.overrideNr.isEmpty())
					put("overrideNrs", new HashMap() {
						{
							for (Iterator<OverrideNr> i = phoneReq.overrideNr
									.iterator(); i.hasNext();) {
								OverrideNr oNr = i.next();
								put(oNr.name, oNr.nr);
							}
						}
					});

			}
		};

		return map;
	}
}
