import java.util.ArrayList;

public class BSTOrderedDictionary implements BSTOrderedDictionaryADT{

	private BSTNode root;
	private int numInternalNodes;
	
	public BSTOrderedDictionary(){
		this.root = new BSTNode();
		this.numInternalNodes = 0;
	}
	
	public BSTNode getRoot() {
		return root;
	}
	
	public int getNumInternalNodes() {
		return numInternalNodes;
	}
	
	public ArrayList<MultimediaItem> get(BSTNode r, String key){
		if (r.isLeaf()) return null;
		else {
			if(r.getData().getName().equals(key)) return r.getData().getMedia();
			else if(r.getData().getName().compareTo(key) > 0){
				return get(r.getLeftChild(),key);
			}else {
				return get(r.getRightChild(),key);
			}
		}
	}
	
	public void put (BSTNode r, String key, String content, int type) {
		BSTNode p = getNode(r,key);
		MultimediaItem tmp = new MultimediaItem(content,type);
		if (!p.isLeaf()) {
			p.getData().add(tmp);
		}else {
			p.setData(new Data(key));
			p.getData().add(tmp);
			BSTNode leftLeaf = new BSTNode();
			BSTNode rightLeaf = new BSTNode();
			leftLeaf.setParent(p);
			rightLeaf.setParent(p);
			p.setLeftChild(leftLeaf);
			p.setRightChild(rightLeaf);
			numInternalNodes++;
		}
	}
	
	public void remove(BSTNode r, String key) throws DictionaryException{
		BSTNode p = getNode(r,key);
		BSTNode c;
		BSTNode s;
		if (p.isLeaf()) throw new DictionaryException("Key does not match any nodes in tree");
		else {
			if(p.getLeftChild().isLeaf() || p.getRightChild().isLeaf()) {
				if(p.getLeftChild().isLeaf()) c = p.getRightChild();
				else c = p.getLeftChild();
				BSTNode parent = p.getParent();
				if (parent != null) {
					if (parent.getLeftChild() == p) {
		                parent.setLeftChild(c);
		            } else {
		                parent.setRightChild(c);
		            }
		            c.setParent(parent);
				}else {
					root = c;
					c.setParent(null);
				}
				numInternalNodes--;
			}
			else {
				s = getNode(p,smallest(p.getRightChild()).getName());
				p.setData(s.getData());
				remove(s,s.getData().getName());
			}
		}
	}
	
	public void remove(BSTNode r, String key, int type) throws DictionaryException{
		ArrayList<MultimediaItem> p = get(r,key);
		if(p == null) throw new DictionaryException("Key does not match any nodes in tree");
		else {
			for (int i = p.size() - 1; i >= 0; i--) {
				if (p.get(i).getType() == type) p.remove(i);
			}
			if (p.isEmpty()) {
				remove(r,key);
			}
		}
	}
	
	public Data smallest(BSTNode r) {
		if(r.isLeaf()) return null;
		if (r.getLeftChild().isLeaf()) return r.getData();
		else return smallest(r.getLeftChild());
	}
	
	public Data largest(BSTNode r) {
		
		if(r.isLeaf()) return null;
		if (r.getRightChild().isLeaf()) return r.getData();
		else return largest(r.getRightChild());
	}
	
	public Data successor (BSTNode r, String key) {
		BSTNode p = getNode(r,key);
		if (p.isLeaf()) return null;
		
	    if (!p.getRightChild().isLeaf()) {
	        return smallest(p.getRightChild());
	    }
	    
			p = p.getParent();
			while(p!= null && p.getData().getName().compareTo(key) < 0) {
				p = p.getParent();
			}
			if (p == null) return null;
			else return p.getData();
	}
	
	public Data predecessor(BSTNode r, String key) {
	    BSTNode p = getNode(r, key);
	    if (p.isLeaf()) return null;

	    if (!p.getLeftChild().isLeaf()) {
	        return largest(p.getLeftChild());
	    }

	    p = p.getParent();
	    while (p != null && p.getData().getName().compareTo(key) > 0) {
	        p = p.getParent();
	    }

	    if (p == null) return null;
	    else return p.getData();
	}
	
	private BSTNode getNode(BSTNode r, String key) {
		if (r.isLeaf()) return r;
		else {
			if(r.getData().getName().equals(key)) return r;
			else if(r.getData().getName().compareTo(key) > 0){
				return getNode(r.getLeftChild(),key);
			}else {
				return getNode(r.getRightChild(),key);
			}
		}
	}
	
}
