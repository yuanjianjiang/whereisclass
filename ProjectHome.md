WhereIsClass是一个在jar中查找指定class的工具，允许在单个或多个文件夹下查找所有jar中的指定class。

# Details #
## GUI ##
### GUI V1.1 2012-05-02 ###
增加了帮助信息
增加了输入文件夹和查询关键字的判断提示；
增加了对文件夹输入的判断提示；
增加文件夹输入框的清除按钮。

### GUI V1.0 2012-05-02 ###
修改了显示结果的界面，支持多内容时候的竖向滚动；
简化了显示结果的空格和制表符；
修复了使用文件选择器出现第一个文件夹名称取不到的Bug；

针对有人提出对Linux Java 1.4 版本的支持，
解决的办法如下：
1、下载源码
2、屏蔽掉OpenAction Line31 @Override
3、修改GUIContext Line14 private static Map<Object, Object> cache = new HashMap<Object, Object>();改为 private static Map cache = new HashMap();

即可

### GUI V0.1 2010-06-23 ###
  * 增加了图形用户界面，用户可以通过界面来操作。
```
>java -jar whereisclass.GUI_v0.1.jar
```

## Command ##

### V0.2 2010-06-23 ###
  * 重构处理方法
  * 修改输入参数判断的Bug
```
java -jar whereisclass_v0.2.jar d:/myJars WhereIsClass
```

### V0.1 2010-06-22 ###

  * 通过命令行运行WhereIsClass
```
>java -jar whereisclass_v0.1.jar d:/myJars WhereIsClass
```