package me.wobblyyyy.build;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileInterface
{
    static String dir = System.getProperty("user.dir");
    static File[] files = new File(dir + File.separator + "src").listFiles();

    public static void writeFileFromFile (File file, String input)
    {
        try
        {
            Files.write(Paths.get(file.getAbsolutePath()), input.getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String readFileFromFile (File file)
    {
        StringBuilder out = new StringBuilder();
        try (FileReader reader = new FileReader(file))
        {
            int ch = reader.read();
            while (ch != -1)
            {
                out.append((char) ch);
                ch = reader.read();
            }
            reader.close();
            return out.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }
}
