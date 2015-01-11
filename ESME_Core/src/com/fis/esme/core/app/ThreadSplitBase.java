package com.fis.esme.core.app;


import java.sql.Connection;

import com.fis.esme.core.util.CommonStatistic;
import com.fss.sql.Database;
import com.fss.thread.ThreadConstant;


/**
 * <p>
 * Title: MCA GW
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * <p>
 * Company: FIS-SOT
 * </p>
 * 
 * @author LiemLT
 * @version 1.0
 */

public abstract class ThreadSplitBase implements Runnable
{

	protected Thread mthrMain;
	protected ThreadSplitManager mParentThreadManager;
	protected int miThreadIndex = 0;
	protected int miDelayTime = 0;
	protected boolean mbEnabled = true;
	protected Connection mcnMain;
	public CommonStatistic mStatCommon;

	protected boolean mbIsAutoConnectDb = false;

	public ThreadSplitBase()
	{
	}

	public void setThreadManager(ThreadSplitManager parentThreadManager)
	{
		mParentThreadManager = parentThreadManager;
	}

	protected ThreadManagerEx getThreadManager()
	{
		return mParentThreadManager.getThreadManager();
	}

	public void setIndex(int iIndex)
	{
		miThreadIndex = iIndex;
	}

	public int getIndex()
	{
		return miThreadIndex;
	}

	public void setAutoConnectDb(boolean bAutoConnectDb)
	{
		mbIsAutoConnectDb = bAutoConnectDb;
	}

	public void disable()
	{
		mbEnabled = false;
	}

	protected boolean isThreadRunning()
	{
		return mParentThreadManager.miThreadStatus == ThreadConstant.THREAD_STARTED
				&& mbEnabled && !mParentThreadManager.isDoRestartManager();
	}

	public void run()
	{
		logMonitor("Split Thread " + miThreadIndex + " started");
		while (isThreadRunning())
		{
			try
			{
				beforeSession();
				processSession();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				logMonitor("Error occured: " + ex.getMessage());
			}
			finally
			{
				try
				{
					afterSession();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					logMonitor("Error occured: " + ex.getMessage());
				}

				if (miDelayTime > 0)
				{
					// Wait some time
					try
					{
						// Delay
						for (int iIndex = 0; iIndex < miDelayTime
								&& mParentThreadManager.miThreadCommand != ThreadConstant.THREAD_STOP; iIndex++)
						{
							mthrMain.sleep(1000); // Time unit is second
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		} // end while (isThreadRunning())

		logMonitor("Split Thread " + miThreadIndex + " closed");
		closed();
	}

	private void closed()
	{
		disable();
		mParentThreadManager.remove(this);
		mParentThreadManager = null;
		mthrMain = null;
	}

	protected void beforeSession() throws Exception
	{
		if (mbIsAutoConnectDb)
		{
			openConnection();
		}
		mStatCommon = mParentThreadManager.mStatCommon;
	}

	protected void afterSession() throws Exception
	{
		if (mbIsAutoConnectDb)
		{
			closeConnection();
		}
		mStatCommon = null;
	}

	protected Connection getConnection()
	{
		return mcnMain;
	}

	protected void openConnection() throws Exception
	{
		// Make sure connection is closed
		closeConnection();
		// Connect to database
		mcnMain = mParentThreadManager.createConnection();
	}

	protected void closeConnection()
	{
		Database.closeObject(mcnMain);
		mcnMain = null;
	}

	public abstract void processSession() throws Exception;

	public void start()
	{
		if (mthrMain != null)
		{
			mthrMain.stop();
		}
		mthrMain = new Thread(this);
		mthrMain.start();
	}

	public void stop()
	{
		try
		{
			if (mthrMain != null)
			{
				mthrMain.stop();
			}
		}
		catch (Exception ex)
		{
			logMonitor("Error occurred: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void setDelayTime(int iDelayTime)
	{
		miDelayTime = iDelayTime;
	}

	public void doRestartManager()
	{
		mParentThreadManager.doRestartManager();
	}	

	public void logMonitor(String strLog)
	{
		mParentThreadManager.logMonitor("Thread " + miThreadIndex + " :"
				+ strLog);
	}

	public void debugMonitor(String strLog, int iDebugLevel)
	{
		System.out.println(""+strLog+","+iDebugLevel);
		mParentThreadManager.debugMonitor("Thread " + miThreadIndex + " :"
				+ strLog, iDebugLevel);
	}

}
