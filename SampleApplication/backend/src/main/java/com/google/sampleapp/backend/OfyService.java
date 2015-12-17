package com.google.sampleapp.backend;

/**
 * Created by Peru on 13-12-2015.
 */
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
public class OfyService {

    static {
        ObjectifyService.register(Quote.class);
        ObjectifyService.register(RegistrationRecord.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
