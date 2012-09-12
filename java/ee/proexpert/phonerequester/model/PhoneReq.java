package ee.proexpert.phonerequester.model;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class PhoneReq {
	// No need for getters/setters. All public
	public int reqNr;
	public String phoneNr;
	public boolean active;
	public Date activeUntil;
	public Time activeUntilTime;
	public String lang;
	public boolean xlStatus;
	public Time xlActiveSince;
	public Time xlActiveUntil;
	public String xlLang;
	public boolean overrideActive;
	public List<OverrideNr> overrideNr;

	@Override
	public String toString() {
		return "PhoneReq [reqNr=" + reqNr + ", phoneNr=" + phoneNr
				+ ", active=" + active + ", activeUntil=" + activeUntil
				+ ", activeUntilTime=" + activeUntilTime + ", lang=" + lang
				+ ", xlStatus=" + xlStatus + ", xlActiveSince=" + xlActiveSince
				+ ", xlActiveUntil=" + xlActiveUntil + ", xlLang=" + xlLang
				+ ", overrideActive=" + overrideActive + ", overrideNr="
				+ overrideNr + "]";
	}

}
