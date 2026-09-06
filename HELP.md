# HELP.md

## invest 项目本地操作手册

### 1 Windows PowerShell 加载环境变量

读取密钥配置文件，注入进程级环境变量

```shell
Get-Content D:\Users\CXH\data\secret\.env.secret.txt | ForEach-Object {$l=$_.Trim();if($l -and !$l.StartsWith("#")){$i=$l.IndexOf('=');$k=$l.Substring(0,$i).Trim();$v=$l.Substring($i+1).Trim();[Environment]::SetEnvironmentVariable($k,$v,"Process")}} ; 
echo "QAZCXH_163_COM_MAIL_PASSWORD=$env:QAZCXH_163_COM_MAIL_PASSWORD";
echo "ZHI_PU_AI_API_KEY=$env:ZHI_PU_AI_API_KEY";
echo "SPRING_PROFILES_ACTIVE=$env:SPRING_PROFILES_ACTIVE";
echo "INVEST_VERSION=$env:INVEST_VERSION";
; $env:JAVA_HOME = "C:\Users\chixu\.jdks\openjdk-26.0.2" ; echo "已设置JAVA_HOME：$env:JAVA_HOME" ;
# 校验Java版本
& "$env:JAVA_HOME\bin\java.exe" -version ;
```

### 2 切换项目目录

```shell
cd ~/IdeaProjects/ricewines/invest/;
```

### 4 版本升级操作

#### 4.1 统一修改全模块版本（不生成pom备份文件）

```shell
./mvnw versions:set --define newVersion=$env:INVEST_VERSION -DgenerateBackupPoms=false ;
```

#### 4.2 本地编译安装

```shell
./mvnw clean install ;
```

### 5 Git 提交、打标签、推送发布流程

```shell
git add . ; git commit -m "#升级包依赖" ; git tag -a v$env:INVEST_VERSION -m "发布版本$env:INVEST_VERSION" ;
# 推送版本标签与开发分支
git push origin v$env:INVEST_VERSION ; git push origin dev_chixh ;
```

```shell
# 生成站点文档
./mvnw site:site ;
# 本地预览站点
./mvnw site:stage ;
```

### 5.1 初始化时使用

```shell
# 创建孤立分支，不带任何提交历史
git checkout --orphan gh-pages;
# 清空分支下全部文件
git rm -rf .;
# 生成初始空提交
git commit --allow-empty -m "init gh‑pages for maven site";
# 推送到远程仓库
git push origin gh-pages;
# 切回你的主业务分支(main/master)
git checkout main;
```

```shell
# 推送文档至代码仓库
./mvnw scm-publish:publish-scm ;
```
