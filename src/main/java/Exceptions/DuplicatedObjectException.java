package Exceptions;

public class DuplicatedObjectException extends RuntimeException{
    public DuplicatedObjectException(String objName){
        super("Object "+objName+" is already in the DB");
    }
}
