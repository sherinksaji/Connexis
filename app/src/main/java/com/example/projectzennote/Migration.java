package com.example.projectzennote;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;
import io.realm.annotations.RealmModule;

public class Migration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        Long version = oldVersion;
        // DynamicRealm exposes an editable schema
        RealmSchema schema = realm.getSchema();
        // Changes from version 0 to 1: Adding lastName.
        // All properties will be initialized with the default value "".
        if (version == 0L) {
            schema.get("NoteModel")
                    .addField("moodBefore", int.class, FieldAttribute.REQUIRED)
                    .addField("moodAfter", int.class, FieldAttribute.REQUIRED);
            version++;
        }
        if (version == 1L) {
            schema.get("NoteModel")
                    .setRequired("text",true);
            version++;
        }




    }
    @Override
    public int hashCode() {
        return 37;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Migration);
    }


}