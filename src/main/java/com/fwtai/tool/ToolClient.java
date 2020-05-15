package com.fwtai.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwtai.bean.FormData;
import com.fwtai.config.ConfigFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端请求|服务器端响应工具类
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年1月11日 19:20:50
 * @QQ号码 444141300
 * @主页 http://www.fwtai.com
*/
public final class ToolClient implements Serializable{

	private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(ToolClient.class);

	/**
	 * 生成简单类型json字符串,仅用于查询返回,客户端只需判断code是否为200才操作,仅用于查询操作,除了list集合之外都可以用data.map获取数据,list的是data.listData,字符串或数字对应是obj
	 * @作者 田应平
	 * @注意 如果传递的是List则在客户端解析listData的key值,即data.listData;是map或HashMap或PageFormData解析map的key值,即data.map;否则解析obj的key值即data.obj或data.map
	 * @用法 解析后判断data.code == AppKey.code.code200 即可
	 * @返回值类型 返回的是json字符串,内部采用JSONObject封装,不可用于redis缓存value
	 * @创建时间 2017年1月11日 上午10:27:53
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public final static String queryJson(final Object object){
        if(object == null || object.toString().trim().length() <= 0){
            return queryEmpty();
        }
        final JSONObject json = new JSONObject();
        if (object instanceof Exception) {
            json.put(ConfigFile.code,ConfigFile.code204);
            json.put(ConfigFile.msg,ConfigFile.msg204);
            json.put(ConfigFile.data,object);
            return json.toJSONString();
        }
        if(object instanceof Map<?,?>){
            final Map<?,?> map = (Map<?,?>) object;
            if(map == null || map.size() <= 0){
                queryEmpty();
            }else {
                json.put(ConfigFile.code,ConfigFile.code200);
                json.put(ConfigFile.msg,ConfigFile.msg200);
                json.put(ConfigFile.data,object);
                return json.toJSONString();
            }
        }
        if(object instanceof List<?>){
            final List<?> list = (List<?>) object;
            if(list == null || list.size() <= 0){
                return queryEmpty();
            }else {
                if (list.get(0) == null){
                    return queryEmpty();
                }else {
                    json.put(ConfigFile.code,ConfigFile.code200);
                    json.put(ConfigFile.msg,ConfigFile.msg200);
                    json.put(ConfigFile.data,object);
                    final String jsonObj = json.toJSONString();
                    final JSONObject j = JSONObject.parseObject(jsonObj);
                    final String listData = j.getString(ConfigFile.data);
                    if (listData.equals("[{}]")){
                        return queryEmpty();
                    }
                    return jsonObj;
                }
            }
        }
        if(String.valueOf(object).toLowerCase().equals("null") || String.valueOf(object).replaceAll("\\s*", "").length() == 0){
            return queryEmpty();
        }else {
            json.put(ConfigFile.code,ConfigFile.code200);
            json.put(ConfigFile.msg,ConfigFile.msg200);
            json.put(ConfigFile.data,object);
            final String jsonObj = json.toJSONString();
            final JSONObject j = JSONObject.parseObject(jsonObj);
            final String obj = j.getString(ConfigFile.data);
            if (obj.equals("{}")){
                return queryEmpty();
            }
            return jsonObj;
        }
	}

	/**
	 * 查询时得到的数据为空返回的json字符串
	 * @作者 田应平
	 * @返回值类型 String
	 * @创建时间 2017年1月11日 下午9:40:21
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	private static final String queryEmpty(){
		final JSONObject json = new JSONObject();
		json.put(ConfigFile.code,ConfigFile.code201);
		json.put(ConfigFile.msg,ConfigFile.msg201);
		return json.toJSONString();
	}

	/**
	 * 生成json字符串对象,直接采用JSONObject封装,执行效率会更高;适用于为增、删、改操作时,判断当前的rows是否大于0来确定是否操作成功,一般在service调用,偷懒的人可以使用本方法
	 * @param rows 执行后受影响的行数
	 * @用法 解析后判断data.code == AppKey.code.code200即可操作
	 * @作者 田应平
	 * @创建时间 2016年12月25日 下午5:44:23
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public static final String executeRows(final int rows){
		final JSONObject json = new JSONObject();
		if(rows > 0){
			json.put(ConfigFile.code,ConfigFile.code200);
			json.put(ConfigFile.msg,ConfigFile.msg200);
			json.put(ConfigFile.data,rows);
			return json.toJSONString();
		}else{
			json.put(ConfigFile.code,ConfigFile.code199);
			json.put(ConfigFile.msg,ConfigFile.msg199);
			json.put(ConfigFile.data,rows);
			return json.toJSONString();
		}
	}

    /**
     * 操作成功生成json字符串对象,失败信息是ConfigFile.msg199,直接采用JSONObject封装,执行效率会更高;适用于为增、删、改操作,一般在service调用
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2020/1/19 11:31
    */
    public static final String executeRows(final int rows,final String success){
        final JSONObject json = new JSONObject();
        if(rows > 0){
            json.put(ConfigFile.code,ConfigFile.code200);
            json.put(ConfigFile.msg,success);
            json.put(ConfigFile.data,rows);
            return json.toJSONString();
        }else{
            json.put(ConfigFile.code,ConfigFile.code199);
            json.put(ConfigFile.msg,ConfigFile.msg199);
            json.put(ConfigFile.data,rows);
            return json.toJSONString();
        }
    }

	/**
	 * 生成自定义的json对象,直接采用JSONObject封装,执行效率会更高;适用于为增、删、改操作,一般在service调用
	 * @param rows 执行后受影响的行数
	 * @param success 执行成功的提示消息
	 * @param failure 执行失败的提示消息
	 * @作者 田应平
	 * @创建时间 2016年12月25日 下午5:50:22
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public static final String executeRows(final int rows,final String success,final String failure){
		final JSONObject json = new JSONObject();
		if(rows > 0){
			json.put(ConfigFile.code,ConfigFile.code200);
			json.put(ConfigFile.msg,success);
			json.put(ConfigFile.data,rows);
			return json.toJSONString();
		}else{
			json.put(ConfigFile.code,ConfigFile.code199);
			json.put(ConfigFile.msg,failure);
			json.put(ConfigFile.data,rows);
			return json.toJSONString();
		}
	}

	/**
	 * 生成json格式字符串,code和msg的key是固定的,直接采用JSONObject封装,执行效率会更高,用于增、删、改、查操作,一般在service层调用
	 * @作者 田应平
	 * @返回值类型 返回的是json字符串,内部采用JSONObject封装
	 * @用法 解析后判断data.code == AppKey.code.code200即可处理操作
	 * @创建时间 2016年12月25日 18:11:16
	 * @QQ号码 444141300
	 * @param code 相关参数协议
	 * @主页 http://www.fwtai.com
	*/
	public static final String createJson(final int code,final String msg){
		final JSONObject json = new JSONObject();
		json.put(ConfigFile.code,code);
		json.put(ConfigFile.msg,msg);
		return json.toJSONString();
	}

    /**
     * 生成json格式字符串,直接采用JSONObject封装,执行效率会更高,用于增、删、改、查操作,一般在service层调用
     * @作者 田应平
     * @返回值类型 返回的是json字符串,内部采用JSONObject封装
     * @用法 解析后判断data.code == AppKey.code.code200即可处理操作
     * @创建时间 2018年7月3日 09:20:05
     * @QQ号码 444141300
     * @param code 相关参数协议
     * @主页 http://www.fwtai.com
    */
    public static final String createJson(final String code,final String msg){
        final JSONObject json = new JSONObject();
        json.put(ConfigFile.code,code);
        json.put(ConfigFile.msg,msg);
        return json.toJSONString();
    }

    /**
     * 生成code为199的json格式数据且msg是提示信息
     * @param 
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019/7/29 15:00
    */
    public final static String createJsonFail(final String msg){
        final JSONObject json = new JSONObject();
        json.put(ConfigFile.code,ConfigFile.code199);
        json.put(ConfigFile.msg,msg);
        return json.toJSONString();
    }

    /**
     * 生成code为200的json格式数据且msg是提示信息
     * @param 
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019/7/29 15:00
    */
    public final static String createJsonSuccess(final String msg){
        final JSONObject json = new JSONObject();
        json.put(ConfigFile.code,ConfigFile.code200);
        json.put(ConfigFile.msg,msg);
        return json.toJSONString();
    }

	/**
	 * 验证必要的参数字段是否为空的返回json格式专用,先调用方法validateField()返回值false后再直接调用本方法返回json字符串
	 * @作者 田应平
	 * @返回值类型 返回的是json字符串,内部采用JSONObject封装
	 * @创建时间 2017年1月11日 下午7:38:48
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public static final String jsonValidateField(){
		final JSONObject json = new JSONObject();
		json.put(ConfigFile.code,ConfigFile.code202);
		json.put(ConfigFile.msg,ConfigFile.msg202);
		return json.toJSONString();
	}

    /**
     * 验证必要的字段是否为空,不验证ckey密钥,一般在service层调用,如果返回为 null 则验证成功,否则失败;适用于增、删、改、查操作!
     * @fields 需要验证的form字段
     * @用法1 final String validate = ToolClient.validateField(params,"kid");if(validate != null)return validate;
     * @用法2 final String validate = ToolClient.validateField(params,new String[]{"id"});if(validate != null)return validate;
     * @作者 田应平
     * @返回值类型 返回的是json字符串,内部采用JSONObject封装,如果返回为 null 则验证成功!
     * @创建时间 2017年2月23日 下午10:10:34
     * @QQ号码 444141300
     * @主页 http://www.fwtai.com
    */
    public final static String validateField(final Map<String,?> params,final String... fields){
        final JSONObject json = new JSONObject();
        if(ToolString.isBlank(params) || ToolString.isBlank(fields)){
            return jsonValidateField();
        }
        if(!ToolString.isBlank(fields)){
            boolean flag = false;
            for (final String p : fields){
                if(ToolString.isBlank(params.get(p))){
                    flag = true;
                    break;
                }
            }
            if(flag)return jsonValidateField();
        }
        return null;
    }

    public final static String validateField(final FormData params,final String... fields){
        final JSONObject json = new JSONObject();
        if(ToolString.isBlank(params) || ToolString.isBlank(fields)){
            return jsonValidateField();
        }
        if(!ToolString.isBlank(fields)){
            boolean flag = false;
            for (final String p : fields){
                if(ToolString.isBlank(params.get(p))){
                    flag = true;
                    break;
                }
            }
            if(flag)return jsonValidateField();
        }
        return null;
    }

    /**
     * 验证必填字段,线程安全
     * @用法1 final String validate = ToolClient.validateForm(params,"kid");if(validate != null)return validate;
     * @用法2 final String validate = ToolClient.validateForm(params,new String[]{"id"});if(validate != null)return validate;
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019/11/2 18:31
   */
    public final static String validateForm(final ConcurrentHashMap<String,String> formData,final String[] fields){
        if(ToolString.isBlank(formData) || ToolString.isBlank(fields)){
            return jsonValidateField();
        }
        if(!ToolString.isBlank(fields)){
            boolean flag = false;
            for(String p : fields){
                if(ToolString.isBlank(formData.get(p))){
                    flag = true;
                    break;
                }
            }
            if(flag)return jsonValidateField();
        }
        return null;
    }

    private static final String jsonValidateInteger(){
        final JSONObject json = new JSONObject();
        json.put(ConfigFile.code,ConfigFile.code199);
        json.put(ConfigFile.msg,"参数类型有误");
        return json.toJSONString();
    }

    /**
     * 验证所输入的数据是否是Integer类型,先验证是否必填后才调用本方法
     * @用法1 final String FieldInteger = ToolClient.validateInteger(pageFormData,"type");if(FieldInteger != null)return FieldInteger;
     * @用法2 final String FieldInteger = ToolClient.validateInteger(pageFormData,new String[]{"category","subset","type"});if(FieldInteger != null)return FieldInteger;
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2020/4/2 13:04
    */
    public final static String validateInteger(final Map<String,?> params,final String... fields){
        for(int i = 0; i < fields.length; i++){
            try {
                final Object o = params.get(fields[i]);
                if(o != null){
                    final String value = String.valueOf(String.valueOf(o));
                    if(value.equalsIgnoreCase("null") || value.equalsIgnoreCase("undefined"))return jsonValidateInteger();
                    if(value == null){
                        return jsonValidateInteger();
                    }
                    Integer.parseInt(value);
                }else{
                    return jsonValidateInteger();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return jsonValidateInteger();
            }
        }
        return null;
    }

    public final static String validateInteger(final FormData params,final String... fields){
        for(int i = 0; i < fields.length;i++){
            try {
                final Object o = params.get(fields[i]);
                if(o != null){
                    final String value = String.valueOf(String.valueOf(o));
                    if(value.equalsIgnoreCase("null") || value.equalsIgnoreCase("undefined"))return jsonValidateInteger();
                    if(value == null){
                        return jsonValidateInteger();
                    }
                    Integer.parseInt(value);
                }else{
                    return jsonValidateInteger();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return jsonValidateInteger();
            }
        }
        return null;
    }

	/**
	 * 生成|计算总页数
	 * @作者 田应平
	 * @返回值类型 Integer
	 * @创建时间 2016年12月2日 下午1:20:53
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public final static Integer totalPage(final Integer total,final Integer pageSize){
		return (total%pageSize) == 0 ? (total/pageSize):(total/pageSize)+1; //总页数
	}

	/**
	 * 生成json对象
	 * @param map
	 * @作者 田应平
	 * @返回值类型 String
	 * @创建时间 2017年7月30日 22:47:24
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final static String jsonObj(final Map<String, Object> map){
		return JSON.toJSONString(map);
	}

	/**
	 * 生成json数组
	 * @param listData
	 * @return
	 * @作者 田应平
	 * @返回值类型 String
	 * @创建时间 2017年1月12日 下午9:28:55
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final static String jsonArray(final List<Map<String, Object>> listData){
		return JSONArray.toJSONString(listData);
	}


	/**
	 * 用于生成出现异常信息时的json固定code:204字符串提示,返回给controller层调用,一般在service层调用
	 * @作者 田应平
	 * @返回值类型 String,内部采用JSONObject封装,msg 为系统统一的‘系统出现错误’
	 * @创建时间 2017年1月10日 21:40:23
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public final static String exceptionJson(){
		final JSONObject json = new JSONObject();
		json.put(ConfigFile.code,ConfigFile.code204);
		json.put(ConfigFile.msg,ConfigFile.msg204);
		return json.toJSONString();
	}

	/**
	 * 用于生成出现异常信息时的json固定code:204字符串提示,返回给controller层调用,一般在service层调用
	 * @param msg 自定义提示的异常信息
	 * @作者 田应平
	 * @返回值类型 String,内部采用JSONObject封装
	 * @创建时间 2017年1月10日 21:40:23
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public final static String exceptionJson(final String msg){
		final JSONObject json = new JSONObject();
		json.put(ConfigFile.code,ConfigFile.code204);
		json.put(ConfigFile.msg,msg);
		return json.toJSONString();
	}

	/**
	 * 返回给客户端系统出现错误的提示信息,已返回给客户端,只能在controller层调用,用于增、删、改、查操作的异常返回给客户端
	 * @注意 不能在service层调用
	 * @作者 田应平
	 * @创建时间 2016年12月25日 下午5:07:16
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final static void responseException(final HttpServletResponse response){
		responseJson(exceptionJson(),response);
		return;
	}

	/**
	 * 返回给客户端系统出现错误的提示信息,已返回给客户端,只能在controller层调用,用于增、删、改、查操作的异常返回给客户端
	 * @param msg 自定义提示的异常信息
	 * @注意 不能在service层调用
	 * @作者 田应平
	 * @创建时间 2016年12月25日 下午5:07:16
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final static void responseException(final HttpServletResponse response,final String msg){
		responseJson(exceptionJson(msg),response);
		return;
	}

    /**
     * 通用的响应json返回json对象,只能在是controller层调用
     * @param json json对象
     * @注意 不能在service层调用
     * @作者 田应平
     * @创建时间 2016年8月18日 17:53:18
     * @QQ号码 444141300
     * @官网 http://www.fwtai.com
    */
    public final static void responseJson(final String json){
        final HttpServletResponse response = getResponse();
        responseJson(json,response);
    }

	/**
	 * 通用的响应json返回json对象,只能在是controller层调用
	 * @param json json对象
	 * @param response
	 * @注意 不能在service层调用
	 * @作者 田应平
	 * @创建时间 2016年8月18日 17:53:18
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final static void responseJson(final String json,final HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter writer = null;
        try {
			writer = response.getWriter();
            if(json == null){
                writer.write(createJson(ConfigFile.code201,ConfigFile.msg201));
                writer.flush();
            }else{
                writer.write(String.valueOf(JSON.parse(json)));
                writer.flush();
            }
		}catch (IOException e){
			logger.error("类ToolClient的方法responseJson出现异常",e.getMessage());
		}finally{
			if(writer != null){
				writer.close();
                writer = null;
			}
		}
	}

    public final static HttpServletResponse getResponse(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

	/**
	 * 获取项目所在的物理路径,推荐使用
	 * @param request
	 * @作者 田应平
	 * @创建时间 2017年9月25日 下午3:47:29
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final static String getWebRoot(final HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath(File.separator);
	}

	/**
	 * 获取访问项目的网站域名,如http://api.yinlz.com
	 * @param request
	 * @作者 田应平
	 * @返回值类型 String
	 * @创建时间 2016年1月16日 15:18:55
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final static String getDomainName(final HttpServletRequest request){
		return request.getScheme()+"://"+request.getServerName();
	}
}