package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "greendao");
        Entity box = schema.addEntity("Candidate");
        box.addIdProperty().autoincrement();
        box.addStringProperty("name");
        box.addIntProperty("age");
        box.addStringProperty("description");
        new DaoGenerator().generateAll(schema, args[0]);
    }
}
