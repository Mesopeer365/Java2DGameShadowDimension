/**
 * Represents a WorldObject's ability to inflict damage.
 */
public interface CanAttack {
    /**
     *
     * @return
     * The damage that the object can deal.
     */
    int getDMG();

    /**
     *
     * @return
     * The name of the object in the form of a string.
     */
    String getName();
}
