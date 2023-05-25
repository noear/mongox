<h1 align="center" style="text-align:center;">
  MongoX
</h1>
<p align="center">
MongoDb ORM 框架（构建类似 sql 的体验，体验风格与 wood 类似）
</p>
<p align="center">
    <a target="_blank" href="https://search.maven.org/search?q=org.noear%20mongox">
        <img src="https://img.shields.io/maven-central/v/org.noear/mongox.svg?label=Maven%20Central" alt="Maven" />
    </a>
    <a target="_blank" href="https://www.apache.org/licenses/LICENSE-2.0.txt">
		<img src="https://img.shields.io/:license-Apache2-blue.svg" alt="Apache 2" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html">
		<img src="https://img.shields.io/badge/JDK-8-green.svg" alt="jdk-8" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html">
		<img src="https://img.shields.io/badge/JDK-11-green.svg" alt="jdk-11" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html">
		<img src="https://img.shields.io/badge/JDK-17-green.svg" alt="jdk-17" />
	</a>
    <br />
    <a target="_blank" href='https://gitee.com/noear/mongox/stargazers'>
		<img src='https://gitee.com/noear/mongox/badge/star.svg' alt='gitee star'/>
	</a>
    <a target="_blank" href='https://github.com/noear/mongox/stargazers'>
		<img src="https://img.shields.io/github/stars/noear/mongox.svg?logo=github" alt="github star"/>
	</a>
</p>
<br/>
<p align="center">
	<a href="https://jq.qq.com/?_wv=1027&k=kjB5JNiC">
	<img src="https://img.shields.io/badge/QQ交流群-22200020-orange"/></a>
</p>

### 入门示例
```java
MgContext db = new MgContext(properties, "demo");
MgContext db = new MgContext("127.0.0.1", 27017, "demo");

//插入
db.table("user")
   .set("id", i)
   .set("type", 1)
   .set("name", "noear")
   .set("nickname", "xidao")
   .insert();

//查询
List<UserModel> list = db.table("user")
        .whereEq("type",1)
        .orderByAsc("id")
        .limit(10,10)
        .selectList(UserModel.class);
```

### 接口字典

#### db.table("table") -> new:MgTableQuery
```swift
//
// 构建相关
//

-build(builder:(tq)->{}) -> self //通过表达式构建自己

-whereTrue()-> self //起一个头，其实什么都没做

-whereMap(map:Map<String,Object>) -> self  //通过map代码设置查询条件

-whereEq(field:String,val:Object) -> self //添加 where = 语句      
-whereNeq(field:String,val:Object) -> self //添加 where != 语句    
-whereLt(field:String,val:Object) -> self //添加 where < 语句
-whereLte(field:String,val:Object) -> self //添加 where <= 语句
-whereGt(field:String,val:Object) -> self //添加 where > 语句
-whereGte(field:String,val:Object) -> self //添加 where >= 语句
-whereLk(field:String,regex:String) -> self //添加 where like 语句（正由表达式）
-whereNlk(field:String,regex:String) -> self //添加 where not like 语句（正由表达式）
-whereBtw(field:String, start:Object, end:Object) -> self //添加 where >=start && <=end 语句
-whereNbtw(field:String, start:Object, end:Object) -> self //添加 where NOT >=start && <=end 语句
-whereIn(field:String, ary:Iterable)-> self //添加 where IN (...) 语句
-whereNin(field:String, ary:Iterable)-> self //添加 where not IN (...) 语句
-whereMod(field:String, base:long, val:long) -> self //添加 where mod(field) = val 语句
-whereNmod(field:String, base:long, val:long) -> self //添加 where not mod(field) = val 语句
-whereAll(field:String, ary:Iterable)-> self //添加 where ALL (...) 语句
-whereSize(field:String, size:long) -> self //添加 where $size(field) = size 语句
-whereExists(field:String, exists:bool) -> self //添加 where $exists() = exists 语句

-andEq(field:String,val:Object) -> self //添加 and = 语句      
-andNeq(field:String,val:Object) -> self //添加 and != 语句    
-andLt(field:String,val:Object) -> self //添加 and < 语句
-andLte(field:String,val:Object) -> self //添加 and <= 语句
-andGt(field:String,val:Object) -> self //添加 and > 语句
-andGte(field:String,val:Object) -> self //添加 and >= 语句
-andLk(field:String,regex:String) -> self //添加 and like 语句（正由表达式）
-andNlk(field:String,regex:String) -> self //添加 and not like 语句（正由表达式）
-andBtw(field:String, start:Object, end:Object) -> self //添加 and >=start && <=end 语句
-andNbtw(field:String, start:Object, end:Object) -> self //添加 and NOT >=start && <=end 语句
-andIn(field:String, ary:Iterable)-> self //添加 and IN (...) 语句
-andNin(field:String, ary:Iterable)-> self //添加 and not IN (...) 语句
-andMod(field:String, base:long, val:long) -> self //添加 and mod(field) = val 语句
-andNmod(field:String, base:long, val:long) -> self //添加 and not mod(field) = val 语句
-andAll(field:String, ary:Iterable)-> self //添加 and ALL (...) 语句
-andSize(field:String, size:long) -> self //添加 where $size(field) = size 语句
-andExists(field:String, exists:bool) -> self //添加 where $exists() = exists 语句


-set(name:String,value:Object) -> self  //设置变量
-setInc(name:String,value:long) -> self //设置自增变量
-setMap(data:Map<String,Object>) -> self    //设置变量(将map输入) 
-setMapIf(data:Map<String,Object>,(k,v)->bool) -> self
-setEntity(data:Object) -> self     //设置变量(将实体输入) 
-setEntityIf(data:Object,(k,v)->bool) -> self


-orderByAsc(field:String) -> self //添加 order by .. ASC语句
-orderByDesc(field:String) -> self //添加 order by .. DESC语句
-andByAsc(field:String) -> self //添加 order by .. ASC语句
-andByDesc(field:String) -> self //添加 order by .. DESC语句

-limit(size:int) -> self //添加 分页语句
-limit(start:int, size:int) -> self //添加 分页语句

//
// 执行相关
//
-insert() -> void //执行插入，使用set接口的数据
-insert(data:Map<String,Object>) -> long //执行插入，并使用data数据
-insertList(dataList:List<Map<String, Object>>) -> void //执行批量插入

-update() -> long //执行更新并返回影响行数，使用set接口的数据；并且需要条件语句

-replace() - > long //执行替换并返回影响行数，使用set接口的数据；并且需要条件语句

-delete() -> long //执行删除，并返回影响行数；并且需要条件语句

-selectExists() -> boolean //执行查询，并返回存在情况
-selectCount() -> long //执行查询，并返回数量
-selectItem(Class<T> clz)-> T
-selectList(Class<T> clz)-> List<T>
-selectMap()-> Document
-selectMapList()-> List<Document>
-selectArray(field:String)-> List<Object>
-selectCursor()-> FindIterable<Document>


//
// 缓存控制相关
//
-caching(service:ICacheService) //使用一个缓存服务
-usingCache(isCache:boolean) //是否使用缓存
-usingCache(seconds:int) //使用缓存时间（单位：秒）
-cacheTag(tag:String) //为缓存添加标签
```

