package org.celiac.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
   @Override
   public String format(LogRecord record) {
      StringBuilder s = new StringBuilder();

      Logger.date.setTime(record.getMillis());
      Logger.args[0] = Logger.date;
      StringBuffer text = new StringBuffer();
      Logger.formatter.format(Logger.args, text, null);
      s.append(text);
      s.append(" ");
      s.append(record.getLevel());
      s.append(" [");

      StackTraceElement[] elements = Thread.currentThread().getStackTrace();
      StackTraceElement element = elements[9];
      s.append(element.getClassName());
      s.append(".");
      s.append(element.getMethodName());
      s.append("] ");

      s.append(record.getMessage());
      s.append("\n");

      if (record.getThrown() != null) {
         try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            record.getThrown().printStackTrace(pw);
            pw.close();
            s.append(sw.toString());
         } catch (Exception ex) {
        	 ex.printStackTrace();
         }
      }

      return s.toString();
   }
}