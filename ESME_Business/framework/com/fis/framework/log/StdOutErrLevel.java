/*    */ package com.fis.framework.log;
/*    */ 
/*    */ import java.io.InvalidObjectException;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.util.logging.Level;
/*    */ 
/*    */ public class StdOutErrLevel extends Level
/*    */ {
/* 26 */   public static Level STDOUT = new StdOutErrLevel("STDOUT", Level.INFO.intValue() + 53);
/*    */ 
/* 30 */   public static Level STDERR = new StdOutErrLevel("STDERR", Level.INFO.intValue() + 54);
/*    */ 
/*    */   private StdOutErrLevel(String name, int value)
/*    */   {
/* 21 */     super(name, value);
/*    */   }
/*    */ 
/*    */   protected Object readResolve()
/*    */     throws ObjectStreamException
/*    */   {
/* 41 */     if (intValue() == STDOUT.intValue())
/* 42 */       return STDOUT;
/* 43 */     if (intValue() == STDERR.intValue())
/* 44 */       return STDERR;
/* 45 */     throw new InvalidObjectException("Unknown instance :" + this);
/*    */   }
/*    */ }

/* Location:           D:\1 NTFS\Project\MCA\WIP\Source\McaBusiness\build\classes\
 * Qualified Name:     com.fis.framework.log.StdOutErrLevel
 * JD-Core Version:    0.6.0
 */