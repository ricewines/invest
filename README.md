# invest

```shell
Get-Content D:\Users\CXH\data\secret\.env.secret.txt | ForEach-Object {$l=$_.Trim();if($l -and !$l.StartsWith("#")){$i=$l.IndexOf('=');$k=$l.Substring(0,$i).Trim();$v=$l.Substring($i+1).Trim();[Environment]::SetEnvironmentVariable($k,$v,"Process")}}
```

```shell
echo "FRPC_WEB_SERVER_PASSWORD=$env:FRPC_WEB_SERVER_PASSWORD";
echo "INVEST_VERSION=$env:INVEST_VERSION";
echo "QAZCXH_163_COM_MAIL_PASSWORD=$env:QAZCXH_163_COM_MAIL_PASSWORD";
echo "ZHI_PU_AI_API_KEY=$env:ZHI_PU_AI_API_KEY";
```

## 提交

```shell
$ cd ~/IdeaProjects/ricewines/invest/ ; $env:JAVA_HOME = "C:\Users\chixu\.jdks\openjdk-26.0.1" ; echo "已设置JAVA_HOME：$env:JAVA_HOME"
```

### 开始新的开发

```shell
./gradlew clean build
```

```shell
git add . ; git commit -m "#18 重新编写部署脚本，升级依赖包" ; git tag -a v$env:INVEST_VERSION -m "发布版本$env:INVEST_VERSION"
```

```shell
git push origin v$env:INVEST_VERSION ; git push origin dev_chixh
```
