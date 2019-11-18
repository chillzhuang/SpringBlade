package com.example.demo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.example.demo.entity.Notice;

/**
 * 通知公告视图类
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticeVO extends Notice {

	@ApiModelProperty(value = "通知类型名")
	private String categoryName;

}
