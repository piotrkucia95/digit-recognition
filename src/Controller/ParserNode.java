package Controller;

/*
 * this code is created by Finn Eggers
 */

import java.util.ArrayList;

public class ParserNode {
	
	private String name;
	private ArrayList<ParserNode> childs = new ArrayList<>();
	private ArrayList<ParserAttribute> attributes = new ArrayList<>();
	
	public ParserNode(String name) {
		this.name = name;
	}
	
	public boolean addAttribute(ParserAttribute att){
		if(att != null){
			if(this.containsAttribute(att)){
				return false;
			}
			attributes.add(att);
			return true;
		}
		return false;
	}
	
	public boolean addAttribute(String att, String value){
		return this.addAttribute(new ParserAttribute(att,value));
	}
	
	public boolean addChild(String name){
		return this.addChild(new ParserNode(name));
	}
	
	public boolean addChild(ParserNode n){
		if(n != null){

			if(this.containsChild(n)){
				return false;
			}
			childs.add(n);
			return true;
		}
		return false;
	}
	
	public ParserNode getChild(String child) {
		for(ParserNode r:childs){
			if(r.getName().equals(child)){
				return r;
			}
		}
		return null;
	}
	
	public ParserAttribute getAttribute(String key) {
		for(ParserAttribute r:attributes){
			if(r.getName().equals(key)){
				return r;
			}
		}
		return null;
	}
	
	public void setAttribute(String att, String value){
		if(this.getAttribute(att) != null){
			this.getAttribute(att).setValue(value);
		}
	}
	
	public boolean removeAttribute(String att){
		return this.removeAttribute(new ParserAttribute(att, ""));
	}
	
	public boolean removeChild(String child){
		return this.removeChild(new ParserNode(child));
	}
	
	public boolean removeAttribute(ParserAttribute att){
		int index = 0;
		for(ParserAttribute r:attributes){
			if(r.equals(att)){
				childs.remove(index);
				return true;
			}index++;
		}
		return false;
	}
	
	public boolean removeChild(ParserNode child) {
		int index = 0;
		for(ParserNode r:childs){
			if(child.equals(r)){
				childs.remove(index);
				return true;
			}index++;
		}
		return false;
	}
	
	public boolean containsAttribute(ParserAttribute s){
		for(ParserAttribute r:attributes){
			if(r.equalAttribute(s)){
				return true;
			}
		}
		return false;
	}

	public boolean containsChild(ParserNode s){
		for(ParserNode r:childs){
			if(s.equals(r)){
				return true;
			}
		}
		return false;
	}
	
	public static ParserNode parse(String c) throws Exception{
		String[] lines = c.split("[;>]");
		ParserNode n = new ParserNode(lines[0].substring(1, lines[0].length()));
	
		int i = 1;
		while(i < lines.length-1){
			String currentLine = lines[i].trim();
			
			String nodeName = "";
			if(currentLine.startsWith("<")){
				nodeName = currentLine.substring(1);
				String batch = "";
				while(!lines[i].trim().startsWith("</"+nodeName)){
					batch += lines[i].trim() + ";";
					i++;
				}
				batch += lines[i].trim() + ";";
				n.addChild(ParserNode.parse(batch));
			}
			else{
				n.addAttribute(ParserAttribute.parse(currentLine));
				
			}i++;
		}
		
		return n;
	}

	public String toParse(int spacesLeft) {
		String res = ParserTools.createSpaces(spacesLeft) + "<" + name + ">" + "\n";
		
		for(ParserAttribute at:attributes){
			res += at.toParse(spacesLeft + 4) + "\n";
		}
		
		for(ParserNode n:childs){
			res += n.toParse(spacesLeft + 4);
		}
		
		res += ParserTools.createSpaces(spacesLeft) + "</" + name + ">"+ "\n";
		return res;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<ParserNode> getChilds(){
		return this.childs;
	}
	
	public boolean equals(Object o){
		if(o.getClass() != this.getClass()){
			return false;
		} if(((ParserNode)o).getName() != this.getName()){
			return false;
		}
		return true;
	}
}
