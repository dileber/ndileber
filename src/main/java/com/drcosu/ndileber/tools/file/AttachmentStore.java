
package com.drcosu.ndileber.tools.file;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.drcosu.ndileber.tools.log.ULog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 用于把附件保存到文件系统中
 */
public class AttachmentStore {
    /**
     * 把拷贝文件
     * @param srcPath
     * @param dstPath
     * @return
     */
    public static long copy(String srcPath, String dstPath) {
    	if (TextUtils.isEmpty(srcPath) || TextUtils.isEmpty(dstPath)) {
    		return -1;
    	}

        File source = new File(srcPath);
        if (!source.exists()) {
            return -1;
        }

        if (srcPath.equals(dstPath)) {
            return source.length();
        }

        FileChannel fcin = null;
        FileChannel fcout = null;
    	try {
            fcin = new FileInputStream(source).getChannel();
            fcout = new FileOutputStream(create(dstPath)).getChannel();
            ByteBuffer tmpBuffer = ByteBuffer.allocateDirect(4096);
            while (fcin.read(tmpBuffer) != -1) {
                tmpBuffer.flip();
                fcout.write(tmpBuffer);
                tmpBuffer.clear();
            }
			return source.length();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fcin != null) {
                    fcin.close();
				}
				if (fcout != null) {
                    fcout.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	return -1;
    }

    /**
     * 获得文件字节大小
     * @param srcPath
     * @return
     */
    public static long getFileLength(String srcPath) {
    	if (TextUtils.isEmpty(srcPath)) {
			return -1;
		}

    	File srcFile = new File(srcPath);
    	if (!srcFile.exists()) {
			return -1;
		}
    	
    	return srcFile.length();
	}

    public static long save(String path, String content) {
        return save(content.getBytes(), path);
    }

    /**
     * 把数据保存到文件系统中，并且返回其大小
     * 
     * @param data
     * @param filePath
     * @return 如果保存失败,则返回-1
     */
    public static long save(byte[] data, String filePath) {
    	if (TextUtils.isEmpty(filePath)) {
    		return -1;
    	}
    	
        File f = new File(filePath);        
        if(f.getParentFile() == null) {
        	return -1;
        }
        
        if (!f.getParentFile().exists()) {// 如果不存在上级文件夹
            f.getParentFile().mkdirs();
        }
        try {
            f.createNewFile();
            FileOutputStream fout = new FileOutputStream(f);
            fout.write(data);
            fout.close();
        } catch (IOException e) {
        	e.printStackTrace();
            return -1;
        }
        return f.length();
    }

    /**
     * 将某个文件移动到另一个位置
     * @param srcFilePath
     * @param dstFilePath
     * @return
     */
    public static boolean move(String srcFilePath, String dstFilePath) {
    	if (TextUtils.isEmpty(srcFilePath) || TextUtils.isEmpty(dstFilePath)) {
			return false;
		}

    	File srcFile = new File(srcFilePath);
    	if (!srcFile.exists() || !srcFile.isFile()) {
			return false;
		}
    	
        File dstFile = new File(dstFilePath);        
        if(dstFile.getParentFile() == null) {
        	return false;
        }
        
        if (!dstFile.getParentFile().exists()) {// 如果不存在上级文件夹
        	dstFile.getParentFile().mkdirs();
        }
  
        return srcFile.renameTo(dstFile);
    }

    /**
     * 创建文件
     * @param filePath
     * @return
     */
    public static File create(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }

        File f = new File(filePath);
        if (!f.getParentFile().exists()) {// 如果不存在上级文件夹
            f.getParentFile().mkdirs();
        }
        try {
            f.createNewFile();
            return f;
        } catch (IOException e) {
        	if(f!=null && f.exists()){
        		f.delete();
        	}
            return null;
        }    
    }

    /**
     * 保存文件返回大小
     * @param is
     * @param filePath
     * @return 保存失败，返回-1
     */
    public static long save(InputStream is, String filePath) {
        File f = new File(filePath);
        if (!f.getParentFile().exists()) {// 如果不存在上级文件夹
            f.getParentFile().mkdirs();
        }
        FileOutputStream fos = null;
        try {
            f.createNewFile();
            fos = new FileOutputStream(f);
            int read = 0;
            byte[] bytes = new byte[8091];
            while ((read = is.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
            return f.length();
        } catch (IOException e) {
        	if(f!=null && f.exists()){
        		f.delete();
        	}
            ULog.e("file", "save is to " + filePath + " failed: " + e.getMessage());
            return -1;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 把文件从文件系统中读取出来
     * 
     * @param path
     * @return 如果无法读取,则返回null
     */
    public static byte[] load(String path) {
        try {
        	File f = new File(path);
        	int unread = (int) f.length();
        	int read = 0;
            byte[] buf = new byte[unread]; // 读取文件长度
            FileInputStream fin = new FileInputStream(f);
            do {
            	int count = fin.read(buf, read, unread);
            	read += count;
            	unread -= count;
			} while (unread != 0);
            fin.close();
            return buf;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 把文件读为string
     * @param path
     * @return
     */
    public static String loadAsString(String path) {
        if (isFileExist(path)) {
            byte[] content = load(path);
            return new String(content);
        } else {
            return null;
        }
    }

    /**
     * 删除指定路径文件
     * 
     * @param path
     */
    public static boolean delete(String path) {
        if(TextUtils.isEmpty(path)){
            return false;
        }
        File f = new File(path);
        if (f.exists()) {
            f = renameOnDelete(f);
            return f.delete();
        } else {
			return false;
		}
    }

    /**
     * 结束应用删除
     * @param path
     */
    public static void deleteOnExit(String path) {
        if(TextUtils.isEmpty(path)){
            return;
        }
        File f = new File(path);
        if (f.exists()) {
            f.deleteOnExit();
        }
    }

    /**
     * 删除路径
     * @param path
     * @return
     */
    public static boolean deleteDir(String path) {
        return deleteDir(path, true);
    }

    /**
     * 删除路径
     * @param path 目录
     * @param rename 是否重命名
     * @return
     */
    private static boolean deleteDir(String path, boolean rename) {
        boolean success = true;
        File file = new File(path);
        if (file.exists()) {
            if (rename) {
                file = renameOnDelete(file);
            }

            File[] list = file.listFiles();
            if (list != null) {
                int len = list.length;
                for (int i = 0; i < len; ++i) {
                    if (list[i].isDirectory()) {
                        deleteDir(list[i].getPath(), false);
                    } else {
                        boolean ret = list[i].delete();
                        if (!ret) {
                            success = false;
                        }
                    }
                }
            }
        } else {
            success = false;
        }
        if (success) {
            file.delete();
        }
        return success;
    }

    /**
     * 在删除之前重命名，以避免Android的文件系统锁定
     * @param file
     * @return
     */
    private static File renameOnDelete(File file) {
        String tmpPath = file.getParent() + "/" + System.currentTimeMillis() + "_tmp";
        File tmpFile = new File(tmpPath);
        if (file.renameTo(tmpFile)) {
            return tmpFile;
        } else {
            return file;
        }
    }

    /**
     * 文件是否存在
     * @param path
     * @return
     */
    public static boolean isFileExist(String path) {
		if (!TextUtils.isEmpty(path) && new File(path).exists()) {
			return true;
		}
		else {
			return false;
		}
	}

    /**
     * 保存图片
     * @param bitmap 图片
     * @param path 路径
     * @param recyle 是否释放
     * @return
     */
    public static boolean saveBitmap(Bitmap bitmap, String path, boolean recyle) {
        if (bitmap == null || TextUtils.isEmpty(path)) {
            return false;
        }

        BufferedOutputStream bos = null;
        try {
            FileOutputStream fos = new FileOutputStream(path);
            bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            return true;

        } catch (FileNotFoundException e) {
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                }
            }
            if (recyle) {
                bitmap.recycle();
            }
        }
    }
}
