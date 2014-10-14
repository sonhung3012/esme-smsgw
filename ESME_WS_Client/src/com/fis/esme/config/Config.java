/*
 * The contents of this file are subject to the Mozilla Public
 * License Version 1.1 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * The Original Code is vox-mail.
 *
 * The Initial Developer of the Original Code is Voxeo Corporation.
 * Portions created by Voxeo are Copyright (C) 2000-2007.
 * All rights reserved.
 * 
 * Contributor(s):
 * ICOA Inc. <info@icoa.com> (http://icoa.com)
 */

package com.fis.esme.config;

import java.util.Properties;

public class Config
{
	private static Properties props = null;
	private static String filename = "resource/wsclient.properties";
	
	public static String getString(String name)
	{
		return getProps().getProperty(name);
	}
	
	private static Properties getProps()
	{
		if (props == null)
		{
			props = PropertyLoader.loadProperties(filename);
		}
		return props;
	}
	
	public static String getURL(String key) {
		return getString(ConfigConstant.SERVICE_URL_KEY) + getString(key)
				+ "?wsdl";
	}
}
