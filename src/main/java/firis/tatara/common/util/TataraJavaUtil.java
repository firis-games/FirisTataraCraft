package firis.tatara.common.util;

import java.lang.reflect.Field;

import firis.tatara.TataraCraft;

public class TataraJavaUtil {
	
	/**
	 * C:\Users\computer\.gradle\caches\minecraft\de\oceanlabs\mcp\mcp_stable\20
	 * ビルドすると難読化されて変数名がかわるので通常変数名と難読化した変数名を設定できるようにして対応する
	 * private変数の値を取得する
	 * @param clsObj クラス
	 * @param fieldName　フィールド名
	 * @param ObfuscatedFiledName 難読化したフィールド名
	 * @return
	 */
	public static Object getDeclaredField(Class<?> clsObj, Object obj, String fieldName, String ObfuscatedFiledName) {
		
		Object result = null;
		
		//private変数に無理やりアクセス
		Field filed = null;
		
		//難読化フィールドを取得
		try {
			filed = clsObj.getDeclaredField(ObfuscatedFiledName);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			TataraCraft.logger.error(ObfuscatedFiledName + ":" + e.toString());
		} catch (SecurityException e) {
			e.printStackTrace();
			TataraCraft.logger.error(ObfuscatedFiledName + ":" + e.toString());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			TataraCraft.logger.error(ObfuscatedFiledName + ":" + e.toString());
		}
		
		//通常フィールドを取得
		if (filed == null) {
			try {
				filed = clsObj.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
				TataraCraft.logger.error(fieldName + ":" + e.toString());
			} catch (SecurityException e) {
				e.printStackTrace();
				TataraCraft.logger.error(fieldName + ":" + e.toString());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				TataraCraft.logger.error(fieldName + ":" + e.toString());
			}
		}
		
		//対象フィールドなければ終わり
		if (filed == null) {
			return result;
		}
		
		//パラメータの取得
		try {
			filed.setAccessible(true);
			result = filed.get(obj);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			TataraCraft.logger.error(filed.getName() + ":" + e.toString());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			TataraCraft.logger.error(filed.getName() + ":" + e.toString());
		}
		
		return result;
	}
	
	
	/**
	 * ビルドすると難読化されて変数名がかわるので通常変数名と難読化した変数名を設定できるようにして対応する
	 * private変数の値を取得する
	 * @param clsObj クラス
	 * @param fieldName　フィールド名
	 * @param ObfuscatedFiledName 難読化したフィールド名
	 * @return
	 */
	public static boolean setDeclaredField(Class<?> clsObj, Object obj, int value, String fieldName, String ObfuscatedFiledName) {
		
		Object result = null;
		
		//private変数に無理やりアクセス
		Field filed = null;
		
		//難読化フィールドを取得
		try {
			filed = clsObj.getDeclaredField(ObfuscatedFiledName);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			TataraCraft.logger.error(ObfuscatedFiledName + ":" + e.toString());
		} catch (SecurityException e) {
			e.printStackTrace();
			TataraCraft.logger.error(ObfuscatedFiledName + ":" + e.toString());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			TataraCraft.logger.error(ObfuscatedFiledName + ":" + e.toString());
		}
		
		//通常フィールドを取得
		if (filed == null) {
			try {
				filed = clsObj.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
				TataraCraft.logger.error(fieldName + ":" + e.toString());
			} catch (SecurityException e) {
				e.printStackTrace();
				TataraCraft.logger.error(fieldName + ":" + e.toString());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				TataraCraft.logger.error(fieldName + ":" + e.toString());
			}
		}
		
		//対象フィールドなければ終わり
		if (filed == null) {
			return false;
		}
		
		//パラメータの取得
		try {
			filed.setAccessible(true);
			
			filed.set(obj, value);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			TataraCraft.logger.error(filed.getName() + ":" + e.toString());
			return false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			TataraCraft.logger.error(filed.getName() + ":" + e.toString());
			return false;
		}
		
		return true;
	}

}
