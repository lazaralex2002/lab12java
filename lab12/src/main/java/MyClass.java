import org.testng.annotations.Test;

public class MyClass
{
    @Test
    public static void staticMethod1()
    {
        System.out.println("Method1");
    }

    @Test
    public static void staticMethod2()
    {
        throw new RuntimeException("RunTimeException");
    }


    public static void staticMethod3()
    {

    }

}