package com.yizhenmoney.damocles.configcenter.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author shim
 *
 */
public class Object2ByteArrayUtils {

	/** 字节数组转为对象
	 * @param bytes
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T extends Serializable> T ByteToObject(byte[] bytes) {
		ByteArrayInputStream bi = null;
		ObjectInputStream oi = null;
		T obj = null;
		try {
			// bytearray to object
			bi = new ByteArrayInputStream(bytes);
			oi = new ObjectInputStream(bi);

			obj = (T) oi.readObject();

			bi.close();
			oi.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != bi) {
				try {
					bi.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != oi) {
				try {
					oi.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}

	/** 对象转为字节数组
	 * @param obj
	 * @return
	 */
	public static byte[] ObjectToByte(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bo = null;
		ObjectOutputStream oo = null;
		try {
			// object to bytearray
			bo = new ByteArrayOutputStream();
			oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);

			bytes = bo.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != bo) {
				try {
					bo.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != oo) {
				try {
					oo.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bytes;
	}

}
