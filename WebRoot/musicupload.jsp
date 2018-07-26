<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
  <head>  
    <base href="<%=basePath%>">  
      
    <title>My JSP 'fileupload.jsp' starting page</title>  
      
    <meta http-equiv="pragma" content="no-cache">  
    <meta http-equiv="cache-control" content="no-cache">  
    <meta http-equiv="expires" content="0">      
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">  
    <meta http-equiv="description" content="This is my page">  
    <!-- 
    <link rel="stylesheet" type="text/css" href="styles.css"> 
    -->  
  
  </head>  
    
  <body>  
     <!-- enctype 默认是 application/x-www-form-urlencoded -->  
     <form action="FileUpLoadAction" enctype="multipart/form-data" method="post" >  
          
               歌      手 ：<input type="text" name="author"><br/>  
               专      辑：<input type="text" name="album"><br/>  
               歌曲封面：<input type="file" name="file1" accept="image/*"><br/>  
               歌       曲：<input type="file" name="file1" accept="audio/*"><br/>  
                 
      <input type="submit" value="提交"/>  
       
     </form>  
    
  </body>  
</html>  