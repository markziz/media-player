import java.util.ArrayList;

public class Data {
	
	private String name;
	private ArrayList<MultimediaItem> media;
	
	public Data(String newName) {
		this.name = newName;
		this.media = new ArrayList<>();
	}
	
	public void add(MultimediaItem newItem) {
		media.add(newItem);
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<MultimediaItem> getMedia(){
		return media;
	}
}
