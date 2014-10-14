/*    */ package com.fis.framework.log;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ class LoggingOutputStream extends ByteArrayOutputStream
/*    */ {
/*    */   private String lineSeparator;
/*    */   private Logger logger;
/*    */   private Level level;
/*    */ 
/*    */   public LoggingOutputStream(Logger logger, Level level)
/*    */   {
/* 25 */     this.logger = logger;
/* 26 */     this.level = level;
/* 27 */     this.lineSeparator = System.getProperty("line.separator");
/*    */   }
/*    */ 
/*    */   public void flush()
/*    */     throws IOException
/*    */   {
/* 38 */     synchronized (this) {
/* 39 */       super.flush();
/* 40 */       String record = toString();
/* 41 */       super.reset();
/*    */     }
/*    */     String record = null;
/* 44 */     if ((record.length() == 0) || (record.equals(this.lineSeparator)))
/*    */     {
/* 46 */       return;
/*    */     }
/*    */ 
/* 49 */     this.logger.logp(this.level, "", "", record);
/*    */   }
/*    */ }

/* Location:           D:\1 NTFS\Project\MCA\WIP\Source\McaBusiness\build\classes\
 * Qualified Name:     com.fis.framework.log.LoggingOutputStream
 * JD-Core Version:    0.6.0
 */