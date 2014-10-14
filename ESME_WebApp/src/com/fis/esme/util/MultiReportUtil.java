package com.fis.esme.util;

import java.io.File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import ReportTDK.Report;

import com.vaadin.data.Item;
import com.vaadin.ui.Table;

import eu.livotov.tpt.i18n.TM;

public class MultiReportUtil
{
	public static void doExportDataOnForm(String template,
			ArrayList<String[]> dataDefinition, ArrayList<String[]> parameters,
			String reportcolumns, Table table, String strDateFormat)
	{
		try
		{
			String[] columns = reportcolumns.split(",");
			
			Collection<?> coll = table.getContainerDataSource().getItemIds();
			
			if (coll.size() < 1)
			{
				MessageAlerter.showErrorMessageI18n(table.getWindow(),
						TM.get("common.report.msg.export.emty"));
				return;
			}

			Vector<Vector<Object>> vData = new Vector<Vector<Object>>();
			Vector<Object> obj = null;
			int stt = 1;
			for (Object object : coll)
			{
				obj = new Vector<Object>();
				for (int i = 0; i < columns.length; i++)
				{
					Item item = table.getItem(object);
					Object value = null;
					if (columns[i].toString().equals("STT"))
					{
						
						value = stt;
					}
					else
					{
						value = (item.getItemProperty(columns[i]).getValue() == null) ? ""
								: item.getItemProperty(columns[i]).getValue();
						
						if (value instanceof Date)
						{
							SimpleDateFormat dateFormat = new SimpleDateFormat(
									strDateFormat);
							value = dateFormat.format(value);
						}
						
					}
					if (dataDefinition != null)
					{
						for (String[] arr : dataDefinition)
						{
							if (columns[i].toString().equals(arr[0])
									&& value.toString().equals(arr[1]))
								value = arr[2];
						}
					}
					obj.add(value);
				}
				stt++;
				vData.add(obj);
			}
			
			String absolutePath = table.getApplication().getContext()
					.getBaseDirectory().getAbsolutePath();
			
			String exportedDir = absolutePath + File.separator + "Report"
					+ File.separator + "Exported" + File.separator + "Form";
			
			//Begin Create Exported/Form
			File exDir = new File(exportedDir);
			if (!exDir.exists())
			{
				exDir = new File(absolutePath + File.separator + "Report"+ File.separator + "Exported");
				exDir.mkdir();
				
				exDir = new File(absolutePath + File.separator + "Report"
						+ File.separator + "Exported" + File.separator + "Form");
				if (!exDir.exists())
					exDir.mkdir();
			}
			//End 
			
			String templateName = template;
			String templateDir = absolutePath + File.separator + "Report"
					+ File.separator + "Template" + File.separator + "Form";
			Date exportDate = FormUtil.getToday(null);
			String strExportDate = new SimpleDateFormat("yyyy-MM-dd")
					.format(exportDate);
			String strFileName = templateName + "_" + strExportDate + "_"
					+ exportDate.getTime() + ".xls";
			String strReportPath = exportedDir + File.separator + strFileName;
			String strTemplatePath = templateDir + File.separator
					+ templateName + ".xls";
			
			ResultSet rs = ReportTDK.Report.convertVectorToResultSet(vData,
					columns);

			
			Report report = new Report(rs, strTemplatePath, strReportPath);
			
			for (String[] arr : parameters)
			{
				report.setParameter(arr[0], arr[1]);
			}
			
			report.fillDataToExcel(true);
			
			table.getWindow().open(
					new FileDownloadResource(new File(strReportPath), table
							.getApplication()));
			MessageAlerter.showMessageI18n(table.getWindow(),
					TM.get("common.report.msg.export.success"));
			
		}
		catch (Exception e)
		{
			MessageAlerter.showErrorMessageI18n(table.getWindow(),
					TM.get("common.report.msg.export.fail"));
			e.printStackTrace();
		}
	}
	
	public static Object doExportData(String absolutePath, String template,
			ArrayList<String[]> dataDefinition, ArrayList<String[]> parameters, Vector<Vector<Vector<Object>>> vData,ArrayList<String[]> columns, String strDateFormat)
	{
//		String[] columns = new String[]{"hourMaxFw","hourMaxSms","maxFwPerHour","maxFwPerSecond","maxFwTime","maxSmsHour","rateFwCallSuccess","summeryDate","totalFwCall","totalMcaAc","totalMcaAcDay","totalMcaDeac","totalMcaDeacCusUnreg","totalMcaDeacFwCallBox","totalMcaDeacNemoney","totalMcaDeacOther","totalMcaDeacPospaidBlock2","totalSmsAdv","totalSmsDay","totalSmsFail","totalSmsFail2Smsc","totalSmsFail2User","totalSmsFailOther","totalSmsSuccess"};
		try
		{

			if (vData.get(0).size() < 1)
			{
				return 0;
			}

			String exportedDir = absolutePath + File.separator + "Report"
					+ File.separator + "Exported";
			
			//Begin Create Exported/Form
			File exDir = new File(exportedDir);
			if (!exDir.exists())
			{
				exDir = new File(absolutePath + File.separator + "Report"+ File.separator + "Exported");
				exDir.mkdir();
			}
			//End 
			
			String templateName = template;
			String templateDir = absolutePath + File.separator + "Report"
					+ File.separator + "Template";
			Date exportDate = FormUtil.getToday(null);
			String strExportDate = new SimpleDateFormat("yyyy-MM-dd")
					.format(exportDate);
			String strFileName = templateName + "_" + strExportDate + "_"
					+ exportDate.getTime() + ".xls";
			String strReportPath = exportedDir + File.separator + strFileName;
			String strTemplatePath = templateDir + File.separator
					+ templateName + ".xls";
			Vector vtRs = new Vector();
			if(vData.size()==columns.size()){
				
				for(int i=0;i<vData.size();i++){
					ResultSet rs = ReportTDK.Report.convertVectorToResultSet(vData.get(i),
							columns.get(i));
					vtRs.add(rs);
				}
				
			}
			
			Report report = new Report(vtRs, strTemplatePath, strReportPath);
			
			for (String[] arr : parameters)
			{
				report.setParameter(arr[0], arr[1]);
			}
			
			report.fillDataToExcel(true);
			
			return strReportPath;
//			return strTemplatePath;
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
}
