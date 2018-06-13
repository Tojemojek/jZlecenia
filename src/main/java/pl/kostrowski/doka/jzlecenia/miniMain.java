package pl.kostrowski.doka.jzlecenia;

import pl.kostrowski.doka.jzlecenia.service.ReadFromExcel;

public class miniMain {

    public static void main(String[] args) {

        ReadFromExcel readFromExcel = new ReadFromExcel();

        try {
            readFromExcel.read();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
