package com.irdstudio.smcenter.core.assembly.plugin.excel.export;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.irdstudio.smcenter.core.assembly.plugin.excel.util.ExcelUtil;
import com.irdstudio.smcenter.core.tinycore.log.ILogger;
import com.irdstudio.smcenter.core.tinycore.log.TLogger;
/**
 * 根据Excel模板导出数据到Excel中
 * @author guangming.li
 * @version 1.0
 * @date 2014-09-17
 */
public class ExcelTemplateBuilder {
	
	/* Excel文件输入流(用于输出新的Excel文件) */
	private FileOutputStream fileOutputStream = null;
	/* POI中的Excel根对象(接口) */
	private Workbook workbook = null;
	/* POI中的sheet对象(接口) */
	private Sheet sheet = null;
	/* Excel文件版本 */
	private int excelFileVerion;
	/* 日志输出对象 */
	private ILogger logger = null;
	/* 当前指向的sheet编号 */
	private int currentSheetNo;

	HSSFPatriarch patriarch = null;


	/**
	 * @用途：加载一个已经存在的模板，将生成的内容保存到 workbook中
	 * @参数：String templateFile：指索要加载的模板的路径，如："C:/Tamplates/texting-1.xls"
	 * @用法：templateFile： String templateFile_Name1 =
	 *                   "C:/Tamplates/texting-1.xls"
	 */
	public ExcelTemplateBuilder(String templateURL) {

		// 检查模板文件是否符合要求(可支持xls与xls格式的Excel)
		this.excelFileVerion = ExcelUtil.getExcelFileVersion(templateURL);
		if (ExcelUtil.EXCEL_VERSION_UNKNOW == this.excelFileVerion) {
			System.out.println("文件名不能为空或文件格式不正确！");
		} else {
			this.logger = TLogger.getLogger("ExcelTemplateBuilder");
			try {
				FileInputStream templateFileInput = new FileInputStream(
						templateURL);
				// excel 2003版本的POI初始化
				if (ExcelUtil.EXCEL_VERSION_2003 == this.excelFileVerion) {
					workbook = new HSSFWorkbook(new POIFSFileSystem(
							templateFileInput));
					sheet = (HSSFSheet) workbook.getSheetAt(0);
				} else {
					// excel 2007版本的POI初始化
					workbook = new XSSFWorkbook(templateFileInput);
					sheet = (XSSFSheet) workbook.getSheetAt(0);
				}
				currentSheetNo = 0;
				this.logger.info("========" + templateURL + "文件加载已完成========");
			} catch (Exception e) {
				this.logger.error("Load Template Erro，文件加载失败！！");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 写入非图片格式信息
	 * 
	 * @描述：这是一个实体类，提供了相应的接口，用于操作Excel，在任意坐标处写入数据。
	 * @参数：int sheetNo:sheet编号 String newContent：你要输入的内容 int beginRow ：行坐标，Excel从 0 算起 int beginCol
	 *            ：列坐标，Excel从 0 算起
	 */
	public void writeIn(String newContent, int sheetNo, int beginRow,
			int beginCell) {
		positionToSheet(sheetNo);
		Row row = sheet.getRow(beginRow);
		if (null == row) {
			// 如果不做空判断，你必须让你的模板文件画好边框，beginRow和beginCell必须在边框最大值以内
			// 否则会出现空指针异常
			row = sheet.createRow(beginRow);
		}
		Cell cell = row.getCell(beginCell);
		if (null == cell) {
			cell = row.createCell(beginCell);
		}
		// 设置存入内容为字符串
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		// 向单元格中放入值
		cell.setCellValue(newContent);
	}

	/**
	 * 定位到指定Sheet
	 * @param sheetNo sheet编号
	 */
	private void positionToSheet(int sheetNo) {
		if (sheetNo != currentSheetNo) {
			if (ExcelUtil.EXCEL_VERSION_2003 == this.excelFileVerion) {
				sheet = (HSSFSheet) workbook.getSheetAt(sheetNo);
			} else {
				sheet = (XSSFSheet) workbook.getSheetAt(sheetNo);
			}
			currentSheetNo = sheetNo;
		}
	}

	/**
	 * 写入图片格式信息
	 * 
	 * @描述：这是一个实体类，提供了相应的接口，用于操作Excel，在任意坐标处写入数据。
	 * @参数： String imageFileURL：他接受一个外界传入的图片路径，图片以 *.jpeg 形式存在。
	 * 
	 * @用法： ReportBuilder twi = new ReportBuilder(); String imageFileURL =
	 *      "D:/workspace/Tamplates/1.jpeg"; twi.writeInTemplate(imageFileURL ,
	 *      0,0, 0, 0, (short)6, 5, (short)8, 8);
	 * 
	 * @param dx1
	 *            ：第一个cell开始的X坐标
	 * @param dy1
	 *            ：第一个cell开始的Y坐标
	 * @param dx2
	 *            ：第二个cell开始的X坐标
	 * @param dy2
	 *            ：第二个cell开始的Y坐标
	 * @param col1
	 *            ：图片的左上角放在第几个列cell (the column(o based); of the first cell)
	 * @param row1
	 *            ：图片的左上角放在第几个行cell (the row(o based); of the first cell)
	 * @param col2
	 *            ：图片的右下角放在第几个列cell (the column(o based); of the second cell)
	 * @param row2
	 *            ：图片的右下角放在第几个行cell (the row(o based); of the second cell)
	 */
	public void writeIn(String imageFileURL, int dx1, int dy1, int dx2,
			int dy2, short col1, int row1, short col2, int row2) {
		BufferedImage bufferImage = null;

		// 写入图片格式信息
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

			// 先把读入的图片放到第一个 ByteArrayOutputStream 中，用于产生ByteArray
			File fileImage = new File(imageFileURL);

			bufferImage = ImageIO.read(fileImage);
			ImageIO.write(bufferImage, "JPG", byteArrayOutputStream);
			System.out.println("ImageIO 写入完成");

			// 准备插入图片
			HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();
			HSSFClientAnchor anchor = new HSSFClientAnchor(dx1, dy1, dx2, dy2,
					col1, row1, col2, row2);

			// 插入图片
			byte[] pictureData = byteArrayOutputStream.toByteArray();
			int pictureFormat = HSSFWorkbook.PICTURE_TYPE_JPEG;
			int pictureIndex = workbook.addPicture(pictureData, pictureFormat);
			patriarch.createPicture(anchor, pictureIndex);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("IO Erro：" + e.getMessage());
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		}
	}

	/**
	 * 写入图片格式信息
	 * 
	 * @描述：这是一个实体类，提供了相应的接口，用于操作Excel，在任意坐标处写入数据。
	 * @参数： ImageInputStream imageInputStream：他接受一个外界传入的图片流，图片以流形式存在。
	 * 
	 * @用法：
	 * 
	 * 
	 * 
	 * 
	 * @param dx1
	 *            ：第一个cell开始的X坐标
	 * @param dy1
	 *            ：第一个cell开始的Y坐标
	 * @param dx2
	 *            ：第二个cell开始的X坐标
	 * @param dy2
	 *            ：第二个cell开始的Y坐标
	 * @param col1
	 *            ：图片的左上角放在第几个列cell (the column(o based); of the first cell)
	 * @param row1
	 *            ：图片的左上角放在第几个行cell (the row(o based); of the first cell)
	 * @param col2
	 *            ：图片的右下角放在第几个列cell (the column(o based); of the second cell)
	 * @param row2
	 *            ：图片的右下角放在第几个行cell (the row(o based); of the second cell)
	 * 
	 */
	public void writeIn(ImageInputStream imageInputStream, int dx1,
			int dy1, int dx2, int dy2, short col1, int row1, short col2,
			int row2) {
		BufferedImage bufferImage = null;
		// 写入图片格式信息
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			// 先把读入的图片放到一个 ByteArrayOutputStream 中，用于产生ByteArray
			bufferImage = ImageIO.read(imageInputStream);
			ImageIO.write(bufferImage, "JPG", byteArrayOutputStream);
			System.out.println("ImageIO 写入完成");

			// 准备插入图片
			HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();
			HSSFClientAnchor anchor = new HSSFClientAnchor(dx1, dy1, dx2, dy2,
					col1, row1, col2, row2);

			// 插入图片
			byte[] pictureData = byteArrayOutputStream.toByteArray();
			int pictureFormat = HSSFWorkbook.PICTURE_TYPE_JPEG;
			int pictureIndex = workbook.addPicture(pictureData, pictureFormat);
			patriarch.createPicture(anchor, pictureIndex);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("IO Erro：" + e.getMessage());
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		}
	}

	/**
	 * 保存模板到目标Excel文件
	 * 
	 * @描述：这个方法用于保存workbook(工作薄)中的内容，并写入到一个Excel文件中
	 * @参数：String templateFile：取得已经保存的类模板 路径名称
	 * @用法：templateFile：String templateFile_Name1 = "C:/Tamplates/texting-1.xls"
	 *                         TemplateAdapter ta = new TemplateAdapter();
	 *                         ta.SaveTemplate(templateFile_Name1);
	 * @param targetExcelFile
	 */
	public void saveAsTargetExcel(String targetExcelFile) {
		try {

			// 建立输出流
			fileOutputStream = new FileOutputStream(targetExcelFile);
			workbook.write(fileOutputStream);

		} catch (Exception e) {
			e.printStackTrace();
			this.logger.info("IO Erro" + e.getMessage());
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		}
	}
}