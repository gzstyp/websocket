package com.fwtai.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * dao底层操作处理工具类
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020/4/29 20:42
 * @QQ号码 444141300
 * @Email service@yinlz.com
 * @官网 <url>http://www.yinlz.com</url>
*/
@Repository
public class DaoHandle{
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	/**
	 * 通用的更新;删除;插入添加
	 * @作者 田应平
	 * @返回值类型 int
	 * @创建时间 2016年12月24日 23:00:14
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public int execute(final String sqlMapId){
		return sqlSession.update(sqlMapId);
	}

	/**
	 * 通用的更新;删除;插入添加
	 * @作者 田应平
	 * @返回值类型 int
	 * @创建时间 2016年12月24日 23:00:09
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public int execute(final String sqlMapId,final Object objParam){
		return sqlSession.update(sqlMapId,objParam);
	}
	
	/**批量插入|更新|删除*/
	public int executeBatch(final String sqlMapId){
		return sqlSession.update(sqlMapId);
	}
	
	/**批量插入|更新|删除,objParam可以是List<HashMap<String, Object>>*/
	public int executeBatch(final String sqlMapId, final Object objParam){
		return sqlSession.update(sqlMapId,objParam);
	}

	/**
	 * 用于查询返回Integer
	 * @作者 田应平
	 * @返回值类型 Integer
	 * @创建时间 2016年12月24日 23:00:55
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public Integer queryForInteger(final String sqlMapId){
		return sqlSession.selectOne(sqlMapId);
	}

	/**
	 * 用于查询返回Integer
	 * @作者 田应平
	 * @返回值类型 Integer
	 * @创建时间 2016年12月24日 23:01:35
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public Integer queryForInteger(final String sqlMapId, final Object objParam){
		return sqlSession.selectOne(sqlMapId,objParam);
	}

	/**
	 * 用于查询返回Integer
	 * @作者 田应平
	 * @返回值类型 Integer
	 * @创建时间 2016年12月24日 23:00:55
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	 */
	public Long queryForLong(final String sqlMapId){
		return sqlSession.selectOne(sqlMapId);
	}

	/**
	 * 用于查询返回Integer
	 * @作者 田应平
	 * @返回值类型 Integer
	 * @创建时间 2016年12月24日 23:01:35
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	 */
	public Long queryForLong(final String sqlMapId, final Object objParam){
		return sqlSession.selectOne(sqlMapId,objParam);
	}

    /**
     * 用于金额查询返回BigDecimal
     * @作者 田应平
     * @返回值类型 Integer
     * @创建时间 2016年12月24日 23:01:35
     * @QQ号码 444141300
     * @主页 http://www.yinlz.com
    */
    public BigDecimal queryForBigDecimal(final String sqlMapId){
        return sqlSession.selectOne(sqlMapId);
    }

    /**
     * 用于金额查询返回BigDecimal
     * @作者 田应平
     * @返回值类型 Integer
     * @创建时间 2016年12月24日 23:01:35
     * @QQ号码 444141300
     * @主页 http://www.yinlz.com
    */
    public BigDecimal queryForBigDecimal(final String sqlMapId,final Object objParam){
        return sqlSession.selectOne(sqlMapId,objParam);
    }

	/**
	 * 用于查询返回String
	 * @作者 田应平
	 * @返回值类型 String
	 * @创建时间 2016年11月20日 下午2:24:52
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public String queryForString(final String sqlMapId){
		return sqlSession.selectOne(sqlMapId);
	}

	/**
	 * 用于查询返回String
	 * @作者 田应平
	 * @返回值类型 String
	 * @创建时间 2016年12月25日 00:44:39
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public String queryForString(final String sqlMapId, final Object objParam){
		return sqlSession.selectOne(sqlMapId,objParam);
	}

	/**
	 * 根据id去查询,或必须保证sql所查询的结果只有一条或限制返回一条数据
	 * @作者 田应平
	 * @返回值类型 Map<String,Object>
	 * @创建时间 2016年12月24日 23:03:07
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public Map<String, Object> queryForMap(final String sqlMapId){
		return sqlSession.selectOne(sqlMapId);
	}

	/**
	 * 必须保存sql所查询的结果只有一条或限制返回一条数据
	 * @作者 田应平
	 * @返回值类型 Map<String,Object>
	 * @创建时间 2016年12月24日 23:03:49
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public Map<String, Object> queryForMap(final String sqlMapId, final Object objParam){
		return sqlSession.selectOne(sqlMapId,objParam);
	}
	
	/**
	 * 根据id去查询,或必须保证sql所查询的结果只有一条或限制返回一条数据
	 * @作者 田应平
	 * @返回值类型 HashMap《String,Object》
	 * @创建时间 2016年12月24日 23:03:07
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public HashMap<String, Object> queryForHashMap(final String sqlMapId){
		return sqlSession.selectOne(sqlMapId);
	}

	/**
	 * 必须保存sql所查询的结果只有一条或限制返回一条数据
	 * @作者 田应平
	 * @返回值类型 HashMap《String,Object》
	 * @创建时间 2016年12月24日 23:03:49
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public HashMap<String, Object> queryForHashMap(final String sqlMapId, final Object objParam){
		return sqlSession.selectOne(sqlMapId,objParam);
	}

	/**
	 * 查询返回List《Map》,含分页
	 * @作者 田应平
	 * @返回值类型 List<Map<String,Object>>
	 * @创建时间 2016年12月24日 23:04:14
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public List<Map<String, Object>> queryForListMap(final String sqlMapId){
		return sqlSession.selectList(sqlMapId);
	}

	/**
	 * 查询返回List《Map》,含分页
	 * @param sqlMapId
	 * @param objParam
	 * @作者 田应平
	 * @返回值类型 List<Map<String,Object>>
	 * @创建时间 2016年12月25日 上午12:47:44
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public List<Map<String, Object>> queryForListMap(final String sqlMapId, final Object objParam){
		return sqlSession.selectList(sqlMapId,objParam);
	}
	
	/**
	 * 查询返回List《HashMap》,含分页
	 * @作者 田应平
	 * @返回值类型 List<Map<String,Object>>
	 * @创建时间 2016年12月24日 23:04:14
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public List<HashMap<String, Object>> queryForListHashMap(final String sqlMapId){
		return sqlSession.selectList(sqlMapId);
	}

	/**
	 * 查询返回List《HashMap》,含分页
	 * @param sqlMapId
	 * @param objParam
	 * @作者 田应平
	 * @返回值类型 List<Map<String,Object>>
	 * @创建时间 2016年12月25日 上午12:47:44
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public List<HashMap<String,Object>> queryForListHashMap(final String sqlMapId, final Object objParam){
		return sqlSession.selectList(sqlMapId,objParam);
	}

    /**
     * 查询对象数据对象
     * @param sqlMapId
     * @作者 田应平
     * @返回值类型 PageFormData
     * @创建时间 2016年12月24日 下午11:12:57
     * @QQ号码 444141300
     * @官网 http://www.yinlz.com
    */
    public <T> T queryForEntity(final String sqlMapId){
        return sqlSession.selectOne(sqlMapId);
    }

    /**
     * 查询对象数据对象
     * @param sqlMapId
     * @param objParam
     * @作者 田应平
     * @返回值类型 PageFormData
     * @创建时间 2016年12月25日 上午12:46:20
     * @QQ号码 444141300
     * @官网 http://www.yinlz.com
    */
    public <T> T queryForEntity(final String sqlMapId, final Object objParam){
        return sqlSession.selectOne(sqlMapId,objParam);
    }

    /**
     * 返回List集合
     * @作者 田应平
     * @返回值类型 int
     * @创建时间 2016年12月24日 23:00:14
     * @QQ号码 444141300
     * @主页 http://www.yinlz.com
    */
    public <T> List<T> selectListEntity(final String sqlMapId){
        return sqlSession.selectList(sqlMapId);
    }

    /**
     * 带参数的LIST
     * @作者 田应平
     * @返回值类型 int
     * @创建时间 2016年12月24日 23:00:14
     * @QQ号码 444141300
     * @主页 http://www.yinlz.com
    */
    public <T> List<T> selectListEntity(final String sqlMapId, final Object objParam){
        return sqlSession.selectList(sqlMapId, objParam);
    }

    /**
     * 请谨慎使用,如果报错则换成返回List<HashMap<String,Object>>
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2018年9月14日 13:55
    */
    public List<HashMap<String,String>> queryForListString(final String sqlMapId){
        return sqlSession.selectList(sqlMapId);
    }

    /**
     * 请谨慎使用,如果报错则换成返回List<HashMap<String,Object>>
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2018年9月14日 13:55
    */
    public List<HashMap<String,String>> queryForListString(final String sqlMapId,final Object objParam){
        return sqlSession.selectList(sqlMapId,objParam);
    }

    /**
     * 返回List集合
     * @作者 田应平
     * @返回值类型 int
     * @创建时间 2016年12月24日 23:00:14
     * @QQ号码 444141300
     * @主页 http://www.yinlz.com
     */
    public <T> List<T> queryListEntity(final String sqlMapId){
        return sqlSession.selectList(sqlMapId);
    }

    /**
     * 带参数的LIST
     * @作者 田应平
     * @返回值类型 int
     * @创建时间 2016年12月24日 23:00:14
     * @QQ号码 444141300
     * @主页 http://www.yinlz.com
     */
    public <T> List<T> queryListEntity(final String sqlMapId, final Object objParam){
        return sqlSession.selectList(sqlMapId, objParam);
    }
}