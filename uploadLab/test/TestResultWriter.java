import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class TestResultWriter {
    public static void writeTestResults(String[] actions, String[] data, String[] results, String[] notes) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test Results");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Action");
        header.createCell(1).setCellValue("Data");
        header.createCell(2).setCellValue("Result");
        header.createCell(3).setCellValue("Note");

        for (int i = 0; i < actions.length; i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(actions[i]);
            row.createCell(1).setCellValue(data[i]);
            row.createCell(2).setCellValue(results[i]);
            row.createCell(3).setCellValue(notes[i]);
        }

        try ( FileOutputStream fileOut = new FileOutputStream("TestResults.xlsx")) {
            workbook.write(fileOut);
            System.out.println("Results written to Excel file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
    }
}
