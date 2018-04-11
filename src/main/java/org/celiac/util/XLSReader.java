package org.celiac.util;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.celiac.datatype.CodeDesc;
import org.celiac.datatype.CompanyTable;
import org.celiac.datatype.GFUser;

public class XLSReader {

    public static String inputFile = PropertiesLoader.getProperties("data.food");
    public static String usersInputFile = PropertiesLoader.getProperties("data.users");
    public static String codeDescInputFile = PropertiesLoader.getProperties("code.desc.users");

    public Map<String, CompanyTable> readWorkBook(InputStream stream) throws Exception {
        POIFSFileSystem fs = new POIFSFileSystem(stream);
        return readWorkBook(fs);
    }

    public Map<String, CompanyTable> readWorkBook() throws Exception {
        POIFSFileSystem fs = new POIFSFileSystem(this.getClass().getResourceAsStream(inputFile));
        return readWorkBook(fs);
    }

    public Map<String, CompanyTable> readWorkBook(POIFSFileSystem fs) throws Exception {
        Map<String, HSSFSheet> sheets = new HashMap<String, HSSFSheet>();
        Map<String, CompanyTable> sheetsFullData = new HashMap<String, CompanyTable>();
        try {
            HSSFWorkbook wb = new HSSFWorkbook(fs);

            // get a reference to all the worksheets
            Logger.print("Getting all sheets:");
            int i = 0;
            HSSFSheet sheet = null;
            String sheetName = null;
            while (true) {
                try {
                    sheet = wb.getSheetAt(i++);
                    sheetName = wb.getSheetName(i - 1);
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
                Logger.print("Found sheet: '" + sheetName + "'");
                // Capital letters
                sheetName = sheetName.toUpperCase();
                sheets.put(sheetName, sheet);
            }

            // Go sheet by sheet and get all colums and thier data
            Logger.print("");

            Iterator<String> it = sheets.keySet().iterator();

            HSSFRow row = null;
            String[] CATAGORY = null;
            String[] PRODUCT = null;
            String[] GLUTEN_FREE = null;

            // Select each sheet and get the data
            while (it.hasNext()) {

                sheetName = it.next().toUpperCase();
                if ("USERS".equals(sheetName)) {
                    throw new Exception(
                            "Found an error in input file. You are trying to update the users file while you should update the merchandise file");
                }
                Logger.print("Collecting data of sheet '" + sheetName + "'");

                if (sheetName.indexOf(".") > -1) {
                    throw new Exception(
                            "Found an error in input file for sheet '" + sheetName + "'. Sheet name can't contain the character '.' .");
                }

                sheet = sheets.get(sheetName);
                CATAGORY = new String[sheet.getLastRowNum()];
                PRODUCT = new String[sheet.getLastRowNum()];
                GLUTEN_FREE = new String[sheet.getLastRowNum()];

                for (int j = 0; j < sheet.getLastRowNum(); j++) {
                    row = sheet.getRow(j + 1);
                    try {
                        CATAGORY[j] = new String(row.getCell((short) 0).getRichStringCellValue().getString());
                        PRODUCT[j] = new String(row.getCell((short) 1).getRichStringCellValue().getString());
                        GLUTEN_FREE[j] = new String(row.getCell((short) 2).getRichStringCellValue().getString());
                    } catch (Exception e) {
                        Logger.info("####### Suspect for error " + row.getRowNum() + " In sheet:" + sheetName);
                        CATAGORY[j] = "";
                        PRODUCT[j] = "";
                        GLUTEN_FREE[j] = "";
                    }
                    if (!((CATAGORY[j].trim().equals("") && PRODUCT[j].trim().equals("") && GLUTEN_FREE[j].trim().equals("")) || (!CATAGORY[j]
                            .trim().equals("")
                            && !PRODUCT[j].trim().equals("") && !GLUTEN_FREE[j].trim().equals("")))) {
                        int theProblematicRow = j + 1;
                        Logger.print("Found an error in input file. Problem with row " + theProblematicRow + " on sheet "
                                + sheetName);
                        Logger.print("Program Exit. No changes were made to DB.");
                        throw new Exception("Found an error in input file. Problem with row " + theProblematicRow + " on sheet "
                                + sheetName);

                    }

                    if (CATAGORY[j].trim().length() > 99 || PRODUCT[j].trim().length() > 99 || GLUTEN_FREE[j].trim().length() > 99) {
                        int theProblematicRow = j + 1;
                        Logger.print("Found an error in input file. Row is longer then 100 characters. Problem with row "
                                + theProblematicRow + " on sheet " + sheetName);
                        Logger.print("Program Exit. No changes were made to DB.");
                        throw new Exception("Found an error in input file. Row is longer then 100 characters. Problem with row "
                                + theProblematicRow + " on sheet " + sheetName);

                    }

                    if (CATAGORY[j].trim().indexOf(".") > -1 || PRODUCT[j].trim().indexOf(".") > -1 || GLUTEN_FREE[j].trim().indexOf(".") > -1) {
                        int theProblematicRow = j + 1;
                        Logger.print("Found an error in input file. Row can't contain the character '.' . Problem with row "
                                + theProblematicRow + " on sheet " + sheetName);
                        Logger.print("Program Exit. No changes were made to DB.");
                        throw new Exception("Found an error in input file. Row can't contain the character '.' . Problem with row "
                                + theProblematicRow + " on sheet " + sheetName);

                    }

                    // Remove all dashes
                    CATAGORY[j] = CATAGORY[j].replaceAll(TemplateReader.getHebrewMapWord("DASHE"), "");
                    PRODUCT[j] = PRODUCT[j].replaceAll(TemplateReader.getHebrewMapWord("DASHE"), "");

                    // check validity of column GLUTEN_FREE
                    // To Do
                    CATAGORY[j] = CATAGORY[j].toUpperCase();
                    PRODUCT[j] = PRODUCT[j].toUpperCase();
                    GLUTEN_FREE[j] = GLUTEN_FREE[j].toUpperCase();

                }

                CompanyTable companyTable = new CompanyTable();
                companyTable.setCATAGORY(CATAGORY);
                companyTable.setPRODUCT(PRODUCT);
                companyTable.setGLUTEN_FREE(GLUTEN_FREE);

                // Remove all dashes from sheet names
                sheetName = sheetName.replaceAll(TemplateReader.getHebrewMapWord("DASHE"), "");

                sheetsFullData.put(sheetName, companyTable);

            }

        } catch (Exception e) {
            Logger.print("!!BANG!! xlCreate() : " + e);
            e.printStackTrace();
            throw e;
        }

        return sheetsFullData;
    }

    public GFUser[] readUsersWorkBook(InputStream stream) throws Exception {
        try {
           
                Workbook wb = WorkbookFactory.create(stream);
                return readUsersWorkBook(wb);
                
                
              
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public GFUser[] readUsersWorkBook() throws Exception {
       
        Workbook wb = WorkbookFactory.create(this.getClass().getResourceAsStream(usersInputFile));
        return readUsersWorkBook(wb);

    }

    
    //public GFUser[] readUsersWorkBook(OPCPackage xs, POIFSFileSystem fs) throws Exception {
    public GFUser[] readUsersWorkBook(Workbook wb) throws Exception {
        GFUser[] usersTable = null;
        Sheet sheet;
        String sheetName;
        try {
          
                sheet = wb.getSheetAt(0);
                sheetName = wb.getSheetName(0).trim();
            
            if (!"users".equals(sheetName)) {
                throw new Exception("Found an error in input file. The sheet you are trying to load is not named 'users'");
            }

            Row row = null;

           
            Logger.print("Collecting data of sheet 'USERS'");

            GFUser[] adminUsers = getAdminUser();
            int numOfAdminUsers = adminUsers.length;

            usersTable = new GFUser[sheet.getLastRowNum() + numOfAdminUsers];

            int howManyRows = 0;
            for (int j = 0; j < sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j + 1);
                if (row.getCell((short) 0) == null) {
                    break;
                }
                howManyRows++;
            }

            int j = 0;
            System.out.println(howManyRows);
            String tmpDate = null;
            Date tmpExpirationDate = null;
            for (; j < howManyRows; j++) {
                //if (j == 20) break;
                row = sheet.getRow(j + 1);
                usersTable[j] = new GFUser();
                try {
                    try {
                        row.getCell((short) 0).setCellType(CellType.STRING);
                        usersTable[j].setCELIAC_MEMBER_ID(row.getCell((short) 0).getRichStringCellValue().getString());
                        //System.out.println("Before '" + row.getCell((short) 0).getRichStringCellValue().getString() + "'");
                        /*
                        String tmp = "" + row.getCell((short) 0).getNumericCellValue();
                        
                        if (tmp.indexOf(".") > 0) {
                            tmp = tmp.substring(0, tmp.indexOf("."));
                        }
                        
                        usersTable[j].setCELIAC_MEMBER_ID(tmp);
                        */
                    } catch (Exception e) {
                        
                        usersTable[j].setCELIAC_MEMBER_ID(row.getCell((short) 0).getRichStringCellValue().getString());
                    }
                    try {
                        row.getCell((short) 1).setCellType(CellType.STRING); 
                        usersTable[j].setUSER_TZ(row.getCell((short) 1).getRichStringCellValue().getString());
                        System.out.println("Before '" + row.getCell((short) 1).getRichStringCellValue().getString() + "'");
                    } catch (Exception e) {
                        
                        String tmp = "" + row.getCell((short) 1).getNumericCellValue();
                        
                        if (tmp.indexOf(".") > 0) {
                            tmp = tmp.substring(tmp.indexOf("."));
                            
                        }
                       
                        usersTable[j].setUSER_TZ(tmp);
                       
                    }
                    //Get the date as String
                    tmpDate = row.getCell((short) 2).getRichStringCellValue().getString();
                    tmpExpirationDate=new SimpleDateFormat("dd-MM-yy").parse(tmpDate);  
                    usersTable[j].setEXPIRATION_DATE(tmpExpirationDate);
                    //usersTable[j].setEXPIRATION_DATE(row.getCell((short) 2).getDateCellValue());
                    //usersTable[j].setDID_BUY_THE_BOOK(new String("" + row.getCell((short) 3).getRichStringCellValue().getString()));
                    usersTable[j].setDID_BUY_THE_BOOK("TRUE");
                } catch (Exception e) {
                    Logger.info("####### Suspect for error in user file " + row.getRowNum());
                    e.printStackTrace();
                    throw new Exception("Found an error in input file. Problem with row " + row.getRowNum());

                }

            }

            System.out.println(j);

            for (int i = 0; i < adminUsers.length; i++) {
                usersTable[j++] = adminUsers[i];
            }

        } catch (Exception e) {
            Logger.print("!!BANG!! xlCreate() : " + e);
            e.printStackTrace();
            throw e;
        }

        return usersTable;
    }

    public CodeDesc[] readCodeDescWorkBook(InputStream stream) throws Exception {
        POIFSFileSystem fs = new POIFSFileSystem(stream);
        return readCodeDescWorkBook(fs);
    }

    public CodeDesc[] readCodeDescWorkBook() throws Exception {
        POIFSFileSystem fs = new POIFSFileSystem(this.getClass().getResourceAsStream(codeDescInputFile));
        return readCodeDescWorkBook(fs);
    }

    public CodeDesc[] readCodeDescWorkBook(POIFSFileSystem fs) throws Exception {

        CodeDesc[] codeDescs = null;

        try {

            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            String sheetName = wb.getSheetName(0).trim();
            if (!"CODE_DESC".equals(sheetName)) {
                throw new Exception("Found an error in input file. The sheet you are trying to load is not named 'CODE_DESC'");
            }

            HSSFRow row = null;

            // Select each sheet and get the data
            //boolean foundEmptyLine = false;
            //foundEmptyLine = false;
            Logger.print("Collecting data of sheet 'CODE_DESC'");

            codeDescs = new CodeDesc[sheet.getLastRowNum()];

            int howManyRows = 0;
            for (int j = 0; j < sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j + 1);
                if (row.getCell((short) 0) == null) {
                    break;
                }
                howManyRows++;
            }

            int j = 0;
            for (; j < howManyRows; j++) {
                row = sheet.getRow(j + 1);
                codeDescs[j] = new CodeDesc();
                try {
                    codeDescs[j].setCODE(new String(row.getCell((short) 0).getRichStringCellValue().getString()));
                    codeDescs[j].setDESC(new String(row.getCell((short) 1).getRichStringCellValue().getString()));

                } catch (Exception e) {
                    Logger.info("####### Suspect for error in code desc file " + row.getRowNum());
                    e.printStackTrace();
                    throw new Exception("Found an error in input file. Problem with row " + row.getRowNum());

                }

            }

        } catch (Exception e) {
            Logger.print("!!BANG!! xlCreate() : " + e);
            e.printStackTrace();
            throw e;
        }

        return codeDescs;
    }

    private GFUser[] getAdminUser() throws Exception {

        GFUser[] gfAdminUser = new GFUser[3];

        Date future = getDateFromString("12/12/9999");
        // future.setYear(3000);
        // Calendar cl = new Calendar();

        gfAdminUser[0] = new GFUser();
        gfAdminUser[0].setCELIAC_MEMBER_ID("rakefet");
        gfAdminUser[0].setUSER_TZ("rakefet");
        gfAdminUser[0].setDID_BUY_THE_BOOK("true");
        gfAdminUser[0].setEXPIRATION_DATE(future);

        gfAdminUser[1] = new GFUser();
        gfAdminUser[1].setCELIAC_MEMBER_ID("tal");
        gfAdminUser[1].setUSER_TZ("tal");
        gfAdminUser[1].setDID_BUY_THE_BOOK("true");
        gfAdminUser[1].setEXPIRATION_DATE(future);

        gfAdminUser[2] = new GFUser();
        gfAdminUser[2].setCELIAC_MEMBER_ID("upload_user_account");
        gfAdminUser[2].setUSER_TZ("upload_user_account");
        gfAdminUser[2].setDID_BUY_THE_BOOK("true");
        gfAdminUser[2].setEXPIRATION_DATE(future);

        return gfAdminUser;

    }

    public Date getDateFromString(String from) throws Exception {

        if ((from == null) || from.trim().equals("")) {
            return null;
        }

        int startDay = new Integer(from.trim().substring(0, from.trim().indexOf("/"))).intValue();
        int startMonth = new Integer(from.trim().substring(from.trim().indexOf("/") + 1, from.trim().lastIndexOf("/"))).intValue() - 1;
        int startYear = new Integer(from.trim().substring(from.trim().lastIndexOf("/") + 1, from.trim().length())).intValue();
        GregorianCalendar startCal = new GregorianCalendar(startYear, startMonth, startDay);
        return startCal.getTime();

    }
}
