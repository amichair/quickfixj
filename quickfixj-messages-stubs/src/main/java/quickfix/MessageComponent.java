package quickfix;

/**
 * Represents a FIX message component.
 */
public abstract class MessageComponent extends FieldMap {

    protected abstract int[] getFields();

    protected abstract int[] getGroupFields();

    protected MessageComponent() {
        super();
    }

    protected MessageComponent(int[] fieldOrder) {
        super(fieldOrder);
    }

}
