package com.drcosu.ndileber.tools.storage;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * 需要初始化的存储工具
 * 文件将全部通过这个工具存储在内存卡上
 */
public class UStorage {
	public final static long K = 1024;
	public final static long M = 1024 * 1024;
	// 外置存储卡默认预警临界值
    private static final long THRESHOLD_WARNING_SPACE = 100 * M;
	// 保存文件时所需的最小空间的默认值
	public static final long THRESHOLD_MIN_SPCAE = 20 * M;

    public static void init(Context context, String rootPath) {
        ExternalStorage.getInstance().init(context, rootPath);
    }

	public static String getStorageRoot(){
		return ExternalStorage.getInstance().getSdkStorageRoot();
	}

	/**
	 *
	 * @param fileName
	 * @param fileType
	 * @return 可用的保存路径或者null
	 */
	public static String getWritePath( String fileName, StorageType fileType) {
		String path = ExternalStorage.getInstance().getWritePath(fileName, fileType);
		if (TextUtils.isEmpty(path)) {
			return null;
		}
		File dir = new File(path).getParentFile();
		if (dir != null && !dir.exists()) {
			dir.mkdirs();
		}
		return path;
	}

	/**
	 * 判断能否使用外置存储
	 */
	public static boolean isExternalStorageExist() {
		return ExternalStorage.getInstance().isSdkStorageReady();
	}


    /**
     * 判断外部存储是否存在，以及是否有足够空间保存指定类型的文件
     *
     * @param fileType
     * @return false: 无存储卡或无空间可写, true: 表示ok
     */
    public static boolean hasEnoughSpaceForWrite( StorageType fileType) {
        if (!ExternalStorage.getInstance().isSdkStorageReady()) {
            return false;
        }

        long residual = ExternalStorage.getInstance().getAvailableExternalSize();
        if (residual < fileType.getStorageMinSize()) {
            return false;
        } else if (residual < THRESHOLD_WARNING_SPACE) {
        }

        return true;
    }
	/**
	 * 根据输入的文件名和类型，找到该文件的全路径。
	 *
	 * @param fileName
	 * @param fileType
	 * @return 如果存在该文件，返回路径，否则返回空
	 */
	public static String getReadPath(String fileName, StorageType fileType) {
		return ExternalStorage.getInstance().getReadPath(fileName, fileType);
	}
	/**
	 * 返回指定类型的文件夹路径
	 *
	 * @param fileType
	 * @return
	 */
	public static String getDirectoryByDirType(StorageType fileType) {
		return ExternalStorage.getInstance().getDirectoryByDirType(fileType);
	}

}
