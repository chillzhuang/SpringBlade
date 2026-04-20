# avue-crud 列配置参考

## 目录
1. [列通用属性](#列通用属性)
2. [字典配置](#字典配置)
3. [显示控制](#显示控制)
4. [排序与过滤](#排序与过滤)
5. [格式化与渲染](#格式化与渲染)
6. [搜索字段属性](#搜索字段属性)
7. [表单字段属性](#表单字段属性)

---

## 列通用属性

column 数组中每个对象的属性：

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| label | string | - | 列标题 |
| prop | string | - | 列属性名 |
| type | string | - | 字段类型 (input/select/date等，详见form-field-types) |
| width | string | - | 列宽度 |
| minWidth | string | - | 列最小宽度 |
| fixed | boolean/string | - | 固定列: true/left/right |
| align | string | 'left' | 对齐方式: left/center/right |
| headerAlign | string | 'left' | 表头对齐方式 |
| className | string | - | 单元格自定义class |
| labelClassName | string | - | 表头自定义class |
| resizable | boolean | true | 列宽可拖拽 |
| slot | boolean | - | 启用具名插槽（插槽名=prop） |
| bind | string | - | 深层数据绑定，如 'info.address' |
| value | - | - | 列默认值 |
| order | number | - | 字段排序，数字越大越靠前 |
| gridRow | boolean | false | 网格模式新起一行 |
| hide | boolean | false | 隐藏列（不在表格中显示） |
| cell | boolean | false | 该列可行内编辑 |
| addDetail | boolean | false | 新增时详情模式 |
| editDetail | boolean | false | 编辑时详情模式 |
| detail | boolean | false | 全局详情模式 |

### 主键配置

```javascript
option: {
  rowKey: 'id',           // 主键字段名，默认 'id'
  rowParentKey: 'parentId' // 父级主键(树表用)
}
```

### 对象格式列配置 (3.2.15+)

column 可以用对象格式替代数组，以 prop 为 key：

```javascript
// 数组格式
column: [
  { label: '名称', prop: 'name' },
  { label: '状态', prop: 'status' }
]

// 对象格式
column: {
  name: { label: '名称' },
  status: { label: '状态' }
}
```

---

## 字典配置

### 字典属性

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| dicData | array/function/promise | - | 本地字典数据 (3.5.0+ 支持函数/Promise) |
| dicUrl | string | - | 远程字典URL |
| dicFlag | boolean | false | 打开表单时重新请求字典 |
| dicQuery | object | - | 字典请求查询参数 |
| dicHeaders | object | - | 字典请求头参数 |
| dicFormatter | function(res) | (res)=>res | 字典返回数据格式化 |
| dicMethod | string | 'GET' | 字典请求方法 |

### Props 字段映射

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| label | string | 'label' | 选项标签属性 |
| value | string | 'value' | 选项值属性 |
| children | string | 'children' | 子选项属性 |
| disabled | string | 'disabled' | 禁用属性 |
| res | string | - | 响应数据层级，如 'data.data' |

```javascript
{
  label: '类型',
  prop: 'type',
  type: 'select',
  // 本地字典
  dicData: [
    { label: '类型A', value: 1 },
    { label: '类型B', value: 2, disabled: true },
  ],
  // 或远程字典
  dicUrl: '/api/dict/type',
  props: {
    label: 'name',
    value: 'code',
    res: 'data.records',
  },
}
```

### 字典联动

通过 `cascader` 属性实现级联联动：

```javascript
column: [
  {
    label: '省份',
    prop: 'province',
    type: 'select',
    dicUrl: '/api/province',
    cascader: ['city'],  // 指定子级字段prop
  },
  {
    label: '城市',
    prop: 'city',
    type: 'select',
    dicUrl: '/api/city?province={{key}}', // {{key}} 为父级选中值
  },
]
```

### 更新字典

```javascript
// 更新指定字段的字典
crudRef.value.updateDic('prop', [{ label: '新选项', value: 1 }])
// 重新加载所有字典
crudRef.value.dicInit()
```

---

## 显示控制

### 溢出省略

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| overHidden | boolean/object | false | 内容溢出显示tooltip |
| showOverflowTooltip | boolean/object | false | 同上 |
| ellipsis | boolean | false | 文本省略 (3.6.4+) |

### 多级表头

通过 `children` 嵌套实现：

```javascript
column: [
  {
    label: '用户信息',
    children: [
      { label: '姓名', prop: 'name' },
      { label: '年龄', prop: 'age' },
    ]
  }
]
```

### 表头固定

```javascript
option: {
  height: '500px',  // 设置高度后表头自动固定
  showHeader: true,  // 默认true
}
```

---

## 排序与过滤

### 排序

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| sortable | boolean/string | - | 排序: true(本地)/custom(远程) |
| defaultSort | object | - | 默认排序: {prop, order} |
| columnSort | boolean | - | 列拖拽排序 |

```javascript
// 远程排序
{
  label: '日期',
  prop: 'date',
  sortable: 'custom',  // 需监听 sort-change 事件
}

// 默认排序
option: {
  defaultSort: { prop: 'date', order: 'descending' }
}
```

### 过滤

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| filters | boolean/array | false | 数据过滤 |
| filterMethod | function | - | 自定义过滤逻辑 |
| filter-multiple | boolean | true | 多选过滤 |

```javascript
{
  label: '状态',
  prop: 'status',
  filters: true,  // 自动从 dicData 生成
  // 或手动指定
  filters: [
    { text: '启用', value: 1 },
    { text: '禁用', value: 0 },
  ],
}
```

---

## 格式化与渲染

### formatter 格式化

```javascript
{
  label: '金额',
  prop: 'amount',
  formatter: (row, value, label, column) => {
    return '¥' + value.toFixed(2)
  },
}
```

### tooltipFormatter (3.6.3+)

```javascript
{
  label: '备注',
  prop: 'remark',
  overHidden: true,
  tooltipFormatter: (row, value, label, column) => {
    return `详细内容: ${value}`
  },
}
```

### html 属性

```javascript
{
  label: '内容',
  prop: 'content',
  html: true,  // 支持HTML翻译
}
```

### render 渲染函数 (3.4.2+)

```javascript
{
  label: '状态',
  prop: 'status',
  // 列区域渲染
  render: (h, { row, index, dic }) => {
    return h('el-tag', { type: row.status === 1 ? 'success' : 'danger' }, row.statusName)
  },
  // 表单区域渲染
  renderForm: (h, { column, $index, dic }) => {
    return h('el-input', { modelValue: form.value[column.prop] })
  },
  // 表头渲染
  renderHeader: (h, { column }) => {
    return h('span', { style: 'color: red' }, column.label)
  },
}
```

### 合计行

```javascript
option: {
  showSummary: true,
  sumColumnList: [
    { name: 'amount', type: 'sum' },     // 求和
    { name: 'price', type: 'average' },   // 平均值
    { name: 'count', type: 'count' },     // 计数
  ],
}

// 或自定义合计
// 通过 summary-method 属性传入函数
```

### 合并行列

```javascript
// 通过 span-method 属性设置
<avue-crud :span-method="spanMethod">

const spanMethod = ({ row, column, rowIndex, columnIndex }) => {
  if (rowIndex === 0 && columnIndex === 0) {
    return { rowspan: 2, colspan: 1 }
  }
}
```

---

## 搜索字段属性

在 column 中通过 `search: true` 启用搜索，以下为搜索相关属性：

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| search | boolean | false | 启用搜索 |
| searchValue | string | - | 搜索初始值 |
| searchPlaceholder | string | - | 搜索占位文字 |
| searchSpan | number | 8 | 搜索栅格数 |
| searchOrder | number | - | 搜索字段排序 |
| searchGutter | number | - | 搜索字段间距 |
| searchRange | boolean | false | 范围搜索 |
| searchType | string | - | 搜索字段类型（覆盖 type） |
| searchLabelWidth | number | 80 | 搜索标签宽度 |
| searchClearable | boolean | false | 搜索可清空 |
| searchMultiple | boolean | false | 搜索多选 |
| searchTags | boolean | false | 搜索标签显示 |
| searchLabelPosition | string | 'left' | 搜索标签位置 |
| searchRules | array | - | 搜索验证规则 |
| searchDisabled | boolean | false | 搜索字段禁用 |
| searchReadonly | boolean | false | 搜索字段只读 |
| searchIndex | number | - | 搜索栏初始显示时包含此字段 |
| searchControl | function | - | 搜索字段联动控制 |
| renderSearch | function | - | 搜索区域自定义渲染函数 (3.4.2+) |

### 搜索栏全局配置

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| searchBtn | boolean | true | 显示搜索按钮 |
| searchBtnText | string | '搜索' | 搜索按钮文字 |
| searchBtnIcon | string | - | 搜索按钮图标 |
| emptyBtn | boolean | true | 显示清空按钮 |
| emptyBtnText | string | '清空' | 清空按钮文字 |
| emptyBtnIcon | string | - | 清空按钮图标 |
| searchShow | boolean | true | 初始显示搜索 |
| searchIcon | boolean | false | 显示折叠按钮 |
| searchIndex | number | 2 | 折叠保留数量 |
| searchMenuPosition | string | 'center' | 搜索按钮位置 |
| searchShowBtn | boolean | true | 显示搜索显隐按钮 |
| searchTip | string | - | 提示内容 |
| searchTipPlacement | string | 'bottom' | 提示方向 |
| searchMenuSpan | number | - | 搜索按钮栅格数 |
| searchPlaceholder | string | - | 搜索占位文字 |
| searchSpan | number | 8 | 搜索默认栅格数 |
| searchGutter | number | - | 搜索默认间距 |
| searchLabelWidth | number | 80 | 搜索默认标签宽度 |
| searchLabelPosition | string | 'left' | 搜索默认标签位置 |

---

## 表单字段属性

column 中控制字段在表单（新增/编辑/查看弹窗）中表现的属性：

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| display | boolean | true | 在表单中显示 |
| disabled | boolean | false | 在表单中禁用 |
| addDisabled | boolean | false | 新增时禁用 |
| editDisabled | boolean | false | 编辑时禁用 |
| viewDisabled | boolean | false | 查看时禁用 |
| addDisplay | boolean | true | 新增时显示 |
| editDisplay | boolean | true | 编辑时显示 |
| viewDisplay | boolean | true | 查看时显示 |
| span | number | 12 | 表单栅格数 |
| gutter | number | 0 | 表单间距 |
| offset | number | 0 | 表单偏移 |
| row | boolean | false | 表单换行 |
| labelWidth | string | 80 | 标签宽度 |
| labelPosition | string | 'left' | 标签位置 |
| rules | array | - | 验证规则 |
| tip | string | - | 内容帮助文字 |
| tipPlacement | string | 'top' | 帮助文字方向 |
| labelTip | string | - | 标题帮助文字 |
| labelTipPlacement | string | 'top' | 标题帮助文字方向 |
| control | function/promise | - | 字段联动控制 (3.5.0+) |
| render | function | - | 列区域渲染 (3.4.2+) |
| renderForm | function | - | 表单区域渲染 (3.4.2+) |
| renderHeader | function | - | 表头渲染 (3.4.2+) |
