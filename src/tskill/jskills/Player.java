package tskill.jskills;

/**
 * Represents a player who has a {@link Rating}.
 */
public class Player<T> implements IPlayer, ISupportPartialPlay, ISupportPartialUpdate {

    /** = 100% play time **/
    private static final double DefaultPartialPlayPercentage = 1.0;

    /** = receive 100% update **/
    private static final double DefaultPartialUpdatePercentage = 1.0;

    /** The identifier for the player, such as a name. **/
    private final T id;

    public T getId() {
        return id;
    }

    public double getPartialPlayPercentage() {
        return partialPlayPercentage;
    }

    public double getPartialUpdatePercentage() {
        return partialUpdatePercentage;
    }

    /**
     * Indicates the percent of the time the player should be weighted where 0.0
     * indicates the player didn't play and 1.0 indicates the player played 100%
     * of the time.
     */
    private final double partialPlayPercentage;

    /**
     * Indicated how much of a skill update a player should receive where 0.0
     * represents no update and 1.0 represents 100% of the update.
     */
    private final double partialUpdatePercentage;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Player<?> other = (Player<?>) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    } 

    /**
     * Constructs a player.
     * 
     * @param id
     *            The identifier for the player, such as a name.
     */
    public Player(T id) {
        this(id, DefaultPartialPlayPercentage, DefaultPartialUpdatePercentage);
    }

    /**
     * Constructs a player.
     * 
     * @param id
     *            The identifier for the player, such as a name.
     * @param partialPlayPercentage
     *            The weight percentage to give this player when calculating a
     *            new rank.
     */
    public Player(T id, double partialPlayPercentage) {
        this(id, partialPlayPercentage, DefaultPartialUpdatePercentage);
    }

    /**
     * Constructs a player.
     * 
     * @param id
     *            The identifier for the player, such as a name.
     * @param partialPlayPercentage
     *            The weight percentage to give this player when calculating a
     *            new rank.
     * @param partialUpdatePercentage
     *            Indicates how much of a skill update a player should receive
     *            where 0 represents no update and 1.0 represents 100% of the
     *            update.
     */
    public Player(T id, double partialPlayPercentage,
            double partialUpdatePercentage) {
        // If they don't want to give a player an id, that's ok...
        Guard.argumentInRangeInclusive(partialPlayPercentage, 0, 1.0,
                "partialPlayPercentage");
        Guard.argumentInRangeInclusive(partialUpdatePercentage, 0, 1.0,
                "partialUpdatePercentage");
        this.id = id;
        this.partialPlayPercentage = partialPlayPercentage;
        this.partialUpdatePercentage = partialUpdatePercentage;
    }

    @Override public String toString() {
        return id != null ? id.toString() : super.toString();
    }
    
    
}