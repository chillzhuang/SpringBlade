# Avue 全局工具函数参考

## $DialogForm - 弹窗表单

```javascript
import { getCurrentInstance } from "vue"
import { $DialogForm } from "@smallwei/avue"

const { appContext } = getCurrentInstance()

// 打开弹窗表单
const dialog = $DialogForm(appContext)({
  option: {
    submitText: "完成",
    column: [
      {
        label: "姓名",
        prop: "name",
        span: 24,
        rules: [{ required: true, message: "请输入姓名", trigger: "blur" }],
      },
    ],
  },
})

// 关闭弹窗
dialog.close()
```

3.5.2+ 版本支持 `close()` 方法主动关闭。

## $Clipboard - 复制剪切板

```javascript
import { $Clipboard } from "@smallwei/avue"

$Clipboard({ text: "要复制的文本" })
  .then(() => ElMessage.success("复制成功"))
  .catch(() => ElMessage.error("复制失败"))
```

## $ImagePreview - 图片预览

```javascript
import { getCurrentInstance } from "vue"
import { $ImagePreview } from "@smallwei/avue"

const { appContext } = getCurrentInstance()
const datas = [
  { thumbUrl: "缩略图URL", url: "大图URL" },
  { thumbUrl: "缩略图URL2", url: "大图URL2" },
]

$ImagePreview(appContext)(datas, 0)  // 第二个参数为起始索引
```

## $Print - 打印

```javascript
import { $Print } from "@smallwei/avue"

// 传入 DOM 元素的 id 或 class
$Print("#test")
```

## $Export - Excel 导出

```javascript
import { $Export } from "@smallwei/avue"

// 需引入 FileSaver.js 和 xlsx
$Export.excel({
  title: "文档标题",
  columns: [
    { label: "标题", prop: "title" },
    { label: "内容", prop: "content" },
  ],
  data: [
    { title: "测试1", content: "内容1" },
    { title: "测试2", content: "内容2" },
  ],
})
```

## $Log - 彩色日志

```javascript
import { $Log } from "@smallwei/avue"

// 内置颜色: default(#35495E), primary(#3488ff), success(#43B883), warning(#e6a23c), danger(#f56c6c)
$Log.capsule("标题", "内容", "primary")
$Log.primary("内容")
$Log.success("内容")
$Log.warning("内容")
$Log.danger("内容")
```

## findObject - 查找列配置对象

```javascript
import { findObject } from "@smallwei/avue"

const option = { column: [{ label: '名称', prop: 'name' }] }
const nameColumn = findObject(option.column, 'name')
// 返回: { label: '名称', prop: 'name' }
```

## findNode - 在数组中查找对象

```javascript
import { findNode } from "@smallwei/avue"

const list = [{ prop: "name" }, { prop: "sex" }]
const obj = findNode(list, "sex", "prop")  // 返回 {prop: 'sex'}
```

## findArray - 查找对象在数组中的索引

```javascript
import { findArray } from "@smallwei/avue"

const list = [{ prop: "name" }, { prop: "sex" }]
const index = findArray(list, "sex", "prop")  // 返回 1
```

## downFile - 文件下载

```javascript
import { downFile } from "@smallwei/avue"

downFile("https://example.com/file.pdf", "document.pdf")
```

## randomId - 生成随机ID

```javascript
import { randomId } from "@smallwei/avue"

const id = randomId()  // 返回随机字符串
```

## loadScript - 动态加载脚本

```javascript
import { loadScript } from "@smallwei/avue"

// 加载 JS
loadScript("js", "https://cdn.example.com/lib.js").then(() => {
  // JS 加载完成
})

// 加载 CSS
loadScript("css", "https://cdn.example.com/style.css").then(() => {
  // CSS 加载完成
})
```

## deepClone - 深拷贝

```javascript
import { deepClone } from "@smallwei/avue"

const original = { name: "张三", info: { age: 18 } }
const cloned = deepClone(original)
```

## setPx - 设置像素值

```javascript
import { setPx } from "@smallwei/avue"

setPx(23)       // '23px'
setPx("100%")   // '100%'
setPx("23px")   // '23px'
```

## validatenull - 判断空值

```javascript
import { validatenull } from "@smallwei/avue"

validatenull({})         // true
validatenull([])         // true
validatenull("")         // true
validatenull(undefined)  // true
validatenull(null)       // true
validatenull("hello")    // false
```

## validData - 数据验证并提供默认值

```javascript
import { validData } from "@smallwei/avue"

validData(undefined, "默认值")     // '默认值'
validData({}, { name: 11 })        // { name: 11 }
validData(true, false)             // true (非空返回原值)
```
