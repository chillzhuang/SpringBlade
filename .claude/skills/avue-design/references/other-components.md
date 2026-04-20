# Avue 其他组件参考

## 目录
1. [Card 卡片](#card-卡片)
2. [DialogForm 弹窗表单](#dialogform-弹窗表单)
3. [Tabs 选项卡](#tabs-选项卡)
4. [Search 搜索](#search-搜索)
5. [Calendar 日历](#calendar-日历)
6. [Flow 流程图](#flow-流程图)
7. [Login 登录](#login-登录)
8. [Article 文章](#article-文章)
9. [Chat 聊天](#chat-聊天)
10. [CountUp 数字动画](#countup-数字动画)
11. [Clipboard 剪切板](#clipboard-剪切板)
12. [Draggable 拖拽](#draggable-拖拽)
13. [TextEllipsis 文本省略](#textellipsis-文本省略)
14. [Sign 电子签名](#sign-电子签名)
15. [Print 打印](#print-打印)
16. [Export 导入导出](#export-导入导出)
17. [ImagePreview 图片预览](#imagepreview-图片预览)
18. [Contextmenu 右键菜单](#contextmenu-右键菜单)
19. [Video 摄像头](#video-摄像头)
20. [Verify 验证码](#verify-验证码)
21. [Screenshot 截图](#screenshot-截图)
22. [License 授权书](#license-授权书)

---

## Card 卡片

```vue
<avue-card :option="option" :data="data" @row-click="rowClick" @row-add="rowAdd">
  <template #menu="{ row, index }">
    <el-button @click="handleEdit(row)">编辑</el-button>
  </template>
</avue-card>
```

### Option 属性

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| addBtn | boolean | true | 新增按钮 |
| span | number | 8 | 栅格数 |
| gutter | number | 20 | 间距 |

### Props 属性

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| title | string | 'title' | 标题属性 |
| img | string | 'img' | 头像属性 |
| info | string | 'info' | 副标题属性 |

### 事件

| 事件名 | 参数 | 说明 |
|-------|------|------|
| row-add | - | 新增 |
| row-click | (row, index) | 行点击 |

---

## DialogForm 弹窗表单

全局API方式调用 (3.4.1+)：

```javascript
import { $DialogForm } from "@smallwei/avue"
import { getCurrentInstance } from "vue"

const { appContext } = getCurrentInstance()

const dialog = $DialogForm(appContext)({
  option: {
    submitText: "确认",
    column: [
      { label: "姓名", prop: "name", span: 24 },
    ],
  },
})

// 3.5.2+ 主动关闭
dialog.close()
```

也可作为组件使用，参考 `default/dialog-form/index` 示例。

---

## Tabs 选项卡

```vue
<avue-tabs :option="option" @change="tabChange" />
```

配合 form、crud 组件使用。

---

## Search 搜索

```vue
<avue-search v-model="searchForm" :option="option" @change="searchChange" />
```

配合 form、crud 组件使用。

---

## Calendar 日历

```vue
<avue-calendar :option="option" :data="calendarData" />
```

---

## Flow 流程图

需引入 jsPlumb：
```html
<script src="https://cdn.staticfile.net/jsPlumb/2.11.1/js/jsplumb.min.js"></script>
```

```vue
<avue-flow :option="option" :width="800" :height="600" @click="nodeClick" />
```

### 属性

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| width | number | - | 画布宽度 |
| height | number | - | 画布高度 |
| option | object | - | 配置 |

### 方法

| 方法名 | 参数 | 说明 |
|-------|------|------|
| nodeAdd | (name) | 添加节点 |
| deleteNode | (id) | 删除节点 |

---

## Login 登录

```vue
<avue-login :option="option" @submit="handleLogin" />
```

---

## Article 文章

```vue
<avue-article :id="container" :offset-top="0" />
```

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| id | string | 'window' | 指定父元素 |
| offset-top | number | 0 | 触发偏移量 |

---

## Chat 聊天

```vue
<avue-chat :option="option" />
```

支持图片、视频、文件、地图等消息类型。

---

## CountUp 数字动画

```vue
<avue-count-up :start="0" :end="12345" :decimals="0" :duration="2" />
```

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| start | number | 0 | 起始数字 |
| end | number | - | 结束数字 |
| decimals | number | 0 | 小数位数 |
| duration | number | 2 | 动画时长(秒) |
| options | object | - | CountUp.js配置 |
| callback | function | - | 开始回调 |

---

## Clipboard 剪切板

```vue
<avue-clipboard :text="copyText" />
```

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| text | string | - | 要复制的文字 |

---

## Draggable 拖拽

```vue
<avue-draggable :width="200" :height="100" :top="50" :left="100" :disabled="false">
  <div>可拖拽内容</div>
</avue-draggable>
```

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| disabled | boolean | false | 禁用拖拽 |
| width | string | - | 宽度 |
| height | string | - | 高度 |
| top | number | 0 | X位置 |
| left | number | 0 | Y位置 |
| z-index | number | 1 | 层级 |

### 事件

| 事件名 | 说明 |
|-------|------|
| focus | 获取焦点 |
| blur | 失去焦点 |

---

## TextEllipsis 文本省略

```vue
<avue-text-ellipsis :text="longText" :height="60" :is-limit-height="true" :use-tooltip="true" />
```

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| text | string | - | 文本内容 |
| width | number | - | 宽度限制 |
| height | number | - | 高度限制 |
| is-limit-height | boolean | true | 启用高度限制 |
| use-tooltip | boolean | false | 使用tooltip |
| placement | string | - | tooltip方向 |

---

## Sign 电子签名

```vue
<avue-sign />
```

兼容移动端和PC端。

---

## Print 打印

```vue
<avue-print :id="printId" :html="htmlContent" />
```

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| id | string | - | DOM元素ID |
| html | string | - | HTML代码片段 |

---

## Export 导入导出

需引入外部库：
```html
<script src="https://cdn.staticfile.net/FileSaver.js/2014-11-29/FileSaver.min.js"></script>
<script src="https://cdn.staticfile.net/xlsx/0.18.2/xlsx.full.min.js"></script>
```

```javascript
import { $Export } from "@smallwei/avue"

// 导出
$Export.excel({
  title: '导出标题',
  columns: [{ label: '姓名', prop: 'name' }],
  data: [{ name: '张三' }],
})
```

---

## ImagePreview 图片预览

```javascript
import { $ImagePreview } from "@smallwei/avue"
import { getCurrentInstance } from "vue"

const { appContext } = getCurrentInstance()
const images = [
  { thumbUrl: '缩略图.jpg', url: '原图.jpg' },
]
$ImagePreview(appContext)(images, 0)
```

---

## Contextmenu 右键菜单

```vue
<div v-contextmenu="menuOptions">
  右键点击此区域
</div>
```

---

## Video 摄像头

```vue
<avue-video ref="video" />
```

支持 start/end 方法控制录制。

---

## Verify 验证码

```vue
<avue-verify />
```

---

## Screenshot 截图

需引入 html2canvas：
```html
<script src="https://cdn.staticfile.net/html2canvas/0.5.0-beta4/html2canvas.min.js"></script>
```

---

## License 授权书

需引入 jsPDF (用于PDF导出)：
```html
<script src="https://cdn.staticfile.net/jspdf/1.5.3/jspdf.min.js"></script>
```
