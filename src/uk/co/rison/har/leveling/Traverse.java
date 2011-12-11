package uk.co.rison.har.leveling;

import android.provider.BaseColumns;


public class Traverse {
   
    // This class cannot be instantiated
    private Traverse() {}
    
       public static class TravColumns implements BaseColumns {
        // This class cannot be instantiated
        private TravColumns() {}

        public static String DEFAULT_SORT_ORDER = "modified DESC";
        public static String NAME = "name";
        public static String TYPE = "type";
        public static String OBSERVER = "observer";
        public static String STAFFMAN = "staffman";
        public static String SURVEY_DATE = "created";
        public static String MODIFIED_DATE = "modified";
        
        //Long now = Long.valueOf(System.currentTimeMillis());
    }
}
