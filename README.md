# invest

## 提交代码

### Maco OS预先配置

```shell
export INVEST_VERSION=$(cat version.txt | tr -d '\n' | tr -d ' ')
JAVA_HOME="~/Library/Java/JavaVirtualMachines/openjdk-25/Contents/Home"
```

```shell
echo $INVEST_VERSION
echo $JAVA_HOME
```

### Windows 11预先配置

```shell
cd ~/IdeaProjects/ricewines/invest/
```

```shell
$$Env:INVEST_VERSION = (Get-Content "./version.txt" -Raw).Trim()
$$Env:JAVA_HOME = "C:\Users\PC\.jdks\openjdk-26.0.1"
```

```shell
echo "已设置版本：$Env:INVEST_VERSION"
echo "已设置JAVA_HOME：$Env:JAVA_HOME"
```

### 开始新的开发

```shell
./gradlew clean build
```

```shell
git add .
```

```shell
git commit -m "#15 升级版本"
```

```shell
git tag -a v$Env:INVEST_VERSION -m "发布版本$Env:INVEST_VERSION"
```

```shell
git push origin v$Env:INVEST_VERSION
```

```shell
git push origin dev_chixh
```