package biz.integsys.adf;

import java.util.TreeSet;

/**
 * Created by jshoop on 12/11/15.
 */
public class NDBTreeSet extends TreeSet<NDB> {
    @Override
    public boolean add(NDB object) {
        for (NDB b : this.toArray(new NDB[0])) {
            if (object.address.contentEquals(b.address)) {
                super.remove(b); //replace
            }
        }
        return super.add(object);
    }
}