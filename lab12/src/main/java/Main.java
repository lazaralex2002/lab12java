import org.testng.annotations.Test;

import java.io.Console;
import java.io.File;
import java.lang.reflect.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        MyClassLoader classLoader = new MyClassLoader();
        int passed = 0;
        int failed = 0;

        Path root = Paths.get(".").normalize().toAbsolutePath();
        String pathForClass = root.toString() + "\\src\\main\\java\\Product.class";
        System.out.println(pathForClass);
        System.out.println("The path for the class: " + pathForClass);


        File pathClass = new File(pathForClass);
        if (pathClass.exists()) {
            URL urlClass = pathClass.toURI().toURL();
            classLoader.addURL(urlClass);
        }


        for(Method method : classLoader.loadClass("MyClass").getMethods()){
            System.out.print(method.getReturnType() + " " + method.getName() + "(");
            boolean first = true;
            for (var i : method.getParameters()) {
                if (first) {
                    System.out.print(i.getType().toGenericString() + " " + i.getName());
                    first = false;
                } else {
                    System.out.print(", " + i.getType().toGenericString() + " " + i.getName());
                }
            }
            System.out.print(");"+"\n");
        }
        System.out.println();
        for (Method method : classLoader.loadClass("MyClass").getMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                try {
                    method.invoke(null);
                    passed++;
                } catch (Throwable throwable)
                {
                    System.out.printf("Test %s failed: %s  %n", method, throwable.getCause());
                    failed++;
                }
            }
        }
        System.out.printf("Passed: %d, Failed %d%n", passed, failed);

    }

}