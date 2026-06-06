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
$ cd ~/IdeaProjects/ricewines/invest/
```

```shell
$ $env:INVEST_VERSION = (Get-Content "./version.txt" -Raw).Trim()
$ $env:JAVA_HOME = "C:\Users\PC\.jdks\openjdk-26.0.1"
```

```shell
echo "已设置版本：$env:INVEST_VERSION"
echo "已设置JAVA_HOME：$env:JAVA_HOME"
```

### 开始新的开发

```shell
./gradlew clean
```

```shell
./gradlew build
```

```shell
git add .
```

```shell
git commit -m "#16 gradle部署中央仓库"
```

```shell
git tag -a v$env:INVEST_VERSION -m "发布版本$env:INVEST_VERSION"
```

```shell
git push origin v$env:INVEST_VERSION
```

```shell
git push origin dev_chixh
```

## 首次生成

```shell
gpg --gen-key
```

## 首次导出

```shell
$ gpg --list-secret-keys --keyid-format=short
```

```shell
$ gpg --export-secret-keys -o secring.gpg
```

```shell
$ gpg --armor --export D0BAB5EB >public.asc
```

```shell
$ ./gradlew.bat clean
```

```shell
$ ./gradlew.bat build
```

```shell
$ ./gradlew.bat publish
```