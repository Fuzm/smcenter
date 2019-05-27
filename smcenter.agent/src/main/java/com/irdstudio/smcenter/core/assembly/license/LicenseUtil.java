package com.irdstudio.smcenter.core.assembly.license;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.irdstudio.smcenter.core.assembly.license.dm.SInstLicense;
import com.irdstudio.smcenter.core.util.crypt.DesBinaryFileReader;
import com.irdstudio.smcenter.core.util.crypt.DesBinaryFileWriter;

/**
 * License辅助类(用于产生或读取License文件)
 * @author gaungming.li
 * @version 1.0
 * @date 2014-07-12
 */
public class LicenseUtil {
	/**
	 * 产生授权文件
	 * @param licenseFileName
	 * @param sysCode
	 * @param ip
	 * @param port
	 * @param sysName
	 * @param authTarget
	 * @param authBeginDate
	 * @param authEndDate
	 * @param remarks
	 */
	public static void createLicenseFile(String licenseFileName,
			String sysCode, String ip, String port, String sysName,
			String authTarget, String authBeginDate, String authEndDate,
			String remarks) {
		DesBinaryFileWriter dbfw = null;
		try {
			dbfw = new DesBinaryFileWriter(new DataOutputStream(
					new FileOutputStream(licenseFileName)));
			dbfw.writeEncryptString(sysCode);
			dbfw.writeEncryptString(ip);
			dbfw.writeEncryptString(port);
			dbfw.writeEncryptString(sysName);
			dbfw.writeEncryptString(authTarget);
			dbfw.writeEncryptString(authBeginDate);
			dbfw.writeEncryptString(authEndDate);
			dbfw.writeEncryptString(remarks);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (dbfw != null) {
				try {
					dbfw.close();
					dbfw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 读取License授权文件
	 * @param licenseFileName
	 */
	public static SInstLicense readLicenseFile(String licenseFileName) {
		SInstLicense instLicense = null;
		DesBinaryFileReader dbfr = null;
		try {
			dbfr = new DesBinaryFileReader(new DataInputStream(
					new FileInputStream(licenseFileName)));
			instLicense = new SInstLicense();
			instLicense.setSysCode(dbfr.readDecryptString().toUpperCase());
			instLicense.setHostName(dbfr.readDecryptString());
			instLicense.setWebPort(dbfr.readDecryptString());
			instLicense.setSysName(dbfr.readDecryptString());
			instLicense.setAuthTarget(dbfr.readDecryptString());
			instLicense.setAuthBeginDate(dbfr.readDecryptString());
			instLicense.setAuthEndDate(dbfr.readDecryptString());
			instLicense.setRemark(dbfr.readDecryptString());
			instLicense.setSysInstId(instLicense.getSysCode() + "_"
					+ instLicense.getHostName() + "_"
					+ instLicense.getWebPort());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (dbfr != null) {
				try {
					dbfr.close();
					dbfr = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return instLicense;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LicenseUtil.createLicenseFile("d:\\imsrvs_lgm.lic","imsrvs", "192.168.152.85", "9081",
				"XXX业务系统", "XXX业务系统", "2017-11-20", "unlimited", "License file create date:2017-11-20");
		SInstLicense instLicense = LicenseUtil.readLicenseFile("d:\\imsrvs_lgm.lic");
		if (instLicense != null) {
			System.out.println(instLicense.getSysInstId());
			System.out.println(instLicense.getSysCode());
			System.out.println(instLicense.getHostName());
			System.out.println(instLicense.getWebPort());
			System.out.println(instLicense.getSysName());
			System.out.println(instLicense.getAuthTarget());
			System.out.println(instLicense.getAuthBeginDate());
			System.out.println(instLicense.getAuthEndDate());
			System.out.println(instLicense.getRemark());
		}
	}

}
