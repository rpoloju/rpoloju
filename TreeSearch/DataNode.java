public class DataNode {
	// Data will be of type Float (not float) - this allows null values as well
	private Float data;

	DataNode() {
		data = null;
	}

	public String toString() {
		return data.toString();
	}

	public DataNode(float x) {
		data = x;
	}

	public float getData() {
		return data.floatValue();
	}

	public boolean inOrder(DataNode dnode) {
		return (dnode.getData() <= this.data.floatValue());
	}
}
