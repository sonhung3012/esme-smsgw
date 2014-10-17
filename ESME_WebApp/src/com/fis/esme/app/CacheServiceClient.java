package com.fis.esme.app;

import com.fis.esme.apparam.ApParamTransferer;
import com.fis.esme.client.ApParamTransfererClient;
import com.fis.esme.client.CpTransfererClient;
import com.fis.esme.client.EmsMoTransfererClient;
import com.fis.esme.client.EmsMtTransfererClient;
import com.fis.esme.client.FileUploadTransfererClient;
import com.fis.esme.client.GroupsDTTransfererClient;
import com.fis.esme.client.GroupsTransfererClient;
import com.fis.esme.client.IsdnPermissionTransfererClient;
import com.fis.esme.client.IsdnPrefixTransfererClient;
import com.fis.esme.client.IsdnSpecialTransfererClient;
import com.fis.esme.client.LanguageTransfererClient;
import com.fis.esme.client.MessageContentTransfererClient;
import com.fis.esme.client.MessageTransfererClient;
import com.fis.esme.client.SchedulerActionTransfererClient;
import com.fis.esme.client.SchedulerDetailTransfererClient;
import com.fis.esme.client.SchedulerTransfererClient;
import com.fis.esme.client.ServiceTransfererClient;
import com.fis.esme.client.ShortCodeTransfererClient;
import com.fis.esme.client.SmsCommandTransfererClient;
import com.fis.esme.client.SmsLogTransfererClient;
import com.fis.esme.client.SmsMtTransfererClient;
import com.fis.esme.client.SmsRoutingTransfererClient;
import com.fis.esme.client.SmscParamTransfererClient;
import com.fis.esme.client.SmscRoutingTransfererClient;
import com.fis.esme.client.SmscTransfererClient;
import com.fis.esme.client.SubscriberDTTransfererClient;
import com.fis.esme.client.SubscriberTransfererClient;
import com.fis.esme.cp.CpTransferer;
import com.fis.esme.emsmo.EmsMoTransferer;
import com.fis.esme.emsmt.EmsMtTransferer;
import com.fis.esme.fileupload.FileUploadTransferer;
import com.fis.esme.groups.GroupsTransferer;
import com.fis.esme.groupsdt.GroupsDTTransferer;
import com.fis.esme.isdnpermission.IsdnPermissionTransferer;
import com.fis.esme.isdnprefix.IsdnPrefixTransferer;
import com.fis.esme.isdnspecial.IsdnSpecialTransferer;
import com.fis.esme.language.LanguageTransferer;
import com.fis.esme.message.MessageTransferer;
import com.fis.esme.messagecontent.MessageContentTransferer;
import com.fis.esme.scheduler.SchedulerTransferer;
import com.fis.esme.scheduleraction.SchedulerActionTransferer;
import com.fis.esme.schedulerdetail.SchedulerDetailTransferer;
import com.fis.esme.service.ServiceTransferer;
import com.fis.esme.shortcode.ShortCodeTransferer;
import com.fis.esme.smsc.SmscTransferer;
import com.fis.esme.smscommand.SmsCommandTransferer;
import com.fis.esme.smscparam.SmscParamTransferer;
import com.fis.esme.smscrouting.SmscRoutingTransferer;
import com.fis.esme.smslog.EsmeSmsLogTransferer;
import com.fis.esme.smsmt.SmsMtTransferer;
import com.fis.esme.smsrouting.SmsRoutingTransferer;
import com.fis.esme.subscriber.SubscriberTransferer;
import com.fis.esme.subscriberdt.SubscriberDTTransferer;

public class CacheServiceClient {

	public static ServiceTransferer serviceService;
	public static SmsCommandTransferer serviceSmsCommand;
	public static ShortCodeTransferer serviceShortCode;
	public static LanguageTransferer serviceLanguage;
	public static ApParamTransferer serviceApParam;
	public static MessageTransferer serviceMessage;
	public static CpTransferer serviceCp;
	public static EsmeSmsLogTransferer smsLogServices;
	public static SmsRoutingTransferer serviceSmsRouting;
	public static IsdnPermissionTransferer serviceIsdnPermission;
	public static IsdnSpecialTransferer serviceIsdnSpecial;
	public static IsdnPrefixTransferer isdnPrefixService;
	public static SmscTransferer smscService;
	public static MessageContentTransferer serviceMessageContent;
	public static SmscParamTransferer smscParamService;
	public static SmscRoutingTransferer smscRoutingService;
	public static FileUploadTransferer fileUploadService;
	public static SmsMtTransferer smsMtService;
	public static EmsMoTransferer emsMoService;
	public static EmsMtTransferer emsMtService;
	public static GroupsTransferer GroupsService;
	public static SubscriberTransferer SubscriberService;
	public static SchedulerTransferer SchedulerService;
	public static SchedulerDetailTransferer SchedulerDetailService;
	public static SchedulerActionTransferer SchedulerActionService;
	public static GroupsDTTransferer serviceGroups;
	public static SubscriberDTTransferer serviceSubscriber;

	public CacheServiceClient() {

		initServiceClient();
	}

	private void initServiceClient() {

		System.out.println("Init service client...");
		try {
			serviceIsdnPermission = IsdnPermissionTransfererClient.getService();
			serviceIsdnSpecial = IsdnSpecialTransfererClient.getService();
			serviceSmsRouting = SmsRoutingTransfererClient.getService();
			smsLogServices = SmsLogTransfererClient.getService();
			serviceService = ServiceTransfererClient.getService();
			serviceSmsCommand = SmsCommandTransfererClient.getService();
			serviceShortCode = ShortCodeTransfererClient.getService();
			serviceLanguage = LanguageTransfererClient.getService();
			serviceApParam = ApParamTransfererClient.getService();
			serviceMessage = MessageTransfererClient.getService();
			serviceCp = CpTransfererClient.getService();
			isdnPrefixService = IsdnPrefixTransfererClient.getService();
			serviceMessageContent = MessageContentTransfererClient.getService();
			smscService = SmscTransfererClient.getService();
			smscParamService = SmscParamTransfererClient.getService();
			smscRoutingService = SmscRoutingTransfererClient.getService();
			fileUploadService = FileUploadTransfererClient.getService();
			smsMtService = SmsMtTransfererClient.getService();
			emsMoService = EmsMoTransfererClient.getService();
			emsMtService = EmsMtTransfererClient.getService();
			GroupsService = GroupsTransfererClient.getService();
			SubscriberService = SubscriberTransfererClient.getService();
			SchedulerService = SchedulerTransfererClient.getService();
			SchedulerDetailService = SchedulerDetailTransfererClient.getService();
			SchedulerActionService = SchedulerActionTransfererClient.getService();
			serviceSubscriber = SubscriberDTTransfererClient.getService();
			serviceGroups = GroupsDTTransfererClient.getService();

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Init service client finish.");
	}

}
