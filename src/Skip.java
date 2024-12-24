package src;
public class Skip extends Card{
    public Skip(String couleur){
        super(couleur,"Skip");
    }

    @Override
    public String toString() {
        return "type : Skip" +
                "couleur"+getCouleur();
    }

}
