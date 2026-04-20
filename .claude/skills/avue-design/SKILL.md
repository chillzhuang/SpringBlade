---
name: avue-design
description: "Avue 组件代码生成与配置指南。当用户需要创建、配置或生成基于 Avue (avue-crud, avue-form, avue-tree 等) 的 Vue 页面代码时使用此 skill。覆盖 CRUD 表格、表单、树组件、数据展示等全部 Avue 组件。支持 Composition API 和 Options API 两种代码风格。当用户提到 avue、crud表格、动态表单、JSON配置表单、低代码表单、avue-crud、avue-form 等关键词时，务必使用此 skill。"
---

# Avue Design - 组件代码生成 Skill 集合

Avue 是基于 Vue3 + Element-Plus 的低代码前端框架，通过 JSON 配置生成页面。版本：v3.8.2，MIT 协议。

## 快速安装

```bash
npm i @smallwei/avue -S
# 或
yarn add @smallwei/avue -S
```

```javascript
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import { createApp } from 'vue'
import Avue from '@smallwei/avue'
import '@smallwei/avue/lib/index.css'

const app = createApp({})
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.use(ElementPlus)
app.use(Avue)
// 若使用字典或上传组件，需传入 axios：
// import axios from 'axios'
// app.use(Avue, { axios })
```

## 参考文件导航

根据用户需求，读取对应的参考文件获取完整配置信息：

### 基础配置
| 参考文件 | 何时读取 |
|---------|---------|
| `references/global-config.md` | 全局配置、安装、国际化、云存储 |
| `references/api-style-guide.md` | 需要生成 Composition API 或 Options API 代码时 |
| `references/utility-apis.md` | 使用 Avue 全局工具函数 ($DialogForm, $Clipboard 等) |

### avue-crud (CRUD 表格组件)
| 参考文件 | 何时读取 |
|---------|---------|
| `references/crud-core.md` | CRUD 基础属性、事件、方法、插槽、按钮、弹窗、分页 |
| `references/crud-column.md` | 列配置、字典、排序、过滤、格式化、渲染 |
| `references/crud-search.md` | 搜索栏配置、搜索字段属性 |
| `references/crud-form-dialog.md` | CRUD 弹窗表单配置、验证规则、字段状态控制 |
| `references/crud-features.md` | 高级功能：树表、虚拟滚动、卡片模式、行编辑、导入导出、拖拽排序、权限控制等 |

### avue-form (表单组件)
| 参考文件 | 何时读取 |
|---------|---------|
| `references/form-core.md` | 表单基础属性、事件、方法、插槽、布局 |
| `references/form-field-types.md` | 全部字段类型 (input/select/date/upload 等 27+ 种) 的详细配置属性 |
| `references/form-advanced.md` | 字典配置、验证规则、动态子表单、文件上传(含云存储)、字段联动 |

### 其他组件
| 参考文件 | 何时读取 |
|---------|---------|
| `references/tree-component.md` | avue-tree 树组件 |
| `references/data-display.md` | 数据展示组件 (DataDisplay, DataCard 等) |
| `references/other-components.md` | Card、DialogForm、Calendar、Flow、Login、Tabs 等独立组件 |

## 代码生成核心规则

### 1. 两种 API 风格

生成代码时必须确认用户要用哪种风格（默认 Composition API）。详细模板见 `references/api-style-guide.md`。

**Composition API (推荐)**：使用 `<script setup>`，`ref()`/`reactive()` 声明数据，箭头函数定义方法。

**Options API**：使用 `export default {}`，`data()` 返回数据，`methods` 对象定义方法。

### 2. avue-crud 基本结构

```vue
<template>
  <basic-container>
    <avue-crud
      ref="crud"
      :option="option"
      :data="data"
      :table-loading="loading"
      v-model="form"
      v-model:page="page"
      v-model:search="search"
      :permission="permissionList"
      :before-open="beforeOpen"
      @row-save="rowSave"
      @row-update="rowUpdate"
      @row-del="rowDel"
      @search-change="searchChange"
      @search-reset="searchReset"
      @selection-change="selectionChange"
      @refresh-change="refreshChange"
      @on-load="onLoad"
    />
  </basic-container>
</template>
```

### 3. avue-form 基本结构

```vue
<template>
  <avue-form
    ref="form"
    v-model="formData"
    :option="option"
    @submit="handleSubmit"
    @reset-change="handleReset"
  />
</template>
```

### 4. option 配置核心

option 是 Avue 组件的核心配置对象，包含：
- 表格/表单级属性（size, height, border, stripe 等）
- `column` 数组：每个字段的完整配置（label, prop, type, rules, dicData 等）
- `group` 数组：分组配置（仅表单）

```javascript
const option = {
  // 表格级配置
  height: 'auto',
  border: true,
  stripe: true,
  index: true,
  selection: true,
  // 列配置
  column: [
    {
      label: '名称',
      prop: 'name',
      type: 'input',        // 字段类型
      search: true,          // 启用搜索
      span: 12,              // 表单中占栅格数
      rules: [{ required: true, message: '请输入名称', trigger: 'blur' }],
      // 字典配置
      dicData: [],           // 本地字典
      dicUrl: '',            // 远程字典URL
      props: { label: 'label', value: 'value' },
    }
  ]
}
```

### 5. 字段类型速查

| 类型 | type 值 | 说明 |
|-----|---------|------|
| 文本输入 | input | 支持 password/textarea/phone/currency/bankCard/idCard/email/code/plate/ip/mac/uscc 子类型 |
| 数字输入 | number | 数字步进器 |
| 下拉选择 | select | 单选/多选/远程搜索/分组 |
| 级联选择 | cascader | 多级联动选择 |
| 多选框 | checkbox | 多选组 |
| 单选框 | radio | 单选组 |
| 日期 | date/datetime/daterange/datetimerange/week/month/year/dates | 日期时间选择 |
| 时间 | time/timerange | 时间选择 |
| 开关 | switch | 布尔切换 |
| 文件上传 | upload | 支持阿里云/七牛/腾讯COS |
| 数组 | array | 数组输入框 |
| 图片 | img | 图片输入 |
| 链接 | url | 超链接输入 |
| 树选择 | tree | 树型选择器 |
| 标签输入 | input-tag | 标签输入 (3.6.0+) |
| 提及 | mention | @提及组件 (3.6.0+) |
| 表格选择 | input-table | 表格弹窗选择 |
| 颜色选择 | color | 颜色选择器 |
| Cron表达式 | input-cron | Cron可视化配置 (3.8.2+) |
| 图标选择 | icon | 图标选择器 |
| 地图选择 | map | 高德地图坐标选择 |
| 评分 | rate | 星级评分 |
| 滑块 | slider | 滑动条 |
| 标题 | title | 分组标题 |
| 动态表单 | dynamic | 子表单/子表格 |

### 6. 关键注意事项

- `column` 可以是数组也可以是对象格式（3.2.15+，用 prop 做 key）
- 深层数据绑定用 `bind` 属性：`bind: 'info.address'`
- 字典联动用 `cascader` 属性指定子字段 prop
- 字段动态控制用 `control` 属性（支持 Promise，3.5.0+）
- 表单分组用 `group` 配置，标签页显示设 `tabs: true`
- 所有组件都支持 `render` 函数自定义渲染（3.4.2+）
- CRUD 弹窗类型用 `dialogType: 'drawer'` 可改为抽屉模式
- 搜索字段通过在 column 中设 `search: true` 启用
