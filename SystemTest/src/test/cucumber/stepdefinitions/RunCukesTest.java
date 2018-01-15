
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import gherkin.ast.Feature;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"})
public class RunCukesTest {
}