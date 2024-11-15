package it.palex.provaLogin.library.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface BasicGenericService {

    default <T> T getFromOptional(Optional<T> optional) {
        if(optional==null || !optional.isPresent()) {
            return null;
        }

        return optional.get();
    }

    default <T> T getFirstResultFromIterable(Iterable<T> it) {
        if(it==null) {
            return null;
        }

        return this.getFirstResultFromIterator(it.iterator());
    }

    default <T> List<T> iterableToList(Iterable<T> it) {
        if(it==null) {
            throw new NullPointerException();
        }
        return this.iteratorToList(it.iterator());
    }

    default <T> List<T> iteratorToList(Iterator<T> it){
        if(it==null) {
            throw new NullPointerException();
        }
        List<T> res = new ArrayList<>();

        while(it.hasNext()) {
            res.add(it.next());
        }

        return res;
    }

    default <T> T getFirstResultFromIterator(Iterator<T> it) {
        if(it==null) {
            return null;
        }
        if(it.hasNext()) {
            return it.next();
        }
        return null;
    }
}
