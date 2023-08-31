import org.testng.TestNG;
import org.testng.collections.Lists;

public class TestRunner {
    public static void main(String[] args) {
        TestNG testng = new TestNG();
        testng.setTestSuites(Lists.newArrayList("testng.xml"));
        testng.run();
    }
}
