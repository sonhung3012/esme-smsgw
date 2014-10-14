/*    */ package com.fis.framework;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class ReadProperty
/*    */ {
/*    */   String str;
/*    */   String key;
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/*  9 */     ReadProperty r = new ReadProperty();
/*    */   }
/*    */   public ReadProperty() {
/*    */     try {
/* 13 */       int check = 0;
/* 14 */       while (check == 0) {
/* 15 */         check = 1;
/* 16 */         BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
/* 17 */         System.out.print("Enter file name which has properties extension :");
/* 18 */         this.str = bf.readLine();
/* 19 */         File f = new File(this.str + ".properties");
/* 20 */         if (f.exists()) {
/* 21 */           Properties pro = new Properties();
/* 22 */           FileInputStream in = new FileInputStream(f);
/* 23 */           pro.load(in);
/* 24 */           System.out.println("All key are given: " + pro.keySet());
/* 25 */           System.out.print("Enter Key : ");
/* 26 */           this.key = bf.readLine();
/* 27 */           String p = pro.getProperty(this.key);
/* 28 */           System.out.println(this.key + " : " + p);
/*    */         }
/*    */         else {
/* 31 */           check = 0;
/* 32 */           System.out.println("File not found!");
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (IOException e) {
/* 37 */       System.out.println(e.getMessage());
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\1 NTFS\Project\MCA\WIP\Source\McaBusiness\build\classes\
 * Qualified Name:     com.fis.framework.ReadProperty
 * JD-Core Version:    0.6.0
 */