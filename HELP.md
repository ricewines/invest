# HELP.md

## invest 项目本地操作手册

### 1 Windows PowerShell 加载环境变量

读取密钥配置文件，注入进程级环境变量

```shell
Get-Content D:\Users\CXH\data\secret\.env.secret.txt | ForEach-Object {$l=$_.Trim();if($l -and !$l.StartsWith("#")){$i=$l.IndexOf('=');$k=$l.Substring(0,$i).Trim();$v=$l.Substring($i+1).Trim();[Environment]::SetEnvironmentVariable($k,$v,"Process")}}
```

### 2 校验环境变量是否加载成功

```shell
echo "INVEST_VERSION=$env:INVEST_VERSION";
echo "QAZCXH_163_COM_MAIL_PASSWORD=$env:QAZCXH_163_COM_MAIL_PASSWORD";
echo "ZHI_PU_AI_API_KEY=$env:ZHI_PU_AI_API_KEY";
```

### 3 切换项目目录 + 指定JDK

```shell
cd ~/IdeaProjects/ricewines/invest/ ; $env:JAVA_HOME = "C:\Users\chixu\.jdks\openjdk-26.0.2" ; echo "已设置JAVA_HOME：$env:JAVA_HOME" ;
# 校验Java版本
& "$env:JAVA_HOME\bin\java.exe" -version ;
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
git add . ; git commit -m "#24 GPG公钥同步到了公钥服务器" ; git tag -a v$env:INVEST_VERSION -m "发布版本$env:INVEST_VERSION" ;
# 推送版本标签与开发分支
git push origin v$env:INVEST_VERSION ; git push origin dev_chixh ;
```
