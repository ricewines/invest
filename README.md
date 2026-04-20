# invest

## 提交代码

### Maco OS预先配置

```shell
INVEST_VERSION="0.1.6"
JAVA_HOME="~/Library/Java/JavaVirtualMachines/openjdk-25/Contents/Home"
$INVEST_VERSION
$JAVA_HOME
```

### Windows 11预先配置

```shell
$$Env:INVEST_VERSION = "0.1.6"
$$Env:INVEST_VERSION
$$Env:JAVA_HOME = "C:\Users\PC\.jdks\openjdk-25.0.2"
$$Env:JAVA_HOME
```

### 开始新的开发

```shell
./gradlew clean
```

```shell
git add .
```

```shell
git commit -m "增加手工调用 #7"
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