# avue-crud 搜索配置参考

## 基本用法

在 column 中设置 `search: true` 开启搜索字段：

```javascript
column: [
  {
    label: '名称',
    prop: 'name',
    search: true,
    searchSpan: 6,
  },
  {
    label: '日期',
    prop: 'date',
    type: 'daterange',
    search: true,
    searchRange: true,
    searchSpan: 8,
  },
]
```

## 搜索变量

Composition API 使用 `v-model:search` 自动同步：
```vue
<avue-crud v-model:search="search" @search-change="searchChange" @search-reset="searchReset">

const search = ref({})
const searchChange = (params, done) => {
  page.value.currentPage = 1
  onLoad()
  done()
}
const searchReset = () => {
  onLoad()
}
```

Options API 使用 query 变量手动同步：
```vue
<avue-crud @search-change="searchChange" @search-reset="searchReset">

searchChange(params, done) {
  this.query = params
  this.page.currentPage = 1
  this.onLoad(this.page, params)
  done()
},
searchReset() {
  this.query = {}
  this.onLoad(this.page)
},
```

## 搜索栏配置

### 全局搜索属性（option 级别）

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| searchBtn | boolean | true | 显示搜索按钮 |
| searchBtnText | string | '搜索' | 搜索按钮文字 |
| emptyBtn | boolean | true | 显示清空按钮 |
| emptyBtnText | string | '清空' | 清空按钮文字 |
| searchShow | boolean | true | 首次加载显示搜索 |
| searchIcon | boolean | false | 显示折叠按钮 |
| searchIndex | number | 2 | 折叠保留字段数 |
| searchMenuPosition | string | 'center' | 按钮位置: left/center/right |
| searchShowBtn | boolean | true | 显示搜索显隐按钮 |
| searchTip | string | - | 提示文字 |
| searchMenuSpan | number | - | 按钮栅格（单独一行时设24） |
| searchSpan | number | 8 | 默认搜索栅格 |
| searchGutter | number | - | 默认搜索间距 |
| searchLabelWidth | number | 80 | 默认搜索标签宽度 |
| searchLabelPosition | string | 'left' | 默认搜索标签位置 |
| searchPlaceholder | string | - | 搜索占位文字 |

### 列级搜索属性

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| search | boolean | false | 启用搜索 |
| searchValue | any | - | 搜索默认值 |
| searchPlaceholder | string | - | 搜索占位文字 |
| searchSpan | number | 8 | 搜索栅格数 |
| searchOrder | number | - | 搜索排序 |
| searchRange | boolean | false | 范围搜索 |
| searchType | string | - | 搜索类型（覆盖type） |
| searchLabelWidth | number | 80 | 搜索标签宽度 |
| searchClearable | boolean | false | 可清空 |
| searchMultiple | boolean | false | 多选 |
| searchTags | boolean | false | 标签显示 |
| searchLabelPosition | string | 'left' | 标签位置 |
| searchRules | array | - | 验证规则 |

## 搜索字段类型覆盖

可以用 searchType 让搜索框使用不同于表格列的类型：

```javascript
{
  label: '日期',
  prop: 'date',
  type: 'date',           // 表格/表单中是日期选择
  search: true,
  searchType: 'daterange', // 搜索中是日期范围
  searchRange: true,
}
```

## 搜索折叠

```javascript
option: {
  searchIcon: true,    // 显示折叠按钮
  searchIndex: 2,      // 折叠时保留2个字段
}
```

## 搜索级联联动

```javascript
column: [
  {
    label: '省份',
    prop: 'province',
    type: 'select',
    search: true,
    dicUrl: '/api/province',
    cascader: ['city'],
  },
  {
    label: '城市',
    prop: 'city',
    type: 'select',
    search: true,
    dicUrl: '/api/city?province={{key}}',
  },
]
```

## 自定义搜索插槽

```vue
<!-- 自定义搜索菜单 -->
<template #searchMenu="{ row, search, reset }">
  <el-button type="primary" @click="search">搜索</el-button>
  <el-button @click="reset">重置</el-button>
  <el-button type="warning" @click="handleExport">导出</el-button>
</template>

<!-- 自定义搜索字段 -->
<template #name-search="{ search }">
  <el-input v-model="search.name" placeholder="自定义搜索" />
</template>
```

## 日期快捷按钮

```javascript
{
  label: '日期',
  prop: 'date',
  search: true,
  dateBtn: true,  // 显示日期快捷按钮
}
```
