package com.github.distriful5061.AllergyProfile.Utils;

import java.io.*;

/**
 * リソース(外部/resourcesフォルダ)を読み込むメソッドをまとめたクラス。
 *
 * @since 1.0
 */
public class ResourceUtils implements BaseUtils {
    @Override
    public int hashCode() {
        return 284747540;
    }

    private static boolean checkBeforeWriteFile(File file){
        if (file.exists()){
            if (file.isFile() && file.canWrite()){
                return true;
            }
        }

        return false;
    }

    private static boolean checkBeforeReadFile(File file){
        if (file.exists()){
            if (file.isFile() && file.canRead()){
                return true;
            }
        }

        return false;
    }

    /**
     * リソースをFile型にして返す、
     *
     * @param name パスかファイル名称
     * @return Fileになったリソースアイテム
     */
    public static File getFileResourcesByName(String name) {
        return new File("src/main/resources/" + name);
    }

    /**
     * プレーンテキストのリソースファイル(src/main/resourcesフォルダから)を取得するメソッド。
     *
     * @param name リソースファイルの名前
     * @return 読み込んだデータ。nullの可能性があります
     */
    public static String getPlainTextResourcesByName(String name) {
        File file = getFileResourcesByName(name);
        String result = null;

        if (!checkBeforeReadFile(file)) return null;

        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file))
        )
        {
            StringBuilder stringBuilder = new StringBuilder();
            String str;

            while ((str = bufferedReader.readLine()) != null) {
                stringBuilder.append(str).append("\n");
            }

            result = stringBuilder.toString();
        } catch (IOException ignored) {}

        return result;
    }

    public static void setPlainTextResourcesByName(String name, String content) throws IOException {
        File file = getFileResourcesByName(name);

        if (!checkBeforeWriteFile(file)) return;

        try (
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        )
        {

            String[] splitString = content.replace("\r\n", "\r").replace("\n", "\r").replace("\r", "\n").split("\n");
            for (String s : splitString) {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }
        }
    }
}
