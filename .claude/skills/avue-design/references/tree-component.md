# avue-tree 树组件参考

## 基本用法

```vue
<avue-tree
  :option="option"
  :data="treeData"
  @node-click="nodeClick"
  @save="handleSave"
  @update="handleUpdate"
  @del="handleDel"
/>
```

## Option 属性

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| defaultExpandAll | boolean | false | 默认展开所有 |
| virtualize | boolean | false | 虚拟DOM模式 |
| dialogWidth | string | '50%' | 弹窗宽度 |
| formOption | object | - | 自定义表单（参考avue-form） |
| menu | boolean | true | 显示菜单栏 |
| addBtn | boolean | true | 新增按钮 |
| editBtn | boolean | true | 编辑按钮 |
| delBtn | boolean | true | 删除按钮 |
| filter | boolean | true | 显示搜索框 |
| props | object | - | 配置选项 |
| lazy | boolean | false | 懒加载 |

## Props 属性

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| label | string | 'label' | 节点名称属性 |
| labelText | string | '名称' | 弹窗新增名称 |
| children | string | 'children' | 子节点属性 |
| value | string | 'value' | 节点值属性和nodeKey |

## 数据结构

```javascript
const treeData = ref([
  {
    label: '一级节点',
    value: '1',
    children: [
      { label: '二级节点A', value: '1-1' },
      { label: '二级节点B', value: '1-2' },
    ],
  },
])
```

## 事件

| 事件名 | 参数 | 说明 |
|-------|------|------|
| save | (parent, data, done, loading) | 新增回调 |
| update | (parent, data, done, loading) | 编辑回调 |
| del | (data, done) | 删除回调 |
| before-open | (done, type) | 打开前回调 |
| before-close | (done, type) | 关闭前回调 |
| node-click | (data, node, component) | 节点点击 |
| check-change | (data, checked, hasCheckedChildren) | 勾选变化 |

## 方法

| 方法名 | 参数 | 说明 |
|-------|------|------|
| filter | (value) | 筛选树节点 |
| updateKeyChildren | (key, data) | 更新子节点 |
| getCheckedNodes | (leafOnly, includeHalfChecked) | 获取勾选节点 |
| setCheckedNodes | (nodes) | 设置勾选节点 |
| getCheckedKeys | (leafOnly) | 获取勾选keys |
| setCheckedKeys | (keys, leafOnly) | 设置勾选keys |
| setChecked | (key/data, checked, deep) | 设置勾选状态 |
| getHalfCheckedNodes | - | 获取半选节点 |
| getHalfCheckedKeys | - | 获取半选keys |
| getCurrentKey | - | 获取当前选中key |
| getCurrentNode | - | 获取当前选中数据 |
| setCurrentKey | (key) | 设置当前选中 |
| setCurrentNode | (node) | 设置当前选中 |
| getNode | (data) | 获取节点 |
| remove | (data) | 删除节点 |
| append | (data, parentNode) | 追加子节点 |
| insertBefore | (data, refNode) | 在前面插入 |
| insertAfter | (data, refNode) | 在后面插入 |

## 插槽

| 插槽名 | 说明 |
|-------|------|
| menu | 按钮插槽 |
| add-btn | 新增按钮插槽 |
| default | 节点内容插槽 |

## 示例

### 多选模式

```vue
<avue-tree :option="{ ...option, multiple: true }" :data="treeData" @check-change="checkChange" />
```

### 懒加载

```vue
<avue-tree :option="option" :data="treeData">

const option = reactive({
  lazy: true,
  props: { label: 'name', value: 'id' },
})

// 在 treeLoad 中处理加载逻辑
```

### 拖拽

参考 `default/tree/drag` 示例。

### 权限控制

参考 `default/tree/permission` 示例。

### 虚拟模式

```javascript
option: { virtualize: true }
// 适用于大数据量场景
```
