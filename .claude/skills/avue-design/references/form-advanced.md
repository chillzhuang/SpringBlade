# avue-form 高级功能参考

## 目录
1. [字典配置](#字典配置)
2. [验证规则](#验证规则)
3. [动态子表单](#动态子表单)
4. [文件上传](#文件上传)
5. [字段联动](#字段联动)
6. [数据格式](#数据格式)
7. [自定义插槽](#自定义插槽)
8. [Mock数据](#mock数据)

---

## 字典配置

### 安装配置

字典组件需要 axios：
```javascript
import axios from 'axios'
app.use(Avue, { axios })
```

### 本地字典

```javascript
{
  label: '类型',
  prop: 'type',
  type: 'select',
  dicData: [
    { label: '类型A', value: 1 },
    { label: '类型B', value: 2, disabled: true },
  ],
}
```

3.5.0+ 支持函数和 Promise：
```javascript
{
  dicData: () => {
    return [{ label: 'A', value: 1 }]
  },
  // 或
  dicData: async () => {
    const res = await fetchOptions()
    return res.data
  },
}
```

### 远程字典

```javascript
{
  label: '部门',
  prop: 'deptId',
  type: 'select',
  dicUrl: '/api/dept/list',
  dicMethod: 'GET',      // 请求方法，默认GET
  dicQuery: { status: 1 }, // 请求参数
  dicHeaders: {},          // 请求头
  props: {
    label: 'name',
    value: 'id',
    children: 'children',
    res: 'data.records',  // 响应数据层级
  },
}
```

### 字典格式化

```javascript
{
  dicUrl: '/api/dict/list',
  dicFormatter: (res) => {
    // 自定义处理响应数据
    return res.data.map(item => ({
      label: item.dictValue,
      value: item.dictKey,
    }))
  },
}
```

### 字典联动

```javascript
column: [
  {
    label: '省份',
    prop: 'province',
    type: 'select',
    dicUrl: '/api/province',
    cascader: ['city', 'district'],  // 指定子级字段
  },
  {
    label: '城市',
    prop: 'city',
    type: 'select',
    dicUrl: '/api/city?province={{key}}',  // {{key}} = 父级选中值
    cascader: ['district'],
  },
  {
    label: '区县',
    prop: 'district',
    type: 'select',
    dicUrl: '/api/district?city={{key}}',
  },
]
```

### 动态更新字典

```javascript
// 更新指定字段字典
formRef.value.updateDic('type', [
  { label: '新选项A', value: 'a' },
  { label: '新选项B', value: 'b' },
])

// 重新加载所有字典
formRef.value.dicInit()
```

### 数据类型转换

```javascript
{
  label: '标签',
  prop: 'tags',
  type: 'select',
  multiple: true,
  dataType: 'string',    // 值存储为字符串
  separator: ',',         // 分隔符，默认逗号
  // 实际存储: "1,2,3"
  // 组件显示: [1, 2, 3]
}

{
  label: '年龄',
  prop: 'age',
  dataType: 'number',    // 强制转数字
}
```

---

## 验证规则

基于 [async-validator](https://github.com/yiminghe/async-validator) 库。

### 基本验证

```javascript
{
  label: '名称',
  prop: 'name',
  rules: [
    { required: true, message: '请输入名称', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在2-20之间', trigger: 'blur' },
  ],
}
```

### 类型验证

```javascript
rules: [
  { type: 'email', message: '请输入正确的邮箱', trigger: 'blur' },
  { type: 'url', message: '请输入正确的URL', trigger: 'blur' },
  { type: 'number', message: '请输入数字', trigger: 'blur' },
]
```

### 自定义验证

```javascript
{
  label: '密码',
  prop: 'password',
  rules: [{
    required: true,
    validator: (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入密码'))
      } else if (value.length < 6) {
        callback(new Error('密码长度不能少于6位'))
      } else {
        callback()
      }
    },
    trigger: 'blur',
  }],
}
```

### 表单验证方法

```javascript
// 验证整个表单
formRef.value.validate((valid, fields) => {
  if (valid) {
    // 验证通过
  } else {
    // 验证失败
    console.log('错误字段:', fields)
  }
})

// 验证指定字段
formRef.value.validateField(['name', 'email'], (errorMsg) => {
  if (!errorMsg) console.log('验证通过')
})

// 清除验证
formRef.value.clearValidate()            // 清除所有
formRef.value.clearValidate(['name'])    // 清除指定字段

// 重置表单
formRef.value.resetFields()
```

### 搜索验证 (CRUD)

```javascript
{
  label: '日期',
  prop: 'date',
  search: true,
  searchRules: [{ required: true, message: '请选择日期', trigger: 'change' }],
}
```

---

## 动态子表单

### 表格模式

```javascript
{
  label: '订单明细',
  prop: 'items',
  type: 'dynamic',
  span: 24,
  children: {
    align: 'center',
    headerAlign: 'center',
    index: true,
    column: [
      {
        label: '商品名称',
        prop: 'productName',
        rules: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
      },
      {
        label: '数量',
        prop: 'quantity',
        type: 'number',
        min: 1,
      },
      {
        label: '单价',
        prop: 'price',
        type: 'number',
        precision: 2,
      },
    ],
  },
}
```

### 表单模式

```javascript
{
  label: '联系方式',
  prop: 'contacts',
  type: 'dynamic',
  span: 24,
  limit: 5,  // 最多5条
  children: {
    type: 'form',
    index: false,
    column: [
      { label: '姓名', prop: 'name', span: 8 },
      { label: '电话', prop: 'phone', span: 8 },
      { label: '职位', prop: 'position', span: 8 },
    ],
  },
}
```

### 自定义增删

```javascript
{
  type: 'dynamic',
  rowAdd: (done) => {
    // 自定义新增逻辑
    done({ name: '默认值', quantity: 1 })
  },
  rowDel: (row, done) => {
    ElMessageBox.confirm('确定删除?').then(() => done())
  },
}
```

### 父子联动

动态子表单支持与父表单字段联动。

---

## 文件上传

### 基本上传

```javascript
{
  label: '附件',
  prop: 'file',
  type: 'upload',
  action: '/api/upload',
  propsHttp: {
    res: 'data',
    url: 'link',
    name: 'originalName',
    fileName: 'file',
  },
}
```

### 上传回调

```vue
<avue-form
  :upload-before="uploadBefore"
  :upload-after="uploadAfter"
  :upload-delete="uploadDelete"
  :upload-preview="uploadPreview"
  :upload-error="uploadError"
  :upload-exceed="uploadExceed"
>

const uploadBefore = (file, done, loading) => {
  // 验证文件
  if (file.size > 10 * 1024 * 1024) {
    ElMessage.error('文件不能超过10MB')
    loading()  // 中断上传
    return
  }
  done(file)  // 继续上传
}

const uploadAfter = (res, done) => {
  // 上传完成
  done()
}

const uploadDelete = (file, column) => {
  // 返回 false 或 rejected Promise 阻止删除
  return new Promise((resolve, reject) => {
    ElMessageBox.confirm('确定删除?')
      .then(() => resolve())
      .catch(() => reject())
  })
}

const uploadPreview = (file, column, done) => {
  // 自定义预览
  window.open(file.url)
}

const uploadError = (error, column) => {
  ElMessage.error('上传失败')
}

const uploadExceed = (limit, files, fileList, column) => {
  ElMessage.warning(`最多上传${limit}个文件`)
}
```

### 图片水印

```javascript
{
  type: 'upload',
  canvasOption: {
    text: '公司名称',
    fontFamily: 'microsoft yahei',
    color: 'rgba(0,0,0,0.3)',
    fontSize: 20,
    opacity: 50,
    bottom: 20,
    right: 20,
    ratio: 0.8,
  },
}
```

### 云存储上传

全局配置见 `global-config.md`。

```javascript
// 阿里云OSS
{ type: 'upload', oss: 'ali' }

// 七牛云
{ type: 'upload', oss: 'qiniu' }

// 腾讯COS
{ type: 'upload', oss: 'cos' }
```

### 数据格式

```javascript
// 字符串格式（逗号分隔URL）
{ type: 'upload', dataType: 'string' }

// 对象格式
{
  type: 'upload',
  dataType: 'object',
  props: { label: 'name', value: 'url' },
}

// JSON字符串格式
{ type: 'upload', dataType: 'json' }
```

---

## 字段联动

### control 属性

```javascript
{
  label: '类型',
  prop: 'type',
  type: 'radio',
  dicData: [
    { label: '个人', value: 1 },
    { label: '企业', value: 2 },
  ],
  control: (val, form) => {
    if (val === 1) {
      return {
        idCard: { display: true, rules: [{ required: true }] },
        companyName: { display: false },
        businessLicense: { display: false },
      }
    } else {
      return {
        idCard: { display: false },
        companyName: { display: true, rules: [{ required: true }] },
        businessLicense: { display: true },
      }
    }
  },
},
{ label: '身份证', prop: 'idCard' },
{ label: '企业名称', prop: 'companyName', display: false },
{ label: '营业执照', prop: 'businessLicense', type: 'upload', display: false },
```

### control 返回值可控制的属性

可以动态控制字段的以下属性：
- `display` - 显示/隐藏
- `disabled` - 启用/禁用
- `rules` - 验证规则
- `dicData` - 字典数据
- `label` - 标签文字
- 其他 column 属性

### Promise 支持 (3.5.0+)

```javascript
control: async (val, form) => {
  const res = await getSubOptions(val)
  return {
    subType: {
      dicData: res.data,
      display: true,
    },
  }
}
```

### 获取组件引用

```javascript
const ref = formRef.value.getPropRef('tableProp')
// 可用于操作 input-table 等复杂组件
```

---

## 数据格式

### 深层数据绑定

```javascript
// 数据: { id: 1, info: { name: '张三', address: { city: '北京' } } }
column: [
  {
    label: '姓名',
    prop: 'name',
    bind: 'info.name',
  },
  {
    label: '城市',
    prop: 'city',
    bind: 'info.address.city',
  },
]
```

### 数据过滤

```javascript
option: {
  filterDic: true,   // 过滤 $xxx 字典翻译字段
  filterNull: true,  // 过滤空值字段
  filterParams: ['id'], // 不被清空的字段（默认包含 rowKey）
}
```

---

## 自定义插槽

### 字段内容插槽

```vue
<avue-form v-model="form" :option="option">
  <!-- 自定义字段 -->
  <template #name="{ value, column, dic, size, disabled }">
    <el-input v-model="form.name" :disabled="disabled" />
  </template>

  <!-- 自定义标签 -->
  <template #name-label="{ column }">
    <span style="color: red">*</span> {{ column.label }}
  </template>

  <!-- 自定义错误 -->
  <template #name-error="{ error }">
    <span style="color: orange">{{ error }}</span>
  </template>

  <!-- 自定义描述 (3.6.4+) -->
  <template #name-desc="{ column }">
    <el-text type="info">请输入真实姓名</el-text>
  </template>

  <!-- 自定义按钮 -->
  <template #menu-form>
    <el-button type="primary" @click="handleSave">保存</el-button>
    <el-button @click="handleDraft">保存草稿</el-button>
  </template>
</avue-form>
```

### render 渲染函数 (3.4.2+)

```javascript
{
  label: '状态',
  prop: 'status',
  render: (h, { column, $index, dic }) => {
    return h('el-switch', {
      modelValue: form.value.status,
      'onUpdate:modelValue': (val) => { form.value.status = val },
    })
  },
}
```

### 第三方组件集成

```vue
<template #editor="{ value, column }">
  <vue-editor v-model="form.content" />
</template>
```

### className 样式控制

```javascript
{
  label: '名称',
  prop: 'name',
  className: 'highlight-field',
}
```

```css
.highlight-field {
  background: #fef0f0;
  border-left: 3px solid #f56c6c;
}
```

---

## Mock数据

需引入 Mock.js：
```html
<script src="https://cdn.staticfile.net/Mock.js/1.0.1-beta3/mock-min.js"></script>
```

```javascript
option: {
  mockBtn: true,  // 显示Mock按钮
  column: [
    {
      label: '姓名',
      prop: 'name',
      mock: { type: 'cname' },  // 中文姓名
    },
    {
      label: '邮箱',
      prop: 'email',
      mock: { type: 'email' },
    },
    {
      label: '地址',
      prop: 'address',
      mock: { type: 'county', prefix: true },
    },
  ],
}

// 或整体 mock 函数
option: {
  mock: () => ({
    name: '张三',
    email: 'zhangsan@test.com',
    address: '北京市朝阳区',
  }),
}
```
