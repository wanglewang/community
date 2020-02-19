#社区
## 资料
[Spring 文档](https://spring.io/guides)  
[Spring Web](https://spring.io/guides/gs/serving-web-content/)  
[es社区](https://elasticsearch.cn/explore)  
[GitHub deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)  
[BootStrap](https://v3.bootcss.com/getting-started/)  
[GitHub OAuth Document](https://developer.github.com/apps/building-github-apps/creating-a-github-app/)
## 工具
[Git](https://git-scm.com/)  
[Visual Paradigm](https://www.visual-paradigm.com/)

## 脚本
```sql
create table USER
(
    ID           INTEGER auto_increment,
    ACCOUNT_ID   VARCHAR(100),
    NAME         VARCHAR(50),
    TOKEN        CHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    constraint USER_PK
        primary key (ID)
);
```

## fly:way
```
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```