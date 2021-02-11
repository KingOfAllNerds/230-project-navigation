import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NavMap {
    private HashMap<String,double[]> locations = new HashMap<String,double[]>();

    public NavMap() {
        String filePath = System.getProperty("user.dir") + File.separator + "NavigationSystem\\resources\\Locations.xlsx";
        readFile(filePath);
    }

    // must be in NavigationSystem folder to be found
    public NavMap(String fileName) {
        String filePath = System.getProperty("user.dir") + File.separator + "NavigationSystem\\resources\\" + fileName;
        readFile(filePath);
    }

    //Excel file needs three columns, 
    //the first column with the name in it, 
    //second column with the latitude
    //third column with the longitude
    //default sheet name
    private void readFile(String locPath) {
        try {
            File file = new File(locPath);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheet("Sheet1");
            for(Row row : sheet) {
                String locName = null;
                double[] coords = new double[2];
                for(Cell cell : row) {
                    if(cell.getColumnIndex() == 0) locName = cell.getStringCellValue();
                    else if(cell.getColumnIndex() == 1) coords[0] = cell.getNumericCellValue();
                    else if(cell.getColumnIndex() == 2) coords[1] = cell.getNumericCellValue();
                }
                locations.put(locName,coords);
            }
            System.out.println(locations.keySet());
            fis.close();
        }
        catch(Exception e) {
            System.out.println("error occured: "+e.toString());
        }
    }
}