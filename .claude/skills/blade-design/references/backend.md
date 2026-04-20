# 后端代码生成参考

本文档包含 SpringBlade 开源版后端各层的完整代码模板。所有模板均来源于 SpringBlade 开源工程真实业务代码（参考 `modules/system`、`modules/desk` 及 Cloud 的 `blade-service/blade-system` 等）。

## 目录
- [一、Entity 实体类](#一entity-实体类)
- [二、DTO 数据传输对象](#二dto-数据传输对象)
- [三、VO 视图对象](#三vo-视图对象)
- [四、Excel 导出类](#四excel-导出类)
- [五、Mapper 接口](#五mapper-接口)
- [六、Mapper XML](#六mapper-xml)
- [七、Service 接口](#七service-接口)
- [八、ServiceImpl 实现](#八serviceimpl-实现)
- [九、Controller 控制器](#九controller-控制器)
- [十、Wrapper 数据转换器](#十wrapper-数据转换器)
- [十一、Feign Client 接口（Cloud）](#十一feign-client-接口cloud)
- [十二、Feign Client 实现（Cloud）](#十二feign-client-实现cloud)

---

## 一、Entity 实体类

> 开源版 Entity 命名**不带 `Entity` 后缀**（如 `User.java`、`Post.java`、`Notice.java`）。

### 模式A：TenantEntity（多租户业务表，默认）

```java
package org.springblade.modules.{module}.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.TenantEntity;
import org.springblade.core.tool.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * {中文名}实体类
 *
 * @author {author}
 */
@Data
@TableName("blade_{table_name}")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "{中文名}")
public class {Name} extends TenantEntity {

    /**
     * {字段注释}
     */
    @Schema(description = "{字段注释}")
    private {Type} {fieldName};

    // --- 字段类型注解模式 ---

    // Long 类型字段（防止前端精度丢失）
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "关联ID")
    private Long refId;

    // Date 类型字段
    @DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
    @Schema(description = "发布时间")
    private Date releaseTime;

    // Integer 字典字段（配合 Wrapper 翻译）
    @Schema(description = "分类")
    private Integer category;

    // String 普通文本
    @Schema(description = "标题")
    private String title;

    // String 大文本
    @Schema(description = "内容")
    private String content;
}
```

**TenantEntity 自动继承的字段**（不需要在子类中声明）：
- `id` (Long) - 主键，@TableId(type = IdType.ASSIGN_ID)，雪花 ID
- `tenantId` (String) - 租户 ID
- `createUser` (Long) - 创建人
- `createDept` (Long) - 创建部门
- `createTime` (Date) - 创建时间
- `updateUser` (Long) - 更新人
- `updateTime` (Date) - 更新时间
- `status` (Integer) - 业务状态，默认 1
- `isDeleted` (Integer) - 逻辑删除标记，@TableLogic，默认 0

### 模式B：BaseEntity（跨租户业务表）

```java
package org.springblade.modules.{module}.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

/**
 * {中文名}实体类
 *
 * @author {author}
 */
@Data
@TableName("blade_{table_name}")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "{中文名}")
public class {Name} extends BaseEntity {

    @Schema(description = "{字段注释}")
    private {Type} {fieldName};
}
```

**BaseEntity 继承字段**：与 TenantEntity 相同但不含 `tenantId`。

### 模式C：Raw Serializable（原生模式）

```java
package org.springblade.modules.{module}.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springblade.core.tool.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * {中文名}实体类
 *
 * @author {author}
 */
@Data
@TableName("blade_{table_name}")
@Schema(description = "{中文名}")
public class {Name} implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "主键id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    // --- 业务字段 ---

    @Schema(description = "{字段注释}")
    private {Type} {fieldName};

    // --- 审计字段（手动定义） ---

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "创建人", hidden = true)
    private Long createUser;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "创建部门", hidden = true)
    private Long createDept;

    @DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
    @Schema(description = "创建时间", hidden = true)
    private Date createTime;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "更新人", hidden = true)
    private Long updateUser;

    @DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
    @Schema(description = "更新时间", hidden = true)
    private Date updateTime;

    @Schema(description = "业务状态", hidden = true)
    private Integer status;

    @TableLogic
    @Schema(description = "是否已删除", hidden = true)
    private Integer isDeleted;
}
```

### Cloud 版本 Entity 差异

Cloud 中 Entity 放在 API 模块，包名不同：
```java
// Boot
package org.springblade.modules.{module}.entity;

// Cloud
package org.springblade.{module}.entity;
```

其余代码完全相同。

---

## 二、DTO 数据传输对象

### 标准 DTO（继承 Entity）

```java
package org.springblade.modules.{module}.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.{module}.entity.{Name};

/**
 * {中文名}数据传输对象
 *
 * @author {author}
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class {Name}DTO extends {Name} {
}
```

大多数情况下 DTO 为空类，仅作为扩展点。如有特定传输需求可添加字段。

### 精简 DTO（仅特定字段）

```java
package org.springblade.modules.{module}.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * {中文名}数据传输对象
 *
 * @author {author}
 */
@Data
public class {Name}DTO implements Serializable {

    @Schema(description = "特定字段")
    private String specificField;
}
```

---

## 三、VO 视图对象

### 标准 VO（简单扩展）

```java
package org.springblade.modules.{module}.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.{module}.entity.{Name};

/**
 * {中文名}视图对象
 *
 * @author {author}
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class {Name}VO extends {Name} {

    /**
     * 分类名称（由 Wrapper 通过字典翻译填充）
     */
    @Schema(description = "分类名称")
    private String categoryName;
}
```

对于每个需要字典翻译的字段，在 VO 中添加对应的 `{字段名}Name` 字段。

### 树形 VO（实现 INode 接口）

用于 Menu、Dict、Dept 等树形结构：

```java
package org.springblade.modules.{module}.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tool.node.INode;
import org.springblade.modules.{module}.entity.{Name};

import java.util.ArrayList;
import java.util.List;

/**
 * {中文名}视图对象（树形）
 *
 * @author {author}
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class {Name}VO extends {Name} implements INode<{Name}VO> {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "主键ID")
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "父节点ID")
    private Long parentId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "子节点")
    private List<{Name}VO> children;

    @Schema(description = "是否有子节点")
    private Boolean hasChildren;

    @Schema(description = "上级名称")
    private String parentName;

    @Override
    public List<{Name}VO> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        return this.children;
    }
}
```

---

## 四、Excel 导出类

```java
package org.springblade.modules.{module}.excel;

import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;

/**
 * {中文名}导出对象
 *
 * @author {author}
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class {Name}Excel implements Serializable {

    @ExcelProperty("{字段中文名}")
    private {Type} {fieldName};

    // 不导出审计字段（createUser、createTime 等）
}
```

> 开源版使用 FastExcel（`cn.idev.excel.*`，由 `blade-starter-excel` 引入）。

---

## 五、Mapper 接口

```java
package org.springblade.modules.{module}.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springblade.modules.{module}.entity.{Name};
import org.springblade.modules.{module}.excel.{Name}Excel;
import org.springblade.modules.{module}.vo.{Name}VO;

import java.util.List;

/**
 * {中文名} Mapper 接口
 *
 * @author {author}
 */
public interface {Name}Mapper extends BaseMapper<{Name}> {

    /**
     * 自定义分页查询
     */
    List<{Name}VO> select{Name}Page(IPage page, {Name}VO {modelCode});

    /**
     * 导出数据查询
     */
    List<{Name}Excel> export{Name}(@Param("ew") Wrapper<{Name}> queryWrapper);
}
```

**树形模板追加方法**：
```java
    /**
     * 树形结构查询
     */
    List<{Name}VO> tree(String tenantId);
```

---

## 六、Mapper XML

### 标准 CRUD

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.springblade.modules.{module}.mapper.{Name}Mapper">

    <!-- 通用查询映射结果 -->
    <resultMap id="{modelCode}ResultMap" type="org.springblade.modules.{module}.entity.{Name}">
        <result column="id" property="id"/>
        <!-- 每个业务字段一行映射 -->
        <result column="{column_name}" property="{fieldName}"/>
        <!-- 审计字段映射（TenantEntity 模式需要） -->
        <result column="create_user" property="createUser"/>
        <result column="create_dept" property="createDept"/>
        <result column="create_time" property="createTime"/>
        <result column="update_user" property="updateUser"/>
        <result column="update_time" property="updateTime"/>
        <result column="status" property="status"/>
        <result column="is_deleted" property="isDeleted"/>
    </resultMap>

    <!-- 自定义分页查询 -->
    <select id="select{Name}Page" resultMap="{modelCode}ResultMap">
        SELECT
            t.*,
            d.dict_value AS categoryName
        FROM blade_{table_name} t
        LEFT JOIN (SELECT * FROM blade_dict WHERE code = '{dictCode}') d
            ON t.category = d.dict_key
        WHERE t.is_deleted = 0
        <if test="{modelCode}.tenantId != null and {modelCode}.tenantId != ''">
            AND t.tenant_id = #{{{modelCode}.tenantId}}
        </if>
        <!-- 动态查询条件 -->
        <if test="{modelCode}.{queryField} != null and {modelCode}.{queryField} != ''">
            AND t.{query_column} LIKE CONCAT('%', #{{{modelCode}.{queryField}}}, '%')
        </if>
        <if test="{modelCode}.{eqField} != null">
            AND t.{eq_column} = #{{{modelCode}.{eqField}}}
        </if>
    </select>

    <!-- 导出查询 -->
    <select id="export{Name}" resultType="org.springblade.modules.{module}.excel.{Name}Excel">
        SELECT * FROM blade_{table_name}
        ${ew.customSqlSegment}
    </select>

</mapper>
```

**注意事项**：
- 如果有字典关联查询，在 select 中 LEFT JOIN blade_dict 表
- 动态条件中 `LIKE` 用于模糊查询字段，`=` 用于精确查询字段
- TenantEntity 模式需要 `tenant_id` 过滤条件
- resultMap 中每个数据库列名（snake_case）映射到 Java 属性（camelCase）

---

## 七、Service 接口

### TenantEntity / BaseEntity 模式

```java
package org.springblade.modules.{module}.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.modules.{module}.entity.{Name};
import org.springblade.modules.{module}.vo.{Name}VO;

/**
 * {中文名} 服务接口
 *
 * @author {author}
 */
public interface I{Name}Service extends BaseService<{Name}> {

    /**
     * 自定义分页
     */
    IPage<{Name}VO> select{Name}Page(IPage<{Name}VO> page, {Name}VO {modelCode});
}
```

### Raw Serializable 模式

```java
package org.springblade.modules.{module}.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.{module}.entity.{Name};

/**
 * {中文名} 服务接口
 *
 * @author {author}
 */
public interface I{Name}Service extends IService<{Name}> {
}
```

---

## 八、ServiceImpl 实现

### TenantEntity / BaseEntity 模式

```java
package org.springblade.modules.{module}.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.modules.{module}.entity.{Name};
import org.springblade.modules.{module}.mapper.{Name}Mapper;
import org.springblade.modules.{module}.service.I{Name}Service;
import org.springblade.modules.{module}.vo.{Name}VO;
import org.springframework.stereotype.Service;

/**
 * {中文名} 服务实现
 *
 * @author {author}
 */
@Service
public class {Name}ServiceImpl extends BaseServiceImpl<{Name}Mapper, {Name}> implements I{Name}Service {

    @Override
    public IPage<{Name}VO> select{Name}Page(IPage<{Name}VO> page, {Name}VO {modelCode}) {
        {modelCode}.setTenantId(AuthUtil.getTenantId());
        return page.setRecords(baseMapper.select{Name}Page(page, {modelCode}));
    }
}
```

**注意**：BaseEntity 模式下不需要 `setTenantId()` 那行。

### Raw Serializable 模式

```java
package org.springblade.modules.{module}.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.modules.{module}.entity.{Name};
import org.springblade.modules.{module}.mapper.{Name}Mapper;
import org.springblade.modules.{module}.service.I{Name}Service;
import org.springframework.stereotype.Service;

/**
 * {中文名} 服务实现
 *
 * @author {author}
 */
@Service
public class {Name}ServiceImpl extends ServiceImpl<{Name}Mapper, {Name}> implements I{Name}Service {
}
```

---

## 九、Controller 控制器

### Boot 版本

```java
package org.springblade.modules.{module}.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.{module}.entity.{Name};
import org.springblade.modules.{module}.service.I{Name}Service;
import org.springblade.modules.{module}.vo.{Name}VO;
import org.springblade.modules.{module}.wrapper.{Name}Wrapper;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * {中文名} 控制器
 *
 * @author {author}
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_{MODULE}_NAME + "/{modelCode}")
@Tag(name = "{中文名}", description = "{中文名}接口")
public class {Name}Controller extends BladeController {

    private final I{Name}Service {modelCode}Service;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @Operation(summary = "详情", description = "传入{modelCode}")
    public R<{Name}VO> detail({Name} {modelCode}) {
        {Name} detail = {modelCode}Service.getOne(Condition.getQueryWrapper({modelCode}));
        return R.data({Name}Wrapper.build().entityVO(detail));
    }

    /**
     * 分页列表
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @Operation(summary = "分页", description = "传入{modelCode}")
    @PreAuth(RoleConstant.HAS_ROLE_ADMIN)
    public R<IPage<{Name}VO>> list(@RequestParam Map<String, Object> {modelCode}, Query query) {
        IPage<{Name}> pages = {modelCode}Service.page(
            Condition.getPage(query),
            Condition.getQueryWrapper({modelCode}, {Name}.class)
        );
        return R.data({Name}Wrapper.build().pageVO(pages));
    }

    /**
     * 自定义分页
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @Operation(summary = "自定义分页", description = "传入{modelCode}")
    public R<IPage<{Name}VO>> page({Name}VO {modelCode}, Query query) {
        IPage<{Name}VO> pages = {modelCode}Service.select{Name}Page(Condition.getPage(query), {modelCode});
        return R.data(pages);
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @Operation(summary = "新增", description = "传入{modelCode}")
    public R save(@Valid @RequestBody {Name} {modelCode}) {
        return R.status({modelCode}Service.save({modelCode}));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @Operation(summary = "修改", description = "传入{modelCode}")
    public R update(@Valid @RequestBody {Name} {modelCode}) {
        return R.status({modelCode}Service.updateById({modelCode}));
    }

    /**
     * 新增或修改
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @Operation(summary = "新增或修改", description = "传入{modelCode}")
    public R submit(@Valid @RequestBody {Name} {modelCode}) {
        return R.status({modelCode}Service.saveOrUpdate({modelCode}));
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @Operation(summary = "逻辑删除", description = "传入ids")
    public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
        return R.status({modelCode}Service.deleteLogic(Func.toLongList(ids)));
    }
}
```

### Cloud 版本差异

Cloud 版本仅以下几处不同：

```java
// 1. 包名不同
package org.springblade.{module}.controller;

// 2. import 路径不同（Entity/VO/Service 都在 org.springblade.{module} 下）
import org.springblade.{module}.entity.{Name};
import org.springblade.{module}.vo.{Name}VO;
import org.springblade.{module}.service.I{Name}Service;

// 3. RequestMapping 不需要服务名前缀
@RequestMapping("/{modelCode}")
```

### 无 Wrapper 版本

如不使用 Wrapper，detail 和 list 方法简化为：

```java
@GetMapping("/detail")
public R<{Name}> detail({Name} {modelCode}) {
    {Name} detail = {modelCode}Service.getOne(Condition.getQueryWrapper({modelCode}));
    return R.data(detail);
}

@GetMapping("/list")
public R<IPage<{Name}>> list(@RequestParam Map<String, Object> {modelCode}, Query query) {
    IPage<{Name}> pages = {modelCode}Service.page(
        Condition.getPage(query),
        Condition.getQueryWrapper({modelCode}, {Name}.class)
    );
    return R.data(pages);
}
```

### Raw Serializable 模式的 remove 方法

```java
@PostMapping("/remove")
@ApiOperationSupport(order = 7)
@Operation(summary = "删除", description = "传入ids")
public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
    return R.status({modelCode}Service.removeBatchByIds(Func.toLongList(ids)));
}
```

### 权限注解使用规则

SpringBlade 开源版仅支持角色级权限：

| 场景 | 写法 |
|------|------|
| 仅管理员可访问（类级） | `@PreAuth(RoleConstant.HAS_ROLE_ADMIN)` 加在类上 |
| 仅管理员可访问（方法级） | `@PreAuth(RoleConstant.HAS_ROLE_ADMIN)` 加在方法上 |
| 隐藏 Swagger 文档 | `@Hidden`（来自 OpenAPI 3） |
| 全公开 | 不加任何权限注解 |

### 导出接口

```java
@GetMapping("/export-{modelCode}")
@ApiOperationSupport(order = 8)
@Operation(summary = "导出数据", description = "传入{modelCode}")
@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
public void export{Name}(@RequestParam Map<String, Object> {modelCode}, HttpServletResponse response) {
    QueryWrapper<{Name}> queryWrapper = Condition.getQueryWrapper({modelCode}, {Name}.class);
    List<{Name}Excel> list = {modelCode}Service.export{Name}(queryWrapper);
    ExcelUtil.export(response, "{中文名}数据", "{中文名}数据表", list, {Name}Excel.class);
}
```

---

## 十、Wrapper 数据转换器

```java
package org.springblade.modules.{module}.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.system.cache.DictCache;
import org.springblade.modules.{module}.entity.{Name};
import org.springblade.modules.{module}.vo.{Name}VO;

/**
 * {中文名}包装类，返回视图层所需的字段
 *
 * @author {author}
 */
public class {Name}Wrapper extends BaseEntityWrapper<{Name}, {Name}VO> {

    public static {Name}Wrapper build() {
        return new {Name}Wrapper();
    }

    @Override
    public {Name}VO entityVO({Name} {modelCode}) {
        {Name}VO {modelCode}VO = BeanUtil.copyProperties({modelCode}, {Name}VO.class);
        // 字典翻译：将字典键值转换为可读名称
        String categoryName = DictCache.getValue("{dictCode}", {modelCode}.getCategory());
        {modelCode}VO.setCategoryName(categoryName);
        return {modelCode}VO;
    }
}
```

**常见翻译模式**：

```java
// 1. 字典翻译
String categoryName = DictCache.getValue("{dictCode}", entity.getCategory());

// 2. 关联实体名称（通过缓存）
User user = UserCache.getUser(entity.getUserId());
if (Func.isNotEmpty(user)) {
    vo.setUserName(user.getAccount());
}

// 3. 父节点名称（树形结构）
if (Func.equals(entity.getParentId(), BladeConstant.TOP_PARENT_ID)) {
    vo.setParentName(BladeConstant.TOP_PARENT_NAME);
} else {
    {Name} parent = {modelCode}Service.getById(entity.getParentId());
    vo.setParentName(parent.getName());
}
```

**树形 Wrapper 追加方法**：

```java
public List<{Name}VO> listNodeVO(List<{Name}> list) {
    List<{Name}VO> collect = list.stream()
        .map(entity -> BeanUtil.copyProperties(entity, {Name}VO.class))
        .collect(Collectors.toList());
    return ForestNodeMerger.merge(collect);
}
```

---

## 十一、Feign Client 接口（Cloud）

放在 API 模块中（`blade-service-api/blade-{module}-api`）：

```java
package org.springblade.{module}.feign;

import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.mp.support.BladePage;
import org.springblade.core.tool.api.R;
import org.springblade.{module}.entity.{Name};
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * {中文名} Feign 接口
 *
 * @author {author}
 */
@FeignClient(value = AppConstant.APPLICATION_{MODULE}_NAME)
public interface I{Name}Client {

    String API_PREFIX = "/feign/client/{modelCode}";
    String TOP = API_PREFIX + "/top";

    /**
     * 获取分页数据
     */
    @GetMapping(TOP)
    BladePage<{Name}> top(@RequestParam("current") Integer current, @RequestParam("size") Integer size);
}
```

---

## 十二、Feign Client 实现（Cloud）

放在 Service 模块中（`blade-service/blade-{module}`）：

```java
package org.springblade.{module}.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BladePage;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.{module}.entity.{Name};
import org.springblade.{module}.service.I{Name}Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {中文名} Feign 实现
 *
 * @author {author}
 */
@Hidden
@RestController
@AllArgsConstructor
public class {Name}Client implements I{Name}Client {

    private final I{Name}Service service;

    @Override
    @GetMapping(TOP)
    public BladePage<{Name}> top(Integer current, Integer size) {
        Query query = new Query();
        query.setCurrent(current);
        query.setSize(size);
        IPage<{Name}> page = service.page(Condition.getPage(query));
        return BladePage.of(page);
    }
}
```

**Feign 实现要点**：
- `@Hidden`：对 Swagger 文档隐藏（内部接口）
- `@RestController`：作为 HTTP 端点暴露
- 路径常量复用接口中定义的常量

---

## 附：完整 import 参考

### Entity 常用 import
```java
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;      // BaseEntity
import org.springblade.core.mp.base.TenantEntity;   // TenantEntity
import org.springblade.core.tool.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;
```

### Controller 常用 import
```java
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.launch.constant.AppConstant;      // Boot 路由前缀使用
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
```

### Service 常用 import
```java
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.secure.utils.AuthUtil;
import org.springframework.stereotype.Service;
```

### Wrapper 常用 import
```java
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.cache.DictCache;        // 字典缓存（Boot 路径）
```
