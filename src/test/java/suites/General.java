package suites;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.TestSet_02_Pin_TouchID;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestSet_02_Pin_TouchID.class,
})
public class General {
}
