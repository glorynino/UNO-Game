public class Skip extends Card{
    public Skip(String couleur){
        super(-1,couleur,"Skip");
    }

    @Override
    public String toString() {
        return "type : Skip" +
                "couleur"+getCouleur();
    }

}
