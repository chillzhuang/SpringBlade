# Blade Commit

通用 Git 提交工具，基于 Gitmoji 规范生成风格统一的提交信息。

## 快速开始

```bash
# 简单模式（默认）— 单行 gitmoji + 描述
/blade-commit

# 详细模式 — gitmoji + conventional commit + 变更列表
/blade-commit -d

# 自定义信息 — 自动补全 gitmoji 格式
/blade-commit -m "新增接口权限模块"
```

## 两种模式

### 简单模式（默认）

单行格式，适用于日常小改动：

```
:sparkles: 新增角色授权接口
:bug: 修复用户分页查询未过滤已删除数据
:zap: 优化字典查询排序逻辑
```

### 详细模式（`-d`）

多行格式，适用于涉及多文件的较大提交：

```
:sparkles: feat(system): 新增接口权限模块

- 新增 ApiScope 实体与对应 Mapper / Service / Controller
- 新增 ApiScopeHandler 处理端点级权限过滤
- 在 RoleController 中追加接口权限授权接口
```

## 常用 Gitmoji

| Gitmoji | 图标 | 场景 |
|---|---|---|
| `:sparkles:` | ✨ | 新增功能 |
| `:bug:` | 🐛 | 修复 Bug |
| `:zap:` | ⚡ | 性能优化 |
| `:recycle:` | ♻️ | 重构 |
| `:memo:` | 📝 | 文档变更 |
| `:wrench:` | 🔧 | 配置变更 |
| `:fire:` | 🔥 | 移除代码 |
| `:tada:` | 🎉 | 发布版本 |
| `:white_check_mark:` | ✅ | 测试 |
| `:lock:` | 🔒 | 安全修复 |

完整对照表见 SKILL.md。

## 安全保障

- **只做 commit**，禁止 push、pull 等任何远程操作
- **零署名**，不添加任何 AI/工具署名
- **逐文件暂存**，不使用 `git add .`，排除敏感文件
- **自动适配**，根据项目历史提交记录匹配语言和风格
