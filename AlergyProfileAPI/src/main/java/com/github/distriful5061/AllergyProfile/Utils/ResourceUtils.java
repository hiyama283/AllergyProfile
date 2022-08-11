package com.github.distriful5061.AllergyProfile.Utils;

import java.io.*;

/**
 *
 */
public class ResourceUtils implements BaseUtils {
    @Override
    public int hashCode() {
        return 284747540;
    }

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
