package ExceptionHandling;

public class containerNotFoundException extends Exception{
    @Override
    public String toString() {
        return "Container Not Found Exception";
    }
}