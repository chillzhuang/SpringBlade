# Avue 代码风格指南：Composition API vs Options API

生成 Avue 代码时，需根据用户偏好选择以下两种风格之一。默认使用 Composition API。

## 目录

1. [Composition API 完整模板](#composition-api-完整模板)
2. [Options API 完整模板](#options-api-完整模板)
3. [关键差异对照表](#关键差异对照表)
4. [转换规则](#转换规则)

---

## Composition API 完整模板

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
    >
      <!-- 自定义左侧按钮 -->
      <template #menu-left>
        <el-button type="danger" @click="handleDelete">删 除</el-button>
      </template>
      <!-- 自定义列内容 -->
      <template #category="{ row }">
        <el-tag>{{ row.categoryName }}</el-tag>
      </template>
    </avue-crud>
  </basic-container>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getList, add, update, remove, getDetail } from '@/api/module/entity'

// ============ 响应式数据 ============
const crud = ref(null)
const form = ref({})
const search = ref({})
const loading = ref(true)
const page = ref({
  pageSize: 10,
  currentPage: 1,
  total: 0,
})
const selectionList = ref([])
const data = ref([])

// ============ Vuex ============
const store = useStore()
const permission = computed(() => store.getters.permission)

// ============ 计算属性 ============
const permissionList = computed(() => ({
  addBtn: !!permission.value.module_add,
  viewBtn: !!permission.value.module_view,
  delBtn: !!permission.value.module_delete,
  editBtn: !!permission.value.module_edit,
}))

const ids = computed(() =>
  selectionList.value.map(ele => ele.id).join(',')
)

// ============ Option 配置 ============
const option = reactive({
  height: 'auto',
  calcHeight: 32,
  tip: false,
  searchShow: true,
  searchMenuSpan: 6,
  border: true,
  index: true,
  selection: true,
  dialogClickModal: false,
  column: [
    {
      label: '名称',
      prop: 'name',
      search: true,
      rules: [{ required: true, message: '请输入名称', trigger: 'blur' }],
    },
    {
      label: '状态',
      prop: 'status',
      type: 'select',
      search: true,
      dicData: [
        { label: '启用', value: 1 },
        { label: '禁用', value: 0 },
      ],
    },
  ],
})

// ============ 生命周期 ============
onMounted(() => {
  // 初始化逻辑
})

// ============ 方法 ============
const onLoad = () => {
  loading.value = true
  getList(page.value.currentPage, page.value.pageSize, Object.assign({}, search.value)).then(res => {
    const resData = res.data.data
    page.value.total = resData.total
    data.value = resData.records
    loading.value = false
    selectionClear()
  })
}

const rowSave = (row, done, loading) => {
  add(row).then(() => {
    onLoad()
    ElMessage.success('操作成功!')
    done()
  }).catch(() => {
    loading()
  })
}

const rowUpdate = (row, index, done, loading) => {
  update(row).then(() => {
    onLoad()
    ElMessage.success('操作成功!')
    done()
  }).catch(() => {
    loading()
  })
}

const rowDel = (row) => {
  ElMessageBox.confirm('确定将选择数据删除?', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => remove(row.id))
    .then(() => {
      onLoad()
      ElMessage.success('操作成功!')
    })
}

const handleDelete = () => {
  if (selectionList.value.length === 0) {
    ElMessage.warning('请选择至少一条数据')
    return
  }
  ElMessageBox.confirm('确定将选择数据删除?', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => remove(ids.value))
    .then(() => {
      onLoad()
      ElMessage.success('操作成功!')
    })
}

const beforeOpen = (done, type) => {
  if (['edit', 'view'].includes(type)) {
    getDetail(form.value.id).then(res => {
      form.value = res.data.data
    })
  }
  done()
}

const searchChange = (params, done) => {
  page.value.currentPage = 1
  onLoad()
  done()
}

const searchReset = () => {
  onLoad()
}

const selectionChange = (list) => {
  selectionList.value = list
}

const selectionClear = () => {
  selectionList.value = []
  crud.value?.toggleSelection()
}

const refreshChange = () => {
  onLoad()
}
</script>
```

---

## Options API 完整模板

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
      :permission="permissionList"
      :before-open="beforeOpen"
      @row-save="rowSave"
      @row-update="rowUpdate"
      @row-del="rowDel"
      @search-change="searchChange"
      @search-reset="searchReset"
      @selection-change="selectionChange"
      @current-change="currentChange"
      @size-change="sizeChange"
      @refresh-change="refreshChange"
      @on-load="onLoad"
    >
      <template #menu-left>
        <el-button type="danger" @click="handleDelete">删 除</el-button>
      </template>
      <template #category="{ row }">
        <el-tag>{{ row.categoryName }}</el-tag>
      </template>
    </avue-crud>
  </basic-container>
</template>

<script>
import { mapGetters } from 'vuex'
import { getList, add, update, remove, getDetail } from '@/api/module/entity'

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
      data: [],
      option: {
        height: 'auto',
        calcHeight: 32,
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        border: true,
        index: true,
        selection: true,
        dialogClickModal: false,
        column: [
          {
            label: '名称',
            prop: 'name',
            search: true,
            rules: [{ required: true, message: '请输入名称', trigger: 'blur' }],
          },
          {
            label: '状态',
            prop: 'status',
            type: 'select',
            search: true,
            dicData: [
              { label: '启用', value: 1 },
              { label: '禁用', value: 0 },
            ],
          },
        ],
      },
    }
  },
  computed: {
    ...mapGetters(['permission']),
    permissionList() {
      return {
        addBtn: !!this.permission.module_add,
        viewBtn: !!this.permission.module_view,
        delBtn: !!this.permission.module_delete,
        editBtn: !!this.permission.module_edit,
      }
    },
    ids() {
      return this.selectionList.map(ele => ele.id).join(',')
    },
  },
  methods: {
    onLoad(page, params = {}) {
      this.loading = true
      getList(page.currentPage, page.pageSize, Object.assign(params, this.query)).then(res => {
        const data = res.data.data
        this.page.total = data.total
        this.data = data.records
        this.loading = false
        this.selectionClear()
      })
    },
    rowSave(row, done, loading) {
      add(row).then(() => {
        this.onLoad(this.page)
        this.$message({ type: 'success', message: '操作成功!' })
        done()
      }).catch(() => {
        loading()
      })
    },
    rowUpdate(row, index, done, loading) {
      update(row).then(() => {
        this.onLoad(this.page)
        this.$message({ type: 'success', message: '操作成功!' })
        done()
      }).catch(() => {
        loading()
      })
    },
    rowDel(row) {
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => remove(row.id))
        .then(() => {
          this.onLoad(this.page)
          this.$message({ type: 'success', message: '操作成功!' })
        })
    },
    handleDelete() {
      if (this.selectionList.length === 0) {
        this.$message.warning('请选择至少一条数据')
        return
      }
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => remove(this.ids))
        .then(() => {
          this.onLoad(this.page)
          this.$message({ type: 'success', message: '操作成功!' })
          this.$refs.crud.toggleSelection()
        })
    },
    beforeOpen(done, type) {
      if (['edit', 'view'].includes(type)) {
        getDetail(this.form.id).then(res => {
          this.form = res.data.data
        })
      }
      done()
    },
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
    selectionChange(list) {
      this.selectionList = list
    },
    selectionClear() {
      this.selectionList = []
      this.$refs.crud.toggleSelection()
    },
    currentChange(currentPage) {
      this.page.currentPage = currentPage
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize
    },
    refreshChange() {
      this.onLoad(this.page)
    },
  },
}
</script>
```

---

## 关键差异对照表

| 特性 | Composition API | Options API |
|-----|----------------|-------------|
| **脚本标签** | `<script setup>` | `<script> export default {}` |
| **数据声明** | `const x = ref(val)` / `reactive({})` | `data() { return { x: val } }` |
| **脚本中访问数据** | `x.value` | `this.x` |
| **模板中访问数据** | `{{ x }}` (自动解包) | `{{ x }}` |
| **计算属性** | `const x = computed(() => ...)` | `computed: { x() { ... } }` |
| **方法** | `const fn = () => {}` | `methods: { fn() {} }` |
| **生命周期** | `onMounted(() => {})` | `mounted() {}` |
| **Vuex** | `const store = useStore()` | `...mapGetters([...])` |
| **消息提示** | `ElMessage.success(...)` | `this.$message(...)` |
| **确认框** | `ElMessageBox.confirm(...)` | `this.$confirm(...)` |
| **组件引用** | `const crud = ref(null)` | `this.$refs.crud` |
| **搜索参数** | `v-model:search="search"` 自动同步 | 手动赋值 `this.query = params` |
| **分页事件** | `v-model:page` 自动处理 | 需要 `currentChange`/`sizeChange` |

## 转换规则

从 Composition API 转换为 Options API：
1. `ref()` 声明 → 移入 `data()` return
2. 移除所有 `.value` 访问
3. 箭头函数 → `methods` 对象方法
4. `computed(() => ...)` → `computed` 对象
5. `ElMessage` / `ElMessageBox` → `this.$message` / `this.$confirm`
6. `useStore()` → `mapGetters`
7. `onMounted()` → `mounted()`
8. 添加 `currentChange` / `sizeChange` 事件处理

从 Options API 转换为 Composition API：
1. `data()` 中的属性 → `ref()` 或 `reactive()`
2. 添加 `.value` 访问
3. `methods` 中的方法 → 顶层箭头函数
4. `this.$xxx` → 导入对应模块
5. `v-model:search="search"` 替代 `query` 变量
6. 移除 `currentChange` / `sizeChange`（由 v-model:page 处理）
