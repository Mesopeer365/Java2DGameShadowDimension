/**
 * Represents an object's property that allows it to be removed
 */
public interface Removable {
    /**
     * Mark object for removal
     */
    void removeObject();

    /**
     * Checks whether the object is marked for removal
     * @return
     * Boolean for the removal of the object
     */
    boolean isRemoved();
}
