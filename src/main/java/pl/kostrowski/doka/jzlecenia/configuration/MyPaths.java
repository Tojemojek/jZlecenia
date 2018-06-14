package pl.kostrowski.doka.jzlecenia.configuration;


public enum MyPaths {

    PATH_TO_SAVE_FILES("/media/Dane/IdeaProjects/jZlecenia/pliki/");

    private String path;

    MyPaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}