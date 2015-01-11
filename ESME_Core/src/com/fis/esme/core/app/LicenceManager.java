package com.fis.esme.core.app;

import com.fis.licence.application.business.LicenceVerifier;
import com.fis.licence.application.business.ProjectKeyManager;
import com.fis.licence.util.lib.LicenceException;
import com.fss.util.DateUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * Title: Capture call center
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * <p>
 * Company: FIS-TES
 * </p>
 * 
 * @author LiemLT
 * @version 1.0 app: FPT.iREC-SERVICE
 */
public class LicenceManager {
	private static Date mdtDayToDown = null;

	public static String getParameter(String strKey) {
		return LicenceVerifier.getParameter(strKey);
	}

	public static void setProjectKey() throws LicenceException, Exception {
		// set private key

		String[] arrProjectKey = loadProjectKey("conf/ProjectKey.lic");
		if (arrProjectKey.length <= 0 || arrProjectKey.length > 5) {
			throw new Exception("Project key is validate, size :"
					+ arrProjectKey.length);
		}
		String[] arrShortKey = new String[4];
		String strProjectCode = arrProjectKey[4];
		arrShortKey[0] = arrProjectKey[0];
		arrShortKey[1] = arrProjectKey[1];
		arrShortKey[2] = arrProjectKey[2];
		arrShortKey[3] = arrProjectKey[3];
		ProjectKeyManager.setProjectKey(arrShortKey, strProjectCode);
	}

	public static void loadLicence() throws LicenceException, Exception {
		LicenceVerifier.loadLicence("conf/Licence.lic");
	}

	public static String[] loadProjectKey(String strProjectFilePath)
			throws IOException, LicenceException {
		BufferedReader br = new BufferedReader(new FileReader(
				strProjectFilePath));

		String strLine = null;
		String[] arrProjectKey = new String[5];
		String strLicence = "";
		String strLicenceKey = "";
		int iLineIndex = 0;

		while ((strLine = br.readLine()) != null && !strLine.equals("")) {
			strLine = strLine.trim();

			if (strLine.startsWith("-")) {
				continue;
			}
			if (strLine.length() != 16) {
				throw new LicenceException("LME-0016",
						"The licence file config is invalid.");
			}
			if (iLineIndex < 5) {
				arrProjectKey[iLineIndex] = strLine;
			} else {
				break;
			}
			iLineIndex++;
		}
		return arrProjectKey;
	}

	public static boolean checkDayToDown() {
		// hack for demo Vianphone Ha Noi
		if (mdtDayToDown == null) {
			String strDayToDown = "01-01-2015";//LicenceManager.getParameter("DAY_TO_DOWN");
			DateFormat FORMAT_DATE = new SimpleDateFormat("dd-MM-yyyy");
			mdtDayToDown = DateUtil.toDate(strDayToDown, FORMAT_DATE);
		}

		if (mdtDayToDown != null && mdtDayToDown.compareTo(new Date()) <= 0) {
			System.out.println("Expired date: " + mdtDayToDown.toString());
			return false;
		}

		return true;
	}

	public static boolean recheckToServer() throws LicenceException, Exception {
		return true;
	}
}
