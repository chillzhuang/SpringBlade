package org.springblade.system.user.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springblade.system.user.service.IUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * UserImportListener
 *
 * @author Chill
 */
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserImportListener extends AnalysisEventListener<UserExcel> {

	/**
	 * 默认每隔3000条存储数据库
	 */
	private int batchCount = 3000;
	/**
	 * 缓存的数据列表
	 */
	private List<UserExcel> list = new ArrayList<>();
	/**
	 * 用户service
	 */
	private final IUserService userService;

	@Override
	public void invoke(UserExcel data, AnalysisContext context) {
		list.add(data);
		// 达到BATCH_COUNT，则调用importer方法入库，防止数据几万条数据在内存，容易OOM
		if (list.size() >= batchCount) {
			// 调用importer方法
			userService.importUser(list);
			// 存储完成清理list
			list.clear();
		}
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {
		// 调用importer方法
		userService.importUser(list);
		// 存储完成清理list
		list.clear();
	}

}
