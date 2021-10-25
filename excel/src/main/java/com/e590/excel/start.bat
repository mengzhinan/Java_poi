:: 获取执行当前 Bat 文件的目录
set Current_Path=%~dp0
:: 执行 jar 内的 main() 函数，并传入如下参数：
:: (必填) 参数1：生成的文件保存的路径。
:: (选填，默认值 xls) 参数2：生成的文件扩展名。可选值：xls or csv
:: (选填，默认值 txt) 参数3：允许读取的文件类型。
:: (选填，默认值 >Example) 参数4：是否是相同的表头格式。
:: Example: "记录时间,是否扫描,可用空间,垃圾,微信,QQ,钉钉,企业微信,应用清理,图片清理,视频清理,音频清理,安装包,大文件,重复文件";
:: Demo1，当前 bat 批处理的执行目录；默认生成 xls 文件；默认只读取 txt 文件；默认 Example 表头
:: java -Dfile.encoding=utf-8 -jar excel_tool.jar %Current_Path%

:: Demo2，当前 bat 批处理的执行目录；默认生成 txt 文件；默认只读取 txt 文件；默认 Example 表头
java -Dfile.encoding=utf-8 -jar excel_tool.jar %Current_Path% txt
pause
