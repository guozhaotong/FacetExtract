package method;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import model.AllHyponymy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 郭朝彤
 * @date 2017/4/19.
 */
public class GetHyponymy {
    public static void main(String[] args) {

    }

    public static AllHyponymy GetHyponymyFromExl(String exlFilePath) {
        Workbook wb;
        ArrayList<String> upLocation = new ArrayList<>();
        ArrayList<String> dnLocation = new ArrayList<>();
        try {
            wb = Workbook.getWorkbook(new File(exlFilePath));
            Sheet sheet = wb.getSheet(0); //get sheet(0)
            for (int i = 1; i < sheet.getRows(); i++) {
                dnLocation.add(sheet.getCell(0, i).getContents());
                upLocation.add(sheet.getCell(1, i).getContents());
            }
        } catch (BiffException | IOException e) {
            e.printStackTrace();
        }
        AllHyponymy allHyponymy = new AllHyponymy(upLocation, dnLocation);
        return allHyponymy;
    }
}
