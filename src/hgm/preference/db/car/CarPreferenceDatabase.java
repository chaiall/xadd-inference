package hgm.preference.db.car;

import hgm.preference.Preference;
import hgm.preference.db.DiscretePreferenceDatabase;
import sve.gibbs.CSVHandler;

import java.util.*;

/**
 * Created by Hadi Afshar.
 * Date: 20/12/13
 * Time: 11:06 AM
 */
public class CarPreferenceDatabase implements DiscretePreferenceDatabase {
    public static final String dbAddress = "./src/hgm/preference/db/car/first/";

    private List<Double[]> items;
    private List<Preference> prefs;

    private Map<Integer /*attribute ID*/, Set<Double> /*discretization levels*/> attribToChoicesMap;

    public static CarPreferenceDatabase fetchCarPreferenceDataBase1stExperiment(Set<Integer> trustedAdviserIds /*IDs start from 0 and max is 59*/) {
          return new CarPreferenceDatabase(dbAddress + "items1.csv", dbAddress + "users1.csv", dbAddress + "prefs1.csv", trustedAdviserIds);
    }

    private CarPreferenceDatabase(String itemsPath, String usersPath, String prefsPath, Set<Integer> trustedAdviserIds /*only preference of a some user should be taken into account */) {
        items = CSVHandler.readcsvDouble(itemsPath);
//        users = CSVHandler.readcsvDouble(usersPath);
        List<Double[]> rawPrefs = CSVHandler.readcsvDouble(prefsPath);

        attribToChoicesMap = makeAttributeDiscretizationMap(items);

        prefs = new ArrayList<Preference>(rawPrefs.size());
        for (Double[] rawPref : rawPrefs) {
            //rawPref[0] is the user. rawPref[1] and [2] are the chosen and un-chosen items resp. with index starting from 1
            Integer adviserId = (int)(double)rawPref[0] - 1;
            if (trustedAdviserIds.contains(adviserId)) {
            prefs.add(new Preference((int)(double)rawPref[1]-1, (int)(double)rawPref[2]-1, Preference.Choice.FIRST)); //always the first item is chosen over the second
            }
        }
    }

    private Map<Integer, Set<Double>> makeAttributeDiscretizationMap(List<Double[]> items) {
        int numAttribs = getNumberOfAttributes();
        Map<Integer, Set<Double>> attribChoices = new HashMap<Integer, Set<Double>>(numAttribs);
        for (Integer attribId = 0; attribId<numAttribs; attribId++) {
            Set<Double> attribValues = new HashSet<Double>();
            for (Double[] item : items) {
                attribValues.add(item[attribId]);
            }
            attribChoices.put(attribId, attribValues);
        }
        return attribChoices;
    }

    @Override
    public int getNumberOfAttributes() {
        return items.get(0).length;
    }

    @Override
    public int getNumberOfItems() {
        return items.size();
    }

    @Override
    public List<Preference> getPreferenceResponses() {
        return prefs;
    }

    @Override
    public Double[] getItemAttributeValues(int itemId) {
        return items.get(itemId);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double[] getAuxiliaryWeightVector() {
        return null;
    }

    public static void main(String[] args) {
        CarPreferenceDatabase db = new CarPreferenceDatabase(dbAddress + "items1.csv", dbAddress + "users1.csv", dbAddress + "prefs1.csv",
                new HashSet<Integer>(Arrays.asList(new Integer[]{0, 1})));
        System.out.println("db.numberOfItems() = " + db.getNumberOfItems());
        for (int i=0; i<db.getNumberOfItems(); i++) {
            System.out.println("db.getItemAttributeValues(" + i + ") = " + Arrays.toString(db.getItemAttributeValues(i)));
        }
        System.out.println("db.getNumberOfAttributes() = " + db.getNumberOfAttributes());
        System.out.println("db.getPreferenceResponses() = " + db.getPreferenceResponses());
        System.out.println("db.getPreferenceResponses().size() = " + db.getPreferenceResponses().size());
    }

    @Override
    public Set<Double> getAttribChoices(Integer attribId) {
        return attribToChoicesMap.get(attribId);
    }
}
