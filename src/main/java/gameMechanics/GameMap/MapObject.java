package gameMechanics.GameMap;
import org.json.simple.JSONObject;

public class MapObject {
	public String type = null;
	public String info = null;
	
	public MapObject(String type, String info) {
		this.type = type;
		this.info = info;
	}
	
	public JSONObject toJSON()
	{
		JSONObject obj = new JSONObject();
		obj.put("type", type);
		if(type.equals("player"))
			obj.put("info", Integer.parseInt(info));
		else
			obj.put("info", info);
		
		return obj;
	}
}
