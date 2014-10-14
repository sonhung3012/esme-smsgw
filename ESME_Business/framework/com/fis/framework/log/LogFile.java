/*    */ package com.fis.framework.log;
/*    */ 
/*    */ import com.fis.framework.util.Config;
/*    */ import java.io.PrintStream;
/*    */ import java.util.logging.FileHandler;
/*    */ import java.util.logging.Handler;
/*    */ import java.util.logging.LogManager;
/*    */ import java.util.logging.Logger;
/*    */ import java.util.logging.SimpleFormatter;
/*    */ 
/*    */ public class LogFile
/*    */ {
/*    */   public static void init()
/*    */     throws Exception
/*    */   {
/* 14 */     Config.getString("name");
/*    */ 
/* 16 */     String log = "E:\\Project/SOT-FPT.VSMS/WIP/Source/FPT.Framework/VSMS/log1";
/*    */ 
/* 18 */     LogManager logManager = LogManager.getLogManager();
/* 19 */     logManager.reset();
/*    */ 
/* 22 */     Handler fileHandler = new FileHandler(log, 10000, 3, true);
/* 23 */     fileHandler.setFormatter(new SimpleFormatter());
/* 24 */     Logger.getLogger("").addHandler(fileHandler);
/*    */ 
/* 28 */     PrintStream stdout = System.out;
/* 29 */     PrintStream stderr = System.err;
/*    */ 
/* 35 */     Logger logger = Logger.getLogger("stdout");
/* 36 */     LoggingOutputStream los = new LoggingOutputStream(logger, StdOutErrLevel.STDOUT);
/* 37 */     System.setOut(new PrintStream(los, true));
/*    */ 
/* 39 */     logger = Logger.getLogger("stderr");
/* 40 */     los = new LoggingOutputStream(logger, StdOutErrLevel.STDERR);
/* 41 */     System.setErr(new PrintStream(los, true));
/*    */ 
/* 44 */     System.out.println("Hello world!");
/*    */ 
/* 47 */     logger = Logger.getLogger("test");
/* 48 */     logger.info("This is a test log message");
/* 49 */     String s = null;
/*    */     try
/*    */     {
/* 52 */       s.trim();
/*    */     } catch (Exception e) {
/* 54 */       e.printStackTrace();
/*    */     }
/*    */ 
/* 58 */     stdout.println("Hello on old stdout");
/* 59 */     System.out.println("good bye Hello world!");
/*    */   }
/*    */ }

/* Location:           D:\1 NTFS\Project\MCA\WIP\Source\McaBusiness\build\classes\
 * Qualified Name:     com.fis.framework.log.LogFile
 * JD-Core Version:    0.6.0
 */