# avue-form 字段类型完整参考

本文档列出 avue-form / avue-crud 中所有可用的字段类型及其完整配置属性。

## 目录

1. [input - 文本输入](#input---文本输入)
2. [number - 数字输入](#number---数字输入)
3. [select - 下拉选择](#select---下拉选择)
4. [cascader - 级联选择](#cascader---级联选择)
5. [checkbox - 多选框](#checkbox---多选框)
6. [radio - 单选框](#radio---单选框)
7. [date - 日期选择](#date---日期选择)
8. [time - 时间选择](#time---时间选择)
9. [switch - 开关](#switch---开关)
10. [upload - 文件上传](#upload---文件上传)
11. [tree - 树选择](#tree---树选择)
12. [input-tag - 标签输入](#input-tag---标签输入)
13. [mention - 提及](#mention---提及)
14. [input-table - 表格选择](#input-table---表格选择)
15. [color - 颜色选择](#color---颜色选择)
16. [icon - 图标选择](#icon---图标选择)
17. [map - 地图选择](#map---地图选择)
18. [rate - 评分](#rate---评分)
19. [slider - 滑块](#slider---滑块)
20. [array/img/url - 数组输入](#arrayimgurl---数组输入)
21. [dynamic - 动态子表单](#dynamic---动态子表单)
22. [title - 标题分隔](#title---标题分隔)
23. [input-cron - Cron表达式](#input-cron---cron表达式)

---

## 通用属性（所有类型共享）

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| placeholder | string | '请输入/请选择'+label | 占位文字 |
| clearable | boolean | true | 可清空 |
| readonly | boolean | false | 只读 |
| disabled | boolean | false | 禁用 |
| change | function({column, value}) | - | 值变化事件 |
| click | function({column, value}) | - | 点击事件 |
| focus | function({column, value}) | - | 聚焦事件 |
| blur | function({column, value}) | - | 失焦事件 |

---

## input - 文本输入

`type: 'input'`（默认类型，可省略）

### 基础属性

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| maxlength | number | - | 最大输入长度 |
| minlength | number | - | 最小输入长度 |
| showWordLimit | boolean | false | 显示字数统计 |
| prepend | string | - | 前置文字 |
| append | string | - | 后置文字 |
| prependClick | function | - | 前置文字点击 |
| appendClick | function | - | 后置文字点击 |
| prefixIcon | string | - | 前缀图标 |
| suffixIcon | string | - | 后缀图标 |
| showPassword | boolean | true | 密码切换图标 |
| autocomplete | string | 'off' | HTML autocomplete 属性 |
| formatters | function | - | 格式化函数 (3.7.0+) |
| parser | function | - | 解析函数（formatters 的逆操作）(3.7.0+) |
| target | string | '_blank' | 链接目标 (url类型时有效) |

### textarea 子类型

```javascript
{ type: 'textarea', rows: 4, minRows: 2, maxRows: 8 }
```

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| rows | number | 2 | 文本域行数 |
| minRows | number | 2 | 最小行数 |
| maxRows | number | 6 | 最大行数 |

### password 子类型

```javascript
{ type: 'password', showPassword: true }
```

### phone 子类型

```javascript
{ type: 'phone', defaultPhoneCode: '+86' }
```

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| defaultPhoneCode | string | '+86' | 默认区号 |
| phoneCodeOptions | array | - | 区号选项 |
| phoneMaxLength | number | 11 | 手机号最大长度 |
| phoneCodePlaceholder | string | '区号' | 区号占位 |
| phonePlaceholder | string | '请输入手机号' | 手机号占位 |

### currency 子类型

```javascript
{ type: 'currency', currency: 'CNY', currencyPrecision: 2 }
```

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| currency | string | 'CNY' | 货币类型 |
| currencySymbolCustom | string | - | 自定义货币符号 |
| currencyUnit | string | - | 货币单位 |
| currencyPrecision | number | 2 | 小数位数 |
| currencyPlaceholder | string | '请输入金额' | 占位 |
| currencyMin | number | - | 最小值 |
| currencyMax | number | - | 最大值 |

### bankCard 子类型

```javascript
{ type: 'bankCard', showBankCardType: true }
```

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| bankCardSeparator | string | ' ' | 分隔符 |
| bankCardSegment | number | 4 | 分段长度 |
| bankCardMaxDigits | number | 19 | 最大长度 |
| bankCardPlaceholder | string | '请输入银行卡号' | 占位 |
| showBankCardType | boolean | true | 显示卡类型 |

### idCard 子类型

```javascript
{ type: 'idCard', showIdCardInfo: true }
```

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| idCardPlaceholder | string | '请输入身份证号' | 占位 |
| showIdCardInfo | boolean | true | 显示身份信息 |
| idCardRealtimeValidate | boolean | true | 实时验证 |

### email 子类型

```javascript
{ type: 'email', showEmailSuffix: true }
```

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| emailPlaceholder | string | '请输入邮箱' | 占位 |
| emailSuffixes | array | ['@qq.com','@163.com',...] | 邮箱后缀建议 |
| showEmailSuffix | boolean | true | 显示建议 |

### code 子类型

```javascript
{ type: 'code', codeLength: 6 }
```

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| codePlaceholder | string | - | 占位 |
| codeLength | number | 6 | 验证码长度 |
| codeSeparator | string | - | 分隔符 |

### plate 子类型（车牌号）

```javascript
{ type: 'plate', defaultPlateProvince: '京' }
```

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| platePlaceholder | string | '请输入车牌号' | 占位 |
| defaultPlateProvince | string | '京' | 默认省份 |
| plateProvinceList | array | ['京','津','沪',...] | 省份列表 |

### ip 子类型

```javascript
{ type: 'ip', ipVersion: 4 }
```

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| ipPlaceholder | string | '请输入IP地址' | 占位 |
| ipVersion | number | 4 | IP版本 |

### mac 子类型

```javascript
{ type: 'mac', macSeparator: ':' }
```

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| macPlaceholder | string | '请输入MAC地址' | 占位 |
| macSeparator | string | ':' | 分隔符 |

### uscc 子类型（统一社会信用代码）

```javascript
{ type: 'uscc' }
```

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| usccPlaceholder | string | '请输入统一社会信用代码' | 占位 |
| usccRealtimeValidate | boolean | true | 实时验证 |

### search 子类型

```javascript
{ type: 'search' }
```

带搜索按钮的输入框，点击搜索按钮触发事件。属性同基础 input。

---

## number - 数字输入

`type: 'number'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| min | number | -Infinity | 最小值 |
| max | number | Infinity | 最大值 |
| step | number | 1 | 步长 |
| stepStrictly | boolean | false | 只能输入步长倍数 |
| precision | number | 2 | 小数精度 |
| controls | boolean | true | 显示控制按钮 |
| controlsPosition | string | 'top' | 按钮位置: top/right |
| suffix | string | - | 后缀文字 (3.5.6+) |
| prefix | string | - | 前缀文字 (3.5.6+) |
| suffixClick | function | - | 后缀点击 (3.5.6+) |
| prefixClick | function | - | 前缀点击 (3.5.6+) |
| align | string | 'left' | 文本对齐 (3.7.2+) |
| disabledScientific | boolean | false | 禁用科学计数法 (3.7.2+) |

```javascript
{
  label: '价格',
  prop: 'price',
  type: 'number',
  min: 0,
  max: 99999,
  step: 0.01,
  precision: 2,
  controlsPosition: 'right',
}
```

---

## select - 下拉选择

`type: 'select'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| multiple | boolean | false | 多选 |
| limit | number | - | 多选最大数量 |
| tags | boolean | false | 标签显示 |
| collapseTags | boolean | false | 折叠标签 (3.2.20+) |
| collapseTagsTooltip | boolean | false | 折叠标签tooltip (3.2.20+) |
| maxCollapseTags | number | - | 最大显示标签数 (3.2.20+) |
| allowCreate | boolean | false | 允许创建新条目 |
| filterable | boolean | false | 可搜索 |
| remote | boolean | false | 远程搜索 |
| defaultFirstOption | boolean | false | 回车选中第一个 |
| loadingText | string | '加载中' | 加载文字 |
| popperClass | string | - | 下拉框class |
| popperAppendToBody | boolean | true | 弹出层追加到body |
| virtualize | boolean | false | 虚拟DOM (大数据) |
| group | boolean | false | 分组显示 |
| drag | boolean | false | 拖拽排序（需Sortable.js） |
| cascader | array | - | 级联子字段prop |
| cascaderIndex | number | - | 级联默认选项索引 |
| emptyValues | array | - | 空值配置 (3.4.4+) |
| valueOnClear | any | - | 清空时的值 (3.4.4+) |
| removeTag | function({value, column}) | - | 移除标签事件 (3.7.3+) |
| all | boolean | false | 显示"全选"选项 |
| noMatchText | string | '无匹配数据' | 无匹配时文字 |
| noDataText | string | '无数据' | 无数据时文字 |

```javascript
// 基本用法
{
  label: '状态',
  prop: 'status',
  type: 'select',
  dicData: [
    { label: '启用', value: 1 },
    { label: '禁用', value: 0 },
  ],
}

// 远程搜索
{
  label: '用户',
  prop: 'userId',
  type: 'select',
  remote: true,
  filterable: true,
  dicUrl: '/api/user/search?name={{key}}',
  props: { label: 'name', value: 'id' },
}

// 自定义模板
{
  label: '用户',
  prop: 'userId',
  type: 'select',
  slot: true,  // 使用 userId + Type 插槽
}

// 分组
{
  label: '城市',
  prop: 'city',
  type: 'select',
  group: true,
  dicData: [
    {
      label: '热门城市',
      children: [
        { label: '北京', value: 'bj' },
        { label: '上海', value: 'sh' },
      ],
    },
  ],
}
```

---

## cascader - 级联选择

`type: 'cascader'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| showAllLevels | boolean | true | 显示完整路径 |
| tags | boolean | false | 标签显示 |
| separator | string | '/' | 分隔符 |
| filterable | boolean | false | 可搜索 |
| filterMethod | function | - | 自定义搜索逻辑 |
| expandTrigger | string | 'hover' | 展开触发方式: hover/click |
| multiple | boolean | false | 多选 |
| collapseTags | boolean | false | 折叠标签 |
| collapseTagsTooltip | boolean | false | 折叠tooltip |
| maxCollapseTags | number | - | 最大显示标签数 |
| checkStrictly | boolean | false | 父子节点独立 |
| emitPath | boolean | true | 返回完整路径数组 |
| lazy | boolean | false | 懒加载 |
| lazyLoad | function(node, resolve) | - | 懒加载函数 |
| effect | string | 'dark' | tooltip主题 (3.7.2+) |
| showCheckedStrategy | string | 'SHOW_ALL' | 选中项回填策略 (3.7.2+) |
| popperClass | string | - | 下拉框class |

```javascript
{
  label: '地区',
  prop: 'area',
  type: 'cascader',
  dicData: [
    {
      label: '北京',
      value: 'bj',
      children: [
        { label: '朝阳区', value: 'cy' },
        { label: '海淀区', value: 'hd' },
      ],
    },
  ],
}

// 懒加载
{
  label: '地区',
  prop: 'area',
  type: 'cascader',
  lazy: true,
  lazyLoad: (node, resolve) => {
    const { level, value } = node
    getAreaChildren(value, level).then(res => {
      resolve(res.data.map(item => ({
        label: item.name,
        value: item.id,
        leaf: level >= 2,  // 标记叶子节点
      })))
    })
  },
}
```

---

## checkbox - 多选框

`type: 'checkbox'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| all | boolean | false | 全选按钮 |
| border | boolean | false | 带边框 |
| button | boolean | false | 按钮样式 |
| min | number | - | 最少选择数 |
| max | number | - | 最多选择数 |
| fill | string | '#409eff' | 选中颜色 |
| textColor | string | '#ffffff' | 选中文字颜色 |

```javascript
{
  label: '爱好',
  prop: 'hobbies',
  type: 'checkbox',
  dicData: [
    { label: '读书', value: 1 },
    { label: '运动', value: 2 },
    { label: '旅行', value: 3 },
  ],
  all: true,
  min: 1,
  max: 3,
}
```

---

## radio - 单选框

`type: 'radio'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| border | boolean | false | 带边框 |
| button | boolean | false | 按钮样式 |

```javascript
{
  label: '性别',
  prop: 'gender',
  type: 'radio',
  dicData: [
    { label: '男', value: 1 },
    { label: '女', value: 2 },
  ],
}
```

---

## date - 日期选择

`type: 'date'`

可用 type 值：`date` | `datetime` | `daterange` | `datetimerange` | `week` | `month` | `year` | `dates` | `monthrange` | `yearrange`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| format | string | - | 显示格式 |
| valueFormat | string | - | 实际值格式 |
| startPlaceholder | string | '开始' | 范围起始占位 |
| endPlaceholder | string | '结束' | 范围结束占位 |
| rangeSeparator | string | '-' | 范围分隔符 |
| defaultTime | string[] | - | 范围默认时间 |
| defaultValue | Date | - | 默认日期 |
| pickerOptions | object | - | 日期选项 |
| popperClass | string | - | 下拉框class |
| prefix-icon | string | - | 前缀图标 |
| cellClassName | function | - | 自定义单元格class |
| showNow | boolean | true | 显示"此刻"按钮 (3.6.0+) |
| disabledHours | function | - | 禁用小时 (3.8.2+) |
| disabledMinutes | function | - | 禁用分钟 (3.8.2+) |
| disabledSeconds | function | - | 禁用秒 (3.8.2+) |
| disabledDate | function(date) | - | 禁用日期判断函数 |
| shortcuts | array/function | - | 快捷日期选项 |
| editable | boolean | true | 输入框可编辑 |
| unlinkPanels | boolean | false | 范围选择时取消面板联动 |

**日期格式表：**

| 格式 | 含义 | 示例 |
|-----|------|------|
| YYYY | 年 | 2024 |
| MM | 月 | 01 |
| DD | 日 | 02 |
| HH | 时(24h) | 03 |
| mm | 分 | 04 |
| ss | 秒 | 05 |
| X | JS时间戳 | 1483326245000 |

```javascript
// 日期
{ type: 'date', format: 'YYYY-MM-DD', valueFormat: 'YYYY-MM-DD' }

// 日期时间
{ type: 'datetime', format: 'YYYY-MM-DD HH:mm:ss', valueFormat: 'YYYY-MM-DD HH:mm:ss' }

// 日期范围
{ type: 'daterange', startPlaceholder: '开始日期', endPlaceholder: '结束日期' }

// 带默认时间的范围
{
  type: 'datetimerange',
  defaultTime: ['00:00:00', '23:59:59'],
}
```

---

## time - 时间选择

`type: 'time'` | `'timerange'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| format | string | - | 显示格式 |
| valueFormat | string | - | 实际值格式 |
| startPlaceholder | string | '开始' | 范围起始占位 |
| endPlaceholder | string | '结束' | 范围结束占位 |
| rangeSeparator | string | '-' | 范围分隔符 |
| defaultValue | Date | - | 默认值 |
| pickerOptions | object | - | 时间选项 |
| popperClass | string | - | 下拉框class |
| arrowControl | boolean | false | 箭头控制时间 |
| editable | boolean | true | 输入框可编辑 |

```javascript
// 基本时间选择
{ type: 'time', format: 'HH:mm:ss', valueFormat: 'HH:mm:ss' }

// 固定时间点
{
  type: 'time',
  pickerOptions: {
    start: '08:00',
    end: '18:00',
    step: '00:30',
  },
}
```

> **TimeSelect 模式**：当配置了 `start`、`end`、`step`、`maxTime`、`minTime` 中的任一属性时，组件自动切换为 TimeSelect（固定时间点选择）模式。

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| start | string | - | 开始时间 (TimeSelect) |
| end | string | - | 结束时间 (TimeSelect) |
| step | string | - | 时间步长 (TimeSelect) |
| maxTime | string | - | 最大可选时间 (TimeSelect) |
| minTime | string | - | 最小可选时间 (TimeSelect) |

```javascript
// TimeSelect 固定时间点选择
{
  type: 'time',
  start: '08:00',
  end: '18:00',
  step: '00:30',
  maxTime: '17:30',
}
```

---

## switch - 开关

`type: 'switch'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| len | number | 40 | 开关宽度 |
| activeColor | string | '#409EFF' | 打开背景色 |
| inactiveColor | string | '#C0CCDA' | 关闭背景色 |
| activeIcon | string | - | 打开图标 (3.2.20+) |
| inactiveIcon | string | - | 关闭图标 (3.2.20+) |
| activeActionIcon | string | - | 打开动作图标 (3.2.20+) |
| inactiveActionIcon | string | - | 关闭动作图标 (3.2.20+) |
| inlinePrompt | boolean | false | 只显示首字符 (3.2.20+) |
| beforeChange | function(done) | - | 切换前钩子 (3.2.20+) |

```javascript
{
  label: '状态',
  prop: 'status',
  type: 'switch',
  dicData: [
    { label: '关闭', value: 0 },
    { label: '开启', value: 1 },
  ],
}
```

---

## upload - 文件上传

`type: 'upload'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| action | string | **必填** | 上传地址 |
| accept | string/array | - | 文件类型限制 |
| fileSize | number | - | 文件大小限制(KB) |
| limit | number | - | 最大文件数 |
| multiple | boolean | false | 多文件上传 |
| directory | boolean | false | 文件夹上传 (3.8.0+) |
| listType | string | 'text' | 文件列表类型: text/picture/picture-card |
| showFileList | boolean | true | 显示文件列表 |
| fileType | string | 'img' | 文件类型: img/audio/video |
| fileText | string | '点击上传' | 上传按钮文字 |
| drag | boolean | false | 拖拽排序（需Sortable.js） |
| data | object | - | 额外参数 |
| headers | object | - | 请求头 |
| httpRequest | function(file, column) | - | 自定义上传逻辑 |
| canvasOption | object | - | 图片水印配置 |
| propsHttp | object | - | 响应结构配置 |
| dataType | string | - | 数据类型: string/object/json |
| loadText | string | - | 加载文字 |
| tip | string | - | 提示文字 |
| dragFile | boolean | false | 拖拽上传区域（区别于 drag 拖拽排序）|
| oss | string | - | 云存储类型: ali/qiniu/cos |
| paramsList | array | - | 额外参数列表 |
| cropperOption | object | - | 图片裁剪配置 |

### propsHttp 响应配置

| 属性 | 类型 | 说明 |
|-----|------|------|
| home | string | 图片根路径 |
| res | string | 响应层级 |
| url | string | 图片地址属性 |
| name | string | 图片名称属性 |
| fileName | string | 上传文件名（默认'file'） |

### canvasOption 水印配置

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| text | string | 'avuejs.com' | 水印文字 |
| fontFamily | string | 'microsoft yahei' | 字体 |
| color | string | '#999' | 颜色 |
| fontSize | number | 16 | 字号 |
| opacity | number | 100 | 透明度 |
| bottom | number | 10 | 底部距离 |
| right | number | 10 | 右侧距离 |
| ratio | number | 1 | 压缩比(0-1) |

```javascript
{
  label: '附件',
  prop: 'files',
  type: 'upload',
  action: '/api/upload',
  listType: 'picture-card',
  multiple: true,
  limit: 5,
  fileSize: 10240,
  accept: 'image/*',
  propsHttp: {
    res: 'data',
    url: 'link',
    name: 'originalName',
  },
  tip: '支持jpg/png格式，单文件不超过10MB',
}
```

---

## tree - 树选择

`type: 'tree'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| multiple | boolean | false | 多选 |
| tags | boolean | false | 标签显示 |
| collapseTags | boolean | false | 折叠标签 (3.2.20+) |
| collapseTagsTooltip | boolean | false | 折叠tooltip (3.2.20+) |
| maxCollapseTags | number | - | 最大显示标签数 (3.2.20+) |
| checkStrictly | boolean | false | 父子独立 |
| parent | boolean | true | 父节点可选 |
| accordion | boolean | false | 手风琴模式 |
| defaultExpandAll | boolean | false | 默认展开所有 |
| expandOnClickNode | boolean | false | 点击展开 |
| checkOnClickLeaf | boolean | false | 点击叶子勾选 (3.6.3+) |
| defaultExpandedKeys | array | - | 默认展开keys |
| defaultCheckedKeys | array | - | 默认勾选keys |
| filterable | boolean | false | 可筛选 |
| filterText | string | '请输入搜索关键字' | 筛选占位 |
| iconClass | string | - | 自定义图标 |
| leafOnly | boolean | true | 仅叶子节点 |
| lazy | boolean | false | 懒加载 |
| treeLoad | function | - | 加载函数 |
| cacheData | array | - | 懒加载缓存 (3.2.20+) |
| nodeClick | function | - | 节点点击回调 |
| checked | function | - | 勾选回调 |
| popperClass | string | - | 下拉框class |
| popperAppendToBody | boolean | true | 弹出层追加到body |

```javascript
{
  label: '部门',
  prop: 'deptId',
  type: 'tree',
  dicUrl: '/api/dept/tree',
  props: { label: 'name', value: 'id' },
  defaultExpandAll: true,
  checkStrictly: true,
}
```

---

## input-tag - 标签输入 (3.6.0+)

`type: 'input-tag'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| maxlength | number | - | 最大输入长度 |
| minlength | number | - | 最小输入长度 |
| drag | boolean | false | 拖拽排序 |
| size | string | - | 尺寸: large/small |
| min | number | -Infinity | 最小标签数 |
| max | number | Infinity | 最大标签数 |
| prefix | string | - | 前置内容 |
| prefixClick | function | - | 前置内容点击回调 |
| suffix | string | - | 后置内容 |
| suffixClick | function | - | 后置内容点击回调 |

```javascript
{
  label: '标签',
  prop: 'tags',
  type: 'input-tag',
  // 按回车添加标签
}
```

---

## mention - 提及 (3.6.0+)

`type: 'mention'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| max | number | - | 最大提及数 |
| remote | boolean | false | 远程搜索 |
| maxlength | number | - | 最大长度 |
| minlength | number | - | 最小长度 |
| prepend | string | - | 前置文字 |
| append | string | - | 后置文字 |
| prefixIcon | string | - | 前缀图标 |
| suffixIcon | string | - | 后缀图标 |
| split | string | ',' | 提及分隔符 |
| prefix | string | '@' | 触发前缀 |
| whole | boolean | false | 仅匹配完整提及 |
| checkIsWhole | function | - | 自定义完整匹配检测 |
| prependClick | function | - | 前置点击回调 |
| appendClick | function | - | 后置点击回调 |

```javascript
{
  label: '备注',
  prop: 'remark',
  type: 'mention',
  dicData: [
    { label: '张三', value: 'zhangsan' },
    { label: '李四', value: 'lisi' },
  ],
}

// 远程搜索
{
  label: '备注',
  prop: 'remark',
  type: 'mention',
  remote: true,
  dicUrl: '/api/user/search?name={{key}}',
}
```

---

## input-table - 表格选择

`type: 'input-table'`

内部使用 avue-crud 组件，配置参考 crud 文档。

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| formatter | function | - | 数据格式化 |
| onLoad | function | - | 加载/查询/分页方法 |
| before-close | function(done) | - | 关闭前回调 (3.4.7+) |
| multiple | boolean | false | 多选 |
| children | object | - | 表格 CRUD 配置（支持所有 avue-crud option 属性） |
| dialogWidth | string | '80%' | 弹窗宽度 |
| prefixIcon | string | - | 前缀图标 |
| suffixIcon | string | - | 后缀图标 |

```javascript
{
  label: '用户',
  prop: 'userId',
  type: 'input-table',
  formatter: (row) => row.name,
  onLoad: ({ page, value, data }, callback) => {
    getList(page.currentPage, page.pageSize, data).then(res => {
      callback({
        total: res.data.total,
        data: res.data.records,
      })
    })
  },
  children: {
    border: true,
    column: [
      { label: '姓名', prop: 'name', search: true },
      { label: '手机号', prop: 'phone' },
    ],
  },
}
```

---

## color - 颜色选择

`type: 'color'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| showAlpha | boolean | true | 显示透明度 |
| colorFormat | string | 'rgb' | 颜色格式: hsl/hsv/hex/rgb |
| popperClass | string | - | 下拉框class |
| predefine | array | - | 预定义颜色 |

```javascript
{
  label: '主题色',
  prop: 'themeColor',
  type: 'color',
  colorFormat: 'hex',
  predefine: ['#ff4500', '#ff8c00', '#ffd700', '#90ee90', '#00ced1', '#1e90ff'],
}
```

---

## icon - 图标选择

`type: 'icon'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| iconList | array | - | 图标数组 |
| before-close | function(done) | - | 关闭前回调 (3.4.7+) |
| dialogWidth | string | '80%' | 弹窗宽度 |

```javascript
{
  label: '图标',
  prop: 'icon',
  type: 'icon',
  iconList: [
    { label: '首页', value: 'el-icon-s-home' },
    { label: '设置', value: 'el-icon-setting' },
  ],
}
```

iconList 支持分组结构：
```javascript
iconList: [
  {
    label: 'Element Plus',
    list: [
      { label: 'Plus', value: 'el-icon-plus' },
      { label: 'Edit', value: 'el-icon-edit' },
    ]
  },
  {
    label: 'Font Awesome',
    list: [
      { label: 'Home', value: 'fa fa-home' },
    ]
  }
]
```

需引入图标库：
```html
<script src="//at.alicdn.com/t/font_2621503_zcbiqy2g1i.js"></script>
```

---

## map - 地图选择

`type: 'map'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| params | object | - | 高德地图初始化参数 |
| before-close | function(done) | - | 关闭前回调 (3.4.7+) |
| mapChange | function | - | 地图变化回调 |
| rows | number | - | 文本域行数 |
| minRows | number | 1 | 最小行数 |
| maxRows | number | - | 最大行数 |
| dialogWidth | string | '80%' | 弹窗宽度 |

需引入高德地图SDK（见 global-config.md）。

```javascript
{
  label: '位置',
  prop: 'location',
  type: 'map',
  params: { zoom: 15, center: [116.397428, 39.90923] },
}
```

> 值格式为数组：`[经度, 纬度, 格式化地址]`，需引入高德地图 SDK。

---

## rate - 评分

`type: 'rate'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| max | number | 5 | 最大分数 |
| allowHalf | boolean | false | 允许半星 |
| lowThreshold | number | 2 | 低分界限 |
| highThreshold | number | 4 | 高分界限 |
| colors | array/object | ['#F7BA2A','#F7BA2A','#F7BA2A'] | 颜色 |
| voidColor | string | '#C6D1DE' | 未选颜色 |
| disabledVoidColor | string | '#EFF2F7' | 禁用未选颜色 |
| iconClasses | array/object | - | 图标class |
| voidIconClass | string | 'el-icon-star-off' | 未选图标 |
| showText | boolean | false | 显示文字 |
| showScore | boolean | false | 显示分数 |
| textColor | string | '#1F2D3D' | 文字颜色 |
| texts | array | ['极差','失望','一般','满意','惊喜'] | 文字列表 |
| scoreTemplate | string | '{value}' | 分数模板 |

```javascript
{
  label: '评分',
  prop: 'score',
  type: 'rate',
  max: 5,
  allowHalf: true,
  showText: true,
  texts: ['极差', '失望', '一般', '满意', '惊喜'],
}
```

---

## slider - 滑块

`type: 'slider'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| min | number | 0 | 最小值 |
| max | number | 100 | 最大值 |
| step | number | 1 | 步长 |
| showInput | boolean | false | 显示输入框 |
| showInputControls | boolean | true | 输入框控制按钮 |
| showStops | boolean | false | 显示间断点 |
| showTooltip | boolean | true | 显示tooltip |
| formatTooltip | function | - | tooltip格式化 |
| tooltipClass | string | - | tooltip class |
| range | boolean | false | 范围选择 |
| vertical | boolean | false | 垂直模式 |
| height | string | - | 垂直模式高度 |
| marks | object | - | 标记 |

```javascript
{
  label: '进度',
  prop: 'progress',
  type: 'slider',
  min: 0,
  max: 100,
  step: 10,
  showStops: true,
  marks: { 0: '0%', 50: '50%', 100: '100%' },
}
```

---

## array/img/url - 数组输入

`type: 'array'` | `'img'` | `'url'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| alone | boolean | false | 单条模式 |
| limit | number | - | 最大条数 |
| fileType | string | 'img' | 文件类型 (img类型): img/audio/video |

```javascript
// 数组输入
{ label: '标签', prop: 'tags', type: 'array', limit: 5 }

// 图片输入
{ label: '图片', prop: 'images', type: 'img', limit: 3, fileType: 'img' }

// 链接输入
{ label: '链接', prop: 'links', type: 'url', limit: 5 }
```

---

## dynamic - 动态子表单

`type: 'dynamic'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| rowAdd | function | - | 自定义新增行方法 |
| rowDel | function | - | 自定义删除行方法 |
| type | string | 'crud' | 显示类型: crud/form |
| limit | number | - | 最大行数 |
| children | object | - | 子表配置 |
| index | boolean | true | 显示序号 |
| max | number | - | 最大行数 |

### children 配置属性

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| type | string | 'crud' | 子表类型: crud/form |
| column | array | - | 子表字段配置 |
| index | boolean | true | 显示序号 |
| indexLabel | string | '#' | 序号标题 |
| addBtn | boolean | true | 显示新增按钮 |
| delBtn | boolean | true | 显示删除按钮 |
| viewBtn | boolean | true | 显示查看按钮 |
| align | string | - | 内容对齐方式 |
| headerAlign | string | - | 表头对齐方式 |
| rowAdd | function | - | 自定义新增行方法 |
| rowDel | function | - | 自定义删除行方法 |
| selectionChange | function | - | 选择变化回调 |
| sortableChange | function | - | 拖拽排序回调 |

```javascript
// 表格模式（默认）
{
  label: '明细',
  prop: 'items',
  type: 'dynamic',
  span: 24,
  children: {
    align: 'center',
    headerAlign: 'center',
    column: [
      { label: '商品', prop: 'product' },
      { label: '数量', prop: 'quantity', type: 'number' },
      { label: '单价', prop: 'price', type: 'number', precision: 2 },
    ],
  },
}

// 表单模式
{
  label: '联系人',
  prop: 'contacts',
  type: 'dynamic',
  span: 24,
  children: {
    type: 'form',
    index: false,
    column: [
      { label: '姓名', prop: 'name', span: 12 },
      { label: '电话', prop: 'phone', span: 12 },
    ],
  },
}
```

---

## title - 标题分隔

`type: 'title'`

用于表单中的分组标题，无输入功能。

```javascript
{
  label: '基本信息',
  prop: 'title1',
  type: 'title',
  span: 24,
}
```

---

## input-cron - Cron表达式 (3.8.2+)

`type: 'input-cron'`

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| placeholder | string | '点击配置Cron表达式' | 占位文字 |
| clearable | boolean | true | 可清空 |
| prefixIcon | string | - | 前缀图标 |
| suffixIcon | string | - | 后缀图标 |
| dialogWidth | string | '700px' | 弹窗宽度 |

```javascript
{
  label: '定时任务',
  prop: 'cron',
  type: 'input-cron',
  span: 24,
}
```
