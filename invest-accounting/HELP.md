# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/4.1.0/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.1.0/maven-plugin/build-image.html)
* [GraalVM Native Image Support](https://docs.spring.io/spring-boot/4.1.0/reference/packaging/native-image/introducing-graalvm-native-images.html)
* [Function](https://docs.spring.io/spring-cloud-function/reference/)
* [Spring Web](https://docs.spring.io/spring-boot/4.1.0/reference/web/servlet.html)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional Links

These additional references should also help you:

* [Configure AOT settings in Build Plugin](https://docs.spring.io/spring-boot/4.1.0/how-to/aot.html)
* [Various sample apps using Spring Cloud Function](https://github.com/spring-cloud/spring-cloud-function/tree/main/spring-cloud-function-samples)

## GraalVM Native Support

This project has been configured to let you generate either a lightweight container or a native executable. It is also
possible to run your tests in a native image.

### Lightweight Container with Cloud Native Buildpacks

If you're already familiar with Spring Boot container images support, this is the easiest way to get started. Docker
should be installed and configured on your machine prior to creating the image.

To create the image, run the following goal:

```shell
Get-Content D:\Users\CXH\chixuehui-data\secret\.env.secret.txt | ForEach-Object {$l=$_.Trim();if($l -and !$l.StartsWith("#")){$i=$l.IndexOf('=');$k=$l.Substring(0,$i).Trim();$v=$l.Substring($i+1).Trim();[Environment]::SetEnvironmentVariable($k,$v,"Process")}}
```

```shell
$$env:JAVA_HOME="C:\Users\chixu\.jdks\graalvm-ce-25.0.2" ; echo "已设置JAVA_HOME：$env:JAVA_HOME"
```

```shell
cd ~\IdeaProjects\ricewines\invest\invest-accounting
```

```shell
$ ./mvnw clean spring-boot:build-image -Pnative
```

Then, you can run the app like any other container:

```
$ docker run --rm -p 8080:8080 demo-m4:0.0.1-SNAPSHOT
```

### Executable with Native Build Tools

Use this option if you want to explore more options such as running your tests in a native image. The GraalVM
`native-image` compiler should be installed and configured on your machine.

NOTE: GraalVM 25+ is required.

To create the executable, run the following goal:

```shell
$ ./mvnw native:compile -Pnative
```

Then, you can run the app as follows:

```
$ target/demo-m4
```

You can also run your existing tests suite in a native image. This is an efficient way to validate the compatibility of
your application.

To run your existing tests in a native image, run the following goal:

```
$ ./mvnw test -PnativeTest
```

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM. While most of the inheritance is
fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent. To prevent this, the
project POM contains empty overrides for these elements. If you manually switch to a different parent and actually want
the inheritance, you need to remove those overrides.

