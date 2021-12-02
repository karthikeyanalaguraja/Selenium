package core.utilities.baseUtilities;

import core.utilities.objects.CoreUser;
import core.utilities.objects.UserType;

/**
 * Intentionally package private and only allows local classes to use the base sql class.
 */
class Sql extends BaseSqlManager{
    public Sql(){
        super();
    }

    /**
     * Intentionally not implemented.
     * @param type
     * @return
     */
    @Override
    public CoreUser getUser(UserType type) {
        return null;
    }
}