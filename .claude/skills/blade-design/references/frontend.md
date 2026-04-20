# 前端代码生成参考

本文档包含 SpringBlade 开源版 Saber 前端各层的完整代码模板。基于 Vue 3 + Element Plus + Avue 技术栈，支持 Options API 和 Composition API 两种风格。

> 前端 API 路径在 Boot 单体与 Cloud 微服务下**完全一致**，都走 `/blade-{module}/...`。Boot 通过 `AppConstant.APPLICATION_XXX_NAME` 前缀对齐该路径，Cloud 则由 Gateway 按服务名 `blade-{module}` 自动路由。

## 目录
- [一、API 层](#一api-层)
- [二、Option 配置层](#二option-配置层)
- [三、Vue 页面 - Options API](#三vue-页面---options-api)
- [四、Vue 页面 - Composition API](#四vue-页面---composition-api)
- [五、树形结构模式](#五树形结构模式)
- [六、主子表模式](#六主子表模式)

---

## 一、API 层

文件位置：`src/api/{module}/{modelCode}.js`

### 标准 CRUD API

```javascript
import request from '@/axios';

/**
 * 获取{中文名}列表（分页）
 */
export const getList = (current, size, params) => {
  return request({
    url: '/blade-{module}/{modelCode}/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    },
  });
};

/**
 * 获取{中文名}详情
 */
export const getDetail = (id) => {
  return request({
    url: '/blade-{module}/{modelCode}/detail',
    method: 'get',
    params: {
      id,
    },
  });
};

/**
 * 删除{中文名}
 */
export const remove = (ids) => {
  return request({
    url: '/blade-{module}/{modelCode}/remove',
    method: 'post',
    params: {
      ids,
    },
  });
};

/**
 * 新增{中文名}
 */
export const add = (row) => {
  return request({
    url: '/blade-{module}/{modelCode}/submit',
    method: 'post',
    data: row,
  });
};

/**
 * 修改{中文名}
 */
export const update = (row) => {
  return request({
    url: '/blade-{module}/{modelCode}/submit',
    method: 'post',
    data: row,
  });
};
```

**注意事项**：
- 新增和修改共用 `/submit` 接口（后端 `saveOrUpdate`）
- `remove` 的 `ids` 是逗号分隔的字符串
- GET 请求用 `params`，POST 请求用 `data`（请求体）
- 如需加密传输，添加 `cryptoToken: true` 和 `cryptoData: true`

### 树形模式追加

```javascript
/**
 * 获取{中文名}树形结构
 */
export const getTree = (tenantId) => {
  return request({
    url: '/blade-{module}/{modelCode}/tree',
    method: 'get',
    params: {
      tenantId,
    },
  });
};
```

### 导出接口

```javascript
import { exportBlob } from '@/api/common';

/**
 * 导出{中文名}数据
 */
export const exportData = (params) => {
  return exportBlob(`/blade-{module}/{modelCode}/export-{modelCode}`, params);
};
```

---

## 二、Option 配置层

文件位置：`src/option/{module}/{modelCode}.js`

### 标准 CRUD 配置

```javascript
export const tableOption = {
  height: 'auto',
  calcHeight: 30,
  tip: false,
  searchShow: true,
  searchMenuSpan: 6,
  border: true,
  index: true,
  selection: true,
  viewBtn: true,
  dialogClickModal: false,
  column: [
    // --- 文本输入框 ---
    {
      label: '{字段中文名}',
      prop: '{fieldName}',
      search: true,                    // 是否为搜索字段
      rules: [
        {
          required: true,
          message: '请输入{字段中文名}',
          trigger: 'blur',
        },
      ],
    },

    // --- 数字输入 ---
    {
      label: '{字段中文名}',
      prop: '{fieldName}',
      type: 'number',
      align: 'right',
      width: 100,
    },

    // --- 下拉选择（远程字典） ---
    {
      label: '{字段中文名}',
      prop: '{fieldName}',
      type: 'select',
      search: true,
      dicUrl: '/blade-system/dict/dictionary?code={dictCode}',
      props: {
        label: 'dictValue',
        value: 'dictKey',
      },
      rules: [
        {
          required: true,
          message: '请选择{字段中文名}',
          trigger: 'change',
        },
      ],
    },

    // --- 下拉选择（本地字典） ---
    {
      label: '{字段中文名}',
      prop: '{fieldName}',
      type: 'select',
      dicData: [
        { label: '选项A', value: 1 },
        { label: '选项B', value: 2 },
      ],
    },

    // --- 开关 ---
    {
      label: '{字段中文名}',
      prop: '{fieldName}',
      type: 'switch',
      align: 'center',
      width: 100,
      dicData: [
        { label: '否', value: 0 },
        { label: '是', value: 1 },
      ],
      value: 0,
    },

    // --- 单选 ---
    {
      label: '{字段中文名}',
      prop: '{fieldName}',
      type: 'radio',
      dicData: [
        { label: '选项A', value: 1 },
        { label: '选项B', value: 2 },
      ],
      value: 1,
    },

    // --- 多选 ---
    {
      label: '{字段中文名}',
      prop: '{fieldName}',
      type: 'checkbox',
      dicData: [
        { label: '选项A', value: 1 },
        { label: '选项B', value: 2 },
      ],
    },

    // --- 日期选择 ---
    {
      label: '{字段中文名}',
      prop: '{fieldName}',
      type: 'date',
      format: 'YYYY-MM-DD',
      valueFormat: 'YYYY-MM-DD',
    },

    // --- 日期时间选择 ---
    {
      label: '{字段中文名}',
      prop: '{fieldName}',
      type: 'datetime',
      format: 'YYYY-MM-DD HH:mm:ss',
      valueFormat: 'YYYY-MM-DD HH:mm:ss',
    },

    // --- 日期范围搜索 ---
    {
      label: '{字段中文名}',
      prop: '{fieldName}',
      type: 'datetimerange',
      searchRange: true,
      search: true,
      hide: true,                      // 列表不显示
      display: false,                  // 表单不显示
      format: 'YYYY-MM-DD HH:mm:ss',
      valueFormat: 'YYYY-MM-DD HH:mm:ss',
    },

    // --- 多行文本 ---
    {
      label: '{字段中文名}',
      prop: '{fieldName}',
      type: 'textarea',
      span: 24,                        // 占满整行
      hide: true,                      // 列表不显示
      minRows: 3,
    },

    // --- 富文本编辑器 ---
    {
      label: '{字段中文名}',
      prop: '{fieldName}',
      component: 'avue-ueditor',
      options: {
        action: '/blade-resource/oss/endpoint/put-file',
        props: {
          res: 'data',
          url: 'link',
        },
      },
      span: 24,
      hide: true,
      minRows: 6,
    },

    // --- 仅列表显示，表单不显示 ---
    {
      label: '{字段中文名}',
      prop: '{fieldName}',
      addDisplay: false,
      editDisplay: false,
    },

    // --- 仅表单显示，列表不显示 ---
    {
      label: '{字段中文名}',
      prop: '{fieldName}',
      hide: true,
    },

    // --- 自定义插槽 ---
    {
      label: '{字段中文名}',
      prop: '{fieldName}',
      slot: true,                      // 启用自定义渲染插槽
    },

    // --- 备注（隐藏在列表） ---
    {
      label: '备注',
      prop: 'remark',
      hide: true,
    },
  ],
};
```

### 分组表单配置

用于复杂表单（如用户管理），将字段分组到多个 Tab 页：

```javascript
export const tableOption = (safe) => {
  // 自定义校验器（使用闭包访问组件实例）
  const validatePass = (rule, value, callback) => {
    if (value === '') {
      callback(new Error('请输入密码'));
    } else {
      callback();
    }
  };

  return {
    height: 'auto',
    calcHeight: 150,
    tip: false,
    searchShow: true,
    searchMenuSpan: 6,
    border: true,
    index: true,
    selection: true,
    viewBtn: true,
    dialogType: 'drawer',
    dialogWidth: 1000,
    dialogClickModal: false,

    // 列表使用 column
    column: [
      { label: '字段A', prop: 'fieldA', search: true },
      { label: '字段B', prop: 'fieldB' },
    ],

    // 表单使用 group（分组标签页）
    group: [
      {
        label: '基础信息',
        prop: 'baseInfo',
        icon: 'el-icon-user-solid',
        column: [
          { label: '字段A', prop: 'fieldA', rules: [{ required: true, message: '请输入', trigger: 'blur' }] },
          { label: '字段B', prop: 'fieldB', type: 'select', dicData: [] },
        ],
      },
      {
        label: '详细信息',
        prop: 'detailInfo',
        icon: 'el-icon-s-order',
        column: [
          { label: '字段C', prop: 'fieldC' },
          { label: '字段D', prop: 'fieldD', type: 'textarea', span: 24 },
        ],
      },
    ],
  };
};
```

### 列属性速查

| 属性 | 类型 | 说明 |
|------|------|------|
| `label` | String | 列标题/表单标签 |
| `prop` | String | 字段名 |
| `type` | String | 组件类型：input/select/radio/checkbox/switch/date/datetime/textarea/number/tree/upload |
| `search` | Boolean | 是否为搜索字段 |
| `hide` | Boolean | 列表中隐藏 |
| `display` | Boolean | 表单中隐藏（false=不显示） |
| `addDisplay` | Boolean | 新增表单中隐藏 |
| `editDisplay` | Boolean | 编辑表单中隐藏 |
| `addDisabled` | Boolean | 新增时禁用 |
| `editDisabled` | Boolean | 编辑时禁用 |
| `span` | Number | 表单栅格宽度（24=整行） |
| `slot` | Boolean | 启用自定义插槽 |
| `rules` | Array | 校验规则 |
| `dicUrl` | String | 远程字典 URL |
| `dicData` | Array | 本地字典数据 |
| `props` | Object | 字典映射 { label, value } |
| `value` | Any | 默认值 |
| `width` | Number | 列宽度 |
| `align` | String | 对齐方式：left/center/right |
| `overHidden` | Boolean | 超长文本省略显示 |
| `component` | String | 自定义组件名 |
| `searchRange` | Boolean | 搜索使用范围选择 |
| `format` | String | 显示格式 |
| `valueFormat` | String | 值格式 |
| `minRows` | Number | textarea 最小行数 |

---

## 三、Vue 页面 - Options API

文件位置：`src/views/{module}/{modelCode}.vue`

```vue
<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      :page="page"
      :permission="permissionList"
      ref="crud"
      v-model="form"
      v-model:search="query"
      :before-open="beforeOpen"
      @row-del="rowDel"
      @row-update="rowUpdate"
      @row-save="rowSave"
      @search-change="searchChange"
      @search-reset="searchReset"
      @selection-change="selectionChange"
      @current-change="currentChange"
      @size-change="sizeChange"
      @refresh-change="refreshChange"
      @on-load="onLoad"
    >
      <template #menu-left>
        <el-button
          type="primary"
          icon="el-icon-plus"
          @click="$refs.crud.rowAdd()"
          v-if="permission.{modelCode}_add"
        >新 增</el-button>
        <el-button
          type="danger"
          icon="el-icon-delete"
          plain
          @click="handleDelete"
          v-if="permission.{modelCode}_delete"
        >删 除</el-button>
      </template>

      <!-- 自定义列插槽示例 -->
      <template #{slotField}="{ row }">
        <el-tag>{{ row.{slotField} }}</el-tag>
      </template>
    </avue-crud>
  </basic-container>
</template>

<script>
import { getList, getDetail, add, update, remove } from '@/api/{module}/{modelCode}';
import { tableOption } from '@/option/{module}/{modelCode}';
import { mapGetters } from 'vuex';

export default {
  data() {
    return {
      form: {},
      query: {},
      loading: true,
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      selectionList: [],
      option: tableOption,
      data: [],
    };
  },

  computed: {
    ...mapGetters(['permission']),
    permissionList() {
      return {
        addBtn: this.validData(this.permission.{modelCode}_add, false),
        viewBtn: this.validData(this.permission.{modelCode}_view, false),
        delBtn: this.validData(this.permission.{modelCode}_delete, false),
        editBtn: this.validData(this.permission.{modelCode}_edit, false),
      };
    },
    ids() {
      let ids = [];
      this.selectionList.forEach(ele => {
        ids.push(ele.id);
      });
      return ids.join(',');
    },
  },

  methods: {
    rowSave(row, done, loading) {
      add(row).then(
        () => {
          this.onLoad(this.page);
          this.$message({
            type: 'success',
            message: '操作成功!',
          });
          done();
        },
        error => {
          loading();
        }
      );
    },

    rowUpdate(row, index, done, loading) {
      update(row).then(
        () => {
          this.onLoad(this.page);
          this.$message({
            type: 'success',
            message: '操作成功!',
          });
          done();
        },
        error => {
          loading();
        }
      );
    },

    rowDel(row) {
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        return remove(row.id);
      }).then(() => {
        this.onLoad(this.page);
        this.$message({
          type: 'success',
          message: '操作成功!',
        });
      });
    },

    handleDelete() {
      if (this.selectionList.length === 0) {
        this.$message.warning('请选择至少一条数据');
        return;
      }
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        return remove(this.ids);
      }).then(() => {
        this.onLoad(this.page);
        this.$message({
          type: 'success',
          message: '操作成功!',
        });
        this.$refs.crud.toggleSelection();
      });
    },

    beforeOpen(done, type) {
      if (['edit', 'view'].includes(type)) {
        getDetail(this.form.id).then(res => {
          this.form = res.data.data;
        });
      }
      done();
    },

    searchChange(params, done) {
      this.query = params;
      this.page.currentPage = 1;
      this.onLoad(this.page, params);
      done();
    },

    searchReset() {
      this.query = {};
      this.onLoad(this.page);
    },

    selectionChange(list) {
      this.selectionList = list;
    },

    selectionClear() {
      this.selectionList = [];
      this.$refs.crud.toggleSelection();
    },

    currentChange(currentPage) {
      this.page.currentPage = currentPage;
    },

    sizeChange(pageSize) {
      this.page.pageSize = pageSize;
    },

    refreshChange() {
      this.onLoad(this.page, this.query);
    },

    onLoad(page, params = {}) {
      this.loading = true;
      getList(
        page.currentPage,
        page.pageSize,
        Object.assign(params, this.query)
      ).then(res => {
        const data = res.data.data;
        this.page.total = data.total;
        this.data = data.records;
        this.loading = false;
        this.selectionClear();
      });
    },
  },
};
</script>
```

---

## 四、Vue 页面 - Composition API

文件位置：`src/views/{module}/{modelCode}.vue`

```vue
<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      :page="page"
      :permission="permissionList"
      ref="crud"
      v-model="form"
      v-model:search="query"
      :before-open="beforeOpen"
      @row-del="rowDel"
      @row-update="rowUpdate"
      @row-save="rowSave"
      @search-change="searchChange"
      @search-reset="searchReset"
      @selection-change="selectionChange"
      @current-change="currentChange"
      @size-change="sizeChange"
      @refresh-change="refreshChange"
      @on-load="onLoad"
    >
      <template #menu-left>
        <el-button
          type="primary"
          icon="el-icon-plus"
          @click="crud.rowAdd()"
          v-if="permission.{modelCode}_add"
        >新 增</el-button>
        <el-button
          type="danger"
          icon="el-icon-delete"
          plain
          @click="handleDelete"
          v-if="permission.{modelCode}_delete"
        >删 除</el-button>
      </template>
    </avue-crud>
  </basic-container>
</template>

<script setup>
import { ref, reactive, computed } from 'vue';
import { useStore } from 'vuex';
import { ElMessage, ElMessageBox } from 'element-plus';
import { getList, getDetail, add, update, remove } from '@/api/{module}/{modelCode}';
import { tableOption } from '@/option/{module}/{modelCode}';

const store = useStore();
const crud = ref(null);

// 数据
const form = ref({});
const query = ref({});
const loading = ref(true);
const data = ref([]);
const selectionList = ref([]);
const option = reactive(tableOption);
const page = ref({
  pageSize: 10,
  currentPage: 1,
  total: 0,
});

// 权限
const permission = computed(() => store.getters.permission);
const permissionList = computed(() => {
  const validData = (value, defaultVal = false) => (value ? true : defaultVal);
  return {
    addBtn: validData(permission.value.{modelCode}_add),
    viewBtn: validData(permission.value.{modelCode}_view),
    delBtn: validData(permission.value.{modelCode}_delete),
    editBtn: validData(permission.value.{modelCode}_edit),
  };
});

const ids = computed(() => {
  return selectionList.value.map(ele => ele.id).join(',');
});

// 方法
const rowSave = (row, done, loading) => {
  add(row).then(
    () => {
      onLoad(page.value);
      ElMessage.success('操作成功!');
      done();
    },
    error => {
      loading();
    }
  );
};

const rowUpdate = (row, index, done, loading) => {
  update(row).then(
    () => {
      onLoad(page.value);
      ElMessage.success('操作成功!');
      done();
    },
    error => {
      loading();
    }
  );
};

const rowDel = (row) => {
  ElMessageBox.confirm('确定将选择数据删除?', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    return remove(row.id);
  }).then(() => {
    onLoad(page.value);
    ElMessage.success('操作成功!');
  });
};

const handleDelete = () => {
  if (selectionList.value.length === 0) {
    ElMessage.warning('请选择至少一条数据');
    return;
  }
  ElMessageBox.confirm('确定将选择数据删除?', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    return remove(ids.value);
  }).then(() => {
    onLoad(page.value);
    ElMessage.success('操作成功!');
    crud.value.toggleSelection();
  });
};

const beforeOpen = (done, type) => {
  if (['edit', 'view'].includes(type)) {
    getDetail(form.value.id).then(res => {
      form.value = res.data.data;
    });
  }
  done();
};

const searchChange = (params, done) => {
  query.value = params;
  page.value.currentPage = 1;
  onLoad(page.value, params);
  done();
};

const searchReset = () => {
  query.value = {};
  onLoad(page.value);
};

const selectionChange = (list) => {
  selectionList.value = list;
};

const currentChange = (currentPage) => {
  page.value.currentPage = currentPage;
};

const sizeChange = (pageSize) => {
  page.value.pageSize = pageSize;
};

const refreshChange = () => {
  onLoad(page.value, query.value);
};

const onLoad = (page, params = {}) => {
  loading.value = true;
  getList(
    page.currentPage,
    page.pageSize,
    Object.assign(params, query.value)
  ).then(res => {
    const resData = res.data.data;
    page.total = resData.total;
    data.value = resData.records;
    loading.value = false;
    selectionList.value = [];
  });
};
</script>
```

### Options API vs Composition API 转换对照

| Options API | Composition API |
|-------------|-----------------|
| `data() { return { form: {} } }` | `const form = ref({})` |
| `computed: { ids() { ... } }` | `const ids = computed(() => { ... })` |
| `methods: { rowSave() { ... } }` | `const rowSave = () => { ... }` |
| `this.form` | `form.value`（模板中直接用 `form`） |
| `this.$refs.crud` | `crud.value` |
| `this.$message.success(...)` | `ElMessage.success(...)` |
| `this.$confirm(...)` | `ElMessageBox.confirm(...)` |
| `...mapGetters(['permission'])` | `const permission = computed(() => store.getters.permission)` |
| `mounted() { ... }` | `onMounted(() => { ... })` |
| `watch: { ... }` | `watch(source, callback)` |

---

## 五、树形结构模式

### Option 配置（追加树形父节点字段）

```javascript
// 在 column 中添加树形父节点字段
{
  label: '上级{中文名}',
  prop: 'parentId',
  type: 'tree',
  dicData: [],                    // 动态加载
  hide: true,
  value: 0,                       // 默认值为顶级
  props: {
    label: 'title',
    value: 'value',
    children: 'children',
  },
},
```

### Vue 页面（树形数据加载）

在 `onLoad` 之后加载树形数据并更新列配置：

```javascript
// Options API
methods: {
  onLoad(page, params = {}) {
    this.loading = true;
    getList(page.currentPage, page.pageSize, Object.assign(params, this.query)).then(res => {
      const data = res.data.data;
      this.page.total = data.total;
      this.data = data.records;
      this.loading = false;

      // 加载树形数据
      getTree().then(treeRes => {
        const column = this.findObject(this.option.column, 'parentId');
        column.dicData = treeRes.data.data;
      });
    });
  },
},

// Composition API
const onLoad = (page, params = {}) => {
  loading.value = true;
  getList(page.currentPage, page.pageSize, Object.assign(params, query.value)).then(res => {
    const resData = res.data.data;
    page.total = resData.total;
    data.value = resData.records;
    loading.value = false;

    getTree().then(treeRes => {
      const column = findObject(option.column, 'parentId');
      column.dicData = treeRes.data.data;
    });
  });
};
```

### 表格树形展示

在 avue-crud 模板中使用树形属性：

```vue
<avue-crud
  :option="option"
  :data="data"
  row-key="id"
  :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
>
```

---

## 六、主子表模式

### API 层（主表 + 子表）

```javascript
// 主表 API（同标准 CRUD）
export const getList = (current, size, params) => { ... };
export const add = (row) => { ... };
export const update = (row) => { ... };
export const remove = (ids) => { ... };

// 子表 API
export const getSubList = (current, size, {fkFieldName}, params) => {
  return request({
    url: '/blade-{module}/{subModelCode}/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
      {fkFieldName},
    },
  });
};

export const addSub = (row) => {
  return request({
    url: '/blade-{module}/{subModelCode}/submit',
    method: 'post',
    data: row,
  });
};

export const updateSub = (row) => {
  return request({
    url: '/blade-{module}/{subModelCode}/submit',
    method: 'post',
    data: row,
  });
};

export const removeSub = (ids) => {
  return request({
    url: '/blade-{module}/{subModelCode}/remove',
    method: 'post',
    params: { ids },
  });
};
```

### Vue 页面（主子表 Drawer 模式）

```vue
<template>
  <basic-container>
    <!-- 主表 -->
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      :page="page"
      :permission="permissionList"
      ref="crud"
      v-model="form"
      @row-del="rowDel"
      @row-update="rowUpdate"
      @row-save="rowSave"
      @on-load="onLoad"
    >
      <template #menu="{ row }">
        <el-button text type="primary" @click="openSub(row)">子表</el-button>
      </template>
    </avue-crud>

    <!-- 子表抽屉 -->
    <el-drawer
      title="{子表中文名}"
      v-model="subBox"
      size="60%"
      append-to-body
    >
      <avue-crud
        :option="subOption"
        :table-loading="subLoading"
        :data="subData"
        :page="subPage"
        ref="crudSub"
        v-model="subForm"
        @row-del="rowDelSub"
        @row-update="rowUpdateSub"
        @row-save="rowSaveSub"
        @on-load="onLoadSub"
      />
    </el-drawer>
  </basic-container>
</template>

<script>
export default {
  data() {
    return {
      // 主表数据
      form: {},
      data: [],
      loading: true,
      page: { pageSize: 10, currentPage: 1, total: 0 },
      option: tableOption,

      // 子表数据
      subBox: false,
      subForm: {},
      subData: [],
      subLoading: true,
      subPage: { pageSize: 10, currentPage: 1, total: 0 },
      subOption: subTableOption,
      mainId: '',
    };
  },

  methods: {
    openSub(row) {
      this.mainId = row.id;
      this.subBox = true;
      this.onLoadSub(this.subPage);
    },

    rowSaveSub(row, done, loading) {
      row.{fkFieldName} = this.mainId;
      addSub(row).then(
        () => {
          this.onLoadSub(this.subPage);
          this.$message.success('操作成功!');
          done();
        },
        error => {
          loading();
        }
      );
    },

    rowUpdateSub(row, index, done, loading) {
      updateSub(row).then(
        () => {
          this.onLoadSub(this.subPage);
          this.$message.success('操作成功!');
          done();
        },
        error => {
          loading();
        }
      );
    },

    rowDelSub(row) {
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        return removeSub(row.id);
      }).then(() => {
        this.onLoadSub(this.subPage);
        this.$message.success('操作成功!');
      });
    },

    onLoadSub(page, params = {}) {
      this.subLoading = true;
      getSubList(
        page.currentPage,
        page.pageSize,
        this.mainId,
        params
      ).then(res => {
        const data = res.data.data;
        this.subPage.total = data.total;
        this.subData = data.records;
        this.subLoading = false;
      });
    },

    // ... 主表方法同标准 CRUD
  },
};
</script>
```

---

## 附：常用工具函数

### 数组与逗号字符串互转

后端用逗号字符串存储多选值（如 deptId="1,2,3"），前端需要转换：

```javascript
import func from '@/utils/func';

// 提交时：数组 → 逗号字符串
row.deptId = func.join(row.deptId);

// 回显时：逗号字符串 → 数组
this.form.deptId = func.split(this.form.deptId);
```

### 导出功能

```javascript
import NProgress from 'nprogress';
import { exportBlob } from '@/api/common';
import { downloadXls } from '@/utils/util';

const handleExport = () => {
  NProgress.start();
  exportBlob(`/blade-{module}/{modelCode}/export-{modelCode}?${qs.stringify(this.query)}`).then(res => {
    downloadXls(res.data, `{中文名}数据${this.$dayjs().format('YYYY-MM-DD')}.xlsx`);
    NProgress.done();
  });
};
```

### 辅助方法 findObject / findColumn

```javascript
// 在 option 的 column 中查找指定 prop 的列
const column = this.findObject(this.option.column, 'fieldName');

// 在 group 中查找
const column = this.findColumn(this.option.group, 'fieldName');
```

这两个是全局挂载的工具方法（在 `main.js` 中注册）。
