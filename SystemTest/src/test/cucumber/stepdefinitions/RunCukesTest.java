
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import gherkin.ast.Feature;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:SystemTest/target/cucumber", "json:SystemTest/target/cucumber.json"})
public class RunCukesTest {
}