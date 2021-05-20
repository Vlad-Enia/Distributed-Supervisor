package Exceptions;

public class ParentKeyException extends RuntimeException{
    public ParentKeyException(String objName){
        super("Parent key for "+objName+" doesn't exist in the DB");
    }
}
