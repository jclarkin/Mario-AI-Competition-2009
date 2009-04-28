package ai;


/**
 * Interface to a generic <code>Evolvable</code>, one of the objects needed by an
 * EA to run.<br>
 *
 * @see EA
 */
public interface Evolvable {

    /**
     * Produces a new instance of the same <code>Evolvable</code>, all the parameters
     * (e.g. mutation rate, limits etc.) must be copied to the new object.
     * <p/>
     * The <code>EA</code> will produce the initial population adding to this first
     * <code>Evolvable</code> as many others as needed, generated by this method.<br>
     * Any random initialization should therefore be placed here.
     *
     * @return the <code>new Evolvable</code>.
     */
    public Evolvable getNewInstance();

    /**
     * Returns a deep copy (i.e. as deep as possible) of the <code>Evolvable</code>.
     *
     * @return the copy.
     */
    public Evolvable copy();

    /**
     * Resets the <code>Evolvable</code> to its default state (e.g. reset a recurrent network).
     */
    public void reset();

    /**
     * Applies the predefined mutation to the <code>Evolvable</code>.
     */
    public void mutate();

    /**
     * Produces a suitable <code>String</code> representation of the <code>Evolvable</code>.
     *
     * @return the info.
     */
    public String toString();
}
