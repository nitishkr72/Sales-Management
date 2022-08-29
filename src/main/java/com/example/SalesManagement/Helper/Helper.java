package com.example.SalesManagement.Helper;

import com.example.SalesManagement.Model.ProductSold;
import com.example.SalesManagement.Model.dummyData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Helper {

    public static boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }
    }

    public static List<dummyData> convertExcelToListOfProduct(InputStream is) {
        List<dummyData> list = new ArrayList<>();

        DataFormatter formatter = new DataFormatter();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);

            XSSFSheet sheet = workbook.getSheet("data");

            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row row = iterator.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cells = row.iterator();

                int cid = 0;

                dummyData p = new dummyData();

                while (cells.hasNext()) {
                    Cell cell = cells.next();

                    switch (cid) {
//                        case 0:
//                            p.setProductId((int) cell.getNumericCellValue());
//                            break;
                        case 1:
                            p.setProductId(formatter.formatCellValue(cell));
                            break;
                        case 2:
                            p.setEmpId(formatter.formatCellValue(cell));
                            break;
                        case 3:
                            p.setTypeId(formatter.formatCellValue(cell));
                            break;
                        case 4:
                            p.setDateSold(formatter.formatCellValue(cell));
                            break;
                        case 5:
                            p.setCost((long) cell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }
                    cid++;

                }

                list.add(p);


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }
}
