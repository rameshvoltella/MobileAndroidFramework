package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.ExperimentTest;
import tests.Experiment2Test;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        ExperimentTest.class,
        Experiment2Test.class
})
public class TestPrimer {

}
