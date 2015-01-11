package com.fis.esme.core.exception;

import com.fss.util.AppException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CommandFailureException extends AppException
{
        ////////////////////////////////////////////////////////
        /**
         * @param strReason cause of exception
         * @author Thai Hoang Hiep - Date: 13/05/2003
         */
        ////////////////////////////////////////////////////////
        public CommandFailureException(String strReason)
        {
                super(strReason);
        }
        ////////////////////////////////////////////////////////
        /**
         * @param strReason cause of exception
         * @param strContext exception context
         * @author Thai Hoang Hiep - Date: 13/05/2003
         */
        ////////////////////////////////////////////////////////
        public CommandFailureException(String strReason,String strContext)
        {
                super(strReason,strContext);
        }
        ////////////////////////////////////////////////////////
        /**
         * @param strReason cause of exception
         * @param strContext exception context
         * @param strInfo additional information
         * @author Thai Hoang Hiep - Date: 13/05/2003
         */
        ////////////////////////////////////////////////////////
        public CommandFailureException(String strReason,String strContext,String strInfo)
        {
                super(strReason,strContext,strInfo);
        }
        ////////////////////////////////////////////////////////
        /**
         * @param e cause of exception
         * @param strContext exception context
         * @author Thai Hoang Hiep - Date: 13/05/2003
         */
        ////////////////////////////////////////////////////////
        public CommandFailureException(Exception e,String strContext)
        {
                super(e,strContext);
        }
        ////////////////////////////////////////////////////////
        /**
         * @param e cause of exception
         * @param strContext exception context
         * @param strInfo additional information
         * @author Thai Hoang Hiep - Date: 13/05/2003
         */
        ////////////////////////////////////////////////////////
        public CommandFailureException(Exception e,String strContext,String strInfo)
        {
                super(e,strContext,strInfo);
        }
}
