package com.express.mvc;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DispatcherServlet extends javax.servlet.http.HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        String path = config.getInitParameter("contentConfigLocation");//获取文件位置
        InputStream is = DispatcherServlet.class.getClassLoader().getResourceAsStream(path);//读取文件信息
        HandlerMapping.load(is);//加载到load中
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1.    获取用户请求的uri   /xx.do
        String uri = req.getRequestURI();
        HandlerMapping.MVCMapping mapping = HandlerMapping.get(uri);
        if( mapping == null){
            resp.sendError(404,"MVC：映射地址不存在："+uri);
            return;
        }
        Object obj = mapping.getObj();
        Method method = mapping.getMethod();
        Object result = null;
        try {
            result = method.invoke(obj, req, resp);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        switch (mapping.getType()){
            case TEXT:
                resp.getWriter().write((String)result);
                break;
            case VIEW:
                resp.sendRedirect((String)result);
                break;
        }
    }

}
