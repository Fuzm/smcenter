package com.irdstudio.ssm.framework.init;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.irdstudio.ssm.admin.service.api.SDicService;
import com.irdstudio.ssm.admin.service.vo.SDicVO;
import com.irdstudio.ssm.framework.util.PathUtil;

/**
 * <p>
 * 	初始化:将字典项数据写到js文件中
 *  在Spring初始化bean之后执行
 * </p> 
 * @author ligm
 * @date 2018-06-08
 */
@Component
public class InitForDict {

	private static Logger logger = LoggerFactory.getLogger(InitForDict.class);

	@Autowired
	@Qualifier("sDicService")
	private SDicService sDicService;

	@PostConstruct
	public void initMethod() {

		// 设定字典文件路径
		String dictFileFullPath = PathUtil.getProjectRootPath()
				+ "ffres/dict/all.js";

		logger.info("初始化字典数据到:" + dictFileFullPath);

		// 读取字典数据并写入到字典文件中
		OutputStreamWriter osw = null;
		try {
			osw = new OutputStreamWriter(new FileOutputStream(dictFileFullPath),"UTF-8");
			StringBuffer sb = new StringBuffer();
			List<SDicVO> list = sDicService.queryAllDict();
			list.forEach(dic -> {
				logger.info("初始化字典项" + dic.getOpttype() + "...");
				List<SDicVO> options = sDicService.queryDictOption(dic
						.getOpttype());

				sb.append("var ").append(dic.getOpttype().toUpperCase());
				sb.append("=[");
				options.forEach(option -> {
					sb.append("{\"enname\":\"").append(option.getEnname())
							.append("\",\"cnname\":\"")
							.append(option.getCnname()).append("\"},");
				});
				if (',' == sb.charAt(sb.length() - 1)) {
					sb.deleteCharAt(sb.length() - 1);
				}
				sb.append("];\r\n");

				// 为每个字典项生成格式化函数
				sb.append("function formatter_")
						.append(dic.getOpttype().toUpperCase())
						.append("(value, row, index){")
						.append("return JLEUtil.formatter_dict(value,")
						.append(dic.getOpttype().toUpperCase()).append(");")
						.append("};\r\n");
			});

			osw.write(sb.toString());
		} catch (Exception e) {
			logger.error("数据字典初始化出错" ,e);
		} finally {
			if (osw != null)
				try {
					osw.close();
				} catch (IOException e) {
					logger.error("输出流关闭出错:" ,e);
				}
		}

		logger.info("字典数据初始化完成!");

	}

}
