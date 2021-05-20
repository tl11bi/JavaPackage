package AI.GeneticAlgorithm;

import java.util.Random;

public class GAShared {






    //default selection as 0
    public static String getShreddedDocURL() {
        if(ShreddedDocURL==null) ShreddedDocURL = ShreddedDocURLS[0];
        return ShreddedDocURL;
    }

    //set the url to the selection
    public static void setShreddedDocURL(int selection) {
        ShreddedDocURL = ShreddedDocURLS[selection];
    }

    //get the url to the selection
    public static char[][] getShreddedDocument() {
        if(shreddedDocument==null){
            shreddedDocument = FitnessCalculator.getShreddedDocument(getShreddedDocURL());
            DIMENSION = shreddedDocument.length;
        }
        return shreddedDocument;
    }

    //return the shreddedDocument
    public static void setShreddedDocument(char[][] shreddedDocument) {
        GAShared.shreddedDocument = shreddedDocument;
    }
}
