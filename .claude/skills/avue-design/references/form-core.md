# avue-form 核心配置参考

## 目录
1. [组件属性](#组件属性)
2. [Option 表单级属性](#option-表单级属性)
3. [布局配置](#布局配置)
4. [按钮配置](#按钮配置)
5. [Column 通用属性](#column-通用属性)
6. [事件](#事件)
7. [方法](#方法)
8. [插槽](#插槽)

## 全局配置

```javascript
app.use(Avue, {
  formOption: {
    // 所有 option 属性都可在此全局设置
  }
})
```

---

## 组件属性

在 `<avue-form>` 标签上设置的属性：

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| modelValue / v-model | object | - | 表单绑定值 |
| option | object | - | 表单配置项 |
| upload-before | function(file, done, loading) | - | 上传前回调 |
| upload-after | function(res, done) | - | 上传后回调 |
| upload-delete | function(file, column) | - | 删除文件钩子 |
| upload-preview | function(file, column, done) | - | 预览文件回调 |
| upload-error | function(error, column) | - | 上传错误回调 |
| upload-exceed | function(limit, files, fileList, column) | - | 超出文件数限制 |
| upload-size | function(fileSize, files, fileList, column) | - | 超出文件大小 (3.4.4+) |
| v-model:status | boolean | false | 表单提交状态（禁用状态双向绑定） |

---

## Option 表单级属性

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| size | string | 'small' | 组件尺寸: medium/small/mini |
| column | array | - | 字段配置数组 |
| labelWidth | string/number | 80 | 标签宽度 |
| labelPosition | string | 'left' | 标签位置: left/right/top |
| labelSuffix | string | ':' | 标签后缀 |
| enter | boolean | false | 回车提交 |
| group | array | - | 分组配置 |
| tabs | boolean | false | 分组标签页显示 |
| detail | boolean | false | 详情/只读模式 |
| disabled | boolean | false | 全部禁用 |
| readonly | boolean | false | 全部只读 |
| formWidth | string | - | 表单容器宽度 |
| tabsActive | number | 0 | 默认激活标签页索引 |
| tabsType | string | - | 标签页类型(同 el-tabs type) |
| tabsVerifyAll | boolean | false | 提交时验证所有标签页（默认仅当前页） |
| filterDic | boolean | false | 过滤 $ 开头的字典翻译字段 |
| filterNull | boolean | false | 过滤空值字段 |
| filterParams | array | - | 重置时保留的字段 |

---

## 布局配置

### 栅格布局

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| span | number | 12 | 字段占用栅格数 (24栅格) |
| gutter | number | 0 | 栅格间距 |
| offset | number | 0 | 栅格左偏移 |
| push | number | 0 | 栅格右移动 |
| pull | number | 0 | 栅格左移动 |
| row | boolean | false | 是否新起一行 |

```javascript
column: [
  { label: '名称', prop: 'name', span: 12 },
  { label: '编码', prop: 'code', span: 12 },
  { label: '备注', prop: 'remark', span: 24, row: true },
]
```

### 分组显示

```javascript
option: {
  group: [
    {
      label: '基本信息',
      icon: 'el-icon-info',
      collapse: false,
      prop: 'base',
      column: [
        { label: '名称', prop: 'name', span: 12 },
        { label: '编码', prop: 'code', span: 12 },
      ],
    },
    {
      label: '详细信息',
      icon: 'el-icon-document',
      prop: 'detail',
      column: [
        { label: '描述', prop: 'description', span: 24 },
      ],
    },
  ],
}
```

#### Group 属性

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| label | string | - | 分组标题 |
| prop | string | - | 分组标识 |
| icon | string | - | 分组图标 |
| collapse | boolean | true | 是否可折叠 |
| arrow | boolean | true | 是否显示折叠箭头 |
| display | boolean | true | 是否显示该分组 |
| header | boolean | true | 是否显示分组头部 |
| column | array | - | 分组内的字段配置 |

### 标签页显示

```javascript
option: {
  tabs: true,
  // tabsVerifyAll: true, // 提交时验证所有标签页
  group: [
    {
      label: '基本信息',
      prop: 'tab1',
      column: [...],
    },
    {
      label: '扩展信息',
      prop: 'tab2',
      column: [...],
    },
  ],
}
```

### 帮助文字

```javascript
column: [
  {
    label: '名称',
    prop: 'name',
    tip: '请输入唯一标识名称',      // 字段右侧帮助文字
    tipPlacement: 'bottom',          // 帮助文字方向
    labelTip: '该字段用于系统唯一标识', // 标签帮助文字
    labelTipPlacement: 'bottom',
  },
]
```

### className 样式

```javascript
column: [
  {
    label: '名称',
    prop: 'name',
    className: 'custom-field-class',
  },
]
```

---

## 按钮配置

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| menuBtn | boolean | true | 显示按钮区 |
| menuSpan | number | 24 | 按钮栅格数 |
| menuPosition | string | 'center' | 按钮位置: left/center/right |
| submitBtn | boolean | true | 显示提交按钮 |
| submitText | string | '提交' | 提交按钮文字 |
| emptyBtn | boolean | true | 显示清空按钮 |
| emptyText | string | '清空' | 清空按钮文字 |
| printBtn | boolean | false | 显示打印按钮 |
| mockBtn | boolean | false | 显示Mock数据按钮（需引入Mock.js） |
| mockText | string | 'Mock' | Mock按钮文字 |
| printText | string | '打印' | 打印按钮文字 |
| submitIcon | string | - | 提交按钮图标 |
| emptyIcon | string | - | 清空按钮图标 |

---

## Column 通用属性

每个字段的通用配置：

### 基础属性

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| label | string | - | 标签文字 |
| prop | string | - | 字段属性名 |
| type | string | 'input' | 字段类型 |
| value | any | - | 默认值 |
| bind | string | - | 深层数据绑定 |
| disabled | boolean | false | 禁用 |
| display | boolean | true | 显示 |
| order | number | - | 排序，数字越大越靠前 |
| size | string | 'small' | 组件尺寸 |
| separator | string | ',' | 选项分隔符 (string dataType) |
| rules | array | - | 验证规则 |

### 栅格属性

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| span | number | 12 | 栅格数 |
| gutter | number | 0 | 间距 |
| offset | number | 0 | 偏移 |
| push | number | 0 | 右移 |
| pull | number | 0 | 左移 |
| row | boolean | false | 换行 |

### 标签属性

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| labelWidth | string/number | 80 | 标签宽度 |
| labelPosition | string | 'left' | 标签位置 |
| tip | string | - | 内容帮助文字 |
| tipPlacement | string | 'top' | 内容帮助方向 |
| labelTip | string | - | 标签帮助文字 |
| labelTipPlacement | string | 'top' | 标签帮助方向 |

### 联动控制

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| control | function/promise | - | 字段联动控制 (3.5.0+) |

```javascript
{
  label: '类型',
  prop: 'type',
  type: 'select',
  dicData: [{ label: 'A', value: 1 }, { label: 'B', value: 2 }],
  control: (val, form) => {
    return {
      subField: {
        display: val === 1,
        disabled: val === 2,
        dicData: val === 1 ? listA : listB,
      }
    }
  },
}
```

### 渲染函数 (3.4.2+)

```javascript
{
  label: '状态',
  prop: 'status',
  render: (h, { column, $index, dic }) => {
    return h('el-tag', {}, '自定义内容')
  },
}
```

---

## 事件

| 事件名 | 参数 | 说明 |
|-------|------|------|
| submit | (form, done) | 表单提交回调。done() 关闭loading |
| reset-change | - | 表单清空回调 |
| change | (form) | 表单数据变化时触发 |
| tab-click | (tab, event) | 标签页点击时触发 |
| error | (error) | 表单验证失败时触发 |
| mock-change | (form) | Mock 数据生成后触发 |

```javascript
// Composition API
const handleSubmit = (form, done) => {
  saveData(form).then(() => {
    ElMessage.success('保存成功')
    done()  // 关闭loading
  }).catch(() => {
    done()
  })
}

const handleReset = () => {
  // 清空后的逻辑
}
```

---

## 方法

通过 `ref` 调用：

| 方法名 | 参数 | 说明 |
|-------|------|------|
| submit | - | 触发表单提交 |
| resetForm | - | 重置表单 |
| updateDic | (prop, dic) | 更新指定字典 |
| dicInit | - | 重新加载所有字典 |
| getPropRef | (prop) | 获取字段ref对象 |
| validate | callback(boolean, object) | 验证整个表单 |
| validateField | (props, callback) | 验证指定字段 |
| resetFields | - | 重置表单到初始值 |
| clearValidate | (props) | 清除验证结果 |
| show | - | 禁用表单（显示loading状态） |
| hide | - | 启用表单（隐藏loading状态） |
| scrollToField | (prop) | 滚动到指定字段 |
| getField | (prop) | 获取字段实例 |

```javascript
// 手动验证
const formRef = ref(null)
formRef.value.validate((valid, fields) => {
  if (valid) {
    // 验证通过
  } else {
    // 验证失败，fields 包含错误信息
  }
})

// 验证指定字段
formRef.value.validateField(['name', 'code'], (errorMsg) => {
  if (!errorMsg) {
    // 验证通过
  }
})
```

---

## 插槽

| 插槽名 | 说明 |
|-------|------|
| `{prop}` | 自定义字段内容 |
| `{prop}-label` | 自定义字段标签 |
| `{prop}-error` | 自定义错误提示 |
| `{prop}-desc` | 自定义描述 (3.6.4+) |
| menu-form | 自定义按钮区域 |
| menu-form-before | 按钮区域前内容 (3.4.3+) |

```vue
<!-- 自定义字段 -->
<template #name="{ value, column, dic, size, disabled }">
  <el-input v-model="formData.name" />
</template>

<!-- 自定义标签 -->
<template #name-label="{ column }">
  <span style="color: red">{{ column.label }}</span>
</template>

<!-- 自定义按钮 -->
<template #menu-form>
  <el-button type="primary" @click="handleSave">保存</el-button>
  <el-button @click="handleCancel">取消</el-button>
</template>
```

---

## 独立表单使用示例

### Composition API

```vue
<template>
  <avue-form ref="formRef" v-model="formData" :option="option" @submit="handleSubmit" />
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

const formRef = ref(null)
const formData = ref({})

const option = reactive({
  labelWidth: 120,
  column: [
    {
      label: '姓名',
      prop: 'name',
      span: 12,
      rules: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
    },
    {
      label: '性别',
      prop: 'gender',
      type: 'radio',
      span: 12,
      dicData: [
        { label: '男', value: 1 },
        { label: '女', value: 2 },
      ],
    },
  ],
})

const handleSubmit = (form, done) => {
  console.log('提交数据:', form)
  done()
}
</script>
```

### Options API

```vue
<template>
  <avue-form ref="form" v-model="formData" :option="option" @submit="handleSubmit" />
</template>

<script>
export default {
  data() {
    return {
      formData: {},
      option: {
        labelWidth: 120,
        column: [
          {
            label: '姓名',
            prop: 'name',
            span: 12,
            rules: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
          },
          {
            label: '性别',
            prop: 'gender',
            type: 'radio',
            span: 12,
            dicData: [
              { label: '男', value: 1 },
              { label: '女', value: 2 },
            ],
          },
        ],
      },
    }
  },
  methods: {
    handleSubmit(form, done) {
      console.log('提交数据:', form)
      done()
    },
  },
}
</script>
```

---

## 数据类型与过滤

### 数据类型转换

```javascript
{
  label: '标签',
  prop: 'tags',
  type: 'select',
  multiple: true,
  dataType: 'string',    // 存储为逗号分隔字符串
  separator: ',',         // 分隔符，默认逗号
}
// 值: "1,2,3" 而不是 [1, 2, 3]
```

### 数据过滤

```javascript
option: {
  filterDic: true,  // 过滤 $ 开头的字典翻译字段
  filterNull: true, // 过滤空值字段
}
```
