import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class W3SchoolPage {

    public W3SchoolPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    WebDriver driver;

    @FindBy(id = "customers")
    WebElement table;


    public String getCellTextByXpath(WebElement table, int searchColumn, String searchText, int returnColumnIndex) {
        String cellXPath = String.format("//tbody/tr[td[%d][text()='%s']]/td[%d]", searchColumn + 1, searchText, returnColumnIndex + 1);
        return table.findElement(By.xpath(cellXPath)).getText();
    }

    public String getTableCellText(WebElement table, int searchColumn, String searchText, int returnColumnIndex) {
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() > searchColumn && cells.get(searchColumn).getText().equals(searchText)) {
                if (returnColumnIndex >= 0 && returnColumnIndex < cells.size()) {
                    return cells.get(returnColumnIndex).getText();
                } else {
                    return "Invalid return column index.";
                }
            }
        }

        return "Text not found in the specified column.";
    }

    public String getTableCellText(WebElement table, String searchColumnName, String searchText) {
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        if (!rows.isEmpty()) {
            WebElement headerRow = rows.get(0);
            List<WebElement> headerCells = headerRow.findElements(By.tagName("th"));
            int searchColumn = -1;

            for (int i = 0; i < headerCells.size(); i++) {
                if (headerCells.get(i).getText().equals(searchColumnName)) {
                    searchColumn = i;
                    break;
                }
            }

            if (searchColumn != -1) {
                for (int i = 1; i < rows.size(); i++) {
                    WebElement row = rows.get(i);
                    List<WebElement> cells = row.findElements(By.tagName("td"));

                    if (cells.size() > searchColumn && cells.get(searchColumn).getText().equals(searchText)) {
                        return cells.get(2).getText(); // Return state cell text
                    }
                }
            }
        }

        return "Text not found in the specified column.";
    }

    public boolean verifyTableCellText(WebElement table, int searchColumn, String searchText, int returnColumnText, String expectedText) {
        String actualText = getTableCellText(table, searchColumn, searchText, returnColumnText);
        ExecutionContext.CURRENT_EXTENT_TEST.get().info("Actual Text: " + actualText + " || Expected Text: " + expectedText);
        System.out.println("Actual Text: " + actualText + " || Expected Text: " + expectedText);
        return actualText.equals(expectedText);
    }

    public boolean verifyTableCellTextXPATH(WebElement table, int searchColumn, String searchText, int returnColumnText, String expectedText) {
        String actualText = getCellTextByXpath(table, searchColumn, searchText, returnColumnText);
        ExecutionContext.CURRENT_EXTENT_TEST.get().info("Actual Text: " + actualText + " || Expected Text: " + expectedText);
        System.out.println("Actual Text: " + actualText + " || Expected Text: " + expectedText);
        return actualText.equals(expectedText);
    }

    public boolean verifyTableCellTextNoIndexes(WebElement table, String searchText, String returnColumnText, String expectedText) {
        String actualText = getTableCellText(table, searchText, returnColumnText);
        ExecutionContext.CURRENT_EXTENT_TEST.get().info("Actual Text: " + actualText + " || Expected Text: " + expectedText);
        System.out.println("Actual Text: " + actualText + " || Expected Text: " + expectedText);
        return actualText.equals(expectedText);
    }
}
