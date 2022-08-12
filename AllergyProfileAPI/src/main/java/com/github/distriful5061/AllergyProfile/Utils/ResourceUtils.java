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
        File file = new File("src/main/resources/"+name);
        String result = null;

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
}
