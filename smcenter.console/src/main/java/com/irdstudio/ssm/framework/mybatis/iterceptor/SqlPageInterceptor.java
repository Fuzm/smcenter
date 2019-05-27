package com.irdstudio.ssm.framework.mybatis.iterceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdstudio.ssm.framework.vo.BaseInfo;


/**
 * mybatis sql分页拦截器
 * @author Cytus_
 * @since 2018-04-29 16:48:23
 * @version 1.0
 */
@Intercepts({@Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class , Integer.class}),
	 @Signature(method = "query", type = Executor.class, args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class SqlPageInterceptor implements Interceptor {
	
	private static Logger logger = LoggerFactory.getLogger(SqlPageInterceptor.class);

	public Object intercept(Invocation invocation) throws Throwable {

			if (invocation.getTarget() instanceof StatementHandler) {

				//获取StatementHandler，默认是RoutingStatementHandler
				StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
				//获取statementHandler包装类
				MetaObject metaObjectHandler = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,
						SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());

				MappedStatement mappedStatement = (MappedStatement) metaObjectHandler.getValue("delegate.mappedStatement");
				String mapId = mappedStatement.getId();

				//拦截以.ByPage结尾的请求，分页功能的统一实现
				if (mapId.matches(".+ByPage$")) {
					//获取进行数据库操作时管理参数的handler
					ParameterHandler parameterHandler = (ParameterHandler) metaObjectHandler.getValue("delegate.parameterHandler");
					BaseInfo pageInfo = (BaseInfo) parameterHandler.getParameterObject();
					pageInfo.checkAndSetPageInfo();
					//修改以byPage结尾的强制分页
					String sql = (String) metaObjectHandler.getValue("delegate.boundSql.sql");
		
					queryTotalRecord(pageInfo, pageInfo, mappedStatement, (Connection) invocation.getArgs()[0]);
		
					String limitSql = buildPageSql(pageInfo, sql, (Connection) invocation.getArgs()[0]);
					logger.info("生成的分页查询sql为："+ limitSql);
					metaObjectHandler.setValue("delegate.boundSql.sql", limitSql);
				}
			}
			return invocation.proceed();
	}

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties arg0) {

	}

	
	/**
	 * 生成分页查询sql
	 * @param pageInfo
	 * @param sql
	 * @return
	 */
	protected String buildPageSql(BaseInfo pageInfo, String sql, Connection conn) {
		int offset = (pageInfo.getPage() - 1) * pageInfo.getSize();
		//mysql
		//return new StringBuilder(sql).append(" limit ").append(offset).append(",").append(pageInfo.getSize()).toString();
		//oracle
		int endOffset = offset + pageInfo.getSize();
		return new StringBuilder("select * from (select t.*, rownum rn from (").append(sql).append(") t) bt where 1=1 and bt.rn between ")
				.append(offset+1).append(" and ").append(endOffset).toString();
	}

	/**
	 * 查询总记录数
	 * @param page 分页信息对象
	 * @param parameterObject 查询参数对象
	 * @param mappedStatement
	 * @param connection
	 * @throws SQLException
	 */
	protected void queryTotalRecord(BaseInfo page, Object parameterObject, MappedStatement mappedStatement, Connection connection) throws SQLException {
		BoundSql boundSql = mappedStatement.getBoundSql(page);
		String sql = boundSql.getSql();
		String countSql = this.buildCountSql(sql);
		logger.info("生成的查询总页数的sql为:"+ countSql);
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, parameterObject);
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, countBoundSql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		   pstmt = connection.prepareStatement(countSql);
		   parameterHandler.setParameters(pstmt);
		   rs = pstmt.executeQuery();
		   if (rs.next()) {
		       int totalRecord = rs.getInt(1);
		       page.setTotal(totalRecord);
		       logger.info("查询到的总页数为:"+ totalRecord);
		   }
		} catch (Exception e) {
			logger.error("执行查询总页数sql出现异常!", e);
		} finally {
		   if (rs != null)
		       try {
		           rs.close();
		       } catch (Exception e) {
	               logger.error("关闭ResultSet时异常.", e);
		       }
		   if (pstmt != null)
		       try {
		           pstmt.close();
		       } catch (Exception e) {
	               logger.error("关闭PreparedStatement时异常.", e);
		       }
		}
	}

	protected String buildCountSql(String sql) {
		return "select count(1) from (" + sql +")";
	}

}