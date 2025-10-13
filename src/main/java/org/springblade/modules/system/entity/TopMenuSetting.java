package org.springblade.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * TopMenuSetting
 *
 * @author Chill
 */
@Data
@TableName("blade_top_menu_setting")
public class TopMenuSetting {

	/**
	 * 主键id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 顶部菜单id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long topMenuId;

	/**
	 * 菜单id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long menuId;

}
