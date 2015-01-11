package com.fis.esme.core.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.fss.util.FileUtil;
import com.fss.util.WildcardFilter;

/**
 * <p>Title: MCA GW</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: FIS-SOT</p>
 *
 * @author LiemLT
 * @version 1.0
 */

public class LogOutputStream extends OutputStream {
    ////////////////////////////////////////////////////////
    // Constant
    ////////////////////////////////////////////////////////
    private int iMaxLogFileSize = 1048576;
    private int miKeepFileDay = 0;
    private int miMaxKeepFile = 0;

    ////////////////////////////////////////////////////////
    // Member variables
    ////////////////////////////////////////////////////////
    private File mflMain;
    final java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat(
            "yyyyMMddHHmmss");

    ////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////
    public LogOutputStream(String strFileName) throws IOException {
        mflMain = new File(strFileName);
        if (!mflMain.exists()) {
            mflMain.createNewFile();
        }
    }

    ////////////////////////////////////////////////////////
    public LogOutputStream(String strFileName, int iKeepDay, int iMaxKeepFile) throws
            IOException {
        mflMain = new File(strFileName);
        if (!mflMain.exists()) {
            mflMain.createNewFile();
        }

        miMaxKeepFile = iMaxKeepFile;
        miKeepFileDay = iKeepDay;
    }

    ////////////////////////////////////////////////////////
    // Override function
    ////////////////////////////////////////////////////////
    public void write(int i) {
        RandomAccessFile fl = null;
        try {
            fl = new RandomAccessFile(mflMain, "rw");
            fl.seek(fl.length());
            fl.write(i);
        } catch (Exception e) {
        } finally {
            FileUtil.safeClose(fl);
        }
    }

    ////////////////////////////////////////////////////////
    public void write(byte[] bt) {
        backup(mflMain.getAbsolutePath(), iMaxLogFileSize);

        RandomAccessFile fl = null;
        try {
            fl = new RandomAccessFile(mflMain, "rw");
            fl.seek(fl.length());
            fl.write(bt);
        } catch (Exception e) {
        } finally {
            FileUtil.safeClose(fl);
        }
    }

    ////////////////////////////////////////////////////////
    public void write(byte[] bt, int offset, int length) {
        backup(mflMain.getAbsolutePath(), iMaxLogFileSize);

        RandomAccessFile fl = null;
        try {
            fl = new RandomAccessFile(mflMain, "rw");
            fl.seek(fl.length());
            if (length > 2) {
                final java.text.SimpleDateFormat fmt = new java.text.
                        SimpleDateFormat("dd/MM HH:mm:ss");
                String strLog = fmt.format(new java.util.Date()) + " ";
                fl.write(strLog.getBytes());
            }
            fl.write(bt, offset, length);
        } catch (Exception e) {
        } finally {
            FileUtil.safeClose(fl);
        }
    }

    public void setMaxLogFileSize(int maxSize) throws Exception {
        if (maxSize <= 0) {
            throw new Exception("FileSize must greater than 0 byte");
        }
        iMaxLogFileSize = maxSize;
    }

    public int getMaxLogFileSize() {
        return iMaxLogFileSize;
    }

    public int getKeepFileDay() {
        return miKeepFileDay;
    }

    public void setKeepFileDay(int iKeepDay) {
        miKeepFileDay = iKeepDay;
    }

    ////////////////////////////////////////////////////////
    private void backup(String strFileName, int iMaxSize) {
        File flSource = new File(strFileName);
        if (flSource.length() > iMaxSize) {
            String strNewName = "";
            if (strFileName.indexOf(".") >= 0) {
                strNewName = strFileName.substring(0,
                        strFileName.lastIndexOf(".")) +
                             fmt.format(new java.util.Date()) +
                             strFileName.substring(strFileName.lastIndexOf("."));
            } else {
                strNewName = strFileName + fmt.format(new java.util.Date());
            }
            FileUtil.renameFile(strFileName, strNewName);

            if (miKeepFileDay > 0) {
                FileUtil.deleteOldFile(mflMain.getParent(), "*.log",
                                       60 * 60 * 24 * 1000 * miKeepFileDay);
            }

            if (miMaxKeepFile > 0) {
                cleanOldFile(mflMain.getParent(), "*.log", miMaxKeepFile);
            }
        }
    }

    ////////////////////////////////////////////////////////
    private void cleanOldFile(String strPath, String strWildcard,
                              int iMaxKeepFile) {
        if (!strPath.endsWith("/")) {
            strPath += "/";
        }
        File flFolder = new File(strPath);
        if (!flFolder.exists()) {
            return;
        }

        String strFileList[] = flFolder.list(new WildcardFilter(strWildcard));

        if (strFileList != null && strFileList.length > iMaxKeepFile) {
            List<File> lstFile = new ArrayList<File>();

            for (int i = 0; i < strFileList.length; i++) {
                File fl = new File(strPath + strFileList[i]);
                lstFile.add(fl);
            }

            Collections.sort(lstFile, new FileSort());
            int iFileIndexRemove = lstFile.size() - iMaxKeepFile;

            for (int i = 0; i < iFileIndexRemove; i++) {
                File fl = lstFile.get(i);
                fl.delete();
            }
        }
    }

    private class FileSort implements Comparator<File> {
        public int compare(File f1,
                           File f2) {
            long lngSub = f1.lastModified() - f2.lastModified();

            if (lngSub > 0) {
                return 1;
            } else if (lngSub == 0) {
                return 0;
            }

            return -1;
        }
    }
}
