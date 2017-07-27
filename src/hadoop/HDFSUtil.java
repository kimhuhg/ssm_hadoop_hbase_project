package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HDFSUtil {
    private HDFSUtil() {}

    // hadoop fs的配置文件
    static Configuration conf = new Configuration(true);
    static {
        // 指定hadoop fs的地址
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        conf.set("fs.defaultFS", "hdfs://192.168.219.128:8020");
    }

    /**
     * 判断路径是否存在
     * @param path
     * @return
     * @throws IOException
     */
    public static boolean exits(String path) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        return fs.exists(new Path(path));
    }

    /**
     * 创建文件
     * @param filePath  HDFS文件路径
     * @param contents  文件内容
     * @throws IOException
     */
    public static void createFile(String filePath, byte[] contents) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path path = new Path(filePath);
        FSDataOutputStream outputStream = fs.create(path);
        outputStream.write(contents);
        outputStream.close();
        fs.close();
    }

    /**
     * 创建文件
     * @param filePath     HDFS文件路径
     * @param fileContent 文件内容
     * @param charSetName 编码
     * @throws IOException
     */
    public static void createFile(String filePath, String fileContent,String charSetName) throws IOException {
        createFile(filePath, fileContent.getBytes(charSetName));
    }

    /**
     * 本地上传文件到HDFS中
     * @param localFilePath        本地文件地址
     * @param remoteFilePath       HDFS路径
     * @throws IOException
     */
    public static void copyFromLocalFile(String localFilePath, String remoteFilePath) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path localPath = new Path(localFilePath);
        Path remotePath = new Path(remoteFilePath);
        fs.copyFromLocalFile(false, true, localPath, remotePath);
        fs.close();
    }

    /**
     * 删除目录或文件
     * @param remoteFilePath
     * @param recursive 递归的  true代表级联删除 false代表只删除指定的文件
     * @return
     * @throws IOException
     */
    public static boolean deleteFile(String remoteFilePath, boolean recursive) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        boolean result = fs.delete(new Path(remoteFilePath), recursive);
        fs.close();
        return result;
    }

    /**
     * 删除目录或文件(如果有子目录,则级联删除)
     * @param remoteFilePath
     * @return
     * @throws IOException
     */
    public static boolean deleteFile(String remoteFilePath) throws IOException {
        return deleteFile(remoteFilePath, true);
    }

    /**
     * 文件重命名
     *
     * @param oldFileName  旧文件路径
     * @param newFileName  新文件路径
     * @return
     * @throws IOException
     */
    public static boolean renameFile(String oldFileName, String newFileName) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path oldPath = new Path(oldFileName);
        Path newPath = new Path(newFileName);
        boolean result = fs.rename(oldPath, newPath);
        fs.close();
        return result;
    }

    /**
     * 创建目录
     * @param dirName
     * @return
     * @throws IOException
     */
    public static boolean createDirectory(String dirName) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path dir = new Path(dirName);
        boolean result = false;
        if (!fs.exists(dir)) {
            result = fs.mkdirs(dir);
        }
        fs.close();
        return result;
    }

    /**
     * 列出指定路径下的所有文件(不包含目录)
     *
     * @param basePath
     * @param recursive
     */
    public static RemoteIterator<LocatedFileStatus> listFiles(String basePath, boolean recursive) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        RemoteIterator<LocatedFileStatus> fileStatusRemoteIterator = fs
                .listFiles(new Path(basePath), recursive);

        return fileStatusRemoteIterator;
    }

    /**
     * 列出指定路径下的文件（非递归）
     *
     * @param basePath
     * @return
     * @throws IOException
     */
    public static RemoteIterator<LocatedFileStatus> listFiles(String basePath) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        RemoteIterator<LocatedFileStatus> remoteIterator = fs.listFiles(
                new Path(basePath), false);
        fs.close();
        return remoteIterator;
    }

    /**
     * 列出指定目录下的文件和子目录信息（非递归）
     *
     * @param dirPath
     * @return
     * @throws IOException
     */
    public static FileStatus[] listStatus(String dirPath) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        FileStatus[] fileStatuses = fs.listStatus(new Path(dirPath));
        fs.close();
        return fileStatuses;
    }

    /**
     * 读取文件内容
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] readFile(String filePath) throws IOException {
        byte[] fileContent = null;
        FileSystem fs = FileSystem.get(conf);
        Path path = new Path(filePath);
        if (fs.exists(path)) {
            InputStream inputStream = null;
            ByteArrayOutputStream outputStream = null;
            try {
                inputStream = fs.open(path);
                outputStream = new ByteArrayOutputStream(
                        inputStream.available());
                IOUtils.copyBytes(inputStream, outputStream, conf);
                fileContent = outputStream.toByteArray();
            } finally {
                IOUtils.closeStream(inputStream);
                IOUtils.closeStream(outputStream);
                fs.close();
            }
        }
        return fileContent;
    }

    /**
     * 下载 hdfs上的文件
     *
     * @param remote
     * @param local
     * @throws IOException
     */
    public static void download(String remote, String local) throws IOException {
        Path path = new Path(remote);
        FileSystem fs = FileSystem.get(conf);
        fs.copyToLocalFile(path, new Path(local));
        System.out.println("download: from" + remote + " to " + local);
        fs.close();
    }
}
