package edu.tcu.cs.frogcreweventscheduler.system.exception;

/**
 * Thrown when an entity with a given id cannot be found.
 */
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String entityName, Object id) {
        super(String.format("Could not find %s with id %s", entityName, id));
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
