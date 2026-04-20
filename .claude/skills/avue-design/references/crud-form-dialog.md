# avue-crud 弹窗表单配置参考

## 弹窗基本配置

option 中的弹窗属性：

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| addTitle | string | '新增' | 新增弹窗标题 |
| editTitle | string | '修改' | 修改弹窗标题 |
| viewTitle | string | '查看' | 查看弹窗标题 |
| dialogType | string | 'dialog' | 弹窗类型: dialog/drawer |
| dialogWidth | string | '60%' | 弹窗宽度 |
| dialogTop | number | 25 | 弹窗距顶部 |
| dialogDrag | boolean | false | 可拖拽 |
| dialogFullscreen | boolean | false | 全屏 |
| dialogCustomClass | string | - | 自定义class |
| dialogEscape | boolean | true | ESC关闭 |
| dialogClickModal | boolean | false | 点击遮罩关闭 |
| dialogCloseBtn | boolean | true | 关闭按钮 |
| dialogModal | boolean | true | 遮罩层 |
| dialogMenuPosition | string | 'right' | 按钮位置: left/center/right |
| dialogDirection | string | 'rtl' | 抽屉方向: rtl/ltr/ttb/tbb |

## 打开/关闭回调

```vue
<avue-crud :before-open="beforeOpen" :before-close="beforeClose">

<!-- Composition API -->
const beforeOpen = (done, type, loading) => {
  // type: 'add' | 'edit' | 'view'
  if (['edit', 'view'].includes(type)) {
    // 加载详情数据
    loading()  // 显示loading (3.5.2+)
    getDetail(form.value.id).then(res => {
      form.value = res.data.data
      done()   // 完成加载
    })
  } else {
    done()
  }
}

const beforeClose = (done, type) => {
  done()
}
```

## 表单验证规则

```javascript
column: [
  {
    label: '名称',
    prop: 'name',
    rules: [
      { required: true, message: '请输入名称', trigger: 'blur' },
      { min: 2, max: 20, message: '长度在2到20之间', trigger: 'blur' },
    ],
  },
  {
    label: '邮箱',
    prop: 'email',
    rules: [
      { required: true, message: '请输入邮箱', trigger: 'blur' },
      { type: 'email', message: '请输入正确的邮箱', trigger: 'blur' },
    ],
  },
  {
    label: '年龄',
    prop: 'age',
    type: 'number',
    rules: [{
      required: true,
      validator: (rule, value, callback) => {
        if (value < 18) {
          callback(new Error('年龄不能小于18岁'))
        } else {
          callback()
        }
      },
      trigger: 'blur',
    }],
  },
]
```

验证规则基于 [async-validator](https://github.com/yiminghe/async-validator) 库。

## 字段显示/禁用控制

```javascript
column: [
  {
    label: '名称',
    prop: 'name',
    // 全局控制
    display: true,       // 是否显示
    disabled: false,     // 是否禁用
    // 按操作类型控制
    addDisplay: true,    // 新增时显示
    editDisplay: true,   // 编辑时显示
    viewDisplay: true,   // 查看时显示
    addDisabled: false,  // 新增时禁用
    editDisabled: true,  // 编辑时禁用（如ID不可编辑）
    viewDisabled: false,
  },
]
```

## 表单默认值

```javascript
column: [
  {
    label: '状态',
    prop: 'status',
    type: 'select',
    value: 1,  // 新增时默认值
    dicData: [
      { label: '启用', value: 1 },
      { label: '禁用', value: 0 },
    ],
  },
]
```

## 字段联动控制 (control)

```javascript
column: [
  {
    label: '类型',
    prop: 'type',
    type: 'select',
    dicData: [
      { label: '文章', value: 1 },
      { label: '链接', value: 2 },
    ],
    control: (val, form) => {
      if (val === 1) {
        return {
          content: { display: true },
          url: { display: false },
        }
      } else {
        return {
          content: { display: false },
          url: { display: true },
        }
      }
    },
  },
  { label: '内容', prop: 'content', type: 'textarea' },
  { label: '链接', prop: 'url', display: false },
]
```

3.5.0+ 支持 Promise：
```javascript
control: async (val, form) => {
  const res = await getConfig(val)
  return { subType: { dicData: res.data } }
}
```

## 表单分组 (group)

```javascript
option: {
  group: [
    {
      label: '基本信息',
      icon: 'el-icon-info',
      collapse: false,  // 默认展开
      prop: 'base',
      column: [
        { label: '名称', prop: 'name' },
        { label: '编码', prop: 'code' },
      ],
    },
    {
      label: '详细信息',
      icon: 'el-icon-document',
      prop: 'detail',
      column: [
        { label: '描述', prop: 'description', type: 'textarea' },
      ],
    },
  ],
  // 使用标签页显示分组
  tabs: true,
  // column 也要配置（表格列用）
  column: [
    { label: '名称', prop: 'name' },
    { label: '编码', prop: 'code' },
    { label: '描述', prop: 'description' },
  ],
}
```

## 表单标签宽度

```javascript
option: {
  labelWidth: 120,        // 全局标签宽度
  labelPosition: 'right', // 全局标签位置: left/right/top
}

// 也可在 column 中单独设置
{
  label: '很长的标签名称',
  prop: 'longLabel',
  labelWidth: 180,
}
```

## 数据过滤

```javascript
option: {
  filterDic: true,   // 过滤字典翻译字段（$开头的字段）
  filterNull: true,  // 过滤空值字段
}
```

## 防止重复提交

```javascript
// row-save 和 row-update 中的 done 和 loading 参数：
// - done(): 关闭表单弹窗
// - loading(): 关闭loading但保持表单打开（用于接口报错时）

const rowSave = (row, done, loading) => {
  add(row).then(() => {
    ElMessage.success('操作成功')
    done()     // 关闭弹窗
    onLoad()
  }).catch(() => {
    loading()  // 关闭loading，保持弹窗
  })
}
```

## 表单字段排序

```javascript
column: [
  { label: '名称', prop: 'name', order: 1 },
  { label: '编码', prop: 'code', order: 3 },  // order大的排前面
  { label: '状态', prop: 'status', order: 2 },
]
```

## 获取字段组件引用

```javascript
const ref = crudRef.value.getPropRef('name')
// ref 即为该字段对应的组件实例
```

## 连续新增（不关闭弹窗）

通过不调用 `done()` 来保持弹窗打开，实现连续新增。

## 自定义表单插槽

```vue
<!-- 自定义表单字段 -->
<template #name-form="{ value, column, dic, size, disabled }">
  <el-input v-model="form.name" />
</template>

<!-- 自定义表单标签 -->
<template #name-label="{ column }">
  <span style="color: red">{{ column.label }}</span>
</template>

<!-- 自定义错误提示 -->
<template #name-error="{ error }">
  <span style="color: orange">{{ error }}</span>
</template>

<!-- 自定义描述 (3.6.4+) -->
<template #name-desc="{ column }">
  <span>这是一段描述文字</span>
</template>

<!-- 自定义表单按钮 -->
<template #menu-form="{ type }">
  <el-button v-if="type === 'add'" type="primary">自定义保存</el-button>
</template>
```

## 权限控制

```javascript
// 对象形式
<avue-crud :permission="{ addBtn: true, editBtn: true, delBtn: false }">

// 函数形式（行级控制）
<avue-crud :permission="getPermission">

const getPermission = (key, row, index) => {
  // key: 'addBtn' | 'editBtn' | 'delBtn' | 'viewBtn' | 'menu'
  if (key === 'editBtn' && row?.status === 1) {
    return false  // 已启用的不允许编辑
  }
  return true
}
```
