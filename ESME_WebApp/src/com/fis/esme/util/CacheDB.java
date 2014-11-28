package com.fis.esme.util;

import java.util.ArrayList;
import java.util.List;

import com.fis.esme.persistence.ApParam;
import com.fis.esme.persistence.EsmeCp;
import com.fis.esme.persistence.EsmeEmsMo;
import com.fis.esme.persistence.EsmeGroups;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.esme.persistence.EsmeSmsc;
import com.fis.esme.persistence.Groups;

public final class CacheDB {

	private CacheDB() {

	}

	public static List<ApParam> cacheApParam = new ArrayList<ApParam>();
	public static List<EsmeSmsc> cacheSmsc = new ArrayList<EsmeSmsc>();
	public static List<EsmeServices> cacheService = new ArrayList<EsmeServices>();
	public static List<EsmeGroups> cacheGroups = new ArrayList<EsmeGroups>();
	public static List<EsmeShortCode> cacheShortCode = new ArrayList<EsmeShortCode>();
	public static List<EsmeSmsCommand> cacheSmsCommand = new ArrayList<EsmeSmsCommand>();
	public static List<EsmeCp> cacheCP = new ArrayList<EsmeCp>();
	public static List<Groups> cacheGroupsDT = new ArrayList<Groups>();
	public static List<EsmeEmsMo> cacheMo = new ArrayList<EsmeEmsMo>();
	// public static final ArrayList<McaPackage> cachePackage = new
	// ArrayList<McaPackage>();
	// public static final ArrayList<PrcAction> cacheAction = new
	// ArrayList<PrcAction>();
	// public static List<PrcCommercialOffer> cacheComOffer = new
	// ArrayList<PrcCommercialOffer>();
	// public static final ArrayList<McaInteraction> cacheInteraction=new
	// ArrayList<McaInteraction>();
	// public static final ArrayList<Output> cacheOutputRG = new
	// ArrayList<Output>();
	// public static final ArrayList<Output> cacheOutputTER = new
	// ArrayList<Output>();
	// public static final ArrayList<Output> cacheOutputPROM = new
	// ArrayList<Output>();

	// public static final HashMap<String, McaSubscriber> cacheSub = new
	// HashMap<String, McaSubscriber>();
	//
	// public static McaSubscriber getSub(String msisdn)
	// {
	// return cacheSub.get(msisdn);
	// }
}
