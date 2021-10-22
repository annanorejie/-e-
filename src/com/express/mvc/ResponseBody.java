package com.express.mvc;

import java.lang.annotation.*;

@Target(ElementType.METHOD)//表示该注释可以用于方法上
@Retention(RetentionPolicy.RUNTIME)//表示只在运行期间有效
@Documented//表示生成到文档里面
/**
 * 注解的作用：
 *  被此注解添加的方法， 会被用于处理请求。
 *  方法返回的内容，会以文字形式返回到客户端
 */
public @interface ResponseBody {
    String value();
}
