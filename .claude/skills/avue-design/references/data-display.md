# Avue 数据展示组件参考

## 组件列表

| 组件 | 说明 |
|-----|------|
| avue-data-display | 数据展示卡片（核心组件） |
| avue-data-pay | 支付数据 |
| avue-data-tabs | 标签数据 |
| avue-data-box | 数据盒子 |
| avue-data-card | 数据卡片 |
| avue-data-cardtext | 文字卡片 |
| avue-data-icons | 图标数据 |
| avue-data-operatext | 操作文字 |
| avue-data-progress | 进度数据 |
| avue-data-rotate | 旋转数据 |
| avue-data-imgtext | 图文数据 |
| avue-data-panel | 面板 |
| avue-data-price | 价格 |
| avue-data-countdown | 倒计时 |
| avue-data-dashboard | 仪表盘 |
| avue-data-list | 数据列表 |
| avue-data-notice | 公告 |
| avue-data-product | 商品卡片 |
| avue-data-profile | 人员档案 |
| avue-data-rank | 排行榜 |
| avue-data-statistic | 统计值 |
| avue-data-task | 任务列表 |
| avue-data-weather | 天气卡片 |

## avue-data-display（核心组件）

### 属性

| 属性 | 类型 | 默认值 | 说明 |
|-----|------|-------|------|
| animation | boolean | true | 是否动画 |
| decimals | number | 0 | 小数位数 |
| span | string | '8' | 栅格数 |
| data | array | - | 数据数组 |

### 数据结构

```javascript
const data = [
  {
    title: '今日访问',
    count: 1234,
    icon: 'el-icon-view',
    color: '#40c9c6',
    decimals: 0,
  },
  {
    title: '销售额',
    count: 56789.50,
    icon: 'el-icon-money',
    color: '#36a3f7',
    decimals: 2,
  },
]
```

### 使用示例

```vue
<avue-data-display :data="displayData" :option="{ decimals: 0, animation: true }" />
```

## 通用使用模式

大部分数据展示组件遵循相同模式：

```vue
<component :data="data" :option="option">
  <!-- 可选插槽自定义 -->
</component>
```

各组件的具体配置请参考对应的示例页面：
- `data/data0` ~ `data/data12` - 基础数据组件示例
- `data/countdown` - 倒计时
- `data/dashboard` - 仪表盘
- `data/list` - 数据列表
- `data/notice` - 公告
- `data/product` - 商品卡片
- `data/profile` - 人员档案
- `data/rank` - 排行榜
- `data/statistic` - 统计值
- `data/task` - 任务列表
- `data/weather` - 天气卡片
