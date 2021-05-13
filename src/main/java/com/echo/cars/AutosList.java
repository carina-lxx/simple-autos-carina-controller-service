package com.echo.cars;

import java.util.ArrayList;
import java.util.List;

public class AutosList {
    private List<Automobile> automobiles;

    public AutosList() {
        this.automobiles = new ArrayList<>();
    }

    public AutosList(List<Automobile> automobiles) {
        this.automobiles = automobiles;
    }

    public List<Automobile> getAutomobiles() {
        return automobiles;
    }

    public void setAutomobiles(List<Automobile> automobiles) {
        this.automobiles = automobiles;
    }

    public boolean isEmpty(){
        return this.automobiles.isEmpty();
    }

    @Override
    public String toString() {
        return "AutosList{" + "automobiles=" + automobiles + '}';
    }
}
