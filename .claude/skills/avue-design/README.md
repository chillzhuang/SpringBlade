# avue-design Skill 集合

Avue 组件代码生成与配置指南，基于 Avue v3.8.2 (Vue3 + Element-Plus) 文档构建。

## 概述

本 Skill 集合为 Claude Code 提供完整的 Avue 组件库知识，使其能够根据用户需求精准生成 Avue 组件代码。覆盖 CRUD 表格、动态表单、树组件、数据展示等全部 Avue 组件，支持 Composition API 和 Options API 两种代码风格。

## 目录结构

```
avue-design/
├── SKILL.md                           # 主入口 - 概览、路由、速查表
├── README.md                          # 本文件
└── references/                        # 参考文档（按需加载）
    │
    │── 基础配置 ───────────────────────
    ├── global-config.md               # 安装、全局配置、国际化、云存储
    ├── api-style-guide.md             # Composition API vs Options API 完整模板
    ├── utility-apis.md                # 16个全局工具函数 ($DialogForm, $Export 等)
    │
    │── avue-crud 表格组件 ─────────────
    ├── crud-core.md                   # 组件属性、Option配置、按钮、弹窗、分页、事件、方法、插槽
    ├── crud-column.md                 # 列配置、字典、Props映射、排序过滤、格式化渲染
    ├── crud-search.md                 # 搜索栏配置、搜索字段属性、级联搜索
    ├── crud-form-dialog.md            # 弹窗表单配置、验证规则、字段控制、分组、权限
    ├── crud-features.md               # 树表、虚拟滚动、卡片模式、行编辑、导入导出、拖拽、插件模式
    │
    │── avue-form 表单组件 ─────────────
    ├── form-core.md                   # 组件属性、布局、按钮、Column通用属性、事件、方法、插槽
    ├── form-field-types.md            # 全部27+字段类型详细配置（input含13子类型、select、date等）
    ├── form-advanced.md               # 字典、验证、动态子表单、文件上传(含云存储)、字段联动、Mock
    │
    │── 其他组件 ─────────────────────
    ├── tree-component.md              # avue-tree 树组件完整API
    ├── data-display.md                # 23个数据展示组件
    └── other-components.md            # Card、DialogForm、Flow、Login、Chat等22个独立组件
```

## 模块说明

### 1. 基础配置（3个文件）

| 文件 | 内容 |
|-----|------|
| `global-config.md` | npm安装、Element-Plus集成、全局crudOption/formOption、阿里云/七牛/腾讯COS云存储配置、水印配置、国际化、外部库CDN |
| `api-style-guide.md` | Composition API 和 Options API 两种风格的完整 CRUD 页面模板，包含详细的差异对照表和转换规则 |
| `utility-apis.md` | 全部16个Avue工具函数：$DialogForm、$Clipboard、$ImagePreview、$Print、$Export、$Log、findObject、deepClone等 |

### 2. avue-crud 表格组件（5个文件）

这是 Avue 最核心的组件，文档最为丰富。

| 文件 | 内容 |
|-----|------|
| `crud-core.md` | 组件属性（21个）、Option表格级属性（索引/选择/展开/行/菜单/网格/工具栏共80+属性）、按钮配置（25+）、弹窗配置（19个）、分页（8个）、事件（35+）、方法（40+含行内编辑/滚动/搜索/视图方法组）、插槽（22+） |
| `crud-column.md` | 列通用属性（20+含hide/cell/detail控制）、字典配置（7个属性+Props映射）、字典联动、显示控制、多级表头、排序过滤、formatter/render/html格式化、合计行、合并行列、搜索字段属性（20+含searchDisabled/searchControl/renderSearch）、表单字段属性（20+） |
| `crud-search.md` | 搜索变量绑定（双API风格）、全局搜索属性（15+）、列级搜索属性（14个）、类型覆盖、折叠、级联联动、自定义插槽 |
| `crud-form-dialog.md` | 弹窗属性（15个）、beforeOpen/beforeClose回调、验证规则(async-validator)、字段display/disabled控制、默认值、control联动、分组/标签页、数据过滤、权限控制 |
| `crud-features.md` | 树表(基本/懒加载)、虚拟滚动(3.7.0+)、卡片网格模式(3.4.0+)、行编辑(单击/双击)、Excel导入导出(含对话框选项)、行/列拖拽排序、合计行、父子表、行复制、高级筛选(8种操作符)、列管理器、权限控制(对象/行级函数)、CRUD插件模式(Mixin)、API/Option文件模板、动态配置切换 |

### 3. avue-form 表单组件（3个文件）

| 文件 | 内容 |
|-----|------|
| `form-core.md` | 组件属性（9个含v-model:status）、Option属性（20个含formWidth/tabsActive/tabsType/tabsVerifyAll/filterDic/filterNull）、布局（栅格/分组/标签页/帮助文字）、Group属性（8个含arrow/display/header）、按钮配置（12个）、Column通用属性（25+）、事件（6个含change/tab-click/error/mock-change）、方法（12个含show/hide/scrollToField/getField）、插槽（6个）、两种API风格完整示例 |
| `form-field-types.md` | 全部27+字段类型完整配置：input(含14子类型:password/textarea/search/phone/currency/bankCard/idCard/email/code/plate/ip/mac/uscc + formatters/parser)、number、select(含all/noMatchText)、cascader、checkbox、radio、date(含disabledDate/shortcuts/unlinkPanels)、time(含arrowControl + TimeSelect自动切换)、switch、upload(含dragFile/oss/cropperOption)、tree、input-tag(含min/max/prefix/suffix)、mention(含split/prefix/whole)、input-table(含children/dialogWidth)、color、icon(含dialogWidth/分组iconList)、map(含mapChange/dialogWidth/值格式说明)、rate、slider、array/img/url、dynamic(含max + 完整children配置12属性)、title、input-cron |
| `form-advanced.md` | 字典配置(本地/远程/格式化/联动/更新)、验证规则(基本/类型/自定义/方法)、动态子表单(表格/表单模式)、文件上传(回调/水印/云存储/数据格式)、字段联动(control/Promise)、数据格式(深层绑定/过滤)、自定义插槽、Mock数据 |

### 4. 其他组件（3个文件）

| 文件 | 内容 |
|-----|------|
| `tree-component.md` | avue-tree完整API：Option属性（12个）、Props属性（4个）、事件（7个）、方法（18个）、插槽（3个）、多选/懒加载/拖拽/虚拟模式示例 |
| `data-display.md` | 23个数据展示组件列表、avue-data-display核心组件属性和数据结构 |
| `other-components.md` | 22个独立组件：Card、DialogForm、Tabs、Search、Calendar、Flow、Login、Article、Chat、CountUp、Clipboard、Draggable、TextEllipsis、Sign、Print、Export、ImagePreview、Contextmenu、Video、Verify、Screenshot、License |

## 工作原理

1. **主入口 SKILL.md**（< 200行）始终加载到上下文中，提供：
   - 快速安装指南
   - 参考文件导航表（根据任务类型指引读取哪个文件）
   - 代码生成核心规则
   - 字段类型速查表

2. **参考文件** 按需加载，当用户需要特定组件的配置时才读取对应文件：
   - 需要创建 CRUD 页面 → 读取 `crud-core.md` + `crud-column.md`
   - 需要配置搜索 → 读取 `crud-search.md`
   - 需要特定字段类型 → 读取 `form-field-types.md`
   - 需要文件上传 → 读取 `form-advanced.md`

3. **双API风格** 支持：
   - Composition API（推荐）：`<script setup>` + `ref()` + 箭头函数
   - Options API：`export default` + `data()` + `methods`

## 数据来源

基于以下 Avue 官方文档与源码完整解析：

- `crud/` 目录 32 个文档（CRUD表格组件）
- `form/` 目录 38 个文档（表单组件）
- `component/` 目录 20 个文档（独立组件）
- `default/` 目录 25 个文档（默认组件）
- `data/` 目录 23 个文档（数据展示组件）
- `docs/` 目录 10 个文档（全局配置、API、安装等）
- Saber3 项目参考文件（Composition API vs Options API 对照）
- Avue v3.8.2 完整源码审计（packages/element-plus/ 全部组件 + packages/core/ 核心模块 + src/ 工具库）

总计 **6,000+ 行** 参考文档，覆盖 Avue v3.8.2 全部组件配置。

## 版本信息

- Avue 版本：v3.8.2 (2026-03-27)
- Vue 版本：Vue 3
- UI 框架：Element-Plus
- 创建日期：2026-04-05
