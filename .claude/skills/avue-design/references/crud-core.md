# avue-crud 核心配置参考

## 目录
1. [组件属性](#组件属性)
2. [Option 表格级属性](#option-表格级属性)
3. [按钮配置](#按钮配置)
4. [弹窗配置](#弹窗配置)
5. [分页配置](#分页配置)
6. [事件](#事件)
7. [方法](#方法)
8. [插槽](#插槽)

## 全局配置

```javascript
app.use(Avue, {
  crudOption: {
    // 所有 option 属性都可在此全局设置
  }
})
```

获取表格原生引用：
```javascript
const crudRef = ref(null)
// 访问 el-table 实例
crudRef.value.$refs.table
```

---

## 组件属性

在 `<avue-crud>` 标签上设置的属性：

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| modelValue / v-model | object | - | 表单绑定值 |
| data | array | - | 表格显示数据 |
| option | object | - | 组件配置项 |
| before-open | function(done, type, loading) | - | 打开弹窗前回调，type: add/edit/view |
| before-close | function(done, type) | - | 关闭弹窗前回调 |
| permission | function/object | - | 按钮权限控制（支持行级） |
| v-model:search | object | - | 搜索变量双向绑定 |
| v-model:page | object | - | 分页变量双向绑定 |
| cell-class-name | function/string | - | 单元格 className 回调 |
| cell-style | function/object | - | 单元格样式回调 |
| header-cell-class-name | function/string | - | 表头单元格 className |
| header-row-class-name | function/string | - | 表头行 className |
| header-row-style | function/object | - | 表头行样式 |
| header-cell-style | function/object | - | 表头单元格样式 |
| row-class-name | function/string | - | 行 className 回调 |
| row-style | function/object | - | 行样式回调 |
| span-method | function | - | 合并行或列 |
| summary-method | function | - | 自定义合计计算 |
| table-loading | boolean | - | 加载状态 |
| upload-before | function(file, done, loading) | - | 上传组件上传前回调 |
| upload-after | function(res, done) | - | 上传组件上传后回调 |
| upload-delete | function(file, column) | - | 删除文件钩子 |
| upload-preview | function(file, column, done) | - | 上传预览回调 |
| upload-error | function(error, column) | - | 上传错误回调 |
| upload-exceed | function(limit, files, fileList, column) | - | 超出文件数限制回调 |
| upload-size | function(fileSize, files, fileList, column) | - | 超出文件大小回调 (3.4.4+) |
| disabled | boolean | false | 禁用所有交互元素 |
| width | number/string | - | 表格宽度 |
| height | number/string | - | 表格高度 |

---

## Option 表格级属性

在 `option` 对象中设置：

### 基础属性

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| size | string | 'small' | 组件尺寸: medium/small/mini |
| column | array | - | 列配置数组 |
| height | string | - | 表格高度，设 'auto' 为自适应窗口 |
| maxHeight | string | - | 表格最大高度 |
| title | string | - | 表格标题 |
| border | boolean | false | 纵向边框 |
| stripe | boolean | false | 斑马纹 |
| fit | boolean | true | 列宽自适应 |
| showHeader | boolean | true | 显示表头 |
| header | boolean | false | 隐藏表格头部操作栏 |
| emptyText | string | '暂无数据' | 空数据提示文字 |
| tooltipEffect | string | 'dark' | tooltip 主题: dark/light |
| tooltipOptions | object | - | tooltip 配置项 |
| scrollbarAlwaysOn | boolean | true | 始终显示滚动条 (3.6.0+) |

### 索引列

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| index | boolean | false | 显示序号 |
| indexLabel | string | '#' | 序号列标题 |
| indexWidth | number | 50 | 序号列宽度 |
| indexFixed | boolean/string | true | 序号列固定: true/left/right |
| indexClassName | string | - | 序号列单元格自定义class |
| indexLabelClassName | string | - | 序号列表头自定义class |

### 选择列

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| selection | boolean | false | 显示多选框 |
| selectable | function | - | 判断行是否可选 |
| selectionWidth | number | 50 | 选择列宽度 |
| selectionFixed | boolean/string | true | 选择列固定 |
| selectionClassName | string | - | 选择列单元格class |
| selectionLabelClassName | string | - | 选择列表头class |
| reserveSelection | boolean | false | 翻页保留选择 |

### 展开行

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| expand | boolean | false | 显示展开 |
| expandWidth | number | 50 | 展开列宽度 |
| expandFixed | boolean/string | true | 展开列固定 |
| expandClassName | string | - | 展开列单元格class |
| expandLabelClassName | string | - | 展开列表头class |
| defaultExpandAll | boolean | false | 默认展开所有行 |
| expandRowKeys | array | - | 展开行的keys |

### 行配置

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| rowKey | string | 'id' | 行数据主键 |
| rowParentKey | string | 'parentId' | 父行主键(树表用) |
| highlightCurrentRow | boolean | false | 高亮当前行 |
| defaultSort | object | - | 默认排序 {prop, order: 'ascending'/'descending'} |
| showOverflowTooltip | boolean/object | false | 溢出提示 |
| showSummary | boolean | false | 显示合计行 |
| lazy | boolean | false | 懒加载子节点 |

### 菜单列

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| menu | boolean | true | 显示操作菜单 |
| menuWidth | number | 220 | 菜单列宽度 |
| menuTitle | string | '操作' | 菜单列标题 |
| menuFixed | boolean/string | true | 菜单列固定 |
| menuType | string | 'text' | 菜单按钮样式: button/icon/text/menu |
| menuHeaderAlign | string | 'center' | 菜单表头对齐 |
| menuAlign | string | 'center' | 菜单按钮对齐 |
| menuClassName | string | - | 菜单单元格class |
| menuLabelClassName | string | - | 菜单表头class |

### 卡片/网格模式 (3.4.0+)

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| grid | boolean | false | 启用网格模式 |
| gridBtn | boolean | true | 显示模式切换按钮 |
| gridSpan | number | 8 | 网格栅格数 |
| gridBackgroundImage | string | - | 网格背景图片 |
| gridBackground | string/function | - | 网格背景色 |

### 工具栏与功能开关

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| addRowBtn | boolean | false | 显示新增行内编辑按钮 |
| cellBtn | boolean | false | 显示行内编辑操作按钮 |
| dateBtn | boolean | false | 显示日期范围选择按钮 |
| mockBtn | boolean | false | 显示Mock数据按钮 |
| selectClearBtn | boolean | true | 显示清空选中按钮 |
| tip | boolean | true | 显示选中项提示 |
| page | boolean | true | 显示分页 |
| searchShow | boolean | true | 搜索栏默认显示 |
| searchIcon | boolean | true | 搜索栏展开/收起图标 |
| searchIndex | number | - | 搜索栏初始显示字段数（超出折叠） |
| searchLabelWidth | number | 80 | 搜索标签宽度 |
| searchSpan | number | 6 | 搜索栅格数 |
| calcHeight | number | 300 | 表格自适应高度偏移量 |
| menuXsWidth | number | 100 | 移动端菜单列宽度 |
| gridXsSpan | number | 12 | 移动端网格栅格数 |
| headerAlign | string | 'left' | 表头默认对齐方式 |
| width | string | '100%' | 表格宽度 |
| formFullscreen | boolean | false | 弹窗显示全屏切换按钮 |
| dialogAppendToBody | boolean | true | 弹窗追加到body |
| filterMultiple | boolean | true | 过滤器默认多选 |

---

## 按钮配置

### 操作按钮

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| addBtn | boolean | true | 新增按钮 |
| addBtnText | string | '新增' | 新增按钮文字 |
| addBtnIcon | string | - | 新增按钮图标 |
| editBtn | boolean | true | 修改按钮 |
| editBtnText | string | '修改' | 修改按钮文字 |
| editBtnIcon | string | - | 修改按钮图标 |
| delBtn | boolean | true | 删除按钮 |
| delBtnText | string | '删除' | 删除按钮文字 |
| delBtnIcon | string | - | 删除按钮图标 |
| viewBtn | boolean | false | 查看按钮 |
| viewBtnText | string | '查看' | 查看按钮文字 |
| viewBtnIcon | string | - | 查看按钮图标 |
| copyBtn | boolean | false | 复制按钮 |

### 弹窗按钮

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| updateBtn | boolean | true | 弹窗修改按钮 |
| updateBtnText | string | '修改' | 修改按钮文字 |
| updateBtnIcon | string | - | 修改按钮图标 |
| saveBtn | boolean | true | 弹窗保存按钮 |
| saveBtnText | string | '保存' | 保存按钮文字 |
| saveBtnIcon | string | - | 保存按钮图标 |
| cancelBtn | boolean | true | 弹窗取消按钮 |
| cancelBtnText | string | '取消' | 取消按钮文字 |
| cancelBtnIcon | string | - | 取消按钮图标 |

### 工具栏按钮

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| printBtn | boolean | false | 打印按钮 |
| excelBtn | boolean | false | 导出按钮 |
| filterBtn | boolean | false | 筛选按钮 |
| refreshBtn | boolean | true | 刷新按钮 |
| columnBtn | boolean | true | 列操作按钮 |
| searchShowBtn | boolean | true | 搜索显隐按钮 |
| addRowBtn | boolean | false | 新增行内编辑按钮 |
| dateBtn | boolean | false | 日期范围选择按钮 |
| gridBtn | boolean | true | 网格/表格切换按钮 |

### 按钮文字图标规则

按钮名 + `Text` = 按钮文字，按钮名 + `Icon` = 按钮图标。设置图标为 `' '`（空格）可禁用图标。

---

## 弹窗配置

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| addTitle | string | '新增' | 新增弹窗标题 |
| editTitle | string | '修改' | 修改弹窗标题 |
| viewTitle | string | '查看' | 查看弹窗标题 |
| dialogDrag | boolean | false | 弹窗可拖拽 |
| dialogFullscreen | boolean | false | 弹窗全屏 |
| dialogCustomClass | string | - | 弹窗自定义class |
| dialogEscape | boolean | true | ESC关闭弹窗 |
| dialogClickModal | boolean | false | 点击遮罩关闭弹窗 |
| dialogCloseBtn | boolean | true | 显示关闭按钮 |
| dialogModal | boolean | true | 显示遮罩层 |
| dialogMenuPosition | string | 'right' | 弹窗按钮位置 |
| dialogTop | number | 25 | 弹窗距顶部距离 |
| dialogType | string | 'dialog' | 弹窗类型: dialog/drawer |
| dialogDirection | string | 'rtl' | 抽屉方向: rtl/ltr/ttb/tbb |
| dialogWidth | string | '60%' | 弹窗宽度 |
| dialogAppendToBody | boolean | true | 弹窗追加到body |
| headerClass | string | - | 弹窗头部自定义class (3.7.0+) |
| bodyClass | string | - | 弹窗内容区自定义class (3.7.0+) |
| footerClass | string | - | 弹窗底部自定义class (3.7.0+) |

---

## 分页配置

通过 `v-model:page` 绑定分页对象：

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| currentPage | number | 1 | 当前页码 |
| pageSize | number | 20 | 每页条数 |
| total | number | 0 | 总条数 |
| pagerCount | number | 7 | 页码按钮数量 |
| background | boolean | true | 分页按钮背景 |
| layout | string | 'prev, pager, next, jumper, ->, total' | 分页组件布局 |
| pageSizes | number[] | [10, 20, 30, 40, 50, 100] | 每页条数选项 |
| simplePage | boolean | false | 只有一页时隐藏分页 |

---

## 事件

### 数据操作事件

| 事件名 | 参数 | 说明 |
|-------|------|------|
| row-save | (row, done, loading) | 新增数据后触发。done() 关闭表单，loading() 保持表单 |
| row-update | (row, index, done, loading) | 修改数据后触发 |
| row-del | (row, index) | 删除行触发 |
| refresh-change | - | 点击刷新按钮后触发 |

### 搜索事件

| 事件名 | 参数 | 说明 |
|-------|------|------|
| search-change | (form, done) | 搜索后触发 |
| search-reset | - | 清空搜索回调 |
| date-change | (date) | 日期选择回调 |

### 选择事件

| 事件名 | 参数 | 说明 |
|-------|------|------|
| select | (selection, row) | 用户勾选 |
| select-all | (selection) | 用户全选 |
| selection-change | (selection) | 选择项变化 |
| current-row-change | (currentRow, oldCurrentRow) | 当前行变化 |
| selection-clear | (selection) | 清空选中时触发 |

### 行/单元格事件

| 事件名 | 参数 | 说明 |
|-------|------|------|
| cell-mouse-enter | (row, column, cell, event) | 单元格hover进入 |
| cell-mouse-leave | (row, column, cell, event) | 单元格hover离开 |
| cell-click | (row, column, cell, event) | 单元格点击 |
| cell-dblclick | (row, column, cell, event) | 单元格双击 |
| row-click | (row, column, event) | 行点击 |
| row-contextmenu | (row, column, event) | 行右击 |
| row-dblclick | (row, column, event) | 行双击 |

### 表头事件

| 事件名 | 参数 | 说明 |
|-------|------|------|
| header-click | (column, event) | 表头点击 |
| header-contextmenu | (column, event) | 表头右击 |
| header-dragend | (newWidth, oldWidth, column, event) | 列宽改变 |

### 排序/过滤事件

| 事件名 | 参数 | 说明 |
|-------|------|------|
| sort-change | ({ column, prop, order }) | 排序变化 |
| filter-change | (filters) | 过滤变化 |
| sortable-change | ({ newIndex, oldIndex }) | 行拖拽排序 |
| column-sortable-change | ({ newIndex, oldIndex }) | 列拖拽排序 (3.5.2+) |

### 其他事件

| 事件名 | 参数 | 说明 |
|-------|------|------|
| on-load | (page) | 页面加载/分页变化时触发 |
| tree-load | (row, treeNode, resolve) | 加载子节点 (lazy=true) |
| expand-change | (row, expandedRows/expanded) | 行展开/收起 |
| grid-status-change | (status) | 网格/表格模式切换 (3.4.7+) |
| scroll | ({scrollLeft, scrollTop}) | 表格滚动 (3.6.0+) |
| tab-click | (tab, event) | 弹窗标签页点击 |
| error | (error) | 表单验证失败 |
| filter | (filterData) | 高级筛选应用 |
| date-change | (date) | 日期范围选择回调 |

---

## 方法

通过 `ref` 调用：

| 方法名 | 参数 | 说明 |
|-------|------|------|
| rowAdd | - | 打开新增弹窗 |
| rowEdit | (row, index) | 打开编辑弹窗 |
| rowView | (row, index) | 打开查看弹窗 |
| updateDic | (prop, dic) | 更新字典 |
| dicInit | - | 重新加载所有字典 |
| getPropRef | (prop) | 获取字段ref对象 |
| validate | (valid, done, msg) | 验证整个表单 |
| validateField | (props, callback) | 验证指定字段 |
| resetFields | - | 重置所有字段 |
| clearValidate | (props) | 清除验证结果 |
| clearSelection | - | 清除多选 |
| toggleSelection | (array) | 切换选择 |
| toggleAllSelection | - | 切换全选 |
| toggleRowExpansion | (row, expanded) | 切换行展开 |
| setCurrentRow | (row) | 设置当前行 |
| clearSort | - | 清除排序 |
| clearFilter | (columnKey) | 清除过滤 |
| doLayout | - | 重新布局表格 |
| refreshTable | - | 重新初始化表格 |
| sort | (prop, order) | 手动排序 |
| updateKeyChildren | (key, data) | 更新懒加载子节点 (3.6.0+) |

### 行内编辑方法

| 方法名 | 参数 | 说明 |
|-------|------|------|
| rowCopy | (row) | 复制行数据到新增弹窗 |
| rowCellAdd | (row) | 新增一行行内编辑数据 |
| rowCell | (row, index) | 切换行编辑状态 |
| rowCellEdit | (row, index) | 进入行编辑模式 |
| rowCellUpdate | (row, index) | 保存行编辑数据 |
| rowCancel | (row, index) | 取消行编辑 |
| closeDialog | (row, index) | 关闭弹窗并更新数据 |
| validateCellForm | - | 验证所有行内编辑表单 |
| validateCellField | (index) | 验证指定行的编辑表单 |

### 数据方法

| 方法名 | 参数 | 说明 |
|-------|------|------|
| findData | (id) | 通过主键值查找行数据 |
| getPermission | (key, row, index) | 获取按钮权限 |
| getTableHeight | - | 重新计算表格高度 |

### 滚动方法 (3.7.0+ 虚拟滚动)

| 方法名 | 参数 | 说明 |
|-------|------|------|
| scrollTo | (coordinates) | 滚动到指定位置 |
| scrollToTop | - | 滚动到顶部 |
| scrollToLeft | - | 滚动到最左侧 |
| scrollRow | (rowKey) | 滚动到指定行（虚拟滚动模式） |
| setScrollTop | (top) | 设置垂直滚动位置 |
| setScrollLeft | (left) | 设置水平滚动位置 |

### 搜索方法

| 方法名 | 参数 | 说明 |
|-------|------|------|
| searchReset | - | 重置搜索表单 |
| searchChange | (form, done) | 手动触发搜索 |

### 视图方法

| 方法名 | 参数 | 说明 |
|-------|------|------|
| handleGridShow | - | 切换网格/表格视图 |
| columns | - | 获取当前列配置 |

---

## 插槽

### 列相关插槽

| 插槽名 | 说明 |
|-------|------|
| `{prop}` | 自定义列内容，参数: {row, index, dic, size, label} |
| `{prop}-header` | 自定义列表头 |
| `{prop}-search` | 自定义搜索输入 |
| `{prop}-form` | 自定义表单字段 |
| `{prop}-label` | 自定义表单标签 |
| `{prop}-error` | 自定义表单错误提示 |
| `{prop}-desc` | 自定义表单描述 (3.6.4+) |
| `{prop}-type` | 自定义字段类型选择器 |

### 区域插槽

| 插槽名 | 说明 |
|-------|------|
| search | 自定义整个搜索区域 |
| searchMenu | 自定义搜索菜单 |
| header | 自定义表格头部 |
| body | 自定义表格主体 (3.4.3+) |
| page | 自定义分页 |
| empty | 自定义空状态 |
| tip | 自定义选中提示区域 |

### 按钮插槽

| 插槽名 | 说明 |
|-------|------|
| menu-left | 自定义菜单左侧 |
| menu-right | 自定义菜单右侧 |
| menu | 自定义整个操作菜单，参数: {row, index, type, size} |
| menu-before | 操作菜单前 (3.4.3+) |
| menu-btn | 自定义菜单按钮 |
| menu-btn-before | 菜单按钮前 (3.4.3+) |
| menu-form | 自定义表单菜单 |
| menu-form-before | 表单菜单前 (3.4.3+) |
| menu-header | 菜单表头插槽 |
