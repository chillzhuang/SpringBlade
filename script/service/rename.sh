#!/bin/bash

# Copyright (c) 2018-2028, Chill Zhuang 庄骞 (bladejava@qq.com).
# <p>
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# <p>
# http://www.apache.org/licenses/LICENSE-2.0
# <p>
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

################################################################
# BladeX 一键修改包名与引用路径(注意需要同时将blade-tool修改并安装依赖) #
################################################################
# original_project_path:     填写你需要修改的项目全路径             #
# new_project_suffix:        源工程文件拷贝至新目录的后缀名         #
# old_package:               需要替换的原包名                     #
# new_package:               需要设置的新包名                     #
################################################################

# 原始工程目录路径
original_project_path="/your/project/path/here"
# 新工程目录名后缀
new_project_suffix="-new"
# 设置原始包名
old_package="org.springblade"
# 设置新包名
new_package="com.example"

echo "copying project..."

# 新工程目录路径
new_project_path="${original_project_path}${new_project_suffix}"

# 拷贝工程目录
cp -r "$original_project_path" "$new_project_path"

# 将包名转换为路径
old_package_path=$(echo "$old_package" | sed 's/\./\//g')
new_package_path=$(echo "$new_package" | sed 's/\./\//g')

# 检查操作系统
os_name=$(uname)
sed_i_option="-i"
if [ "$os_name" = "Darwin" ]; then
    # MacOS 需要一个空的扩展名来避免创建备份文件
    sed_i_option="-i ''"
else
    # 对于非MacOS系统，使用-i选项，不提供扩展名，避免创建备份文件
    sed_i_option="-i"
fi

echo "replacing package names and moving files..."
# 查找并替换Java、XML、Properties和YAML文件中的包名
# 同时移动文件到新的目录结构
find "$new_project_path" \( -iname "*.java" -o -iname "*.xml" -o -iname "*.properties" -o -iname "*.yml" -o -iname "*.yaml" \) -print0 | while IFS= read -r -d $'\0' file; do
  # 替换文件中的包名
  if [ "$os_name" = "Darwin" ]; then
    sed -i '' "s|$old_package|$new_package|g" "$file"
  else
    sed -i "s|$old_package|$new_package|g" "$file"
  fi
  echo "modified: $file"

  # 如果文件路径包含旧包名路径，则移动文件
  if echo "$file" | grep -q "$old_package_path"; then
    new_file=$(echo "$file" | sed "s|$old_package_path|$new_package_path|g")
    new_dir=$(dirname "$new_file")
    mkdir -p "$new_dir"
    mv "$file" "$new_file"
    echo "moved to: $new_file"
  fi
done

# 删除空目录的逻辑，确保包括顶层目录在内的所有空目录都被删除
echo "removing empty directories..."
find "$new_project_path" -type d -empty -delete 2>/dev/null

# 从新工程目录的根部开始，递归删除所有空的目录
# 这个过程需要重复，直到没有更多的空目录为止
while IFS= read -r -d '' dir; do
    find "$dir" -type d -empty -delete 2>/dev/null
done < <(find "$new_project_path" -type d -print0 | sort -rz)

# 检查并删除原始包名的顶级目录
top_level_old_package_dir="$new_project_path/$(echo "$old_package" | cut -d '.' -f 1)"
if [ -d "$top_level_old_package_dir" ] && [ ! "$(ls -A "$top_level_old_package_dir")" ]; then
    rm -r "$top_level_old_package_dir"
fi

echo "package name and paths have been updated in the new project directory."
