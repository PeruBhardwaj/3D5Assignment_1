package com.google.sampleapp.backend;

/**
 * Created by Peru on 14-12-2015.
 */

        import com.googlecode.objectify.annotation.Entity;
        import com.googlecode.objectify.annotation.Id;
        import com.googlecode.objectify.annotation.Index;

/**
 * The Objectify object model for device registrations we are persisting
 */
@Entity
public class RegistrationRecord {

    @Id
    Long id;

    @Index
    private String regId;
    // you can add more fields...
    private String fbId;

    public RegistrationRecord() {
        fbId = null;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
       this.fbId = fbId;
    }
}
