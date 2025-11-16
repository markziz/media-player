
public class BSTNode {
	
	private BSTNode parent;
	private BSTNode leftChild;
	private BSTNode rightChild;
	private Data data;
	
	public BSTNode() {
		this.parent = null;
		this.leftChild = null;
		this.rightChild = null;
		this.data = null;
	}
	
	public BSTNode(BSTNode newParent, BSTNode newLeftChild, BSTNode newRightChild, Data
			newData){
		this.parent = newParent;
		this.leftChild = newLeftChild;
		this.rightChild = newRightChild;
		this.data = newData;
	}
	
	public BSTNode getParent(){
		return parent;
	}
	
	public BSTNode getLeftChild() {
		return leftChild;
	}
	
	public BSTNode getRightChild() {
		return rightChild;
	}
	
	public Data getData() {
		return data;
	}
	
	public void setParent(BSTNode newParent) {
		this.parent = newParent;
	}
	
	public void setLeftChild(BSTNode newLeftChild) {
		this.leftChild = newLeftChild;
	}
	
	public void setRightChild(BSTNode newRightChild) {
		this.rightChild = newRightChild;
	}
	
	public void setData(Data newData) {
		this.data = newData;
	}
	
	public boolean isLeaf() {
		return leftChild == null && rightChild == null;
	}
}
