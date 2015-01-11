package com.fis.esme.core.http.util;


import java.util.HashMap;
import java.util.Map;

import com.fis.esme.core.http.entity.CpInfo;
import com.fis.esme.core.http.entity.SubInfo;
import com.fis.esme.core.util.LinkQueue;


/**
 * *
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * <p>
 * Company: FIS-SOT
 * </p>
 * 
 * @author SonNH17
 * @version 1.0
 */

public class GlobalParameter
{
	public final static String		   VERSION   = " [Verson:0.1 - 15/05/13 by SonNH17]";
	
	// Map
	public static HashMap<String, SubInfo> mSession  = new HashMap<String, SubInfo>();
	// Time dequeue (s)
	public final static int			 miTimeout = 200;
	// Queu User To CP
	public static LinkQueue<SubInfo>	  mlqMT	= new LinkQueue<SubInfo>(
												    5000);
	// Queue CP To User
	public static LinkQueue<SubInfo>	  mlqMO	= new LinkQueue<SubInfo>(
												    5000);
	
	public static Map<String, CpInfo>	 mapCP	= new HashMap<String, CpInfo>();
}
