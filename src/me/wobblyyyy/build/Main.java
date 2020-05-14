package me.wobblyyyy.build;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main
{
    public static void main (String[] arguments) throws IOException
    {
        long startTime = System.currentTimeMillis();

        Arrays.sort(FileInterface.files);

        List<String> argumentsAsList = Arrays.asList(arguments);

        new File(FileInterface.dir + File.separator + "min").mkdirs();

        int before = 0;
        StringBuilder finished = new StringBuilder();

        for (final File f : FileInterface.files)
        {
            long sStartTime = System.currentTimeMillis();
            System.out.println();
            System.out.println("---------");
            System.out.println("> File: " + f.getPath());
            System.out.println("> Pre-minification size (bytes): " + f.length());

            String text = FileInterface.readFileFromFile(f);
            before += text.length();
            text = !(argumentsAsList.contains("-nomin") || argumentsAsList.contains("-qb") || argumentsAsList.contains("-quick")) ?
                    MinInterface.minify(text.getBytes()) :
                    "\n/* START " + f.getName() + "*/\n" + text + "\n/* END " + f.getName() + "*/\n";

            System.out.println("> Post-minification size (bytes): " + text.length());

            finished.append(text);

            File minSrc = new File(FileInterface.dir + File.separator + "min" + File.separator + f.getName());
            FileInterface.writeFileFromFile(minSrc, text);

            long sEndTime = System.currentTimeMillis();

            System.out.println("> Total execution time: " + (sEndTime - sStartTime) + "ms");
            System.out.println("---------");
            System.out.println();
        }

        File build = argumentsAsList.contains("-current") ?
                new File(FileInterface.dir + File.separator + "score.js") :
                new File(FileInterface.dir + File.separator + new SimpleDateFormat("'score'$yyy-MM-dd$hh-mm-ss$'.js'").format(new Date()));

        FileInterface.writeFileFromFile(build, finished.toString());

        System.out.println("---------");
        System.out.println("> FINISHED BUILD");
        System.out.println("> File: " + build.getPath());
        System.out.println("> Pre-minification size (bytes): " + before);
        System.out.println("> Post-minification size (bytes): " + finished.length());

        long endTime = System.currentTimeMillis();

        System.out.println("> Total execution time: " + (endTime - startTime) + "ms");
        System.out.println("---------");
        System.out.println();
    }
}
