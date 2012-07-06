package edu.ecnu.bugIR.query.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class BM25FParameters {

    private static float B = 0.75f;
    private static float K1 = 2f;

    protected BM25FParameters() {
    };

    /**
     * @return the BM25 length normalization parameter b, generally b =[0,1], by
     *         default is equals to 0.75
     */
    public static float getB() {
        return B;
    }

    /**
     * Set the BM25 length normalization parameter
     * 
     * @param b
     *            the b parameter, generally b =[0,1], by default is equals to
     *            0.75
     */
    public static void setB(float b) {
        B = b;
    }

    /**
     * 
     * @return the k1 parameter, by default is equivalent to 2
     */
    public static float getK1() {
        return K1;
    }

    /**
     * Set the k1 parameter, by default is equivalent to 2
     * 
     * @param k1
     */
    public static void setK1(float k1) {
        K1 = k1;
    }

    protected static Map<Integer, Float> avgLength = new HashMap<Integer, Float>();

    /**
     * Load field average length from a file with the next format: <BR>
     * FIELD_NAME <BR>
     * FLOAT_VALUE <BR>
     * ANOTHER_FIELD_NAME <BR>
     * ANOTHER_FIELD_VALUE<BR>
     * for example:<BR>
     * CONTENT<BR>
     * 459.2903f<BR>
     * ANCHOR<BR>
     * 84.55523f<BR>
     * 
     * @param path
     *            absolute path of the file
     * @throws NumberFormatException
     * @throws IOException
     */
    public static void load(String path) throws NumberFormatException,
            IOException {
        BufferedReader in = new BufferedReader(new FileReader(path));
        String line;
        while (null != (line = in.readLine())) {
            Integer field = new Integer(line);
            Float avg = new Float(in.readLine());
            BM25FParameters.setAverageLength(field, avg);
        }
        in.close();
    }

    /**
     * Set the average length for the field 'field'
     * 
     * @param field
     * @param avg
     */
    public static void setAverageLength(int field, float avg) {
        BM25FParameters.avgLength.put(field, avg);
    }

    /**
     * Return the field 'field' average length
     * 
     * @param field
     * @return field average length
     */
    public static float getAverageLength(String field) {
        try {
            return BM25FParameters.avgLength.get(field);
        } catch (NullPointerException e) {
            System.out
                    .println("Can't find average length for field '"
                            + field
                            + "' you have to set field average length values, before execute a search.");
            throw e;
        }
    }
}
