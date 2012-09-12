package ee.proexpert.phonerequester.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ee.proexpert.phonerequester.service.impl.DefaultPhoneRequestService;

public class RowStr {
	private static final Log log = LogFactory.getLog(DefaultPhoneRequestService.class);
	public String str;

	public RowStr(String str) {
		this.str = str;
		log.debug("Created RowStr:" + str);
	}

	public String left(int amount) {
		log.debug("Start removing " + amount + " chars.");
		if (StringUtils.isBlank(this.str)) {
			log.debug("String empty.");
			return "";
		}
		String s = StringUtils.left(this.str, amount);

		if (this.str.length() < amount)
			this.str = "";
		else
			this.str = this.str.substring(amount, this.str.length());

		log.debug("Removed '" + s + "', left '" + this.str + "'");
		return s.trim();
	}
}
