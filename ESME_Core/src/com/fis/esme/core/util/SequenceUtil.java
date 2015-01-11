package com.fis.esme.core.util;

/**
 * <p>Title: MCA Core</p>
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
public class SequenceUtil {
    private static int generateCount = 0;

    public static synchronized String getUniqueString() {
        if (generateCount > 999) {
            generateCount = 0;
        }

        String uniqueNumber = Long.toString(System.currentTimeMillis()) +
                              Integer.toString(generateCount);
        generateCount++;

        return uniqueNumber;
    }

    private static int miMTSilentCount = 0;

    public static synchronized String getMTSilentUnique() {
        if (miMTSilentCount > 2099999) {
            miMTSilentCount = 0;
        }

        miMTSilentCount++;

        return String.valueOf(miMTSilentCount);
    }
}
