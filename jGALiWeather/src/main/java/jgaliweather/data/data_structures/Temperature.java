package jgaliweather.data.data_structures;

import java.util.ArrayList;

/* Defines a Temperature template, used to store some data about temperature. */
public class Temperature {

    private String clim_eval;
    private String variation_eval;
    private String variability_eval;
    private ArrayList<Integer> mxlist;
    private ArrayList<Integer> mnlist;

    public Temperature(String clim_eval, String variation_eval, String variability_eval, ArrayList<Integer> mxlist, ArrayList<Integer> mnlist) {
        this.clim_eval = clim_eval;
        this.variation_eval = variation_eval;
        this.variability_eval = variability_eval;
        this.mxlist = mxlist;
        this.mnlist = mnlist;
    }

    public String getClim_eval() {
        return clim_eval;
    }

    public void setClim_eval(String clim_eval) {
        this.clim_eval = clim_eval;
    }

    public String getVariation_eval() {
        return variation_eval;
    }

    public void setVariation_eval(String variation_eval) {
        this.variation_eval = variation_eval;
    }

    public String getVariability_eval() {
        return variability_eval;
    }

    public void setVariability_eval(String variability_eval) {
        this.variability_eval = variability_eval;
    }

    public ArrayList<Integer> getMxlist() {
        return mxlist;
    }

    public void setMxlist(ArrayList<Integer> mxlist) {
        this.mxlist = mxlist;
    }

    public ArrayList<Integer> getMnlist() {
        return mnlist;
    }

    public void setMnlist(ArrayList<Integer> mnlist) {
        this.mnlist = mnlist;
    }

}
