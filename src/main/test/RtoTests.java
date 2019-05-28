import com.epam.lab.rto.controller.PersonalAreaControllerTest;
import com.epam.lab.rto.controller.RouteControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.context.annotation.Import;

@RunWith(Suite.class)
@Suite.SuiteClasses({PersonalAreaControllerTest.class, RouteControllerTest.class})
public class RtoTests {
}
