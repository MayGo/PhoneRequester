package ee.proexpert.phonerequester.service.impl;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ee.proexpert.phonerequester.model.RowStr;
import ee.proexpert.phonerequester.model.OverrideNr;
import ee.proexpert.phonerequester.model.PhoneReq;
import ee.proexpert.phonerequester.service.PhoneRequestService;

/**
 * Implementation of the PhoneRequestService interface.
 * 
 * @author Your Name Here
 * @version 1.0
 * 
 */
@Service
public class DefaultPhoneRequestService implements PhoneRequestService {

	private static final Log log = LogFactory.getLog(DefaultPhoneRequestService.class);
	/** Holds last recieved PhoneRequest */
	private PhoneReq lastPhoneReq;

	int timesRunned = 0;
	private static final int MAX_RUN_TIMES = 10;

	/** {@inheritDoc} */
	@Override
	public PhoneReq getLastPhoneReq() {
		return lastPhoneReq;
	}

	private void setLastPhoneReq(PhoneReq p) {
		if (p != null)
			lastPhoneReq = p;
	}

	/** {@inheritDoc} */
	@Override
	@Scheduled(fixedRate = 5000)
	public void doWebQuery() {
		this.increment();
		String url = "http://people.proekspert.ee/ak/data_" + timesRunned + ".txt";
		URL u;
		InputStream is = null;
		DataInputStream dis;
		String s;

		try {
			u = new URL(url);
			is = u.openStream();
			log.info("Query for file: " + url);
			u = new URL(url);
			is = u.openStream();
			dis = new DataInputStream(new BufferedInputStream(is));

			s = dis.readLine();
			setLastPhoneReq(parseRow(s));

		} catch (MalformedURLException mue) {
			log.error("MalformedURLException happened.");
			// mue.printStackTrace();
		} catch (IOException ioe) {
			log.error("IOException happened. File not found:" + url);
			// ioe.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException ioe) {
				// just going to ignore this one
			}
		}
		if (timesRunned >= MAX_RUN_TIMES) {
			timesRunned = 0;
			log.info("Reseting. Maximum times runned:" + MAX_RUN_TIMES);
		}
	}

	/**
	 * Function that parses custom string that contains Phone Service
	 * parameters. And return these parameters as PhoneReq object.
	 * 
	 * @param row
	 * @return PhoneReq
	 */
	private PhoneReq parseRow(String row) {

		log.debug("Parsing:" + row);
		if (StringUtils.isBlank(row)) {
			log.error("Unparseable row.Empty.");
			return null;
		}

		SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMDD");
		RowStr m = new RowStr(row);
		Map<String, String> hm = new HashMap<String, String>();

		hm.put("active", m.left(1));
		hm.put("phoneNr", m.left(20));
		hm.put("xlStatus", m.left(1));
		hm.put("lang", m.left(1));
		hm.put("xlLang", m.left(1));
		hm.put("activeUntil", m.left(8));
		hm.put("activeUntilTime", m.left(4));
		hm.put("xlActiveSince", m.left(4));
		hm.put("xlActiveUntil", m.left(4));
		hm.put("overrideActive", m.left(1));
		hm.put("overrideNrs", m.left(120));
		hm.put("overrideNames", m.left(160));

		PhoneReq p = new PhoneReq();

		p.reqNr = timesRunned;
		p.active = (hm.get("active") == "A") ? true : false;
		p.phoneNr = hm.get("phoneNr").trim();
		p.xlStatus = (hm.get("xlStatus") == "J") ? true : false;
		p.lang = (hm.get("lang") == "I") ? "Inglise" : (hm.get("lang") == "E") ? "Eesti" : "";
		p.xlLang = (hm.get("xlLang") == "I") ? "Inglise" : (hm.get("xlLang") == "E") ? "Eesti" : "";
		
		if (!StringUtils.isBlank(hm.get("activeUntil"))) {
			try {
				p.activeUntil = formatter.parse(hm.get("activeUntil"));
			} catch (ParseException e) {
				log.error("Error parsing date"+hm.get("activeUntil"));
				//e.printStackTrace();
			}
		}

		p.activeUntilTime = parseTime(hm.get("activeUntilTime"));
		p.xlActiveSince = parseTime(hm.get("xlActiveSince"));
		p.xlActiveUntil = parseTime(hm.get("xlActiveUntil"));

		p.overrideActive = (hm.get("overrideActive") == "K") ? true : false;

		List<OverrideNr> overrideNr = new ArrayList<OverrideNr>();

		if (hm.get("overrideNrs") != "") {
			//Lets get override numbers and their names
			RowStr nrs = new RowStr(hm.get("overrideNrs"));
			RowStr names = new RowStr(hm.get("overrideNames"));
			OverrideNr oNr = new OverrideNr();
			oNr.nr = nrs.left(15);
			oNr.name = names.left(20);
			hm.get("overrideNrs");
			overrideNr.add(oNr);
		}
		p.overrideNr = overrideNr;
		log.debug("Created:"+p);
		return p;
	}

	private Time parseTime(String t) {
		Time time = (!StringUtils.isBlank(t)) ? Time.valueOf(t.substring(0, 2) + ":" + t.substring(2, 4) + ":00") : null;
		return time;
	}

	private void increment() {
		timesRunned++;
		log.debug("Times run: " + timesRunned);
	}

}
