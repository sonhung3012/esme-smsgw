/*    */ package com.fis.framework.log;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.logging.FileHandler;
/*    */ import java.util.logging.Handler;
/*    */ import java.util.logging.LogManager;
/*    */ import java.util.logging.Logger;
/*    */ import java.util.logging.SimpleFormatter;
/*    */ 
/*    */ public class Main
/*    */ {
/*    */   public static void main(String[] args)
/*    */     throws Exception
/*    */   {
/* 18 */     String log = "E:\\Project/SOT-FPT.VSMS/WIP/Source/FPT.Framework/VSMS/log1";
/*    */ 
/* 20 */     LogManager logManager = LogManager.getLogManager();
/* 21 */     logManager.reset();
/*    */ 
/* 24 */     Handler fileHandler = new FileHandler(log, 10, 3, true);
/* 25 */     fileHandler.setFormatter(new SimpleFormatter());
/* 26 */     Logger.getLogger("").addHandler(fileHandler);
/*    */ 
/* 29 */     PrintStream stdout = System.out;
/* 30 */     PrintStream stderr = System.err;
/*    */ 
/* 36 */     Logger logger = Logger.getLogger("stdout");
/* 37 */     LoggingOutputStream los = new LoggingOutputStream(logger, StdOutErrLevel.STDOUT);
/* 38 */     System.setOut(new PrintStream(los, true));
/*    */ 
/* 40 */     logger = Logger.getLogger("stderr");
/* 41 */     los = new LoggingOutputStream(logger, StdOutErrLevel.STDERR);
/* 42 */     System.setErr(new PrintStream(los, true));
/* 43 */     int i = 0;
/* 44 */     while (i < 100) {
/* 45 */       i++;
/*    */ 
/* 47 */       System.out.println("Hello world!");
/*    */ 
/* 50 */       logger = Logger.getLogger("test");
/* 51 */       logger.info("This is a test log message");
/* 52 */       String s = null;
/*    */       try
/*    */       {
/* 55 */         s.trim();
/*    */       } catch (Exception e) {
/* 57 */         e.printStackTrace();
/*    */       }
/*    */ 
/* 61 */       System.out.println("Hello on old stdout");
/*    */ 
/* 63 */       System.out.println("good bye Hello world!");
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\1 NTFS\Project\MCA\WIP\Source\McaBusiness\build\classes\
 * Qualified Name:     com.fis.framework.log.Main
 * JD-Core Version:    0.6.0
 */