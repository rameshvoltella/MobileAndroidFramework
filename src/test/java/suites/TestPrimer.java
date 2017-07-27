package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.Experiment;
import tests.Experiment2;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        Experiment.class,
        Experiment2.class
})
public class TestPrimer {
}
