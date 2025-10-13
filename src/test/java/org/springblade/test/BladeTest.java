package org.springblade.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springblade.core.test.BladeBootTest;
import org.springblade.core.test.BladeSpringExtension;
import org.springblade.modules.desk.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Blade单元测试
 *
 * @author Chill
 */
@ExtendWith(BladeSpringExtension.class)
@BladeBootTest(appName = "blade-runner", profile = "test")
public class BladeTest {

	@Autowired
	private INoticeService noticeService;

	@Test
	public void contextLoads() {
		Long count = noticeService.count();
		System.out.println("notice数量：[" + count + "] 个");
	}

}
