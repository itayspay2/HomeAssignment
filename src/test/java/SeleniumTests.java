import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;


public class SeleniumTests extends TestBase {
    W3SchoolPage w3SchoolPage = new W3SchoolPage(ExecutionContext.CURRENT_DRIVER.get());
    PropertiesReader companiesReader = new PropertiesReader("src/src/resources/companies.properties");
    PropertiesReader statesReader = new PropertiesReader("src/src/resources/states.properties");

    int searchColumn = 0;// Company column
    String searchText = companiesReader.getValue("company1");
    int returnColumnIndex = 2; // State column
    String expectedState = statesReader.getValue("company1state");
    String tableHeader = "Company";


    @Test
    public void ExerciseTestCheckTableValues() {
        ExecutionContext.CURRENT_EXTENT_TEST.set(
                ExecutionContext.CURRENT_EXTENT_REPORTS.get().createTest("Find Element by Complete Text Match", "test finding element by complete text match"));
        ExecutionContext.CURRENT_DRIVER.get().get("https://www.w3schools.com/html/html_tables.asp");
        ExecutionContext.CURRENT_WAIT.get().until(ExpectedConditions.visibilityOf(w3SchoolPage.table));
        boolean isTextMatching = w3SchoolPage.verifyTableCellText(w3SchoolPage.table, searchColumn, searchText, returnColumnIndex, expectedState);
        Assertions.assertTrue(isTextMatching, "Text Does Not Match");

    }

    @Test
    public void CheckAllCompaniesStateValues() {
        ExecutionContext.CURRENT_EXTENT_TEST.set(
                ExecutionContext.CURRENT_EXTENT_REPORTS.get().createTest("Check all companies", "iterate over all companies and check state values"));
        ExecutionContext.CURRENT_DRIVER.get().get("https://www.w3schools.com/html/html_tables.asp");
        ExecutionContext.CURRENT_WAIT.get().until(ExpectedConditions.visibilityOf(w3SchoolPage.table));
        Set<String> companyKeys = companiesReader.getAllKeys();
        for (String company : companyKeys) {
            String companyName = companiesReader.getValue(company);
            String expectedState = statesReader.getValue(company + "state");
            boolean isTextMatchingNoIndexes = w3SchoolPage.verifyTableCellTextNoIndexes(w3SchoolPage.table, tableHeader, companyName, expectedState);
            Assertions.assertTrue(isTextMatchingNoIndexes, "Text Does Not Match NO INDEXES");
        }
    }

    @Test
    public void CheckTableValuesOnlyXPATH() {
        ExecutionContext.CURRENT_EXTENT_TEST.set(
                ExecutionContext.CURRENT_EXTENT_REPORTS.get().createTest("Check only by xpath", "this test is using getTableCellTextByXpath"));
        ExecutionContext.CURRENT_DRIVER.get().get("https://www.w3schools.com/html/html_tables.asp");
        ExecutionContext.CURRENT_WAIT.get().until(ExpectedConditions.visibilityOf(w3SchoolPage.table));
        boolean isTextMatchingXPATH = w3SchoolPage.verifyTableCellTextXPATH(w3SchoolPage.table, searchColumn, searchText, returnColumnIndex, expectedState);
        Assertions.assertTrue(isTextMatchingXPATH, "Text Does Not Match XPATH");
    }

    @Test
    public void CheckTableWithoutUsingIndexes() {
        ExecutionContext.CURRENT_EXTENT_TEST.set(
                ExecutionContext.CURRENT_EXTENT_REPORTS.get().createTest("Find Element by Complete Text Match", "test finding element by complete text match"));
        ExecutionContext.CURRENT_DRIVER.get().get("https://www.w3schools.com/html/html_tables.asp");
        ExecutionContext.CURRENT_WAIT.get().until(ExpectedConditions.visibilityOf(w3SchoolPage.table));
        boolean isTextMatchingNoIndexes = w3SchoolPage.verifyTableCellTextNoIndexes(w3SchoolPage.table, tableHeader, searchText, expectedState);
        Assertions.assertTrue(isTextMatchingNoIndexes, "Text Does Not Match NO INDEXES");
    }


}
