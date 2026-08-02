# README.md

# invest 后端项目说明

## 一、项目简述

invest 金融业务后端项目，基于Spring Boot + Maven GraalVM Native 构建，制品支持发布至 Maven Central 中央仓库。

## 二、关键规范

1. **GPG签名强制规范**
   Maven Central 会直接拒绝无密码、无有效公钥的GPG签名制品； 发布前必须为GPG私钥设置密码，并将公钥上传至
   `keys.openpgp.org` / `keyserver.ubuntu.com` 公钥服务器。
2. **版本管理规范**
   通过 `versions:set` 统一管理多模块版本，默认关闭pom备份文件生成。
3. **依赖配置**
   邮件服务、智谱AI接口密钥通过本地环境文件 `.env.secret.txt` 注入，禁止明文写在代码/配置文件。

## 三、配套技术说明

1. 构建工具：Maven / Maven Wrapper
2. JDK 版本：OpenJDK 26.0.2
3. 支持 GraalVM Native 原生镜像打包
4. 制品发布：Sonatype Maven Central

## 四、环境配置说明

敏感配置统一存放本地路径：`D:\Users\CXH\data\secret\.env.secret.txt`
包含配置项：

- INVEST_VERSION：项目版本号
- QAZCXH_163_COM_MAIL_PASSWORD：163邮箱密码
- ZHI_PU_AI_API_KEY：智谱AI调用密钥

## 五、构建与发布注意事项

1. 正式发布前必须校验GPG公钥已同步至公钥服务器，等待3~10分钟同步完成再执行deploy；
2. 发布打包命令需增加参数跳过Spring Boot重打包、Native镜像构建：

```shell
./mvnw clean deploy -Dmaven.test.skip=true -DskipNativeBuild -Dspring-boot.repackage.skip=true
```

3. 所有敏感密钥、密码仅通过环境变量注入，禁止写入代码仓库。