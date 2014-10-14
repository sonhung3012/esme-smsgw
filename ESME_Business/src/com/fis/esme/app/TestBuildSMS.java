package com.fis.esme.app;
//package com.fis.prc.app;
///**
// * 
// */
//
//import java.io.File;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//import javax.xml.namespace.QName;
//
//import com.fis.prc.buildmt.BuildMTTransferer;
//import com.fis.prc.customercare.Exception_Exception;
//import com.fis.prc.customercare.PRC;
//import com.fis.prc.mt.persistence.BuildMTInput;
//import com.fis.prc.mt.persistence.PrcAddChildOutput;
//import com.fis.prc.mt.persistence.PrcSubLogOutput;
//
///**
// * @author Administrator
// * 
// */
//public class TestBuildSMS {
//
//	public TestBuildSMS() {
//	}
//
//	// public static void dangkychachinh(URL wsdlURL) {
//	//
//	// PRCService ss = new PRCService(wsdlURL, new QName(
//	// "http://customercare.prc.fis.com/", "PRCService"));
//	// PRC port = ss.getPRCPort();
//	//
//	// pes.PrcSubOutput output = null;
//	// try {
//	// output = port
//	// .registerPRCService("909123129", "BASIC", "2", "admin");
//	// } catch (Exception_Exception e1) {
//	// e1.printStackTrace();
//	// }
//	// if (output.getErrorCode() == null
//	// || output.getErrorCode().length() <= 0) {
//	//
//	// output.setErrorCode("REG_SUCCESS");
//	//
//	// BuildMTTransfererService ss1 = new BuildMTTransfererService(
//	// BuildMTTransfererService.WSDL_LOCATION, new QName(
//	// "http://buildmt.prc.fis.com/",
//	// "BuildMTTransfererService"));
//	// BuildMTTransferer port1 = ss1.getBuildMTTransfererPort();
//	//
//	// try {
//	// port1.buildMultiMT("1", "1", output, 0);
//	// } catch (Exception e) {
//	// e.printStackTrace();
//	// }
//	// } else {
//	// System.out.println(output.getErrorCode());
//	// }
//	// }
//
//	public static void main(String[] args) {
//
//		URL wsdlURL = com.fis.prc.customercare.PRCService.WSDL_LOCATION;
//		if (args.length > 0 && args[0] != null && !"".equals(args[0])) {
//			File wsdlFile = new File(args[0]);
//			try {
//				if (wsdlFile.exists()) {
//					wsdlURL = wsdlFile.toURI().toURL();
//				} else {
//					wsdlURL = new URL(args[0]);
//				}
//			} catch (MalformedURLException e) {
//				e.printStackTrace();
//			}
//		}
//
//		dangkycon(wsdlURL);
//
//	}
//
//	public static void dangkycon(URL wsdlURL) {
//
//		com.fis.prc.customercare.PRCService ss = new com.fis.prc.customercare.PRCService(
//				wsdlURL, new QName("http://customercare.prc.fis.com/",
//						"PRCService"));
//		PRC port = ss.getPRCPort();
//
//		PrcAddChildOutput output = null;
//		try {
//			output = (PrcAddChildOutput) port.addChild("909123123",
//					"909123843", "2", "admin");
//		} catch (Exception_Exception e1) {
//			e1.printStackTrace();
//		}
//		if (output.getErrorCode() == null
//				|| output.getErrorCode().length() <= 0) {
//			System.out.println("add ok!");
//			output.setErrorCode("ADD_CHILD_SUCCESS_WEB");
//
//			BuildMTTransfererService ss1 = new BuildMTTransfererService(
//					BuildMTTransfererService.WSDL_LOCATION, new QName(
//							"http://buildmt.prc.fis.com/",
//							"BuildMTTransfererService"));
//			BuildMTTransferer port1 = ss1.getBuildMTTransfererPort();
//
//			try {
//				System.out.println(output);
//				BuildMTInput in = new BuildMTInput();
//				in.setPrcSubLogOutput((PrcSubLogOutput) output);
//				port1.buildMT("3", "1", in, 1);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else {
//			System.out.println(output.getErrorCode());
//		}
//	}
//
//}
