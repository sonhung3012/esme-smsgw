package com.fis.esme.core.entity;

import java.util.List;
import java.util.Vector;
import com.fss.util.WildcardFilter;

public class SMSCMORoutingUtil {

	public static SMSMORoutingObject getServiceInfo(String shortcode,
			String cmd_code, List<SMSMORoutingInfo> smscInfo) {
		return getSMSMORouting(shortcode, cmd_code, smscInfo);
	}

	/**
	 * 
	 * @param submitIsdn
	 * @param shortcode
	 * @param lSMSCMTRountingObject
	 * @param defaulShortcode
	 * @return
	 */
	public static SMSCMTRountingObject getSMSMTRouting(String submitIsdn,
			String shortcode, List<SMSCMTRountingObject> lSMSCMTRountingObject,
			boolean defaulShortcode) {
		SMSCMTRountingObject SMSCMTRountingObjectTemp = null;
		if (lSMSCMTRountingObject != null && lSMSCMTRountingObject.size() > 0) {
			for (int i = 0; i < lSMSCMTRountingObject.size(); i++) {
				SMSCMTRountingObject smsCMTRountingObject = lSMSCMTRountingObject
						.get(i);
				if (WildcardFilter.match(
						smsCMTRountingObject.getPrefix_value(), submitIsdn)) {
					return smsCMTRountingObject;
				} else if (smsCMTRountingObject.getShortcode()
						.equalsIgnoreCase(shortcode)) {
					SMSCMTRountingObjectTemp = smsCMTRountingObject;
				}
			}
		}
		if (defaulShortcode) {
			return SMSCMTRountingObjectTemp;
		}
		return null;
	}

	/**
	 * 
	 * @param shortcode
	 * @param commandCode
	 * @param smscInfo
	 * @return
	 */
	public static SMSMORoutingObject getSMSMORouting(String shortcode,
			String commandCode, List<SMSMORoutingInfo> smscInfo) {
		if (smscInfo != null && smscInfo.size() > 0) {
			for (int i = 0; i < smscInfo.size(); i++) {
				SMSMORoutingInfo smsMORoutingInfo = smscInfo.get(i);
				if (WildcardFilter.match(smsMORoutingInfo.getShortcode()
						.toLowerCase(), shortcode.toLowerCase())) {
					Vector<SMSMORoutingObject> vtSMSMORoutingObject = smsMORoutingInfo
							.getVtSMSMORouting();
					if (vtSMSMORoutingObject != null
							&& vtSMSMORoutingObject.size() > 0) {
						for (int index = 0; index < vtSMSMORoutingObject.size(); index++) {
							SMSMORoutingObject smsMORoutingObject = vtSMSMORoutingObject
									.get(index);
							if (WildcardFilter.match(smsMORoutingObject
									.getCmd_code().toLowerCase(), commandCode
									.toLowerCase())) {
								return smsMORoutingObject;
							}
						}
					}
				}
			}
		}
		return null;
	}
}
