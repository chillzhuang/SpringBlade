# Avue 全局配置参考

## 安装依赖

```bash
npm i @smallwei/avue -S
# 或
yarn add @smallwei/avue -S
```

**必须依赖：**
- `element-plus` - UI组件库
- `@element-plus/icons-vue` - 图标库

**可选依赖：**
- `axios` - 字典组件和上传组件需要

## 完整安装示例

```javascript
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import { createApp } from 'vue'
import Avue from '@smallwei/avue'
import '@smallwei/avue/lib/index.css'

const app = createApp({})

// 注册所有图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.use(ElementPlus)
app.use(Avue)
```

## 带 axios 的安装（字典/上传组件必需）

```javascript
import axios from 'axios'
const app = createApp({})
app.use(Avue, { axios })
```

## 全局配置选项

```javascript
app.use(AVUE, {
  size: 'small',           // 组件默认尺寸: 'small' | 'mini' | 'medium'
  crudOption: {},           // Crud组件全局默认配置
  formOption: {},           // Form组件全局默认配置
  appendToBody: true,       // 弹窗是否追加到body
  modalAppendToBody: true,  // 遮罩层是否追加到body
  cos: {},                  // 腾讯云COS配置
  qiniu: {},                // 七牛云配置
  ali: {},                  // 阿里云OSS配置
  canvas: {},               // 全局水印配置
})
```

### Crud 全局配置示例

```javascript
app.use(Avue, {
  crudOption: {
    border: true,
    stripe: true,
    index: true,
    align: 'center',
    headerAlign: 'center',
    menuWidth: 200,
    searchLabelWidth: 110,
    height: 'auto',
  }
})
```

### Form 全局配置示例

```javascript
app.use(Avue, {
  formOption: {
    labelWidth: 120,
    labelPosition: 'right',
    size: 'small',
  }
})
```

## 云存储配置

### 阿里云 OSS

```javascript
app.use(AVUE, {
  ali: {
    region: 'oss-cn-beijing',
    endpoint: 'oss-cn-beijing.aliyuncs.com',
    stsToken: '',
    accessKeyId: 'your-access-key-id',
    accessKeySecret: 'your-access-key-secret',
    bucket: 'your-bucket-name',
  }
})
```

### 七牛云

```javascript
// 需引入 CryptoJS：
// <script src="https://cdn.staticfile.net/crypto-js/4.0.0/crypto-js.min.js"></script>
app.use(AVUE, {
  qiniu: {
    AK: 'your-access-key',
    SK: 'your-secret-key',
    scope: 'your-scope',
    url: 'https://upload.qiniup.com',
    deadline: 1,
  }
})
```

### 腾讯云 COS

```javascript
// 需引入 cos-js-sdk-v5：
// <script src="https://cdn.staticfile.net/cos-js-sdk-v5/1.3.2/cos-js-sdk-v5.min.js"></script>
app.use(AVUE, {
  cos: {
    SecretId: 'your-secret-id',
    SecretKey: 'your-secret-key',
    Bucket: 'your-bucket',
    Region: 'ap-beijing',
  }
})
```

## 全局水印配置

```javascript
app.use(AVUE, {
  canvas: {
    text: 'avuejs.com',        // 水印文字
    fontFamily: 'microsoft yahei', // 字体
    color: '#999',              // 颜色
    fontSize: 16,               // 字号
    opacity: 100,               // 透明度
    bottom: 10,                 // 底部距离
    right: 10,                  // 右侧距离
    ratio: 1,                   // 压缩比 0-1
  }
})
```

## 国际化

```javascript
import Avue from '@smallwei/avue'
import enLocale from '@smallwei/avue/lib/locale/lang/en'
import zhLocale from '@smallwei/avue/lib/locale/lang/zh'

// 使用英文
app.use(Avue, { locale: enLocale })

// 配合 vue-i18n
const messages = {
  en: { ...enLocale },
  zh: { ...zhLocale },
}
```

支持的语言：`zh`（简体中文）、`en`（英文）。

## 外部库引入（按需）

```html
<!-- Excel 导入导出 -->
<script src="https://cdn.staticfile.net/FileSaver.js/2014-11-29/FileSaver.min.js"></script>
<script src="https://cdn.staticfile.net/xlsx/0.18.2/xlsx.full.min.js"></script>

<!-- 拖拽排序 -->
<script src="https://cdn.staticfile.net/Sortable/1.10.0-rc2/Sortable.min.js"></script>

<!-- 高德地图 -->
<script>
  window._AMapSecurityConfig = { securityJsCode: 'xxxxx' }
</script>
<script src="https://webapi.amap.com/maps?v=2.0&key=xxxxxxxx&plugin=AMap.PlaceSearch,AMap.Geocoder"></script>
<script src="https://webapi.amap.com/ui/1.1/main.js?v=1.0.11"></script>

<!-- 图标库 -->
<script src="//at.alicdn.com/t/font_2621503_zcbiqy2g1i.js"></script>

<!-- 流程图 -->
<script src="https://cdn.staticfile.net/jsPlumb/2.11.1/js/jsplumb.min.js"></script>

<!-- 截图 -->
<script src="https://cdn.staticfile.net/html2canvas/0.5.0-beta4/html2canvas.min.js"></script>

<!-- PDF导出 -->
<script src="https://cdn.staticfile.net/jspdf/1.5.3/jspdf.min.js"></script>

<!-- Mock数据 -->
<script src="https://cdn.staticfile.net/Mock.js/1.0.1-beta3/mock-min.js"></script>
```
