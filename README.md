# invest

## 提交

```shell
$ cd ~/IdeaProjects/ricewines/invest/ ; $env:INVEST_VERSION = (Get-Content "./version.txt" -Raw).Trim() ; $env:JAVA_HOME = "C:\Users\PC\.jdks\openjdk-26.0.1" ; echo "已设置版本：$env:INVEST_VERSION" ; echo "已设置JAVA_HOME：$env:JAVA_HOME"
```

### 开始新的开发

```shell
./gradlew clean build
```

```shell
git add . ; git commit -m "#17 修改投资比例提示词" ; git tag -a v$env:INVEST_VERSION -m "发布版本$env:INVEST_VERSION"
```

```shell
git push origin v$env:INVEST_VERSION ; git push origin dev_chixh
```
