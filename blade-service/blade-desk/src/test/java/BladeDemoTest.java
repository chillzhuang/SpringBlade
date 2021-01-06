import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springblade.core.test.BladeBootTest;
import org.springblade.core.test.BladeSpringExtension;
import org.springblade.desk.DeskApplication;
import org.springblade.desk.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Blade单元测试
 *
 * @author Chill
 */
@ExtendWith(BladeSpringExtension.class)
@SpringBootTest(classes = DeskApplication.class)
@BladeBootTest(appName = "blade-desk", profile = "test", enableLoader = true)
public class BladeDemoTest {

	@Autowired
	private INoticeService noticeService;

	@Test
	public void contextLoads() {
		int count = noticeService.count();
		System.out.println("notice数量：[" + count + "] 个");
	}

}
